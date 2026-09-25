package com.pragma.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@SpringBootApplication
public class ApiGatewayApplication {

    private static final String PAYMENT_SERVICE_HOST = "localhost";
    private static final int PAYMENT_SERVICE_PORT = 8080;
    private static final String FRAUD_SERVICE_HOST = "localhost";
    private static final int FRAUD_SERVICE_PORT = 8081;

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://" + PAYMENT_SERVICE_HOST + ":" + PAYMENT_SERVICE_PORT)
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(16 * 1024 * 1024))
                .build();
    }

    @Bean
    public Scheduler boundedElasticScheduler() {
        return Schedulers.boundedElastic();
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("payment-service", r -> r
                        .path("/api/payments/**")
                        .filters(f -> f
                                .stripPrefix(1)
                                .addRequestHeader("X-Gateway", "api-gateway")
                                .addResponseHeader("X-Response-Time", "${executionTime}")
                                .circuitBreaker(config -> config
                                        .setName("paymentCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/payments")))
                        .uri("http://" + PAYMENT_SERVICE_HOST + ":" + PAYMENT_SERVICE_PORT))
                .route("fraud-service", r -> r
                        .path("/api/fraud/**")
                        .filters(f -> f
                                .stripPrefix(1)
                                .addRequestHeader("X-Gateway", "api-gateway")
                                .addResponseHeader("X-Response-Time", "${executionTime}")
                                .circuitBreaker(config -> config
                                        .setName("fraudCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/fraud")))
                        .uri("http://" + FRAUD_SERVICE_HOST + ":" + FRAUD_SERVICE_PORT))
                .route("health-check", r -> r
                        .path("/health")
                        .filters(f -> f
                                .setPath("/actuator/health"))
                        .uri("http://" + PAYMENT_SERVICE_HOST + ":" + PAYMENT_SERVICE_PORT))
                .route("actuator", r -> r
                        .path("/actuator/**")
                        .uri("http://" + PAYMENT_SERVICE_HOST + ":" + PAYMENT_SERVICE_PORT))
                .build();
    }
}