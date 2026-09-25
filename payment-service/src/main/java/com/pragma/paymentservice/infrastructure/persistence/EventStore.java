package com.pragma.paymentservice.infrastructure.persistence;


import com.pragma.paymentservice.domain.event.EventType;
import com.pragma.paymentservice.domain.event.PaymentEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class EventStore {

    private final ReactiveMongoTemplate mongoTemplate;
    private static final String COLLECTION_NAME = "payment_events";

    public EventStore(ReactiveMongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Mono<Void> appendEvent(PaymentEvent event) {
        if (event.getEventId() == null) {
            event.setEventId(UUID.randomUUID());
        }
        if (event.getEventTime() == null) {
            event.setEventTime(LocalDateTime.now());
        }

        return existsByEventId(event.getEventId())
                .flatMap(exists -> {
                    if (exists) {
                        log.warn("Evento duplicado detectado: {} para paymentId: {}",
                                event.getEventId(), event.getPaymentId());
                        return Mono.empty();
                    }
                    return mongoTemplate.insert(event, COLLECTION_NAME)
                            .doOnSuccess(e -> log.info("Evento almacenado: {} para paymentId: {}",
                                    e.getEventType(), e.getPaymentId()))
                            .then();
                });
    }

    public Flux<PaymentEvent> readEvents(UUID paymentId) {
        Query query = new Query(Criteria.where("paymentId").is(paymentId))
                .addCriteria(Criteria.where("eventTime").gte(LocalDateTime.of(2000, 1, 1, 0, 0)))
                .limit(1000);

        return mongoTemplate.find(query, PaymentEvent.class, COLLECTION_NAME)
                .sort((e1, e2) -> e1.getEventTime().compareTo(e2.getEventTime()))
                .doOnNext(event -> log.debug("Evento leído: {} para paymentId: {}",
                        event.getEventType(), event.getPaymentId()));
    }

    public Mono<Boolean> existsByEventId(UUID eventId) {
        Query query = new Query(Criteria.where("eventId").is(eventId));
        return mongoTemplate.exists(query, COLLECTION_NAME);
    }

    public Mono<Long> countEventsByPaymentId(UUID paymentId) {
        Query query = new Query(Criteria.where("paymentId").is(paymentId));
        return mongoTemplate.count(query, COLLECTION_NAME);
    }

    public Mono<Void> deleteEventsByPaymentId(UUID paymentId) {
        Query query = new Query(Criteria.where("paymentId").is(paymentId));
        return mongoTemplate.remove(query, COLLECTION_NAME)
                .doOnSuccess(r -> log.info("Eventos eliminados para paymentId: {}, count: {}",
                        paymentId, r.getDeletedCount()))
                .then();
    }

    public Flux<PaymentEvent> readEventsByType(UUID paymentId, PaymentEvent.EventType eventType) {
        Query query = new Query(
                Criteria.where("paymentId").is(paymentId)
                        .and("eventType").is(eventType)
        );
        return mongoTemplate.find(query, PaymentEvent.class, COLLECTION_NAME);
    }

    public Mono<PaymentEvent> readLatestEvent(UUID paymentId) {
        Query query = new Query(Criteria.where("paymentId").is(paymentId))
                .limit(1);
        return mongoTemplate.find(query, PaymentEvent.class, COLLECTION_NAME)
                .sort((e1, e2) -> e2.getEventTime().compareTo(e1.getEventTime()))
                .singleOrEmpty();
    }

    public Mono<String> getEventStream(UUID paymentId) {
        return readEvents(paymentId)
                .map(event -> String.format(
                        "{\"eventId\":\"%s\",\"type\":\"%s\",\"time\":\"%s\"}",
                        event.getEventId(),
                        event.getEventType(),
                        event.getEventTime()
                ))
                .collect(Collectors.joining(",\n", "[\n", "]\n"));
    }
}