package com.pragma.paymentservice.infrastructure.messaging;

import com.pragma.paymentservice.domain.event.PaymentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentEventPublisher {

    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    @Value("${app.kafka.topics.payment-events:payment-events}")
    private String paymentEventsTopic;

    @Value("${app.kafka.topics.payment-notifications:payment-notifications}")
    private String paymentNotificationsTopic;

    public Mono<Void> publishPaymentCreated(PaymentEvent event) {
        return publishEvent(event, paymentEventsTopic)
                .doOnSuccess(result -> log.info("PaymentCreated event published for paymentId: {}",
                        event.getPaymentId()))
                .doOnError(error -> log.error("Failed to publish PaymentCreated event for paymentId: {}",
                        event.getPaymentId(), error))
                .then();
    }

    public Mono<Void> publishPaymentAuthorized(PaymentEvent event) {
        return publishEvent(event, paymentEventsTopic)
                .doOnSuccess(result -> log.info("PaymentAuthorized event published for paymentId: {}",
                        event.getPaymentId()))
                .doOnError(error -> log.error("Failed to publish PaymentAuthorized event for paymentId: {}",
                        event.getPaymentId(), error))
                .then();
    }

    public Mono<Void> publishPaymentFailed(PaymentEvent event) {
        return publishEvent(event, paymentEventsTopic)
                .doOnSuccess(result -> log.info("PaymentFailed event published for paymentId: {}",
                        event.getPaymentId()))
                .doOnError(error -> log.error("Failed to publish PaymentFailed event for paymentId: {}",
                        event.getPaymentId(), error))
                .then();
    }

    public Mono<Void> publishPaymentSettled(PaymentEvent event) {
        return publishEvent(event, paymentEventsTopic)
                .doOnSuccess(result -> log.info("PaymentSettled event published for paymentId: {}",
                        event.getPaymentId()))
                .doOnError(error -> log.error("Failed to publish PaymentSettled event for paymentId: {}",
                        event.getPaymentId(), error))
                .then();
    }

    public Mono<Void> publishNotification(PaymentEvent event) {
        return publishEvent(event, paymentNotificationsTopic)
                .doOnSuccess(result -> log.info("Notification event published for paymentId: {}",
                        event.getPaymentId()))
                .doOnError(error -> log.error("Failed to publish notification event for paymentId: {}",
                        event.getPaymentId(), error))
                .then();
    }

    private Mono<SendResult<String, PaymentEvent>> publishEvent(PaymentEvent event, String topic) {
        String key = event.getPaymentId() != null ? event.getPaymentId().toString() : UUID.randomUUID().toString();

        return Mono.fromFuture(() -> {
            CompletableFuture<SendResult<String, PaymentEvent>> future = kafkaTemplate.send(topic, key, event);
            return future.whenComplete((result, ex) -> {
                if (ex != null) {
                    log.error("Error sending event to Kafka topic {}: {}", topic, ex.getMessage());
                } else {
                    log.debug("Event sent successfully to topic {}: partition={}, offset={}",
                            topic,
                            result.getRecordMetadata().partition(),
                            result.getRecordMetadata().offset());
                }
            });
        }).subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Void> publishBatch(java.util.List<PaymentEvent> events) {
        return Flux.fromIterable(events)
                .flatMap(this::publishPaymentCreated)
                .then();
    }

    public Mono<Long> publishWithRetry(PaymentEvent event, int maxRetries) {
        return Mono.defer(() -> publishEvent(event, paymentEventsTopic)
                        .map(SendResult::getRecordMetadata)
                        .map(metadata -> metadata.offset()))
                .retryWhen(reactor.util.retry.Retry.backoff(maxRetries, java.time.Duration.ofSeconds(1))
                        .doBeforeRetry(signal -> log.warn("Retry attempt {} for event {}",
                                signal.totalRetries() + 1, event.getEventId())))
                .onErrorResume(error -> {
                    log.error("All retries exhausted for event: {}", event.getEventId(), error);
                    return Mono.just(-1L);
                });
    }
}