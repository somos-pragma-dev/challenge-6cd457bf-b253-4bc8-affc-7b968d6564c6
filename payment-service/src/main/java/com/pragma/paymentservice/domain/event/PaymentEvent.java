package com.pragma.paymentservice.domain.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public abstract class PaymentEvent {
    private UUID eventId;
    private UUID paymentId;
    private LocalDateTime eventTime;
    private EventType eventType;

    public enum EventType {
        PAYMENT_CREATED,
        PAYMENT_AUTHORIZED,
        PAYMENT_FAILED,
        PAYMENT_SETTLED
    }
}