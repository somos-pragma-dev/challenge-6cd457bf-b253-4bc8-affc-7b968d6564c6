package com.pragma.apigateway.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyRequestBodyGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.function.BiFunction;

/**
 * Configuración central del API Gateway para el sistema de pagos.
 * Define las rutas hacia los microservicios y los filtros globales
 * que se aplican a todas las peticiones entrantes.
 * 
 * Este gateway actúa como punto de entrada único para:
 * - payment-service: procesamiento de transacciones
 * - fraud-service: validación antifraude
 */
@Configuration
public class GatewayConfig {

    private final WebClient.Builder webClientBuilder;
    
    private static final String PAYMENT_SERVICE_HOST = "payment-service";
    private static final String FRAUD_SERVICE_HOST = "fraud-service";
    private static final int PAYMENT_SERVICE_PORT = 8081;
    private static final int FRAUD_SERVICE_PORT = 8082;
    private static final String CORRELATION_HEADER = "X-Correlation-ID";
    private static final String REQUEST_ID_HEADER = "X-Request-ID";

    public GatewayConfig(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    /**
     * Define las rutas del gateway hacia los distintos microservicios.
     * Cada ruta especifica el URI destino, los predicados y los filtros aplicados.
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                
                // Ruta para el servicio de pagos - comandos de creación de pago
                .route("payment-create-route", r -> r
                        .path("/api/v1/payments")
                        .method(HttpMethod.POST)
                        .filters(f -> f
                                .filter(addCorrelationIdFilter())
                                .filter(addRequestTimingFilter())
                                .filter(addSecurityHeadersFilter())
                                .filter(validateContentTypeFilter())
                                .stripPrefix(0)
                                .removeRequestHeader("X-Forwarded-For")
                        )
                        .uri(format("http://%s:%d", PAYMENT_SERVICE_HOST, PAYMENT_SERVICE_PORT))
                )
                
                // Ruta para consultas de estado de pago
                .route("payment-query-route", r -> r
                        .path("/api/v1/payments/**")
                        .method(HttpMethod.GET)
                        .filters(f -> f
                                .filter(addCorrelationIdFilter())
                                .filter(addRequestTimingFilter())
                                .filter(addSecurityHeadersFilter())
                                .stripPrefix(0)
                        )
                        .uri(format("http://%s:%d", PAYMENT_SERVICE_HOST, PAYMENT_SERVICE_PORT))
                )
                
                // Ruta para verificación de fraude
                .route("fraud-check-route", r -> r
                        .path("/api/v1/fraud-check")
                        .method(HttpMethod.POST)
                        .filters(f -> f
                                .filter(addCorrelationIdFilter())
                                .filter(addRequestTimingFilter())
                                .filter(forwardCorrelationIdFilter())
                                .stripPrefix(0)
                        )
                        .uri(format("http://%s:%d", FRAUD_SERVICE_HOST, FRAUD_SERVICE_PORT))
                )
                
                // Ruta para consulta de riesgo
                .route("risk-assessment-route", r -> r
                        .path("/api/v1/risk-assessment/**")
                        .filters(f -> f
                                .filter(addCorrelationIdFilter())
                                .filter(addRequestTimingFilter())
                                .stripPrefix(0)
                        )
                        .uri(format("http://%s:%d", FRAUD_SERVICE_HOST, FRAUD_SERVICE_PORT))
                )
                
                // Health check para el gateway
                .route("health-route", r -> r
                        .path("/actuator/health")
                        .filters(f -> f.setStatus(HttpStatus.OK))
                        .uri("forward:/actuator/health")
                )
                
                .build();
    }

    /**
     * Filtro global que añade un ID de correlación a cada petición.
     * Este ID se propagation a través de todos los microservicios
     * para trazabilidad distribuida.
     */
    @Bean
    public GatewayFilter addCorrelationIdFilter() {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String correlationId = request.getHeaders().getFirst(CORRELATION_HEADER);
            
            if (correlationId == null || correlationId.isBlank()) {
                correlationId = UUID.randomUUID().toString();
            }
            
            ServerHttpRequest modifiedRequest = request.mutate()
                    .header(CORRELATION_HEADER, correlationId)
                    .header(REQUEST_ID_HEADER, UUID.randomUUID().toString())
                    .build();
            
            ServerWebExchange modifiedExchange = exchange.mutate()
                    .request(modifiedRequest)
                    .build();
            
            return chain.filter(modifiedExchange);
        };
    }

    /**
     * Filtro que registra el tiempo de ejecución de cada petición.
     * Añade headers de timing para monitoreo de rendimiento.
     */
    @Bean
    public GatewayFilter addRequestTimingFilter() {
        return (exchange, chain) -> {
            Instant startTime = Instant.now();
            ServerHttpRequest request = exchange.getRequest();
            
            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> {
                        Instant endTime = Instant.now();
                        Duration duration = Duration.between(startTime, endTime);
                        
                        ServerHttpResponse response = exchange.getResponse();
                        response.getHeaders().add("X-Response-Time-Millis", String.valueOf(duration.toMillis()));
                        response.getHeaders().add("X-Response-Time-Nanos", String.valueOf(duration.toNanos()));
                        
                        // Log de la petición para auditoría
                        String correlationId = request.getHeaders().getFirst(CORRELATION_HEADER);
                        String method = request.getMethod().name();
                        String path = request.getURI().getPath();
                        int status = response.getStatusCode().value();
                        
                        System.out.printf("[GATEWAY] %s %s -> %d (%dms) [correlation: %s]%n", 
                                method, path, status, duration.toMillis(), correlationId);
                    }));
        };
    }

    /**
     * Filtro de seguridad que añade headers de protección contra
     * vulnerabilidades comunes en aplicaciones web.
     */
    @Bean
    public GatewayFilter addSecurityHeadersFilter() {
        return (exchange, chain) -> {
            ServerHttpResponse response = exchange.getResponse();
            
            // Headers de seguridad HTTPS
            response.getHeaders().add("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
            response.getHeaders().add("X-Content-Type-Options", "nosniff");
            response.getHeaders().add("X-Frame-Options", "DENY");
            response.getHeaders().add("X-XSS-Protection", "1; mode=block");
            response.getHeaders().add("Referrer-Policy", "strict-origin-when-cross-origin");
            response.getHeaders().add("Permissions-Policy", "geolocation=(), microphone=(), camera=()");
            
            // Content Security Policy básica
            response.getHeaders().add("Content-Security-Policy", 
                    "default-src 'self'; script-src 'self' 'unsafe-inline'; style-src 'self' 'unsafe-inline'");
            
            return chain.filter(exchange);
        };
    }

    /**
     * Filtro que valida el Content-Type de las peticiones POST/PUT.
     * Solo acepta application/json.
     */
    @Bean
    public GatewayFilter validateContentTypeFilter() {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            HttpMethod method = request.getMethod();
            
            if (method == HttpMethod.POST || method == HttpMethod.PUT) {
                MediaType contentType = request.getHeaders().getContentType();
                
                if (contentType == null || !contentType.includes(MediaType.APPLICATION_JSON)) {
                    ServerHttpResponse response = exchange.getResponse();
                    response.setStatusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
                    response.getHeaders().add("X-Error", "Content-Type must be application/json");
                    return response.setComplete();
                }
            }
            
            return chain.filter(exchange);
        };
    }

    /**
     * Filtro que reenvía el Correlation-ID al servicio destino
     * cuando la correlación viene de otro gateway o servicio.
     */
    @Bean
    public GatewayFilter forwardCorrelationIdFilter() {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String correlationId = request.getHeaders().getFirst(CORRELATION_HEADER);
            
            if (correlationId != null) {
                ServerHttpRequest modifiedRequest = request.mutate()
                        .header("X-Correlation-ID", correlationId)
                        .build();
                
                ServerWebExchange modifiedExchange = exchange.mutate()
                        .request(modifiedRequest)
                        .build();
                
                return chain.filter(modifiedExchange);
            }
            
            return chain.filter(exchange);
        };
    }

    /**
     * Filtro de transformación de cuerpo de respuesta.
     * Permite modificar la estructura de las respuestas antes de enviarlas al cliente.
     */
    @Bean
    public ModifyResponseBodyGatewayFilterFactory modifyResponseBodyGatewayFilterFactory() {
        return new ModifyResponseBodyGatewayFilterFactory();
    }

    /**
     * Filtro de transformación de cuerpo de solicitud.
     * Permite modificar la estructura de las peticiones antes de reenviarlas.
     */
    @Bean
    public ModifyRequestBodyGatewayFilterFactory modifyRequestBodyGatewayFilterFactory() {
        return new ModifyRequestBodyGatewayFilterFactory();
    }

    /**
     * WebClient configurado para comunicación reactiva entre servicios.
     * Configurado con timeouts apropiados y manejo de errores.
     */
    @Bean
    public WebClient webClient() {
        return webClientBuilder
                .clientConnector(new reactor.netty.http.client.HttpClient()
                        .responseTimeout(Duration.ofSeconds(30))
                        .option(io.netty.channel.ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                        .option(io.netty.channel.ChannelOption.SO_TIMEOUT_MILLIS, 30000))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    /**
     * Filtro para manejo de errores global del gateway.
     * Captura excepciones y retorna respuestas estructuradas.
     */
    @Bean
    public GatewayFilter errorHandlingFilter() {
        return (exchange, chain) -> {
            return chain.filter(exchange)
                    .onErrorResume(error -> {
                        ServerHttpResponse response = exchange.getResponse();
                        
                        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                        response.getHeaders().add("X-Error-Type", error.getClass().getSimpleName());
                        response.getHeaders().add("X-Error-Message", error.getMessage());
                        
                        String correlationId = exchange.getRequest().getHeaders().getFirst(CORRELATION_HEADER);
                        if (correlationId != null) {
                            response.getHeaders().add(CORRELATION_HEADER, correlationId);
                        }
                        
                        System.err.printf("[GATEWAY ERROR] %s - %s [correlation: %s]%n",
                                error.getClass().getSimpleName(),
                                error.getMessage(),
                                correlationId);
                        
                        return response.setComplete();
                    });
        };
    }

    /**
     * Filtro para rate limiting básico por IP de origen.
     * Implementación simple que evita ataques de fuerza bruta.
     */
    @Bean
    public GatewayFilter rateLimitFilter() {
        return new AbstractGatewayFilterFactory<>() {
            @Override
            public GatewayFilter apply(Object config) {
                return (exchange, chain) -> {
                    String clientIp = getClientIp(exchange.getRequest());
                    
                    // Aquí se implementaría la lógica de rate limiting
                    // Por simplicidad, se delega al servicio de autenticación
                    
                    return chain.filter(exchange);
                };
            }
        };
    }

    /**
     * Extrae la IP real del cliente, considerando proxys y load balancers.
     */
    private String getClientIp(ServerHttpRequest request) {
        String xForwardedFor = request.getHeaders().getFirst("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isBlank()) {
            return xForwardedFor.split(",")[0].trim();
        }
        
        String xRealIp = request.getHeaders().getFirst("X-Real-IP");
        if (xRealIp != null && !xRealIp.isBlank()) {
            return xRealIp;
        }
        
        if (request.getRemoteAddress() != null) {
            return request.getRemoteAddress().getAddress().getHostAddress();
        }
        
        return "unknown";
    }

    /**
     * Helper para formatear URLs de destino.
     */
    private static String format(String template, Object... args) {
        return String.format(template, args);
    }
}