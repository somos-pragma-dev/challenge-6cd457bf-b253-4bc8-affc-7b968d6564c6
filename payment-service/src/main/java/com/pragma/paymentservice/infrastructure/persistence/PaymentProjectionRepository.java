package com.pragma.paymentservice.infrastructure.persistence;

import com.pragma.paymentservice.domain.model.PaymentAggregate.PaymentStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Repository
public class PaymentProjectionRepository {
    private static final Logger log = LoggerFactory.getLogger(PaymentProjectionRepository.class);
    private static final String COLLECTION_NAME = "payment_projections";

    private final ReactiveMongoTemplate mongoTemplate;

    public PaymentProjectionRepository(ReactiveMongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Mono<Boolean> savePaymentProjection(UUID paymentId, UUID orderId, String cardNumber,
                                                java.math.BigDecimal amount, PaymentStatus status,
                                                LocalDateTime createdAt, LocalDateTime updatedAt,
                                                Map<String, String> metadata) {
        log.debug("Saving projection for payment: {}", paymentId);
        
        return Mono.defer(() -> {
            PaymentProjection projection = new PaymentProjection(
                paymentId, orderId, maskCardNumber(cardNumber), amount, status,
                createdAt, updatedAt, metadata
            );
            return mongoTemplate.save(projection, COLLECTION_NAME)
                .map(saved -> {
                    log.debug("Projection saved successfully: {}", saved.getPaymentId());
                    return true;
                })
                .onErrorResume(error -> {
                    log.error("Failed to save projection: {}", error.getMessage());
                    return Mono.just(false);
                });
        });
    }

    public Mono<PaymentProjection> findByPaymentId(UUID paymentId) {
        log.debug("Finding projection by paymentId: {}", paymentId);
        
        Query query = new Query(Criteria.where("paymentId").is(paymentId));
        return mongoTemplate.findOne(query, PaymentProjection.class, COLLECTION_NAME)
            .doOnSuccess(projection -> {
                if (projection != null) {
                    log.debug("Projection found: {}", paymentId);
                } else {
                    log.debug("No projection found for: {}", paymentId);
                }
            });
    }

    public Flux<PaymentProjection> findByOrderId(UUID orderId) {
        log.debug("Finding projections by orderId: {}", orderId);
        
        Query query = new Query(Criteria.where("orderId").is(orderId));
        return mongoTemplate.find(query, PaymentProjection.class, COLLECTION_NAME);
    }

    public Flux<PaymentProjection> findByStatus(PaymentStatus status) {
        log.debug("Finding projections by status: {}", status);
        
        Query query = new Query(Criteria.where("status").is(status.name()));
        return mongoTemplate.find(query, PaymentProjection.class, COLLECTION_NAME);
    }

    public Flux<PaymentProjection> findByMerchantId(String merchantId) {
        log.debug("Finding projections by merchantId: {}", merchantId);
        
        Query query = new Query(Criteria.where("merchantId").is(merchantId));
        return mongoTemplate.find(query, PaymentProjection.class, COLLECTION_NAME);
    }

    public Flux<PaymentProjection> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        log.debug("Finding projections by date range: {} to {}", startDate, endDate);
        
        Query query = new Query(
            Criteria.where("createdAt").gte(startDate).lte(endDate)
        );
        return mongoTemplate.find(query, PaymentProjection.class, COLLECTION_NAME);
    }

    public Mono<Long> countByStatus(PaymentStatus status) {
        log.debug("Counting projections by status: {}", status);
        
        Query query = new Query(Criteria.where("status").is(status.name()));
        return mongoTemplate.count(query, PaymentProjection.class, COLLECTION_NAME);
    }

    public Mono<Boolean> updatePaymentStatus(UUID paymentId, PaymentStatus newStatus) {
        log.debug("Updating payment status in projection: {} to {}", paymentId, newStatus);
        
        Query query = new Query(Criteria.where("paymentId").is(paymentId));
        org.springframework.data.mongodb.core.Update update = new org.springframework.data.mongodb.core.Update()
            .set("status", newStatus.name())
            .set("updatedAt", LocalDateTime.now());
        
        return mongoTemplate.updateFirst(query, update, COLLECTION_NAME)
            .map(result -> result.getModifiedCount() > 0);
    }

    public Mono<Boolean> deleteProjection(UUID paymentId) {
        log.debug("Deleting projection: {}", paymentId);
        
        Query query = new Query(Criteria.where("paymentId").is(paymentId));
        return mongoTemplate.remove(query, COLLECTION_NAME)
            .map(result -> result.getDeletedCount() > 0);
    }

    public Flux<PaymentProjection> findRecentPayments(int limit) {
        log.debug("Finding recent payments, limit: {}", limit);
        
        Query query = new Query()
            .limit(limit)
            .addCriteria(new org.springframework.data.domain.Sort(
                org.springframework.data.domain.Sort.Direction.DESC, "createdAt"));
        return mongoTemplate.find(query, PaymentProjection.class, COLLECTION_NAME);
    }

    public Mono<java.math.BigDecimal> sumAmountByStatus(PaymentStatus status) {
        log.debug("Summing amounts by status: {}", status);
        
        Query query = new Query(Criteria.where("status").is(status.name()));
        return mongoTemplate.find(query, PaymentProjection.class, COLLECTION_NAME)
            .reduce(java.math.BigDecimal.ZERO, (acc, proj) -> acc.add(proj.getAmount()));
    }

    private String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4) {
            return "****";
        }
        return "****" + cardNumber.substring(cardNumber.length() - 4);
    }

    public static class PaymentProjection {
        private UUID paymentId;
        private UUID orderId;
        private String maskedCardNumber;
        private java.math.BigDecimal amount;
        private String status;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Map<String, String> metadata;
        private String merchantId;

        public PaymentProjection() {}

        public PaymentProjection(UUID paymentId, UUID orderId, String maskedCardNumber,
                                  java.math.BigDecimal amount, PaymentStatus status,
                                  LocalDateTime createdAt, LocalDateTime updatedAt,
                                  Map<String, String> metadata) {
            this.paymentId = paymentId;
            this.orderId = orderId;
            this.maskedCardNumber = maskedCardNumber;
            this.amount = amount;
            this.status = status.name();
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.metadata = metadata;
            if (metadata != null) {
                this.merchantId = metadata.get("merchantId");
            }
        }

        public UUID getPaymentId() { return paymentId; }
        public void setPaymentId(UUID paymentId) { this.paymentId = paymentId; }

        public UUID getOrderId() { return orderId; }
        public void setOrderId(UUID orderId) { this.orderId = orderId; }

        public String getMaskedCardNumber() { return maskedCardNumber; }
        public void setMaskedCardNumber(String maskedCardNumber) { this.maskedCardNumber = maskedCardNumber; }

        public java.math.BigDecimal getAmount() { return amount; }
        public void setAmount(java.math.BigDecimal amount) { this.amount = amount; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

        public LocalDateTime getUpdatedAt() { return updatedAt; }
        public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

        public Map<String, String> getMetadata() { return metadata; }
        public void setMetadata(Map<String, String> metadata) { this.metadata = metadata; }

        public String getMerchantId() { return merchantId; }
        public void setMerchantId(String merchantId) { this.merchantId = merchantId; }
    }
}