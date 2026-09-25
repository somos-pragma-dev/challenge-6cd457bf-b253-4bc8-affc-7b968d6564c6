package com.pragma.fraudservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.BoundedElasticScheduler;

import java.util.concurrent.TimeUnit;

/**
 * Punto de entrada del microservicio de detección de fraude.
 * Configura el cliente Web para comunicación con servicios externos
 * y el scheduler para operaciones bloqueantes.
 */
@SpringBootApplication
public class FraudServiceApplication {

    private static final int BOUNDED_ELASTIC_THREADS = 100;
    private static final int BOUNDED_ELASTIC_QUEUE_SIZE = 1000;

    private final WebClient webClient;
    private final Scheduler boundedElasticScheduler;

    public FraudServiceApplication(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("http://localhost:8080")
                .filter((request, next) -> {
                    return next.exchange(request);
                })
                .build();
        
        this.boundedElasticScheduler = BoundedElasticScheduler.create(
                BOUNDED_ELASTIC_THREADS,
                BOUNDED_ELASTIC_QUEUE_SIZE,
                true,
                "fraud-bounded"
        );
    }

    public static void main(String[] args) {
        SpringApplication.run(FraudServiceApplication.class, args);
    }

    public WebClient webClient() {
        return this.webClient;
    }

    public Scheduler boundedElasticScheduler() {
        return this.boundedElasticScheduler;
    }

    /**
     * Calcula el nivel de riesgo basado en múltiples factores.
     * @param amount monto de la transacción
     * @param cardNumber número de tarjeta
     * @param customerId identificador del cliente
     * @return nivel de riesgo entre 0.0 y 1.0
     */
    public double calculateRiskLevel(java.math.BigDecimal amount, String cardNumber, String customerId) {
        double baseRisk = 0.1;
        
        if (amount.compareTo(new java.math.BigDecimal("10000")) > 0) {
            baseRisk += 0.3;
        } else if (amount.compareTo(new java.math.BigDecimal("5000")) > 0) {
            baseRisk += 0.15;
        }
        
        if (cardNumber != null && cardNumber.startsWith("4111")) {
            baseRisk += 0.1;
        }
        
        int customerIdHash = customerId != null ? customerId.hashCode() : 0;
        if (customerIdHash % 10 == 0) {
            baseRisk += 0.2;
        }
        
        return Math.min(baseRisk, 1.0);
    }

    /**
     * Determina si una transacción debe ser marcada como sospechosa.
     * @param riskLevel nivel de riesgo calculado
     * @param velocity número de transacciones recientes
     * @return true si la transacción es sospechosa
     */
    public boolean isSuspicious(double riskLevel, int velocity) {
        if (riskLevel > 0.7) {
            return true;
        }
        if (velocity > 10 && riskLevel > 0.4) {
            return true;
        }
        return velocity > 20;
    }

    /**
     * Genera un veredicto de fraude basado en el análisis completo.
     * @param riskLevel nivel de riesgo
     * @param isSuspicious indicadores de sospecha
     * @return veredicto de fraude
     */
    public FraudVerdict generateFraudVerdict(double riskLevel, boolean isSuspicious) {
        if (riskLevel > 0.8 || isSuspicious) {
            return FraudVerdict.REJECT;
        } else if (riskLevel > 0.5) {
            return FraudVerdict.REVIEW;
        } else {
            return FraudVerdict.APPROVE;
        }
    }

    /**
     * Enum que representa los posibles veredictos del análisis de fraude.
     */
    public enum FraudVerdict {
        APPROVE(0, "Transacción aprobada"),
        REVIEW(1, "Requiere revisión manual"),
        REJECT(2, "Transacción rechazada por riesgo de fraude");

        private final int code;
        private final String description;

        FraudVerdict(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public int getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }
    }
}