package com.pragma.paymentservice.domain.model;

import com.pragma.paymentservice.domain.event.PaymentEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PaymentAggregate {
    private UUID paymentId;
    private UUID orderId;
    private String cardNumber;
    private BigDecimal amount;
    private PaymentStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<PaymentEvent> events;

    public enum PaymentStatus {
        PENDING,
        AUTHORIZED,
        FAILED,
        SETTLED
    }

    public PaymentAggregate(UUID paymentId, UUID orderId, String cardNumber, BigDecimal amount) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.cardNumber = validateCardNumber(cardNumber);
        this.amount = validateAmount(amount);
        this.status = PaymentStatus.PENDING;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.events = new ArrayList<>();
    }

    private String validateCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 13 || cardNumber.length() > 19) {
            throw new IllegalArgumentException("Número de tarjeta inválido");
        }
        return cardNumber;
    }

    private BigDecimal validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Monto debe ser positivo");
        }
        return amount;
    }

    public Mono<PaymentAggregate> applyEvent(PaymentEvent event) {
        return Mono.just(this)
                .flatMap(aggregate -> {
                    switch (event.getEventType()) {
                        case PAYMENT_CREATED:
                            this.status = PaymentStatus.PENDING;
                            break;
                        case PAYMENT_AUTHORIZED:
                            this.status = PaymentStatus.AUTHORIZED;
                            break;
                        case PAYMENT_FAILED:
                            this.status = PaymentStatus.FAILED;
                            break;
                        case PAYMENT_SETTLED:
                            this.status = PaymentStatus.SETTLED;
                            break;
                        default:
                            return Mono.error(new IllegalArgumentException("Tipo de evento desconocido"));
                    }
                    this.updatedAt = LocalDateTime.now();
                    this.events.add(event);
                    return Mono.just(this.toBuilder().build());
                });
    }

    public Flux<PaymentEvent> getUncommittedEvents() {
        return Flux.fromIterable(this.events);
    }

    public Mono<PaymentAggregate> clearUncommittedEvents() {
        return Mono.just(this.toBuilder()
                .events(new ArrayList<>())
                .build());
    }
}