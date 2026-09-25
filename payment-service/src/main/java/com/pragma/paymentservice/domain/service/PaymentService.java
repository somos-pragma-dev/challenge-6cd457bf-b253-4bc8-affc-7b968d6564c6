package com.pragma.paymentservice.domain.service;

import com.pragma.paymentservice.domain.event.PaymentCreatedEvent;
import com.pragma.paymentservice.domain.event.PaymentEvent;
import com.pragma.paymentservice.domain.model.PaymentAggregate;
import com.pragma.paymentservice.domain.model.PaymentAggregate.PaymentStatus;
import com.pragma.paymentservice.infrastructure.messaging.PaymentEventPublisher;
import com.pragma.paymentservice.infrastructure.persistence.EventStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentService {
    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

    private final EventStore eventStore;
    private final PaymentEventPublisher eventPublisher;

    public PaymentService(EventStore eventStore, PaymentEventPublisher eventPublisher) {
        this.eventStore = eventStore;
        this.eventPublisher = eventPublisher;
    }

    public Mono<PaymentAggregate> createPayment(UUID orderId, String cardNumber, 
                                                  BigDecimal amount, String currency,
                                                  String paymentMethod, String merchantId) {
        log.info("Creating payment for order: {}, amount: {}", orderId, amount);
        
        return Mono.defer(() -> {
            UUID paymentId = UUID.randomUUID();
            PaymentAggregate aggregate = new PaymentAggregate(paymentId, orderId, cardNumber, amount);
            
            PaymentCreatedEvent createdEvent = PaymentCreatedEvent.create(
                paymentId, orderId, cardNumber, amount, currency, paymentMethod, merchantId
            );
            
            return aggregate.applyEvent(createdEvent)
                .flatMap(aggregateWithEvent -> {
                    PaymentAggregate finalAggregate = aggregateWithEvent;
                    return eventStore.appendEvent(paymentId, createdEvent)
                        .then(eventPublisher.publishPaymentEvent(createdEvent))
                        .thenReturn(finalAggregate);
                })
                .doOnSuccess(payment -> log.info("Payment created successfully: {}", payment.getPaymentId()))
                .doOnError(error -> log.error("Failed to create payment: {}", error.getMessage()));
        });
    }

    public Mono<PaymentAggregate> getPaymentById(UUID paymentId) {
        log.debug("Retrieving payment: {}", paymentId);
        
        return eventStore.getEventsForPayment(paymentId)
            .collectList()
            .flatMap(events -> {
                if (events.isEmpty()) {
                    return Mono.empty();
                }
                return rebuildAggregateFromEvents(paymentId, events);
            });
    }

    public Flux<PaymentEvent> getPaymentHistory(UUID paymentId) {
        log.debug("Retrieving payment history: {}", paymentId);
        return eventStore.getEventsForPayment(paymentId);
    }

    public Mono<PaymentAggregate> processPayment(UUID paymentId, PaymentEvent event) {
        log.info("Processing payment event for: {}", paymentId);
        
        return getPaymentById(paymentId)
            .switchIfEmpty(Mono.error(new IllegalStateException("Payment not found: " + paymentId)))
            .flatMap(aggregate -> aggregate.applyEvent(event))
            .flatMap(updatedAggregate -> eventStore.appendEvent(paymentId, event)
                .then(eventPublisher.publishPaymentEvent(event))
                .thenReturn(updatedAggregate));
    }

    public Mono<PaymentAggregate> authorizePayment(UUID paymentId) {
        log.info("Authorizing payment: {}", paymentId);
        return getPaymentById(paymentId)
            .flatMap(aggregate -> {
                aggregate.authorizePayment();
                return aggregate.getUncommittedEvents()
                    .collectList()
                    .flatMap(events -> {
                        if (events.isEmpty()) {
                            return Mono.just(aggregate);
                        }
                        return Flux.fromIterable(events)
                            .flatMap(event -> eventStore.appendEvent(paymentId, event)
                                .then(eventPublisher.publishPaymentEvent(event)))
                            .then(aggregate.clearUncommittedEvents())
                            .thenReturn(aggregate);
                    });
            });
    }

    public Mono<PaymentAggregate> settlePayment(UUID paymentId) {
        log.info("Settling payment: {}", paymentId);
        return getPaymentById(paymentId)
            .flatMap(aggregate -> {
                aggregate.settlePayment();
                return aggregate.getUncommittedEvents()
                    .collectList()
                    .flatMap(events -> {
                        if (events.isEmpty()) {
                            return Mono.just(aggregate);
                        }
                        return Flux.fromIterable(events)
                            .flatMap(event -> eventStore.appendEvent(paymentId, event)
                                .then(eventPublisher.publishPaymentEvent(event)))
                            .then(aggregate.clearUncommittedEvents())
                            .thenReturn(aggregate);
                    });
            });
    }

    public Mono<PaymentAggregate> failPayment(UUID paymentId, String reason) {
        log.info("Failing payment: {}, reason: {}", paymentId, reason);
        return getPaymentById(paymentId)
            .flatMap(aggregate -> {
                aggregate.failPayment();
                return aggregate.getUncommittedEvents()
                    .collectList()
                    .flatMap(events -> {
                        if (events.isEmpty()) {
                            return Mono.just(aggregate);
                        }
                        return Flux.fromIterable(events)
                            .flatMap(event -> eventStore.appendEvent(paymentId, event)
                                .then(eventPublisher.publishPaymentEvent(event)))
                            .then(aggregate.clearUncommittedEvents())
                            .thenReturn(aggregate);
                    });
            });
    }

    private Mono<PaymentAggregate> rebuildAggregateFromEvents(UUID paymentId, 
                                                                 java.util.List<PaymentEvent> events) {
        if (events.isEmpty()) {
            return Mono.empty();
        }
        
        PaymentEvent firstEvent = events.get(0);
        PaymentAggregate aggregate = new PaymentAggregate(
            paymentId,
            firstEvent.getPaymentId(),
            "",
            BigDecimal.ZERO
        );
        
        return Flux.fromIterable(events)
            .flatMap(aggregate::applyEvent)
            .last()
            .flatMap(PaymentAggregate::clearUncommittedEvents);
    }

    public Flux<PaymentAggregate> getAllPayments() {
        log.debug("Retrieving all payments");
        return eventStore.getAllPaymentIds()
            .flatMap(this::getPaymentById)
            .filter(payment -> payment != null);
    }

    public Mono<PaymentAggregate> updatePaymentStatus(UUID paymentId, PaymentStatus status) {
        log.info("Updating payment status: {} to {}", paymentId, status);
        return getPaymentById(paymentId)
            .switchIfEmpty(Mono.error(new IllegalStateException("Payment not found: " + paymentId)));
    }
}