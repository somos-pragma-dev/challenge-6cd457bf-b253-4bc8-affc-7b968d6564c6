package com.pragma.paymentservice.application.query;


import com.pragma.paymentservice.domain.model.PaymentStatus;
import com.pragma.paymentservice.domain.model.PaymentAggregate;
import com.pragma.paymentservice.infrastructure.persistence.EventStore;
import com.pragma.paymentservice.infrastructure.persistence.PaymentProjectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class QueryHandler {

    private final PaymentProjectionRepository projectionRepository;
    private final EventStore eventStore;

    public Mono<PaymentAggregate> getPaymentById(UUID paymentId) {
        return eventStore.readEvents(paymentId)
                .collectList()
                .filter(events -> !events.isEmpty())
                .flatMap(events -> {
                    PaymentAggregate aggregate = new PaymentAggregate(
                            paymentId,
                            UUID.fromString("00000000-0000-0000-0000-000000000000"),
                            "",
                            java.math.BigDecimal.ZERO
                    );
                    return Flux.fromIterable(events)
                            .flatMap(aggregate::applyEvent)
                            .last(aggregate);
                });
    }

    public Mono<PaymentQuery.PaymentSummary> getPaymentSummary(UUID paymentId) {
        return projectionRepository.findByPaymentId(paymentId)
                .map(entity -> new PaymentQuery.PaymentSummary(
                        entity.getPaymentId(),
                        entity.getOrderId(),
                        entity.getAmount(),
                        entity.getStatus().name(),
                        entity.getCreatedAt(),
                        entity.getUpdatedAt()
                ));
    }

    public Flux<PaymentQuery.PaymentListItem> getPaymentsByOrderId(UUID orderId) {
        return projectionRepository.findByOrderId(orderId)
                .map(entity -> new PaymentQuery.PaymentListItem(
                        entity.getPaymentId(),
                        entity.getAmount(),
                        entity.getStatus().name(),
                        entity.getCreatedAt()
                ));
    }

    public Flux<PaymentQuery.PaymentListItem> getPaymentsByStatus(String status) {
        return projectionRepository.findByStatus(com.pragma.paymentservice.domain.model.PaymentAggregate.PaymentStatus.valueOf(status))
                .map(entity -> new PaymentQuery.PaymentListItem(
                        entity.getPaymentId(),
                        entity.getAmount(),
                        entity.getStatus().name(),
                        entity.getCreatedAt()
                ));
    }

    public Mono<Long> countPaymentsByStatus(String status) {
        return projectionRepository.countByStatus(com.pragma.paymentservice.domain.model.PaymentAggregate.PaymentStatus.valueOf(status));
    }

    public Mono<PaymentQuery.PaymentDetails> getPaymentDetails(UUID paymentId) {
        return projectionRepository.findByPaymentId(paymentId)
                .map(entity -> new PaymentQuery.PaymentDetails(
                        entity.getPaymentId(),
                        entity.getOrderId(),
                        maskCardNumber(entity.getCardNumber()),
                        entity.getAmount(),
                        entity.getStatus().name(),
                        entity.getFraudCheckStatus(),
                        entity.getRiskAssessmentStatus(),
                        entity.getCreatedAt(),
                        entity.getUpdatedAt()
                ));
    }

    public Flux<PaymentQuery.PaymentListItem> getRecentPayments(int limit) {
        return projectionRepository.findTopByOrderByCreatedAtDesc(limit)
                .map(entity -> new PaymentQuery.PaymentListItem(
                        entity.getPaymentId(),
                        entity.getAmount(),
                        entity.getStatus().name(),
                        entity.getCreatedAt()
                ));
    }

    public Mono<PaymentQuery.PaymentStats> getPaymentStats() {
        return projectionRepository.countAll()
                .flatMap(total -> projectionRepository
                        .countByStatus(com.pragma.paymentservice.domain.model.PaymentAggregate.PaymentStatus.AUTHORIZED)
                        .map(authorized -> new PaymentQuery.PaymentStats(
                                total,
                                authorized,
                                total - authorized
                        )));
    }

    private String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4) {
            return "****";
        }
        return "****-****-****-" + cardNumber.substring(cardNumber.length() - 4);
    }
}