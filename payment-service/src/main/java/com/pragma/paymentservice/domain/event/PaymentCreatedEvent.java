package com.pragma.paymentservice.domain.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class PaymentCreatedEvent extends PaymentEvent {
    private final UUID orderId;
    private final String cardNumber;
    private final BigDecimal amount;
    private final String currency;
    private final String paymentMethod;
    private final String merchantId;

    public PaymentCreatedEvent(
            UUID eventId,
            UUID paymentId,
            LocalDateTime eventTime,
            UUID orderId,
            String cardNumber,
            BigDecimal amount,
            String currency,
            String paymentMethod,
            String merchantId) {
        super(eventId, paymentId, eventTime, EventType.CREATED);
        this.orderId = orderId;
        this.cardNumber = cardNumber;
        this.amount = amount;
        this.currency = currency;
        this.paymentMethod = paymentMethod;
        this.merchantId = merchantId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public static PaymentCreatedEvent create(UUID paymentId, UUID orderId, String cardNumber, 
                                               BigDecimal amount, String currency, 
                                               String paymentMethod, String merchantId) {
        return new PaymentCreatedEvent(
            UUID.randomUUID(),
            paymentId,
            LocalDateTime.now(),
            orderId,
            cardNumber,
            amount,
            currency,
            paymentMethod,
            merchantId
        );
    }

    @Override
    public String toString() {
        return "PaymentCreatedEvent{" +
                "eventId=" + getEventId() +
                ", paymentId=" + getPaymentId() +
                ", eventTime=" + getEventTime() +
                ", orderId=" + orderId +
                ", cardNumber='****' + (cardNumber != null ? cardNumber.substring(Math.max(0, cardNumber.length() - 4)) : "") + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", merchantId='" + merchantId + '\'' +
                '}';
    }
}