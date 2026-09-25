package com.pragma.paymentservice.application.command;



import com.pragma.paymentservice.domain.model.PaymentStatus;
import com.pragma.paymentservice.infrastructure.client.FraudCheckResponse;
import com.pragma.paymentservice.domain.model.PaymentAggregate;
import com.pragma.paymentservice.domain.service.PaymentService;
import com.pragma.paymentservice.infrastructure.client.FraudClient;
import com.pragma.paymentservice.infrastructure.messaging.PaymentEventPublisher;
import com.pragma.paymentservice.infrastructure.persistence.EventStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("CommandHandler - Pruebas Unitarias")
class CommandHandlerTest {

    @Mock
    private PaymentService paymentService;
    
    @Mock
    private EventStore eventStore;
    
    @Mock
    private FraudClient fraudClient;
    
    @Mock
    private PaymentEventPublisher eventPublisher;

    private CommandHandler commandHandler;

    @BeforeEach
    void setUp() {
        commandHandler = new CommandHandler(paymentService, eventStore, fraudClient, eventPublisher);
    }

    @Test
    @DisplayName("Debe crear un pago exitosamente cuando pasa la validación de fraude")
    void handleCreatePayment_Success() {
        CreatePaymentCommand command = new CreatePaymentCommand(
                UUID.randomUUID(),
                "4532015112830366",
                new BigDecimal("150.00")
        );

        PaymentAggregate mockAggregate = new PaymentAggregate(
                command.paymentId(),
                UUID.randomUUID(),
                command.cardNumber(),
                command.amount()
        );

        FraudClient.FraudCheckResponse fraudResponse = 
                new FraudClient.FraudCheckResponse("APPROVED", "LOW_RISK");

        when(fraudClient.checkFraud(any(UUID.class), anyString(), any(BigDecimal.class)))
                .thenReturn(Mono.just(fraudResponse));
        when(paymentService.processPayment(any(PaymentAggregate.class)))
                .thenReturn(Mono.just(mockAggregate));
        when(eventStore.save(any())).thenReturn(Mono.empty());
        when(eventPublisher.publish(any())).thenReturn(Mono.empty());

        StepVerifier.create(commandHandler.handleCreatePayment(command))
                .expectNextMatches(result -> 
                        result.paymentId().equals(command.paymentId()) &&
                        result.status().equals(PaymentAggregate.PaymentStatus.PENDING))
                .verifyComplete();
    }

    @Test
    @DisplayName("Debe rechazar el pago cuando falla la verificación de fraude")
    void handleCreatePayment_FraudRejected() {
        CreatePaymentCommand command = new CreatePaymentCommand(
                UUID.randomUUID(),
                "4532015112830366",
                new BigDecimal("150.00")
        );

        FraudClient.FraudCheckResponse fraudResponse = 
                new FraudClient.FraudCheckResponse("REJECTED", "HIGH_RISK");

        when(fraudClient.checkFraud(any(UUID.class), anyString(), any(BigDecimal.class)))
                .thenReturn(Mono.just(fraudResponse));

        StepVerifier.create(commandHandler.handleCreatePayment(command))
                .expectErrorMatches(e -> 
                        e.getMessage().contains("Fraud check failed"))
                .verify();
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el monto es inválido")
    void handleCreatePayment_InvalidAmount() {
        CreatePaymentCommand command = new CreatePaymentCommand(
                UUID.randomUUID(),
                "4532015112830366",
                new BigDecimal("-50.00")
        );

        StepVerifier.create(commandHandler.handleCreatePayment(command))
                .expectErrorMatches(e -> 
                        e.getMessage().contains("Amount must be positive"))
                .verify();
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el número de tarjeta es inválido")
    void handleCreatePayment_InvalidCardNumber() {
        CreatePaymentCommand command = new CreatePaymentCommand(
                UUID.randomUUID(),
                "123",
                new BigDecimal("150.00")
        );

        StepVerifier.create(commandHandler.handleCreatePayment(command))
                .expectErrorMatches(e -> 
                        e.getMessage().contains("Invalid card number"))
                .verify();
    }

    @Test
    @DisplayName("Debe manejar errores de comunicación con el servicio de fraude")
    void handleCreatePayment_FraudServiceError() {
        CreatePaymentCommand command = new CreatePaymentCommand(
                UUID.randomUUID(),
                "4532015112830366",
                new BigDecimal("150.00")
        );

        when(fraudClient.checkFraud(any(UUID.class), anyString(), any(BigDecimal.class)))
                .thenReturn(Mono.error(new RuntimeException("Connection timeout")));

        StepVerifier.create(commandHandler.handleCreatePayment(command))
                .expectErrorMatches(e -> 
                        e.getMessage().contains("Fraud check failed"))
                .verify();
    }
}