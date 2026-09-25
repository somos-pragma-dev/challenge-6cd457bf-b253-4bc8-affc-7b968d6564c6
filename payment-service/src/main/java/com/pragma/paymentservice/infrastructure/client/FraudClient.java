package com.pragma.paymentservice.infrastructure.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Component
public class FraudClient {

    private final WebClient webClient;
    private final String fraudServiceBaseUrl;

    public FraudClient(
            WebClient webClient,
            @Value("${external.services.fraud-service.url:http://localhost:8082}") String fraudServiceBaseUrl) {
        this.webClient = webClient;
        this.fraudServiceBaseUrl = fraudServiceBaseUrl;
    }

    public Mono<FraudCheckResponse> checkFraud(UUID paymentId, String cardNumber, BigDecimal amount) {
        FraudCheckRequest request = new FraudCheckRequest(paymentId, cardNumber, amount);
        
        return webClient
                .post()
                .uri(fraudServiceBaseUrl + "/api/v1/fraud/check")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(FraudCheckResponse.class)
                .timeout(java.time.Duration.ofSeconds(5))
                .onErrorResume(e -> Mono.just(new FraudCheckResponse("ERROR", "Service unavailable: " + e.getMessage())));
    }

    public record FraudCheckRequest(UUID paymentId, String cardNumber, BigDecimal amount) {}

    public record FraudCheckResponse(String status, String riskLevel) {
        public boolean isApproved() {
            return "APPROVED".equalsIgnoreCase(status) || "LOW_RISK".equalsIgnoreCase(riskLevel);
        }
    }
}