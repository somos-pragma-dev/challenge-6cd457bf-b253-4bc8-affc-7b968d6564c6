package com.pragma.paymentservice.application.command;


import com.pragma.paymentservice.domain.event.EventType;
import com.pragma.paymentservice.domain.event.PaymentEvent;
import com.pragma.paymentservice.domain.event.PaymentCreatedEvent;
import com.pragma.paymentservice.domain.model.PaymentAggregate;
import com.pragma.paymentservice.domain.model.PaymentAggregate.PaymentStatus;
import com.pragma.paymentservice.domain.service.PaymentService;
import com.pragma.paymentservice.infrastructure.messaging.PaymentEventPublisher;
import com.pragma.paymentservice.infrastructure.persistence.EventStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class CommandHandler {
    private static final Logger log = LoggerFactory.getLogger(CommandHandler.class);
    
    private final PaymentService paymentService;
    private final EventStore eventStore;
    private final PaymentEventPublisher eventPublisher;

    public CommandHandler(PaymentService paymentService, 
                          EventStore eventStore,
                          PaymentEventPublisher eventPublisher) {
        this.paymentService = paymentService;
        this.eventStore = eventStore;
        this.eventPublisher = eventPublisher;
    }

    public Mono<PaymentCommandResult> handleCreatePayment(CreatePaymentCommand command) {
        log.info("Iniciando procesamiento del comando de pago para el pedido: {}", 
                 command.getOrderId());
        
        return eventStore.findByIdempotencyKey(command.getIdempotencyKey())
            .flatMap(existingEvent -> {
                log.warn("Comando duplicado detectado con clave de idempotencia: {}", 
                         command.getIdempotencyKey());
                return Mono.just(new PaymentCommandResult(
                    extractPaymentIdFromEvent(existingEvent),
                    PaymentStatus.PENDING,
                    "Pago duplicado - retornando estado existente",
                    true
                ));
            })
            .switchIfEmpty(Mono.defer(() -> processNewPayment(command)));
    }

    private Mono<PaymentCommandResult> processNewPayment(CreatePaymentCommand command) {
        UUID paymentId = UUID.randomUUID();
        log.info("Creando nuevo pago con ID: {} para el pedido: {}", 
                 paymentId, command.getOrderId());
        
        PaymentAggregate aggregate = new PaymentAggregate(
            paymentId,
            command.getOrderId(),
            command.getCardNumber(),
            command.getAmount()
        );

        return aggregate.applyEvent(new PaymentCreatedEvent(
            UUID.randomUUID(),
            paymentId,
            LocalDateTime.now(),
            PaymentEvent.EventType.PAYMENT_CREATED,
            command.getAmount(),
            command.getCustomerId(),
            command.getCurrency(),
            command.getPaymentMethod()
        ))
        .flatMap(updatedAggregate -> {
            log.info("Evento de pago creado aplicado al agregado para paymentId: {}", paymentId);
            return paymentService.initiatePaymentProcess(updatedAggregate);
        })
        .flatMap(processedAggregate -> {
            Flux<PaymentEvent> uncommittedEvents = processedAggregate.getUncommittedEvents();
            return uncommittedEvents
                .flatMap(event -> eventStore.save(event)
                    .then(eventPublisher.publish(event)))
                .then(Mono.defer(() -> {
                    log.info("Todos los eventos del pago {} han sido persistidos y publicados", 
                             paymentId);
                    return processedAggregate.clearUncommittedEvents();
                }))
                .thenReturn(new PaymentCommandResult(
                    paymentId,
                    processedAggregate.getStatus(),
                    "Pago procesado exitosamente",
                    false
                ));
        })
        .onErrorResume(error -> {
            log.error("Error al procesar el pago para el pedido {}: {}", 
                      command.getOrderId(), error.getMessage());
            return Mono.just(new PaymentCommandResult(
                paymentId,
                PaymentStatus.FAILED,
                "Error al procesar el pago: " + error.getMessage(),
                false
            ));
        });
    }

    private UUID extractPaymentIdFromEvent(PaymentEvent event) {
        return event.getPaymentId();
    }

    public record PaymentCommandResult(
        UUID paymentId,
        PaymentStatus status,
        String message,
        boolean isDuplicate
    ) {}
}