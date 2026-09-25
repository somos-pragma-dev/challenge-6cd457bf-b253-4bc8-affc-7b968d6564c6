package com.pragma.paymentservice.infrastructure.config;

import com.pragma.paymentservice.infrastructure.persistence.EventStore;
import com.pragma.paymentservice.infrastructure.messaging.PaymentEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@Configuration
public class EventSourcingConfig {

    private final ReactiveMongoTemplate mongoTemplate;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public EventSourcingConfig(
            ReactiveMongoTemplate mongoTemplate,
            KafkaTemplate<String, Object> kafkaTemplate) {
        this.mongoTemplate = mongoTemplate;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Bean
    public EventStore eventStore(ReactiveMongoTemplate template) {
        return new EventStore(template, eventScheduler());
    }

    @Bean
    public PaymentEventPublisher eventPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        return new PaymentEventPublisher(kafkaTemplate, paymentEventsTopic());
    }

    @Bean
    public Scheduler eventScheduler() {
        return Schedulers.boundedElastic().onDuplicateHook();
    }

    @Bean
    public String paymentEventsTopic() {
        return "payment-events";
    }

    @Bean
    public EventSourcingProperties eventSourcingProperties(
            @Value("${event-sourcing.snapshot-threshold:50}") int snapshotThreshold,
            @Value("${event-sourcing.replay-timeout-ms:30000}") long replayTimeout) {
        return new EventSourcingProperties(snapshotThreshold, Duration.ofMillis(replayTimeout));
    }

    public record EventSourcingProperties(int snapshotThreshold, Duration replayTimeout) {}
}