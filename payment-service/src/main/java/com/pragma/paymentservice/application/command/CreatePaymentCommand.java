package com.pragma.paymentservice.application.command;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class CreatePaymentCommand {
    private final UUID orderId;
    private final String cardNumber;
    private final BigDecimal amount;
    private final String currency;
    private final String paymentMethod;
    private final String customerId;
    private final LocalDateTime requestedAt;
    private final String idempotencyKey;

    public CreatePaymentCommand(UUID orderId, String cardNumber, BigDecimal amount, 
                                 String currency, String paymentMethod, String customerId,
                                 String idempotencyKey) {
        this.orderId = validateOrderId(orderId);
        this.cardNumber = validateCardNumber(cardNumber);
        this.amount = validateAmount(amount);
        this.currency = validateCurrency(currency);
        this.paymentMethod = validatePaymentMethod(paymentMethod);
        this.customerId = validateCustomerId(customerId);
        this.requestedAt = LocalDateTime.now();
        this.idempotencyKey = validateIdempotencyKey(idempotencyKey);
    }

    private UUID validateOrderId(UUID orderId) {
        if (orderId == null) {
            throw new IllegalArgumentException("El identificador del pedido no puede ser nulo");
        }
        return orderId;
    }

    private String validateCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.isBlank()) {
            throw new IllegalArgumentException("El número de tarjeta es obligatorio");
        }
        String cleaned = cardNumber.replaceAll("\\s+", "");
        if (!cleaned.matches("^\\d{13,19}$")) {
            throw new IllegalArgumentException("El número de tarjeta debe contener entre 13 y 19 dígitos");
        }
        return cleaned;
    }

    private BigDecimal validateAmount(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("El monto del pago es obligatorio");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto del pago debe ser mayor que cero");
        }
        if (amount.scale() > 2) {
            throw new IllegalArgumentException("El monto no puede tener más de dos decimales");
        }
        return amount;
    }

    private String validateCurrency(String currency) {
        if (currency == null || currency.isBlank()) {
            return "USD";
        }
        String upperCurrency = currency.toUpperCase();
        if (!upperCurrency.matches("^[A-Z]{3}$")) {
            throw new IllegalArgumentException("La moneda debe ser un código ISO de 3 letras");
        }
        return upperCurrency;
    }

    private String validatePaymentMethod(String paymentMethod) {
        if (paymentMethod == null || paymentMethod.isBlank()) {
            return "CARD";
        }
        String upperMethod = paymentMethod.toUpperCase();
        if (!upperMethod.matches("^(CARD|TOKEN|CRYPTO|WALLET)$")) {
            throw new IllegalArgumentException("Método de pago no soportado");
        }
        return upperMethod;
    }

    private String validateCustomerId(String customerId) {
        if (customerId == null || customerId.isBlank()) {
            throw new IllegalArgumentException("El identificador del cliente es obligatorio");
        }
        return customerId;
    }

    private String validateIdempotencyKey(String idempotencyKey) {
        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            return UUID.randomUUID().toString();
        }
        return idempotencyKey;
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

    public String getCustomerId() {
        return customerId;
    }

    public LocalDateTime getRequestedAt() {
        return requestedAt;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public String getMaskedCardNumber() {
        if (cardNumber.length() <= 4) {
            return "****";
        }
        return "****" + cardNumber.substring(cardNumber.length() - 4);
    }
}