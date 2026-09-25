package com.pragma.fraudservice.infrastructure.messaging;

import com.pragma.fraudservice.application.FraudCheckService;
import com.pragma.fraudservice.domain.model.FraudCheckRequest;
import com.pragma.fraudservice.domain.model.FraudCheckResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Map;
import java.util.HashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class FraudEventListener {

    private final FraudCheckService fraudCheckService;
    private final FraudResultPublisher fraudResultPublisher;

    @KafkaListener(topics = "${app.kafka.topics.payment-created:payment-created}", 
                   groupId = "${spring.kafka.consumer.group-id:fraud-service-group}",
                   containerFactory = "kafkaListenerContainerFactory")
    public void handlePaymentCreatedEvent(Map<String, Object> eventData, Acknowledgment acknowledgment) {
        log.info("Recibido evento de pago creado: {}", eventData);
        
        try {
            UUID paymentId = UUID.fromString((String) eventData.get("paymentId"));
            UUID orderId = UUID.fromString((String) eventData.get("orderId"));
            String cardNumber = (String) eventData.get("cardNumber");
            String customerId = (String) eventData.get("customerId");
            Double amount = ((Number) eventData.get("amount")).doubleValue();
            
            FraudCheckRequest request = FraudCheckRequest.builder()
                    .paymentId(paymentId)
                    .orderId(orderId)
                    .cardNumber(maskCardNumber(cardNumber))
                    .customerId(customerId)
                    .amount(java.math.BigDecimal.valueOf(amount))
                    .build();
            
            fraudCheckService.performFraudCheck(request)
                    .doOnSuccess(result -> {
                        log.info("Resultado de fraude para paymentId {}: {}", 
                                paymentId, result.getStatus());
                        publishFraudCheckResult(result, eventData);
                        acknowledgment.acknowledge();
                    })
                    .doOnError(error -> {
                        log.error("Error procesando evento para paymentId {}: {}", 
                                paymentId, error.getMessage());
                        handleFailure(paymentId, error.getMessage(), eventData);
                        acknowledgment.acknowledge();
                    })
                    .subscribe();
            
        } catch (Exception e) {
            log.error("Error al parsear evento de pago creado: {}", e.getMessage(), e);
            acknowledgment.acknowledge();
        }
    }

    @KafkaListener(topics = "${app.kafka.topics.payment-validation:payment-validation}", 
                   groupId = "${spring.kafka.consumer.group-id:fraud-service-group}",
                   containerFactory = "kafkaListenerContainerFactory")
    public void handlePaymentValidationEvent(Map<String, Object> eventData, Acknowledgment acknowledgment) {
        log.info("Recibido evento de validación de pago: {}", eventData);
        
        try {
            UUID paymentId = UUID.fromString((String) eventData.get("paymentId"));
            String validationType = (String) eventData.get("validationType");
            
            if ("RECHECK".equals(validationType)) {
                performRecheck(paymentId, eventData)
                        .doOnSuccess(result -> {
                            publishFraudCheckResult(result, eventData);
                            acknowledgment.acknowledge();
                        })
                        .doOnError(error -> {
                            log.error("Error en recheck para paymentId {}: {}", paymentId, error.getMessage());
                            acknowledgment.acknowledge();
                        })
                        .subscribe();
            } else {
                acknowledgment.acknowledge();
            }
            
        } catch (Exception e) {
            log.error("Error al parsear evento de validación: {}", e.getMessage(), e);
            acknowledgment.acknowledge();
        }
    }

    private Mono<FraudCheckResult> performRecheck(UUID paymentId, Map<String, Object> eventData) {
        return fraudCheckService.getFraudCheckResult(paymentId)
                .switchIfEmpty(Mono.error(new IllegalStateException("No existe resultado de fraude para recheck: " + paymentId)))
                .flatMap(existingResult -> {
                    log.info("Rechecando resultado existente para paymentId: {}, status: {}", 
                            paymentId, existingResult.getStatus());
                    String cardNumber = maskCardNumber((String) eventData.get("cardNumber"));
                    String customerId = (String) eventData.get("customerId");
                    Double amount = ((Number) eventData.get("amount")).doubleValue();
                    
                    FraudCheckRequest request = FraudCheckRequest.builder()
                            .paymentId(paymentId)
                            .orderId(existingResult.getOrderId())
                            .cardNumber(cardNumber)
                            .customerId(customerId)
                            .amount(java.math.BigDecimal.valueOf(amount))
                            .recheck(true)
                            .previousCheckId(existingResult.getCheckId())
                            .build();
                    
                    return fraudCheckService.performFraudCheck(request);
                });
    }

    private void publishFraudCheckResult(FraudCheckResult result, Map<String, Object> originalEvent) {
        Map<String, Object> fraudEvent = new HashMap<>();
        fraudEvent.put("paymentId", result.getPaymentId().toString());
        fraudEvent.put("orderId", result.getOrderId() != null ? result.getOrderId().toString() : null);
        fraudEvent.put("customerId", result.getCustomerId());
        fraudEvent.put("fraudCheckId", result.getCheckId().toString());
        fraudEvent.put("status", result.getStatus());
        fraudEvent.put("blocked", result.isBlocked());
        fraudEvent.put("reason", result.getReason());
        fraudEvent.put("fraudScore", result.getFraudScore());
        fraudEvent.put("checkedAt", result.getCheckedAt().toString());
        fraudEvent.put("timestamp", LocalDateTime.now().toString());
        fraudEvent.put("originalEvent", originalEvent);
        
        String topic = result.isBlocked() ? 
                "payment-fraud-blocked" : "payment-fraud-approved";
        
        fraudResultPublisher.publishFraudResult(fraudEvent, topic);
        log.info("Publicado resultado de fraude en topic {} para paymentId: {}", 
                topic, result.getPaymentId());
    }

    private void handleFailure(UUID paymentId, String errorMessage, Map<String, Object> eventData) {
        Map<String, Object> failureEvent = new HashMap<>();
        failureEvent.put("paymentId", paymentId.toString());
        failureEvent.put("failureType", "FRAUD_CHECK_FAILURE");
        failureEvent.put("errorMessage", errorMessage);
        failureEvent.put("timestamp", LocalDateTime.now().toString());
        failureEvent.put("originalEvent", eventData);
        
        fraudResultPublisher.publishFraudResult(failureEvent, "payment-fraud-failure");
        log.warn("Publicado evento de fracaso para paymentId: {}", paymentId);
    }

    private String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4) {
            return "****";
        }
        return "****-****-****-" + cardNumber.substring(cardNumber.length() - 4);
    }
}