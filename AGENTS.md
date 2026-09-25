# AGENTS.md

Instrucciones para el agente de IA que abra este repositorio (Claude Code, Cursor, Codex, Copilot, Gemini). Se cargan solas: no hay que pegar nada en ningun chat.

## Que es este repositorio

Es el codigo base de un reto de aprendizaje de Pragma: **Diseño y Prototipado de Microservicios en un Sistema de Pagos**.

| | |
|---|---|
| Tema | Arquitectura de Microservicios con Event Sourcing CQRS Domain Driven Design y Patrones de Integracion Empresarial en un Sistema Distribuido de Alta Disponibilidad |
| Nivel | senior-l1 |
| Chapter | Backend |
| Especialidad | Java |
| Stack | Java / Spring Boot 3.4 |
| Patron arquitectonico | microservicio reactivo con CQRS, Event Sourcing y Domain Driven Design |
| Tiempo estimado | 40 horas |

## Receta del stack

Esqueleto obligatorio:

- `pom.xml en la raiz`
- `clase con @SpringBootApplication`
- `application.yml en src/main/resources`
- `capa de dominio con entidades y puertos`
- `capa de aplicacion con casos de uso`
- `capa de infraestructura con adaptadores y @RestController`

Trampas conocidas:

- TODA `<version>` del pom va con tres segmentos: la del parent (ej. `3.5.6`) y la de cada dependencia que la lleve (ej. Resilience4j `2.2.0`). `3.4` y `2.0` no existen como artefacto y el build muere resolviendo dependencias.
- Las dependencias que el parent POM gestiona van SIN `<version>`: `spring-boot-starter-web`, `-data-jpa`, `-validation`, `-test`, etc.
- Resilience4j publica un artefacto por linea de Spring Boot. Con Spring Boot 3 va `resilience4j-spring-boot3` con version de tres segmentos (ej. `2.2.0`, no `2.0`). `resilience4j-spring-boot2` es de Spring Boot 2 y rompe el arranque.
- Si usas anotaciones de validacion (`@NotNull`, `@Size`, `@Positive`) declara `spring-boot-starter-validation`: el starter web no las trae.
- El `spring-boot-maven-plugin` tiene que estar en `<build><plugins>` o no se empaqueta ejecutable.
- Spring Boot 3 usa `jakarta.*`, nunca `javax.*`.
- Cada archivo empieza con su `package` y con un `import` por cada clase del proyecto que viva en otro paquete. Usar `PaymentService` desde `infrastructure` sin `import com.x.application.PaymentService` no compila.

Dependencias:

- org.springframework.boot:spring-boot-starter-webflux 3.4.0
- org.springframework.boot:spring-boot-starter-data-mongodb-reactive 3.4.0
- org.springframework.boot:spring-boot-starter-actuator 3.4.0
- org.springframework.cloud:spring-cloud-starter-gateway 4.1.2
- org.springframework.kafka:spring-kafka 3.2.0
- io.projectreactor:reactor-core 3.6.5
- org.projectlombok:lombok 1.18.32
- org.springframework.boot:spring-boot-starter-test n/a
- org.testcontainers:mongodb 1.19.8
- org.testcontainers:kafka 1.19.8

## Tu tarea

Dejar este proyecto en estado **verificable**: que el comando de verificacion corra sin errores. Escribi los archivos en disco, en este repositorio. No generes ZIPs ni archivos adjuntos.

En orden:

1. Corre `mvn clean compile` y mira que falla.
2. Completa lo que falte de la lista de abajo: manifiesto de dependencias, punto de entrada, capa de interfaz y las capas del patron declarado.
3. Arregla SOLO los errores que impiden compilar o arrancar.
4. Volve a correr `mvn clean compile` hasta que pase.
5. Pará ahí.

## Regla dura: las fases son trabajo del humano

**PROHIBIDO implementar los entregables de las fases.** El valor del reto esta en que la persona los resuelva. Tu trabajo es que tenga un proyecto que arranca; el hueco pedagogico se queda como esta.

No resuelvas nada de esto:

- **Fase 1 — Definición del Modelo de Dominio**: Modelo de dominio definido con agregados, entidades, reglas de negocio y relaciones.
- **Fase 2 — Implementación de Event Sourcing**: Mecanismo de Event Sourcing implementado para registrar y reconstruir el estado de las transacciones de pago.
- **Fase 3 — Implementación de CQRS**: Patrón de CQRS implementado con comandos y consultas separados para el sistema de pagos.
- **Fase 4 — Implementación de Patrones de Integración Empresarial**: Patrones de integración empresarial implementados para la comunicación entre los microservicios del sistema de pagos.

Distincion operativa:

- **Arreglar** (si): import faltante, tipo que no existe, dependencia sin declarar, error de sintaxis, archivo referenciado que no existe.
- **No tocar** (no): logica de negocio incompleta, validaciones ausentes, secretos hardcodeados, APIs deprecadas que funcionan, concurrencia insegura, patrones mejorables. Eso es lo que la persona tiene que encontrar.

## Lo que falta y tenes que completar

### 1. Archivos que la arquitectura declara (1 de 26)

La propuesta arquitectonica del reto los lista y no llegaron al repo. Crealos con implementacion real, respetando la capa en la que viven:

- [ ] `README.md`

### 2. Referencias colgando (54)

Salieron de un analisis estatico del codigo que SI esta en el repo. Cada una rompe la compilacion:

- [ ] `fraud-service/src/main/java/com/pragma/fraudservice/application/FraudCheckService.java` — `com.pragma.fraudservice.domain.model.FraudCheckResult`
      El import com.pragma.fraudservice.domain.model.FraudCheckResult usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- [ ] `fraud-service/src/main/java/com/pragma/fraudservice/application/FraudCheckService.java` — `com.pragma.fraudservice.domain.model.FraudCheckRequest`
      El import com.pragma.fraudservice.domain.model.FraudCheckRequest usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- [ ] `fraud-service/src/main/java/com/pragma/fraudservice/application/FraudCheckService.java` — `com.pragma.fraudservice.domain.repository.FraudCheckRepository`
      El import com.pragma.fraudservice.domain.repository.FraudCheckRepository usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- [ ] `fraud-service/src/main/java/com/pragma/fraudservice/application/FraudCheckService.java` — `com.pragma.fraudservice.infrastructure.client.RiskBureauClient`
      El import com.pragma.fraudservice.infrastructure.client.RiskBureauClient usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- [ ] `fraud-service/src/main/java/com/pragma/fraudservice/application/FraudCheckService.java` — `com.pragma.fraudservice.infrastructure.client.FraudEngineClient`
      El import com.pragma.fraudservice.infrastructure.client.FraudEngineClient usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- [ ] `fraud-service/src/main/java/com/pragma/fraudservice/infrastructure/messaging/FraudEventListener.java` — `com.pragma.fraudservice.domain.model.FraudCheckRequest`
      El import com.pragma.fraudservice.domain.model.FraudCheckRequest usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- [ ] `fraud-service/src/main/java/com/pragma/fraudservice/infrastructure/messaging/FraudEventListener.java` — `com.pragma.fraudservice.domain.model.FraudCheckResult`
      El import com.pragma.fraudservice.domain.model.FraudCheckResult usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- [ ] `payment-service/src/main/java/com/pragma/paymentservice/domain/model/PaymentAggregate.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `fraud-service/src/main/java/com/pragma/fraudservice/FraudServiceApplication.java` — `reactor.core.scheduler`
      El import reactor.core.scheduler.Scheduler pertenece a reactor.core.scheduler, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `fraud-service/src/main/java/com/pragma/fraudservice/application/FraudCheckService.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `fraud-service/src/main/java/com/pragma/fraudservice/infrastructure/messaging/FraudEventListener.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `api-gateway/src/main/java/com/pragma/apigateway/ApiGatewayApplication.java` — `reactor.core.scheduler`
      El import reactor.core.scheduler.Scheduler pertenece a reactor.core.scheduler, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `api-gateway/src/main/java/com/pragma/apigateway/config/GatewayConfig.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `payment-service/src/main/java/com/pragma/paymentservice/domain/service/PaymentService.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `payment-service/src/main/java/com/pragma/paymentservice/domain/service/PaymentService.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `payment-service/src/main/java/com/pragma/paymentservice/infrastructure/persistence/PaymentProjectionRepository.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `payment-service/src/main/java/com/pragma/paymentservice/infrastructure/persistence/PaymentProjectionRepository.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `payment-service/src/main/java/com/pragma/paymentservice/application/command/CommandHandler.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `payment-service/src/main/java/com/pragma/paymentservice/application/command/CommandHandler.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `payment-service/src/main/java/com/pragma/paymentservice/application/query/PaymentQuery.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `payment-service/src/main/java/com/pragma/paymentservice/application/query/PaymentQuery.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `payment-service/src/main/java/com/pragma/paymentservice/application/query/QueryHandler.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `payment-service/src/main/java/com/pragma/paymentservice/infrastructure/persistence/EventStore.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `payment-service/src/main/java/com/pragma/paymentservice/infrastructure/messaging/PaymentEventPublisher.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `payment-service/src/main/java/com/pragma/paymentservice/infrastructure/messaging/PaymentEventPublisher.java` — `reactor.core.scheduler`
      El import reactor.core.scheduler.Schedulers pertenece a reactor.core.scheduler, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `payment-service/src/main/java/com/pragma/paymentservice/infrastructure/client/FraudClient.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `payment-service/src/main/java/com/pragma/paymentservice/infrastructure/config/EventSourcingConfig.java` — `reactor.core.scheduler`
      El import reactor.core.scheduler.Scheduler pertenece a reactor.core.scheduler, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `payment-service/src/test/java/com/pragma/paymentservice/application/command/CommandHandlerTest.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `fraud-service/src/main/java/com/pragma/fraudservice/application/FraudCheckService.java` — `FraudCheckRequest.paymentId`
      Se invoca `paymentId` sobre `FraudCheckRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `fraud-service/src/main/java/com/pragma/fraudservice/application/FraudCheckService.java` — `FraudCheckRequest.orderId`
      Se invoca `orderId` sobre `FraudCheckRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `fraud-service/src/main/java/com/pragma/fraudservice/application/FraudCheckService.java` — `FraudCheckRequest.cardNumber`
      Se invoca `cardNumber` sobre `FraudCheckRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `fraud-service/src/main/java/com/pragma/fraudservice/application/FraudCheckService.java` — `FraudCheckRequest.amount`
      Se invoca `amount` sobre `FraudCheckRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `fraud-service/src/main/java/com/pragma/fraudservice/application/FraudCheckService.java` — `FraudCheckRequest.customerId`
      Se invoca `customerId` sobre `FraudCheckRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `payment-service/src/main/java/com/pragma/paymentservice/domain/service/PaymentService.java` — `PaymentEventPublisher.publishPaymentEvent`
      Se invoca `publishPaymentEvent` sobre `PaymentEventPublisher`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `payment-service/src/main/java/com/pragma/paymentservice/domain/service/PaymentService.java` — `EventStore.getEventsForPayment`
      Se invoca `getEventsForPayment` sobre `EventStore`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `payment-service/src/main/java/com/pragma/paymentservice/domain/service/PaymentService.java` — `PaymentAggregate.authorizePayment`
      Se invoca `authorizePayment` sobre `PaymentAggregate`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `payment-service/src/main/java/com/pragma/paymentservice/domain/service/PaymentService.java` — `PaymentAggregate.settlePayment`
      Se invoca `settlePayment` sobre `PaymentAggregate`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `payment-service/src/main/java/com/pragma/paymentservice/domain/service/PaymentService.java` — `PaymentAggregate.failPayment`
      Se invoca `failPayment` sobre `PaymentAggregate`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `payment-service/src/main/java/com/pragma/paymentservice/domain/service/PaymentService.java` — `EventStore.getAllPaymentIds`
      Se invoca `getAllPaymentIds` sobre `EventStore`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `payment-service/src/main/java/com/pragma/paymentservice/infrastructure/persistence/PaymentProjectionRepository.java` — `PaymentStatus.name`
      Se invoca `name` sobre `PaymentStatus`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `payment-service/src/main/java/com/pragma/paymentservice/application/command/CommandHandler.java` — `EventStore.findByIdempotencyKey`
      Se invoca `findByIdempotencyKey` sobre `EventStore`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `payment-service/src/main/java/com/pragma/paymentservice/application/command/CommandHandler.java` — `PaymentService.initiatePaymentProcess`
      Se invoca `initiatePaymentProcess` sobre `PaymentService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `payment-service/src/main/java/com/pragma/paymentservice/application/command/CommandHandler.java` — `EventStore.save`
      Se invoca `save` sobre `EventStore`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `payment-service/src/main/java/com/pragma/paymentservice/application/command/CommandHandler.java` — `PaymentEventPublisher.publish`
      Se invoca `publish` sobre `PaymentEventPublisher`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `payment-service/src/main/java/com/pragma/paymentservice/application/query/PaymentQuery.java` — `EventStore.findByPaymentId`
      Se invoca `findByPaymentId` sobre `EventStore`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `payment-service/src/main/java/com/pragma/paymentservice/application/query/PaymentQuery.java` — `PaymentStatus.name`
      Se invoca `name` sobre `PaymentStatus`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `payment-service/src/main/java/com/pragma/paymentservice/application/query/PaymentQuery.java` — `PaymentProjectionRepository.findByCustomerId`
      Se invoca `findByCustomerId` sobre `PaymentProjectionRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `payment-service/src/main/java/com/pragma/paymentservice/application/query/QueryHandler.java` — `PaymentProjectionRepository.findTopByOrderByCreatedAtDesc`
      Se invoca `findTopByOrderByCreatedAtDesc` sobre `PaymentProjectionRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `payment-service/src/main/java/com/pragma/paymentservice/application/query/QueryHandler.java` — `PaymentProjectionRepository.countAll`
      Se invoca `countAll` sobre `PaymentProjectionRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `payment-service/src/test/java/com/pragma/paymentservice/application/command/CommandHandlerTest.java` — `CreatePaymentCommand.paymentId`
      Se invoca `paymentId` sobre `CreatePaymentCommand`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `payment-service/src/test/java/com/pragma/paymentservice/application/command/CommandHandlerTest.java` — `CreatePaymentCommand.cardNumber`
      Se invoca `cardNumber` sobre `CreatePaymentCommand`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `payment-service/src/test/java/com/pragma/paymentservice/application/command/CommandHandlerTest.java` — `CreatePaymentCommand.amount`
      Se invoca `amount` sobre `CreatePaymentCommand`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `payment-service/src/test/java/com/pragma/paymentservice/application/command/CommandHandlerTest.java` — `EventStore.save`
      Se invoca `save` sobre `EventStore`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `payment-service/src/test/java/com/pragma/paymentservice/application/command/CommandHandlerTest.java` — `PaymentEventPublisher.publish`
      Se invoca `publish` sobre `PaymentEventPublisher`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

### Presentes (28)

- `payment-service/pom.xml`
- `payment-service/src/main/java/com/pragma/paymentservice/PaymentServiceApplication.java`
- `payment-service/src/main/resources/application.yml`
- `payment-service/src/main/java/com/pragma/paymentservice/domain/model/PaymentAggregate.java`
- `payment-service/src/main/java/com/pragma/paymentservice/domain/model/PaymentEntity.java`
- `payment-service/src/main/java/com/pragma/paymentservice/domain/event/PaymentEvent.java`
- `fraud-service/pom.xml`
- `fraud-service/src/main/java/com/pragma/fraudservice/FraudServiceApplication.java`
- `fraud-service/src/main/resources/application.yml`
- `fraud-service/src/main/java/com/pragma/fraudservice/application/FraudCheckService.java`
- `fraud-service/src/main/java/com/pragma/fraudservice/infrastructure/messaging/FraudEventListener.java`
- `api-gateway/pom.xml`
- `api-gateway/src/main/java/com/pragma/apigateway/ApiGatewayApplication.java`
- `api-gateway/src/main/resources/application.yml`
- `api-gateway/src/main/java/com/pragma/apigateway/config/GatewayConfig.java`
- `payment-service/src/main/java/com/pragma/paymentservice/domain/event/PaymentCreatedEvent.java`
- `payment-service/src/main/java/com/pragma/paymentservice/domain/service/PaymentService.java`
- `payment-service/src/main/java/com/pragma/paymentservice/infrastructure/persistence/PaymentProjectionRepository.java`
- `payment-service/src/main/java/com/pragma/paymentservice/application/command/CreatePaymentCommand.java`
- `payment-service/src/main/java/com/pragma/paymentservice/application/command/CommandHandler.java`
- `payment-service/src/main/java/com/pragma/paymentservice/application/query/PaymentQuery.java`
- `payment-service/src/main/java/com/pragma/paymentservice/application/query/QueryHandler.java`
- `payment-service/src/main/java/com/pragma/paymentservice/infrastructure/persistence/EventStore.java`
- `payment-service/src/main/java/com/pragma/paymentservice/infrastructure/messaging/PaymentEventPublisher.java`
- `payment-service/src/main/java/com/pragma/paymentservice/infrastructure/client/FraudClient.java`
- `payment-service/src/main/java/com/pragma/paymentservice/infrastructure/config/EventSourcingConfig.java`
- `payment-service/src/test/java/com/pragma/paymentservice/application/command/CommandHandlerTest.java`
- `docker-compose.yml`

### Capas del patron declarado

Cada una tiene que existir como directorio real con al menos un archivo. Codigo plano en la raiz no satisface el patron.

- `payment-service`
- `payment-service/src/main/java/com/pragma/paymentservice`
- `payment-service/src/main/java/com/pragma/paymentservice/application`
- `payment-service/src/main/java/com/pragma/paymentservice/application/command`
- `payment-service/src/main/java/com/pragma/paymentservice/application/query`
- `payment-service/src/main/java/com/pragma/paymentservice/domain`
- `payment-service/src/main/java/com/pragma/paymentservice/domain/model`
- `payment-service/src/main/java/com/pragma/paymentservice/domain/event`
- `payment-service/src/main/java/com/pragma/paymentservice/domain/service`
- `payment-service/src/main/java/com/pragma/paymentservice/infrastructure`
- `payment-service/src/main/java/com/pragma/paymentservice/infrastructure/persistence`
- `payment-service/src/main/java/com/pragma/paymentservice/infrastructure/messaging`
- `payment-service/src/main/java/com/pragma/paymentservice/infrastructure/client`
- `payment-service/src/main/java/com/pragma/paymentservice/infrastructure/config`
- `payment-service/src/main/resources`
- `payment-service/src/test/java/com/pragma/paymentservice`
- `fraud-service`
- `fraud-service/src/main/java/com/pragma/fraudservice`
- `fraud-service/src/main/java/com/pragma/fraudservice/application`
- `fraud-service/src/main/java/com/pragma/fraudservice/domain`
- `fraud-service/src/main/java/com/pragma/fraudservice/infrastructure`
- `fraud-service/src/main/resources`
- `fraud-service/src/test/java/com/pragma/fraudservice`
- `api-gateway`
- `api-gateway/src/main/java/com/pragma/apigateway`
- `api-gateway/src/main/java/com/pragma/apigateway/config`
- `api-gateway/src/main/resources`

## Verificacion

```bash
mvn clean compile
```

Ese comando pasando es la definicion de "terminado" para vos.

## Convenciones que tenes que respetar

- Un solo ecosistema: no declares librerias de otro lenguaje ni mezcles gestores de paquetes.
- Toda libreria que uses tiene que estar declarada en el manifiesto de dependencias.
- Todo import declarado tiene que usarse; todo tipo usado tiene que existir o venir de una dependencia declarada.
- El patron es **microservicio reactivo con CQRS, Event Sourcing y Domain Driven Design**: los contratos (interfaces, puertos) los define la capa interna y los implementa la externa, nunca al revés.
- Los archivos que crees llevan implementacion real, no stubs: sin `TODO`, sin cuerpos vacios, sin `// getters y setters`.

## Contexto del candidato

Sirve para calibrar el nivel del codigo, no para resolver las fases.

- Brecha que el reto ataca: Implementar un sistema completo con los patrones mencionados

---

*Generado por Challenge Generator — Pragma. `README.md` tiene el enunciado completo del reto para la persona. `PROMPT_MEJORA.md` es la variante para pegar en un chat, si se prefiere ese flujo.*
