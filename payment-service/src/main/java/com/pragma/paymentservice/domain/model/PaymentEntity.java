package com.pragma.paymentservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "payments")
public class PaymentEntity {
    @Id
    private UUID paymentId;
    private UUID orderId;
    private String cardNumber;
    private BigDecimal amount;
    private PaymentAggregate.PaymentStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String fraudCheckStatus;
    private String riskAssessmentStatus;

    public PaymentEntity(UUID paymentId, UUID orderId, String cardNumber, BigDecimal amount) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.cardNumber = validateCardNumber(cardNumber);
        this.amount = validateAmount(amount);
        this.status = PaymentAggregate.PaymentStatus.PENDING;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.fraudCheckStatus = "PENDING";
        this.riskAssessmentStatus = "PENDING";
    }

    private String validateCardNumber(String cardNumber) {
        if (cardNumber == null || !cardNumber.matches("^\\d{13,19}$")) {
            throw new IllegalArgumentException("Número de tarjeta debe contener entre 13 y 19 dígitos");
        }
        return cardNumber;
    }

    private BigDecimal validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero");
        }
        return amount;
    }

    public void authorizePayment() {
        if (this.status != PaymentAggregate.PaymentStatus.PENDING) {
            throw new IllegalStateException("El pago solo puede ser autorizado si está en estado PENDING");
        }
        this.status = PaymentAggregate.PaymentStatus.AUTHORIZED;
        this.updatedAt = LocalDateTime.now();
    }

    public void failPayment() {
        this.status = PaymentAggregate.PaymentStatus.FAILED;
        this.updatedAt = LocalDateTime.now();
    }

    public void settlePayment() {
        if (this.status != PaymentAggregate.PaymentStatus.AUTHORIZED) {
            throw new IllegalStateException("El pago solo puede ser liquidado si está autorizado");
        }
        this.status = PaymentAggregate.PaymentStatus.SETTLED;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateFraudCheckStatus(String status) {
        if (!List.of("PENDING", "APPROVED", "REJECTED").contains(status)) {
            throw new IllegalArgumentException("Estado de fraude inválido");
        }
        this.fraudCheckStatus = status;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateRiskAssessmentStatus(String status) {
        if (!List.of("PENDING", "LOW", "MEDIUM", "HIGH").contains(status)) {
            throw new IllegalArgumentException("Estado de riesgo inválido");
        }
        this.riskAssessmentStatus = status;
        this.updatedAt = LocalDateTime.now();
    }
}