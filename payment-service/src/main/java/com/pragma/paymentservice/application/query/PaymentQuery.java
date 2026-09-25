package com.pragma.paymentservice.application.query;


import com.pragma.paymentservice.domain.event.PaymentEvent;
import com.pragma.paymentservice.domain.model.PaymentAggregate;
import com.pragma.paymentservice.domain.model.PaymentAggregate.PaymentStatus;
import com.pragma.paymentservice.infrastructure.persistence.EventStore;
import com.pragma.paymentservice.infrastructure.persistence.PaymentProjectionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class PaymentQuery {
    private static final Logger log = LoggerFactory.getLogger(PaymentQuery.class);

    private final EventStore eventStore;
    private final PaymentProjectionRepository projectionRepository;

    public PaymentQuery(EventStore eventStore, PaymentProjectionRepository projectionRepository) {
        this.eventStore = eventStore;
        this.projectionRepository = projectionRepository;
    }

    public Mono<PaymentQueryResult> getPaymentById(UUID paymentId) {
        log.debug("Consultando pago por ID: {}", paymentId);
        
        return projectionRepository.findByPaymentId(paymentId)
            .flatMap(projection -> {
                log.debug("Proyección encontrada para el pago: {}", paymentId);
                return Mono.just(mapToQueryResult(projection));
            })
            .switchIfEmpty(Mono.defer(() -> {
                log.debug("No se encontró proyección, reconstruyendo desde eventos para: {}", 
                          paymentId);
                return eventStore.findByPaymentId(paymentId)
                    .collectList()
                    .flatMap(events -> {
                        if (events.isEmpty()) {
                            log.warn("No se encontró ningún evento para el pago: {}", paymentId);
                            return Mono.empty();
                        }
                        return rebuildAggregateFromEvents(paymentId, events);
                    });
            }));
    }

    public Mono<PaymentQueryResult> getPaymentByOrderId(UUID orderId) {
        log.debug("Consultando pago por ID de pedido: {}", orderId);
        
        return projectionRepository.findByOrderId(orderId)
            .flatMap(projection -> {
                log.debug("Proyección encontrada para el pedido: {}", orderId);
                return Mono.just(mapToQueryResult(projection));
            })
            .switchIfEmpty(Mono.defer(() -> {
                log.debug("No se encontró proyección por orderId, buscando en eventos: {}", orderId);
                return eventStore.findByPaymentId(orderId)
                    .collectList()
                    .flatMap(events -> {
                        if (events.isEmpty()) {
                            log.warn("No se encontró ningún evento para el pedido: {}", orderId);
                            return Mono.empty();
                        }
                        return rebuildAggregateFromEvents(orderId, events);
                    });
            }));
    }

    public Flux<PaymentQueryResult> getPaymentsByStatus(PaymentStatus status) {
        log.debug("Consultando pagos con estado: {}", status);
        
        return projectionRepository.findByStatus(status.name())
            .flatMap(projection -> {
                PaymentQueryResult result = mapToQueryResult(projection);
                return Flux.just(result);
            })
            .switchIfEmpty(Flux.empty());
    }

    public Flux<PaymentQueryResult> getPaymentsByCustomer(String customerId) {
        log.debug("Consultando pagos para el cliente: {}", customerId);
        
        return projectionRepository.findByCustomerId(customerId)
            .flatMap(projection -> {
                PaymentQueryResult result = mapToQueryResult(projection);
                return Flux.just(result);
            })
            .switchIfEmpty(Flux.empty());
    }

    private Mono<PaymentQueryResult> rebuildAggregateFromEvents(UUID paymentId, 
                                                                  java.util.List<com.pragma.paymentservice.domain.event.PaymentEvent> events) {
        log.info("Reconstruyendo agregado desde {} eventos para el pago: {}", 
                 events.size(), paymentId);
        
        if (events.isEmpty()) {
            return Mono.empty();
        }

        com.pragma.paymentservice.domain.event.PaymentEvent firstEvent = events.get(0);
        PaymentAggregate aggregate = new PaymentAggregate(
            paymentId,
            firstEvent.getPaymentId(),
            "",
            java.math.BigDecimal.ZERO
        );

        return Flux.fromIterable(events)
            .flatMap(event -> aggregate.applyEvent(event))
            .last()
            .map(agg -> new PaymentQueryResult(
                paymentId,
                agg.getOrderId(),
                agg.getStatus(),
                agg.getAmount(),
                agg.getCreatedAt(),
                agg.getUpdatedAt(),
                "Reconstruido desde Event Store",
                true
            ));
    }

    private PaymentQueryResult mapToQueryResult(Object projection) {
        try {
            var method = projection.getClass().getMethod("getPaymentId");
            UUID paymentId = (UUID) method.invoke(projection);
            
            method = projection.getClass().getMethod("getOrderId");
            UUID orderId = (UUID) method.invoke(projection);
            
            method = projection.getClass().getMethod("getStatus");
            String statusStr = (String) method.invoke(projection);
            PaymentStatus status = PaymentStatus.valueOf(statusStr);
            
            method = projection.getClass().getMethod("getAmount");
            java.math.BigDecimal amount = (java.math.BigDecimal) method.invoke(projection);
            
            LocalDateTime createdAt = null;
            LocalDateTime updatedAt = null;
            
            try {
                method = projection.getClass().getMethod("getCreatedAt");
                createdAt = (LocalDateTime) method.invoke(projection);
            } catch (NoSuchMethodException e) {
                // Campo opcional
            }
            
            try {
                method = projection.getClass().getMethod("getUpdatedAt");
                updatedAt = (LocalDateTime) method.invoke(projection);
            } catch (NoSuchMethodException e) {
                // Campo opcional
            }

            return new PaymentQueryResult(
                paymentId,
                orderId,
                status,
                amount,
                createdAt,
                updatedAt,
                "Desde proyección",
                false
            );
        } catch (Exception e) {
            log.error("Error al mapear la proyección: {}", e.getMessage());
            throw new RuntimeException("Error al procesar la consulta de pago", e);
        }
    }

    public record PaymentQueryResult(
        UUID paymentId,
        UUID orderId,
        PaymentStatus status,
        java.math.BigDecimal amount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String source,
        boolean isReconstructed
    ) {
        public String getStatusDescription() {
            return switch (status) {
                case PENDING -> "Pago en proceso de validación";
                case AUTHORIZED -> "Pago autorizado";
                case FAILED -> "Pago fallido";
                case SETTLED -> "Pago liquidado";
                case CANCELLED -> "Pago cancelado";
            };
        }

        public boolean isFinalState() {
            return status == PaymentStatus.SETTLED || 
                   status == PaymentStatus.FAILED || 
                   status == PaymentStatus.CANCELLED;
        }
    }
}