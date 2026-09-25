# Prompt para Mejorar el Codigo Base

Copia y pega el contenido del bloque de abajo en un asistente de IA (Claude, ChatGPT)
para obtener un ZIP con el proyecto completo y arrancable.

Si preferis trabajar en tu editor con un agente local (Claude Code, Cursor, Copilot), usa `AGENTS.md` en vez de este archivo: dice lo mismo pero para que escriba los archivos en disco.

## Las dos reglas que no se negocian

1. **Completa el boilerplate.** Todo lo que el proyecto necesita para compilar y arrancar: manifiesto de dependencias, punto de entrada, configuracion, capa de interfaz, y las capas del patron arquitectonico declarado. Eso es andamiaje y es tu trabajo.
2. **NO resuelvas el reto.** Los entregables de las fases son el trabajo de la persona. El hueco pedagogico se deja como esta: el proyecto arranca, pero lo que el reto pide implementar NO esta implementado.

Dicho de otra forma: si algo impide compilar, arreglalo. Si algo es logica de negocio incompleta, validaciones ausentes, un secreto hardcodeado o un patron mejorable, dejalo exactamente como esta — es lo que la persona tiene que encontrar.

## Lo que le falta a este proyecto

Esto NO lo tenes que adivinar: salio de comparar el proyecto contra la arquitectura declarada del reto y de un analisis estatico del codigo. Completalo TODO.

### Archivos que la arquitectura del reto declara y no estan

Creálos con implementacion real, en la capa que les corresponde:

- `README.md`

### Referencias colgando en el codigo que si esta

Cada una rompe la compilacion:

- `fraud-service/src/main/java/com/pragma/fraudservice/application/FraudCheckService.java` — `com.pragma.fraudservice.domain.model.FraudCheckResult`: El import com.pragma.fraudservice.domain.model.FraudCheckResult usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- `fraud-service/src/main/java/com/pragma/fraudservice/application/FraudCheckService.java` — `com.pragma.fraudservice.domain.model.FraudCheckRequest`: El import com.pragma.fraudservice.domain.model.FraudCheckRequest usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- `fraud-service/src/main/java/com/pragma/fraudservice/application/FraudCheckService.java` — `com.pragma.fraudservice.domain.repository.FraudCheckRepository`: El import com.pragma.fraudservice.domain.repository.FraudCheckRepository usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- `fraud-service/src/main/java/com/pragma/fraudservice/application/FraudCheckService.java` — `com.pragma.fraudservice.infrastructure.client.RiskBureauClient`: El import com.pragma.fraudservice.infrastructure.client.RiskBureauClient usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- `fraud-service/src/main/java/com/pragma/fraudservice/application/FraudCheckService.java` — `com.pragma.fraudservice.infrastructure.client.FraudEngineClient`: El import com.pragma.fraudservice.infrastructure.client.FraudEngineClient usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- `fraud-service/src/main/java/com/pragma/fraudservice/infrastructure/messaging/FraudEventListener.java` — `com.pragma.fraudservice.domain.model.FraudCheckRequest`: El import com.pragma.fraudservice.domain.model.FraudCheckRequest usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- `fraud-service/src/main/java/com/pragma/fraudservice/infrastructure/messaging/FraudEventListener.java` — `com.pragma.fraudservice.domain.model.FraudCheckResult`: El import com.pragma.fraudservice.domain.model.FraudCheckResult usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- `payment-service/src/main/java/com/pragma/paymentservice/domain/model/PaymentAggregate.java` — `reactor.core.publisher`: El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `fraud-service/src/main/java/com/pragma/fraudservice/FraudServiceApplication.java` — `reactor.core.scheduler`: El import reactor.core.scheduler.Scheduler pertenece a reactor.core.scheduler, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `fraud-service/src/main/java/com/pragma/fraudservice/application/FraudCheckService.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `fraud-service/src/main/java/com/pragma/fraudservice/infrastructure/messaging/FraudEventListener.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `api-gateway/src/main/java/com/pragma/apigateway/ApiGatewayApplication.java` — `reactor.core.scheduler`: El import reactor.core.scheduler.Scheduler pertenece a reactor.core.scheduler, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `api-gateway/src/main/java/com/pragma/apigateway/config/GatewayConfig.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `payment-service/src/main/java/com/pragma/paymentservice/domain/service/PaymentService.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `payment-service/src/main/java/com/pragma/paymentservice/domain/service/PaymentService.java` — `reactor.core.publisher`: El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `payment-service/src/main/java/com/pragma/paymentservice/infrastructure/persistence/PaymentProjectionRepository.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `payment-service/src/main/java/com/pragma/paymentservice/infrastructure/persistence/PaymentProjectionRepository.java` — `reactor.core.publisher`: El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `payment-service/src/main/java/com/pragma/paymentservice/application/command/CommandHandler.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `payment-service/src/main/java/com/pragma/paymentservice/application/command/CommandHandler.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `payment-service/src/main/java/com/pragma/paymentservice/application/query/PaymentQuery.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `payment-service/src/main/java/com/pragma/paymentservice/application/query/PaymentQuery.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `payment-service/src/main/java/com/pragma/paymentservice/application/query/QueryHandler.java` — `reactor.core.publisher`: El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `payment-service/src/main/java/com/pragma/paymentservice/infrastructure/persistence/EventStore.java` — `reactor.core.publisher`: El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `payment-service/src/main/java/com/pragma/paymentservice/infrastructure/messaging/PaymentEventPublisher.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `payment-service/src/main/java/com/pragma/paymentservice/infrastructure/messaging/PaymentEventPublisher.java` — `reactor.core.scheduler`: El import reactor.core.scheduler.Schedulers pertenece a reactor.core.scheduler, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `payment-service/src/main/java/com/pragma/paymentservice/infrastructure/client/FraudClient.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `payment-service/src/main/java/com/pragma/paymentservice/infrastructure/config/EventSourcingConfig.java` — `reactor.core.scheduler`: El import reactor.core.scheduler.Scheduler pertenece a reactor.core.scheduler, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `payment-service/src/test/java/com/pragma/paymentservice/application/command/CommandHandlerTest.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `fraud-service/src/main/java/com/pragma/fraudservice/application/FraudCheckService.java` — `FraudCheckRequest.paymentId`: Se invoca `paymentId` sobre `FraudCheckRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `fraud-service/src/main/java/com/pragma/fraudservice/application/FraudCheckService.java` — `FraudCheckRequest.orderId`: Se invoca `orderId` sobre `FraudCheckRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `fraud-service/src/main/java/com/pragma/fraudservice/application/FraudCheckService.java` — `FraudCheckRequest.cardNumber`: Se invoca `cardNumber` sobre `FraudCheckRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `fraud-service/src/main/java/com/pragma/fraudservice/application/FraudCheckService.java` — `FraudCheckRequest.amount`: Se invoca `amount` sobre `FraudCheckRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `fraud-service/src/main/java/com/pragma/fraudservice/application/FraudCheckService.java` — `FraudCheckRequest.customerId`: Se invoca `customerId` sobre `FraudCheckRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `payment-service/src/main/java/com/pragma/paymentservice/domain/service/PaymentService.java` — `PaymentEventPublisher.publishPaymentEvent`: Se invoca `publishPaymentEvent` sobre `PaymentEventPublisher`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `payment-service/src/main/java/com/pragma/paymentservice/domain/service/PaymentService.java` — `EventStore.getEventsForPayment`: Se invoca `getEventsForPayment` sobre `EventStore`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `payment-service/src/main/java/com/pragma/paymentservice/domain/service/PaymentService.java` — `PaymentAggregate.authorizePayment`: Se invoca `authorizePayment` sobre `PaymentAggregate`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `payment-service/src/main/java/com/pragma/paymentservice/domain/service/PaymentService.java` — `PaymentAggregate.settlePayment`: Se invoca `settlePayment` sobre `PaymentAggregate`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `payment-service/src/main/java/com/pragma/paymentservice/domain/service/PaymentService.java` — `PaymentAggregate.failPayment`: Se invoca `failPayment` sobre `PaymentAggregate`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `payment-service/src/main/java/com/pragma/paymentservice/domain/service/PaymentService.java` — `EventStore.getAllPaymentIds`: Se invoca `getAllPaymentIds` sobre `EventStore`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `payment-service/src/main/java/com/pragma/paymentservice/infrastructure/persistence/PaymentProjectionRepository.java` — `PaymentStatus.name`: Se invoca `name` sobre `PaymentStatus`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `payment-service/src/main/java/com/pragma/paymentservice/application/command/CommandHandler.java` — `EventStore.findByIdempotencyKey`: Se invoca `findByIdempotencyKey` sobre `EventStore`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `payment-service/src/main/java/com/pragma/paymentservice/application/command/CommandHandler.java` — `PaymentService.initiatePaymentProcess`: Se invoca `initiatePaymentProcess` sobre `PaymentService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `payment-service/src/main/java/com/pragma/paymentservice/application/command/CommandHandler.java` — `EventStore.save`: Se invoca `save` sobre `EventStore`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `payment-service/src/main/java/com/pragma/paymentservice/application/command/CommandHandler.java` — `PaymentEventPublisher.publish`: Se invoca `publish` sobre `PaymentEventPublisher`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `payment-service/src/main/java/com/pragma/paymentservice/application/query/PaymentQuery.java` — `EventStore.findByPaymentId`: Se invoca `findByPaymentId` sobre `EventStore`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `payment-service/src/main/java/com/pragma/paymentservice/application/query/PaymentQuery.java` — `PaymentStatus.name`: Se invoca `name` sobre `PaymentStatus`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `payment-service/src/main/java/com/pragma/paymentservice/application/query/PaymentQuery.java` — `PaymentProjectionRepository.findByCustomerId`: Se invoca `findByCustomerId` sobre `PaymentProjectionRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `payment-service/src/main/java/com/pragma/paymentservice/application/query/QueryHandler.java` — `PaymentProjectionRepository.findTopByOrderByCreatedAtDesc`: Se invoca `findTopByOrderByCreatedAtDesc` sobre `PaymentProjectionRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `payment-service/src/main/java/com/pragma/paymentservice/application/query/QueryHandler.java` — `PaymentProjectionRepository.countAll`: Se invoca `countAll` sobre `PaymentProjectionRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `payment-service/src/test/java/com/pragma/paymentservice/application/command/CommandHandlerTest.java` — `CreatePaymentCommand.paymentId`: Se invoca `paymentId` sobre `CreatePaymentCommand`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `payment-service/src/test/java/com/pragma/paymentservice/application/command/CommandHandlerTest.java` — `CreatePaymentCommand.cardNumber`: Se invoca `cardNumber` sobre `CreatePaymentCommand`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `payment-service/src/test/java/com/pragma/paymentservice/application/command/CommandHandlerTest.java` — `CreatePaymentCommand.amount`: Se invoca `amount` sobre `CreatePaymentCommand`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `payment-service/src/test/java/com/pragma/paymentservice/application/command/CommandHandlerTest.java` — `EventStore.save`: Se invoca `save` sobre `EventStore`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `payment-service/src/test/java/com/pragma/paymentservice/application/command/CommandHandlerTest.java` — `PaymentEventPublisher.publish`: Se invoca `publish` sobre `PaymentEventPublisher`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

## Como saber que terminaste

```bash
mvn clean compile
```

Ese comando corriendo sin errores es la definicion de "listo".

---

```
## Briefing del reto (autoridad)
Este bloque manda sobre los archivos adjuntos. El stack y el rol salen de AQUÍ, no de un topic genérico ni de markdown placeholder.

### Contexto técnico original
Implementar un sistema completo con los patrones mencionados

### Reto
- Tema: Arquitectura de Microservicios con Event Sourcing CQRS Domain Driven Design y Patrones de Integracion Empresarial en un Sistema Distribuido de Alta Disponibilidad
- Seniority: senior-l1
- Tipo: practical
- Título: Diseño y Prototipado de Microservicios en un Sistema de Pagos
- Tiempo estimado: 40 horas

### Fases (trabajo del HUMANO — PROHIBIDO completarlas)
No implementes estos entregables. Dejalos como hueco pedagógico. El asistente solo materializa el proyecto arrancable para que el participante pueda trabajar.
- Fase 1: Definición del Modelo de Dominio — objetivo: Identificar y definir los agregados y entidades del dominio de pagos. — entregable (NO resolver): Modelo de dominio definido con agregados, entidades, reglas de negocio y relaciones.
- Fase 2: Implementación de Event Sourcing — objetivo: Implementar el patrón de Event Sourcing para registrar los eventos de las transacciones de pago. — entregable (NO resolver): Mecanismo de Event Sourcing implementado para registrar y reconstruir el estado de las transacciones de pago.
- Fase 3: Implementación de CQRS — objetivo: Implementar el patrón de CQRS para separar las responsabilidades de lectura y escritura en el sistema de pagos. — entregable (NO resolver): Patrón de CQRS implementado con comandos y consultas separados para el sistema de pagos.
- Fase 4: Implementación de Patrones de Integración Empresarial — objetivo: Implementar los patrones de integración empresarial para la comunicación entre los microservicios. — entregable (NO resolver): Patrones de integración empresarial implementados para la comunicación entre los microservicios del sistema de pagos.

Eres un asistente experto en análisis, corrección y generación de archivos de cualquier tipo:
código fuente, documentación, hojas de cálculo, documentos Word, configuraciones, entre otros.
Voy a enviarte una cadena de texto que contiene uno o más archivos. Cada archivo está delimitado por un marcador con el siguiente formato:
// === ARCHIVO: ruta/del/archivo.extension ===
o también puede aparecer como:
## === ARCHIVO: ruta/del/archivo.extension ===
Lo que sigue al marcador puede ser:

El contenido real del archivo (código, texto, YAML, etc.)
Una descripción en lenguaje natural de lo que debe contener el archivo


TU TAREA
PASO 0 — ¿Esto es un proyecto o una carcasa?
Antes de extraer archivos, leé el Briefing (si está) y diagnosticá el adjunto.

Es CARCASA si ocurre CUALQUIERA de estas:
- No hay manifiesto de dependencias del stack del briefing (manifest.json de VTEX IO / package.json / pom.xml / build.gradle / requirements.txt / go.mod / *.tf / *.csproj, según corresponda)
- Hay un "binario" que en realidad es un comentario ("no puede ser mostrado como texto plano", placeholder .fig/.docx vacío)
- Los markdowns ya completan entregables de fases posteriores ("se implementó fade-in", lista de áreas ya resuelta)

Si es CARCASA:
- MATERIALIZÁ un proyecto que arranca en el stack del briefing (VTEX IO Store Framework, Angular, Terraform, pytest, Nest, etc.). Incluí manifiesto, punto de entrada y capa de interfaz reales.
- NO copies los markdowns de "solución" como si fueran el producto. Son ruido de generación.
- NO resuelvas las fases del briefing (están marcadas PROHIBIDO). Dejá el hueco pedagógico: el flujo existe, las microinteracciones/calidad/infra que el reto pide NO están hechas.
- Después seguí al PASO 5 (ZIP).

Si es un proyecto REAL (manifiesto + código que compila o arranca):
- Seguí PASO 1 en adelante. 🔴 compilación sí. 🟡 pedagógico no.

PASO 1 — Detección y extracción
Identifica todos los archivos presentes en la cadena. Para cada archivo extrae:

Su ruta completa (ej: src/main/java/com/pragma/Service.java)
Su contenido o descripción

PASO 2 — Clasificación por tipo
Clasifica cada archivo en una de estas categorías:
A) Código fuente (Java, Python, TypeScript, JavaScript, Kotlin, etc.)
B) Configuración / documentación (YAML, properties, Markdown, JSON, txt, etc.)
C) Excel (.xlsx, .xls, .csv)
D) Word (.docx, .doc)
E) Otro tipo de archivo binario o especial
PASO 3 — Clasificación de errores en código fuente

Objetivo prioritario: que el proyecto compile. No corrijas flujo de negocio ni lógica funcional.

Antes de modificar cualquier archivo de código fuente, clasifica cada problema encontrado en una de estas dos categorías:
🔴 ERROR DE COMPILACIÓN — corregir siempre
Son errores que impiden que el proyecto arranque, sin valor pedagógico:

Import faltante o incorrecto
Clase, método o variable referenciada que no existe en ningún archivo del proyecto
Error de sintaxis
Anotación con atributos inválidos
Dependencia ausente en pom.xml, package.json, etc.
Archivo referenciado que no existe y debe ser creado con implementación mínima

→ CORREGIR estos errores.
🟡 PROBLEMA FUNCIONAL O DE CALIDAD — preservar siempre
Son problemas que no impiden compilar. Pueden ser intencionales para el aprendizaje:

Clave secreta hardcodeada ("secret", "password123")
API deprecada que funciona pero tiene reemplazo moderno
Lógica de negocio incorrecta o incompleta
Código redundante o de baja legibilidad
Falta de validaciones en flujo de negocio
Patrones de diseño incorrectos pero funcionales
Concurrencia no segura
Configuración funcional pero no óptima

→ PRESERVAR tal cual. No corregir, no mejorar, no comentar.
PASO 4 — Procesamiento según tipo de archivo
Tipo A — Código fuente
Aplica únicamente las correcciones clasificadas como 🔴 ERROR DE COMPILACIÓN.
No alteres ningún elemento clasificado como 🟡 PROBLEMA FUNCIONAL O DE CALIDAD.
Si falta un archivo referenciado, créalo con la implementación mínima necesaria para compilar.
Tipo B — Configuración / documentación
Extrae el contenido tal cual, sin modificaciones salvo errores evidentes de sintaxis
(ej: YAML mal indentado).
Tipo C — Excel (.xlsx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un archivo Excel funcional con:

Fila de encabezados en negrita con color de fondo distintivo
Columnas con ancho ajustado al contenido
Tipos de dato correctos por columna
Validaciones si la descripción lo indica
Hojas nombradas descriptivamente si hay más de una
Filas de ejemplo si no hay datos reales

Tipo D — Word (.docx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un documento Word funcional con:

Estilos de título (Título 1, Título 2) para jerarquía de secciones
Fuente legible (Calibri o equivalente), tamaño 11-12pt para cuerpo
Márgenes estándar
Tabla de contenido si tiene múltiples secciones
Tablas con encabezados en negrita si aplica

Tipo E — Otro
Genera el archivo con el contenido o estructura más apropiada según la descripción.
PASO 5 — Exportación en ZIP
Empaqueta todos los archivos en un único archivo ZIP descargable respetando exactamente
la estructura de rutas indicada por los marcadores.
El ZIP debe incluir:

Archivos de código con únicamente los errores de compilación corregidos
Archivos de configuración y documentación sin cambios
Archivos nuevos creados para resolver dependencias de compilación faltantes
Archivos Excel y Word generados desde descripción

IMPORTANTE: El ZIP debe estar listo para descargar al finalizar. No preguntes si el usuario
quiere generarlo. Simplemente genera el archivo y proporciona el enlace de descarga; No debes desplegar en el chat el resumen de lo que arreglaste al Zip, solo entregalo.

REGLAS IMPORTANTES

No omitas ningún archivo aunque no tenga errores ni modificaciones
Respeta los nombres y rutas exactas indicadas por los marcadores
Si un archivo no tiene marcador claro, infiere el nombre desde su contenido
Si la cadena contiene solo documentación, placeholders o binarios fake, NO la reproduzcas:
aplicá PASO 0 (materializar el proyecto del briefing). Reproducir la carcasa es un fallo.
No agregues texto después del enlace de descarga del ZIP
No preguntes si el usuario quiere el ZIP: simplemente generalo siempre
Si detectas que falta un archivo de configuración necesario para compilar
(pom.xml, package.json, requirements.txt, build.gradle, etc.), créalo e inclúyelo
inferiendo su contenido desde los imports y frameworks detectados en el código
Nunca corrijas problemas 🟡 aunque parezcan obvios o fáciles de mejorar.
El participante que recibirá este proyecto los debe encontrar y resolver él mismo.


INPUT
Aquí está la cadena con los archivos:

// === ARCHIVO: payment-service/pom.xml ===
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.4.0</version>
        <relativePath/>
    </parent>

    <groupId>com.pragma</groupId>
    <artifactId>payment-service</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>payment-service</name>
    <description>Microservicio de pagos con Event Sourcing y CQRS</description>

    <properties>
        <java.version>21</java.version>
        <spring-cloud.version>2023.0.3</spring-cloud.version>
    </properties>

    <dependencies>
        <!-- Spring Boot Starters -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webflux</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-mongodb-reactive</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-gateway</artifactId>
        </dependency>

        <!-- Kafka -->
        <dependency>
            <groupId>org.springframework.kafka</groupId>
            <artifactId>spring-kafka</artifactId>
            <version>3.2.0</version>
        </dependency>

        <!-- Reactor -->
        <dependency>
            <groupId>io.projectreactor</groupId>
            <artifactId>reactor-core</artifactId>
            <version>3.6.5</version>
        </dependency>

        <!-- Lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.32</version>
            <scope>provided</scope>
        </dependency>

        <!-- Test -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.testcontainers</groupId>
            <artifactId>mongodb</artifactId>
            <version>1.19.8</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.testcontainers</groupId>
            <artifactId>kafka</artifactId>
            <version>1.19.8</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>

// === ARCHIVO: payment-service/src/main/java/com/pragma/paymentservice/PaymentServiceApplication.java ===
package com.pragma.paymentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableKafka
@EnableAsync
public class PaymentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
                .build();
    }

    @Bean
    public reactor.core.scheduler.Scheduler boundedElasticScheduler() {
        return reactor.core.scheduler.Schedulers.newBoundedElastic(10, 100, "bounded-elastic");
    }
}

// === ARCHIVO: payment-service/src/main/resources/application.yml ===
spring:
  application:
    name: payment-service
  
  data:
    mongodb:
      uri: mongodb://localhost:27017/payment_service
      auto-index-creation: true

  kafka:
    bootstrap-servers: localhost:9092
    consumer:
      group-id: payment-service-group
      auto-offset-reset: earliest
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
      properties:
        spring.json.trusted.packages: "*"
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
      retries: 3
      acks: all

server:
  port: 8081
  
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    health:
      show-details: always

logging:
  level:
    com.pragma.paymentservice: DEBUG
    org.springframework.data.mongodb: DEBUG
    org.springframework.kafka: DEBUG
    reactor.netty.http.client: DEBUG

app:
  fraud-service:
    url: http://localhost:8082/fraud-check
  
  event-sourcing:
    event-store-collection: payment_events
    snapshot-frequency: 10
    max-snapshots: 5

// === ARCHIVO: payment-service/src/main/java/com/pragma/paymentservice/domain/model/PaymentAggregate.java ===
package com.pragma.paymentservice.domain.model;

import com.pragma.paymentservice.domain.event.PaymentEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PaymentAggregate {
    private UUID paymentId;
    private UUID orderId;
    private String cardNumber;
    private BigDecimal amount;
    private PaymentStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<PaymentEvent> events;

    public enum PaymentStatus {
        PENDING,
        AUTHORIZED,
        FAILED,
        SETTLED
    }

    public PaymentAggregate(UUID paymentId, UUID orderId, String cardNumber, BigDecimal amount) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.cardNumber = validateCardNumber(cardNumber);
        this.amount = validateAmount(amount);
        this.status = PaymentStatus.PENDING;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.events = new ArrayList<>();
    }

    private String validateCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 13 || cardNumber.length() > 19) {
            throw new IllegalArgumentException("Número de tarjeta inválido");
        }
        return cardNumber;
    }

    private BigDecimal validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Monto debe ser positivo");
        }
        return amount;
    }

    public Mono<PaymentAggregate> applyEvent(PaymentEvent event) {
        return Mono.just(this)
                .flatMap(aggregate -> {
                    switch (event.getEventType()) {
                        case PAYMENT_CREATED:
                            this.status = PaymentStatus.PENDING;
                            break;
                        case PAYMENT_AUTHORIZED:
                            this.status = PaymentStatus.AUTHORIZED;
                            break;
                        case PAYMENT_FAILED:
                            this.status = PaymentStatus.FAILED;
                            break;
                        case PAYMENT_SETTLED:
                            this.status = PaymentStatus.SETTLED;
                            break;
                        default:
                            return Mono.error(new IllegalArgumentException("Tipo de evento desconocido"));
                    }
                    this.updatedAt = LocalDateTime.now();
                    this.events.add(event);
                    return Mono.just(this.toBuilder().build());
                });
    }

    public Flux<PaymentEvent> getUncommittedEvents() {
        return Flux.fromIterable(this.events);
    }

    public Mono<PaymentAggregate> clearUncommittedEvents() {
        return Mono.just(this.toBuilder()
                .events(new ArrayList<>())
                .build());
    }
}

// === ARCHIVO: payment-service/src/main/java/com/pragma/paymentservice/domain/model/PaymentEntity.java ===
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

// === ARCHIVO: payment-service/src/main/java/com/pragma/paymentservice/domain/event/PaymentEvent.java ===
package com.pragma.paymentservice.domain.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public abstract class PaymentEvent {
    private UUID eventId;
    private UUID paymentId;
    private LocalDateTime eventTime;
    private EventType eventType;

    public enum EventType {
        PAYMENT_CREATED,
        PAYMENT_AUTHORIZED,
        PAYMENT_FAILED,
        PAYMENT_SETTLED
    }
}

// === ARCHIVO: fraud-service/pom.xml ===
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.4.0</version>
        <relativePath/>
    </parent>

    <groupId>com.pragma</groupId>
    <artifactId>fraud-service</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>fraud-service</name>
    <description>Microservicio de detección de fraude con análisis de riesgo en tiempo real</description>

    <properties>
        <java.version>21</java.version>
        <spring-cloud.version>2023.0.3</spring-cloud.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webflux</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-mongodb-reactive</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.kafka</groupId>
            <artifactId>spring-kafka</artifactId>
            <version>3.2.0</version>
        </dependency>
        <dependency>
            <groupId>io.projectreactor</groupId>
            <artifactId>reactor-core</artifactId>
            <version>3.6.5</version>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.32</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.testcontainers</groupId>
            <artifactId>mongodb</artifactId>
            <version>1.19.8</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.testcontainers</groupId>
            <artifactId>kafka</artifactId>
            <version>1.19.8</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>

// === ARCHIVO: fraud-service/src/main/java/com/pragma/fraudservice/FraudServiceApplication.java ===
package com.pragma.fraudservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.BoundedElasticScheduler;

import java.util.concurrent.TimeUnit;

/**
 * Punto de entrada del microservicio de detección de fraude.
 * Configura el cliente Web para comunicación con servicios externos
 * y el scheduler para operaciones bloqueantes.
 */
@SpringBootApplication
public class FraudServiceApplication {

    private static final int BOUNDED_ELASTIC_THREADS = 100;
    private static final int BOUNDED_ELASTIC_QUEUE_SIZE = 1000;

    private final WebClient webClient;
    private final Scheduler boundedElasticScheduler;

    public FraudServiceApplication(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("http://localhost:8080")
                .filter((request, next) -> {
                    return next.exchange(request);
                })
                .build();
        
        this.boundedElasticScheduler = BoundedElasticScheduler.create(
                BOUNDED_ELASTIC_THREADS,
                BOUNDED_ELASTIC_QUEUE_SIZE,
                true,
                "fraud-bounded"
        );
    }

    public static void main(String[] args) {
        SpringApplication.run(FraudServiceApplication.class, args);
    }

    public WebClient webClient() {
        return this.webClient;
    }

    public Scheduler boundedElasticScheduler() {
        return this.boundedElasticScheduler;
    }

    /**
     * Calcula el nivel de riesgo basado en múltiples factores.
     * @param amount monto de la transacción
     * @param cardNumber número de tarjeta
     * @param customerId identificador del cliente
     * @return nivel de riesgo entre 0.0 y 1.0
     */
    public double calculateRiskLevel(java.math.BigDecimal amount, String cardNumber, String customerId) {
        double baseRisk = 0.1;
        
        if (amount.compareTo(new java.math.BigDecimal("10000")) > 0) {
            baseRisk += 0.3;
        } else if (amount.compareTo(new java.math.BigDecimal("5000")) > 0) {
            baseRisk += 0.15;
        }
        
        if (cardNumber != null && cardNumber.startsWith("4111")) {
            baseRisk += 0.1;
        }
        
        int customerIdHash = customerId != null ? customerId.hashCode() : 0;
        if (customerIdHash % 10 == 0) {
            baseRisk += 0.2;
        }
        
        return Math.min(baseRisk, 1.0);
    }

    /**
     * Determina si una transacción debe ser marcada como sospechosa.
     * @param riskLevel nivel de riesgo calculado
     * @param velocity número de transacciones recientes
     * @return true si la transacción es sospechosa
     */
    public boolean isSuspicious(double riskLevel, int velocity) {
        if (riskLevel > 0.7) {
            return true;
        }
        if (velocity > 10 && riskLevel > 0.4) {
            return true;
        }
        return velocity > 20;
    }

    /**
     * Genera un veredicto de fraude basado en el análisis completo.
     * @param riskLevel nivel de riesgo
     * @param isSuspicious indicadores de sospecha
     * @return veredicto de fraude
     */
    public FraudVerdict generateFraudVerdict(double riskLevel, boolean isSuspicious) {
        if (riskLevel > 0.8 || isSuspicious) {
            return FraudVerdict.REJECT;
        } else if (riskLevel > 0.5) {
            return FraudVerdict.REVIEW;
        } else {
            return FraudVerdict.APPROVE;
        }
    }

    /**
     * Enum que representa los posibles veredictos del análisis de fraude.
     */
    public enum FraudVerdict {
        APPROVE(0, "Transacción aprobada"),
        REVIEW(1, "Requiere revisión manual"),
        REJECT(2, "Transacción rechazada por riesgo de fraude");

        private final int code;
        private final String description;

        FraudVerdict(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public int getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }
    }
}

// === ARCHIVO: fraud-service/src/main/resources/application.yml ===
server:
  port: 8083

spring:
  application:
    name: fraud-service
  data:
    mongodb:
      uri: mongodb://localhost:27017/fraud_db
      auto-index-creation: true
      uuid-representation: standard
  kafka:
    bootstrap-servers: localhost:9092
    consumer:
      group-id: fraud-service-group
      auto-offset-reset: earliest
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.apache.kafka.common.serialization.StringDeserializer
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer

management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    health:
      show-details: always
  health:
    mongo:
      enabled: true
    kafka:
      enabled: true

fraud:
  analysis:
    risk-thresholds:
      low: 0.3
      medium: 0.6
      high: 0.8
    velocity:
      window-minutes: 60
      max-transactions: 10
    rules:
      - name: high-amount
        threshold: 10000
        risk-increase: 0.3
      - name: velocity-check
        max-transactions: 20
        risk-increase: 0.2
      - name: suspicious-card-pattern
        patterns: ["4111", "4000"]
        risk-increase: 0.15
  external-services:
    risk-bureau:
      url: http://risk-bureau:8084/api/v1/risk
      timeout-ms: 5000
      retry-attempts: 3
    blacklist:
      url: http://blacklist:8085/api/v1/check
      timeout-ms: 3000
      retry-attempts: 2

logging:
  level:
    root: INFO
    com.pragma.fraudservice: DEBUG
    org.springframework.kafka: INFO
    org.mongodb.driver: INFO
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"

// === ARCHIVO: fraud-service/src/main/java/com/pragma/fraudservice/application/FraudCheckService.java ===
package com.pragma.fraudservice.application;

import com.pragma.fraudservice.domain.model.FraudCheckResult;
import com.pragma.fraudservice.domain.model.FraudCheckRequest;
import com.pragma.fraudservice.domain.repository.FraudCheckRepository;
import com.pragma.fraudservice.infrastructure.client.RiskBureauClient;
import com.pragma.fraudservice.infrastructure.client.FraudEngineClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class FraudCheckService {

    private final FraudCheckRepository fraudCheckRepository;
    private final FraudEngineClient fraudEngineClient;
    private final RiskBureauClient riskBureauClient;

    public Mono<FraudCheckResult> performFraudCheck(FraudCheckRequest request) {
        log.info("Iniciando validación antifraude para paymentId: {} y orderId: {}", 
                request.paymentId(), request.orderId());
        
        return validateWithFraudEngine(request)
                .flatMap(fraudResult -> {
                    if (fraudResult.isBlocked()) {
                        log.warn("Pago bloqueado por motor antifraude para paymentId: {}", 
                                request.paymentId());
                        return saveBlockedResult(request, fraudResult.getReason());
                    }
                    return validateWithRiskBureau(request)
                            .flatMap(riskResult -> {
                                if (riskResult.isHighRisk()) {
                                    log.warn("Alto riesgo detectado por buró para paymentId: {}", 
                                            request.paymentId());
                                    return saveHighRiskResult(request, riskResult.getRiskLevel());
                                }
                                return saveApprovedResult(request);
                            });
                })
                .doOnSuccess(result -> log.info("Validación antifraude completada para paymentId: {} con resultado: {}", 
                        request.paymentId(), result.getStatus()))
                .doOnError(error -> log.error("Error en validación antifraude para paymentId: {} - Error: {}", 
                        request.paymentId(), error.getMessage()));
    }

    private Mono<FraudCheckResult> validateWithFraudEngine(FraudCheckRequest request) {
        return fraudEngineClient.checkFraud(
                request.paymentId(),
                request.cardNumber(),
                request.amount(),
                request.customerId()
        ).defaultIfEmpty(createDefaultFraudResult(request.paymentId(), false, "APPROVED"));
    }

    private Mono<FraudCheckResult> validateWithRiskBureau(FraudCheckRequest request) {
        return riskBureauClient.assessRisk(
                request.customerId(),
                request.orderId(),
                request.amount()
        ).defaultIfEmpty(createDefaultRiskResult(request.paymentId(), false, "LOW"));
    }

    private FraudCheckResult createDefaultFraudResult(UUID paymentId, boolean blocked, String reason) {
        return FraudCheckResult.builder()
                .checkId(UUID.randomUUID())
                .paymentId(paymentId)
                .blocked(blocked)
                .reason(reason)
                .checkedAt(LocalDateTime.now())
                .build();
    }

    private FraudCheckResult createDefaultRiskResult(UUID paymentId, boolean highRisk, String riskLevel) {
        return FraudCheckResult.builder()
                .checkId(UUID.randomUUID())
                .paymentId(paymentId)
                .blocked(highRisk)
                .reason("RISK_ASSESSMENT: " + riskLevel)
                .checkedAt(LocalDateTime.now())
                .build();
    }

    private Mono<FraudCheckResult> saveBlockedResult(FraudCheckRequest request, String reason) {
        FraudCheckResult result = FraudCheckResult.builder()
                .checkId(UUID.randomUUID())
                .paymentId(request.paymentId())
                .orderId(request.orderId())
                .customerId(request.customerId())
                .status("BLOCKED")
                .blocked(true)
                .reason(reason)
                .checkedAt(LocalDateTime.now())
                .fraudScore(100)
                .build();
        return fraudCheckRepository.save(result)
                .thenReturn(result);
    }

    private Mono<FraudCheckResult> saveHighRiskResult(FraudCheckRequest request, String riskLevel) {
        FraudCheckResult result = FraudCheckResult.builder()
                .checkId(UUID.randomUUID())
                .paymentId(request.paymentId())
                .orderId(request.orderId())
                .customerId(request.customerId())
                .status("HIGH_RISK")
                .blocked(true)
                .reason("HIGH_RISK: " + riskLevel)
                .checkedAt(LocalDateTime.now())
                .fraudScore(75)
                .build();
        return fraudCheckRepository.save(result)
                .thenReturn(result);
    }

    private Mono<FraudCheckResult> saveApprovedResult(FraudCheckRequest request) {
        FraudCheckResult result = FraudCheckResult.builder()
                .checkId(UUID.randomUUID())
                .paymentId(request.paymentId())
                .orderId(request.orderId())
                .customerId(request.customerId())
                .status("APPROVED")
                .blocked(false)
                .reason("APPROVED")
                .checkedAt(LocalDateTime.now())
                .fraudScore(10)
                .build();
        return fraudCheckRepository.save(result)
                .thenReturn(result);
    }

    public Mono<FraudCheckResult> getFraudCheckResult(UUID paymentId) {
        log.info("Consultando resultado de fraude para paymentId: {}", paymentId);
        return fraudCheckRepository.findByPaymentId(paymentId)
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("No se encontró resultado de fraude para paymentId: {}", paymentId);
                    return Mono.empty();
                }));
    }

    public Flux<FraudCheckResult> getFraudCheckHistory(String customerId, LocalDateTime fromDate) {
        log.info("Consultando historial de fraude para customerId: {} desde: {}", customerId, fromDate);
        return fraudCheckRepository.findByCustomerIdAndCheckedAtAfter(customerId, fromDate);
    }

    public Mono<Map<String, Object>> getFraudStatistics(LocalDateTime fromDate, LocalDateTime toDate) {
        log.info("Generando estadísticas de fraude desde: {} hasta: {}", fromDate, toDate);
        return fraudCheckRepository.findByCheckedAtBetween(fromDate, toDate)
                .collectList()
                .map(results -> {
                    Map<String, Object> stats = new HashMap<>();
                    long total = results.size();
                    long blocked = results.stream().filter(FraudCheckResult::isBlocked).count();
                    long approved = total - blocked;
                    double blockRate = total > 0 ? (blocked * 100.0 / total) : 0.0;
                    double avgScore = results.stream()
                            .mapToInt(FraudCheckResult::getFraudScore)
                            .average()
                            .orElse(0.0);
                    stats.put("totalChecks", total);
                    stats.put("blocked", blocked);
                    stats.put("approved", approved);
                    stats.put("blockRate", blockRate);
                    stats.put("averageFraudScore", avgScore);
                    stats.put("fromDate", fromDate);
                    stats.put("toDate", toDate);
                    return stats;
                });
    }
}

// === ARCHIVO: fraud-service/src/main/java/com/pragma/fraudservice/infrastructure/messaging/FraudEventListener.java ===
package com.pragma.fraudservice.infrastructure.messaging;

import com.pragma.fraudservice.application.FraudCheckService;
import com.pragma.fraudservice.domain.model.FraudCheckRequest;
import com.pragma.fraudservice.domain.model.FraudCheckResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Map;
import java.util.HashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class FraudEventListener {

    private final FraudCheckService fraudCheckService;
    private final FraudResultPublisher fraudResultPublisher;

    @KafkaListener(topics = "${app.kafka.topics.payment-created:payment-created}", 
                   groupId = "${spring.kafka.consumer.group-id:fraud-service-group}",
                   containerFactory = "kafkaListenerContainerFactory")
    public void handlePaymentCreatedEvent(Map<String, Object> eventData, Acknowledgment acknowledgment) {
        log.info("Recibido evento de pago creado: {}", eventData);
        
        try {
            UUID paymentId = UUID.fromString((String) eventData.get("paymentId"));
            UUID orderId = UUID.fromString((String) eventData.get("orderId"));
            String cardNumber = (String) eventData.get("cardNumber");
            String customerId = (String) eventData.get("customerId");
            Double amount = ((Number) eventData.get("amount")).doubleValue();
            
            FraudCheckRequest request = FraudCheckRequest.builder()
                    .paymentId(paymentId)
                    .orderId(orderId)
                    .cardNumber(maskCardNumber(cardNumber))
                    .customerId(customerId)
                    .amount(java.math.BigDecimal.valueOf(amount))
                    .build();
            
            fraudCheckService.performFraudCheck(request)
                    .doOnSuccess(result -> {
                        log.info("Resultado de fraude para paymentId {}: {}", 
                                paymentId, result.getStatus());
                        publishFraudCheckResult(result, eventData);
                        acknowledgment.acknowledge();
                    })
                    .doOnError(error -> {
                        log.error("Error procesando evento para paymentId {}: {}", 
                                paymentId, error.getMessage());
                        handleFailure(paymentId, error.getMessage(), eventData);
                        acknowledgment.acknowledge();
                    })
                    .subscribe();
            
        } catch (Exception e) {
            log.error("Error al parsear evento de pago creado: {}", e.getMessage(), e);
            acknowledgment.acknowledge();
        }
    }

    @KafkaListener(topics = "${app.kafka.topics.payment-validation:payment-validation}", 
                   groupId = "${spring.kafka.consumer.group-id:fraud-service-group}",
                   containerFactory = "kafkaListenerContainerFactory")
    public void handlePaymentValidationEvent(Map<String, Object> eventData, Acknowledgment acknowledgment) {
        log.info("Recibido evento de validación de pago: {}", eventData);
        
        try {
            UUID paymentId = UUID.fromString((String) eventData.get("paymentId"));
            String validationType = (String) eventData.get("validationType");
            
            if ("RECHECK".equals(validationType)) {
                performRecheck(paymentId, eventData)
                        .doOnSuccess(result -> {
                            publishFraudCheckResult(result, eventData);
                            acknowledgment.acknowledge();
                        })
                        .doOnError(error -> {
                            log.error("Error en recheck para paymentId {}: {}", paymentId, error.getMessage());
                            acknowledgment.acknowledge();
                        })
                        .subscribe();
            } else {
                acknowledgment.acknowledge();
            }
            
        } catch (Exception e) {
            log.error("Error al parsear evento de validación: {}", e.getMessage(), e);
            acknowledgment.acknowledge();
        }
    }

    private Mono<FraudCheckResult> performRecheck(UUID paymentId, Map<String, Object> eventData) {
        return fraudCheckService.getFraudCheckResult(paymentId)
                .switchIfEmpty(Mono.error(new IllegalStateException("No existe resultado de fraude para recheck: " + paymentId)))
                .flatMap(existingResult -> {
                    log.info("Rechecando resultado existente para paymentId: {}, status: {}", 
                            paymentId, existingResult.getStatus());
                    String cardNumber = maskCardNumber((String) eventData.get("cardNumber"));
                    String customerId = (String) eventData.get("customerId");
                    Double amount = ((Number) eventData.get("amount")).doubleValue();
                    
                    FraudCheckRequest request = FraudCheckRequest.builder()
                            .paymentId(paymentId)
                            .orderId(existingResult.getOrderId())
                            .cardNumber(cardNumber)
                            .customerId(customerId)
                            .amount(java.math.BigDecimal.valueOf(amount))
                            .recheck(true)
                            .previousCheckId(existingResult.getCheckId())
                            .build();
                    
                    return fraudCheckService.performFraudCheck(request);
                });
    }

    private void publishFraudCheckResult(FraudCheckResult result, Map<String, Object> originalEvent) {
        Map<String, Object> fraudEvent = new HashMap<>();
        fraudEvent.put("paymentId", result.getPaymentId().toString());
        fraudEvent.put("orderId", result.getOrderId() != null ? result.getOrderId().toString() : null);
        fraudEvent.put("customerId", result.getCustomerId());
        fraudEvent.put("fraudCheckId", result.getCheckId().toString());
        fraudEvent.put("status", result.getStatus());
        fraudEvent.put("blocked", result.isBlocked());
        fraudEvent.put("reason", result.getReason());
        fraudEvent.put("fraudScore", result.getFraudScore());
        fraudEvent.put("checkedAt", result.getCheckedAt().toString());
        fraudEvent.put("timestamp", LocalDateTime.now().toString());
        fraudEvent.put("originalEvent", originalEvent);
        
        String topic = result.isBlocked() ? 
                "payment-fraud-blocked" : "payment-fraud-approved";
        
        fraudResultPublisher.publishFraudResult(fraudEvent, topic);
        log.info("Publicado resultado de fraude en topic {} para paymentId: {}", 
                topic, result.getPaymentId());
    }

    private void handleFailure(UUID paymentId, String errorMessage, Map<String, Object> eventData) {
        Map<String, Object> failureEvent = new HashMap<>();
        failureEvent.put("paymentId", paymentId.toString());
        failureEvent.put("failureType", "FRAUD_CHECK_FAILURE");
        failureEvent.put("errorMessage", errorMessage);
        failureEvent.put("timestamp", LocalDateTime.now().toString());
        failureEvent.put("originalEvent", eventData);
        
        fraudResultPublisher.publishFraudResult(failureEvent, "payment-fraud-failure");
        log.warn("Publicado evento de fracaso para paymentId: {}", paymentId);
    }

    private String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4) {
            return "****";
        }
        return "****-****-****-" + cardNumber.substring(cardNumber.length() - 4);
    }
}

// === ARCHIVO: api-gateway/pom.xml ===
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.4.0</version>
        <relativePath/>
    </parent>

    <groupId>com.pragma</groupId>
    <artifactId>api-gateway</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>api-gateway</name>
    <description>API Gateway para el sistema de pagos distribuidos</description>

    <properties>
        <java.version>21</java.version>
        <spring-cloud.version>2023.0.3</spring-cloud.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webflux</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-gateway</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-mongodb-reactive</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.kafka</groupId>
            <artifactId>spring-kafka</artifactId>
            <version>3.2.0</version>
        </dependency>
        <dependency>
            <groupId>io.projectreactor</groupId>
            <artifactId>reactor-core</artifactId>
            <version>3.6.5</version>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.32</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.testcontainers</groupId>
            <artifactId>mongodb</artifactId>
            <version>1.19.8</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.testcontainers</groupId>
            <artifactId>kafka</artifactId>
            <version>1.19.8</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>

// === ARCHIVO: api-gateway/src/main/java/com/pragma/apigateway/ApiGatewayApplication.java ===
package com.pragma.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@SpringBootApplication
public class ApiGatewayApplication {

    private static final String PAYMENT_SERVICE_HOST = "localhost";
    private static final int PAYMENT_SERVICE_PORT = 8080;
    private static final String FRAUD_SERVICE_HOST = "localhost";
    private static final int FRAUD_SERVICE_PORT = 8081;

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://" + PAYMENT_SERVICE_HOST + ":" + PAYMENT_SERVICE_PORT)
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(16 * 1024 * 1024))
                .build();
    }

    @Bean
    public Scheduler boundedElasticScheduler() {
        return Schedulers.boundedElastic();
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("payment-service", r -> r
                        .path("/api/payments/**")
                        .filters(f -> f
                                .stripPrefix(1)
                                .addRequestHeader("X-Gateway", "api-gateway")
                                .addResponseHeader("X-Response-Time", "${executionTime}")
                                .circuitBreaker(config -> config
                                        .setName("paymentCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/payments")))
                        .uri("http://" + PAYMENT_SERVICE_HOST + ":" + PAYMENT_SERVICE_PORT))
                .route("fraud-service", r -> r
                        .path("/api/fraud/**")
                        .filters(f -> f
                                .stripPrefix(1)
                                .addRequestHeader("X-Gateway", "api-gateway")
                                .addResponseHeader("X-Response-Time", "${executionTime}")
                                .circuitBreaker(config -> config
                                        .setName("fraudCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/fraud")))
                        .uri("http://" + FRAUD_SERVICE_HOST + ":" + FRAUD_SERVICE_PORT))
                .route("health-check", r -> r
                        .path("/health")
                        .filters(f -> f
                                .setPath("/actuator/health"))
                        .uri("http://" + PAYMENT_SERVICE_HOST + ":" + PAYMENT_SERVICE_PORT))
                .route("actuator", r -> r
                        .path("/actuator/**")
                        .uri("http://" + PAYMENT_SERVICE_HOST + ":" + PAYMENT_SERVICE_PORT))
                .build();
    }
}

// === ARCHIVO: api-gateway/src/main/resources/application.yml ===
server:
  port: 8082

spring:
  application:
    name: api-gateway
  cloud:
    gateway:
      routes:
        - id: payment-service
          uri: http://localhost:8080
          predicates:
            - Path=/api/payments/**
          filters:
            - StripPrefix=1
            - AddRequestHeader=X-Gateway,api-gateway
            - name: CircuitBreaker
              args:
                name: paymentCircuitBreaker
                fallbackUri: forward:/fallback/payments
        - id: fraud-service
          uri: http://localhost:8081
          predicates:
            - Path=/api/fraud/**
          filters:
            - StripPrefix=1
            - AddRequestHeader=X-Gateway,api-gateway
            - name: CircuitBreaker
              args:
                name: fraudCircuitBreaker
                fallbackUri: forward:/fallback/fraud
        - id: actuator
          uri: http://localhost:8080
          predicates:
            - Path=/actuator/**
      globalcors:
        cors-configurations:
          '[/**]':
            allowedOrigins:
              - http://localhost:3000
              - http://localhost:4200
            allowedMethods:
              - GET
              - POST
              - PUT
              - DELETE
              - PATCH
              - OPTIONS
            allowedHeaders:
              - "*"
            allowCredentials: true
            maxAge: 3600
      httpclient:
        connect-timeout: 5000
        response-timeout: 10s
        pool:
          type: elastic
          max-connections: 200
          acquire-timeout: 30s
  data:
    mongodb:
      uri: mongodb://localhost:27017/gateway
      database: gateway
      auto-index-creation: true
  kafka:
    bootstrap-servers: localhost:9092
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
      acks: all
      retries: 3
    consumer:
      group-id: api-gateway-group
      auto-offset-reset: earliest
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
      properties:
        spring.json.trusted.packages: "com.pragma.*"

management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,gateway
      base-path: /actuator
  endpoint:
    health:
      show-details: always
    gateway:
      enabled: true
  metrics:
    tags:
      application: ${spring.application.name}

logging:
  level:
    root: INFO
    org.springframework.cloud.gateway: DEBUG
    reactor.netty: INFO
    org.springframework.kafka: WARN
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"

resilience4j:
  circuitbreaker:
    instances:
      paymentCircuitBreaker:
        registerHealthIndicator: true
        slidingWindowSize: 10
        minimumNumberOfCalls: 5
        permittedNumberOfCallsInHalfOpenState: 3
        automaticTransitionFromOpenToHalfOpenEnabled: true
        waitDurationInOpenState: 5s
        failureRateThreshold: 50
        eventConsumerBufferSize: 10
      fraudCircuitBreaker:
        registerHealthIndicator: true
        slidingWindowSize: 10
        minimumNumberOfCalls: 5
        permittedNumberOfCallsInHalfOpenState: 3
        automaticTransitionFromOpenToHalfOpenEnabled: true
        waitDurationInOpenState: 5s
        failureRateThreshold: 50
        eventConsumerBufferSize: 10
  retry:
    instances:
      paymentRetry:
        maxAttempts: 3
        waitDuration: 1000ms
        enableExponentialBackoff: true
        exponentialBackoffMultiplier: 2
        retryExceptions:
          - java.io.IOException
          - java.util.concurrent.TimeoutException
      fraudRetry:
        maxAttempts: 3
        waitDuration: 1000ms
        enableExponentialBackoff: true
        exponentialBackoffMultiplier: 2
  ratelimiter:
    instances:
      default:
        limitForPeriod: 100
        limitRefreshPeriod: 1s
        timeoutDuration: 0

// === ARCHIVO: api-gateway/src/main/java/com/pragma/apigateway/config/GatewayConfig.java ===
package com.pragma.apigateway.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyRequestBodyGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.function.BiFunction;

/**
 * Configuración central del API Gateway para el sistema de pagos.
 * Define las rutas hacia los microservicios y los filtros globales
 * que se aplican a todas las peticiones entrantes.
 * 
 * Este gateway actúa como punto de entrada único para:
 * - payment-service: procesamiento de transacciones
 * - fraud-service: validación antifraude
 */
@Configuration
public class GatewayConfig {

    private final WebClient.Builder webClientBuilder;
    
    private static final String PAYMENT_SERVICE_HOST = "payment-service";
    private static final String FRAUD_SERVICE_HOST = "fraud-service";
    private static final int PAYMENT_SERVICE_PORT = 8081;
    private static final int FRAUD_SERVICE_PORT = 8082;
    private static final String CORRELATION_HEADER = "X-Correlation-ID";
    private static final String REQUEST_ID_HEADER = "X-Request-ID";

    public GatewayConfig(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    /**
     * Define las rutas del gateway hacia los distintos microservicios.
     * Cada ruta especifica el URI destino, los predicados y los filtros aplicados.
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                
                // Ruta para el servicio de pagos - comandos de creación de pago
                .route("payment-create-route", r -> r
                        .path("/api/v1/payments")
                        .method(HttpMethod.POST)
                        .filters(f -> f
                                .filter(addCorrelationIdFilter())
                                .filter(addRequestTimingFilter())
                                .filter(addSecurityHeadersFilter())
                                .filter(validateContentTypeFilter())
                                .stripPrefix(0)
                                .removeRequestHeader("X-Forwarded-For")
                        )
                        .uri(format("http://%s:%d", PAYMENT_SERVICE_HOST, PAYMENT_SERVICE_PORT))
                )
                
                // Ruta para consultas de estado de pago
                .route("payment-query-route", r -> r
                        .path("/api/v1/payments/**")
                        .method(HttpMethod.GET)
                        .filters(f -> f
                                .filter(addCorrelationIdFilter())
                                .filter(addRequestTimingFilter())
                                .filter(addSecurityHeadersFilter())
                                .stripPrefix(0)
                        )
                        .uri(format("http://%s:%d", PAYMENT_SERVICE_HOST, PAYMENT_SERVICE_PORT))
                )
                
                // Ruta para verificación de fraude
                .route("fraud-check-route", r -> r
                        .path("/api/v1/fraud-check")
                        .method(HttpMethod.POST)
                        .filters(f -> f
                                .filter(addCorrelationIdFilter())
                                .filter(addRequestTimingFilter())
                                .filter(forwardCorrelationIdFilter())
                                .stripPrefix(0)
                        )
                        .uri(format("http://%s:%d", FRAUD_SERVICE_HOST, FRAUD_SERVICE_PORT))
                )
                
                // Ruta para consulta de riesgo
                .route("risk-assessment-route", r -> r
                        .path("/api/v1/risk-assessment/**")
                        .filters(f -> f
                                .filter(addCorrelationIdFilter())
                                .filter(addRequestTimingFilter())
                                .stripPrefix(0)
                        )
                        .uri(format("http://%s:%d", FRAUD_SERVICE_HOST, FRAUD_SERVICE_PORT))
                )
                
                // Health check para el gateway
                .route("health-route", r -> r
                        .path("/actuator/health")
                        .filters(f -> f.setStatus(HttpStatus.OK))
                        .uri("forward:/actuator/health")
                )
                
                .build();
    }

    /**
     * Filtro global que añade un ID de correlación a cada petición.
     * Este ID se propagation a través de todos los microservicios
     * para trazabilidad distribuida.
     */
    @Bean
    public GatewayFilter addCorrelationIdFilter() {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String correlationId = request.getHeaders().getFirst(CORRELATION_HEADER);
            
            if (correlationId == null || correlationId.isBlank()) {
                correlationId = UUID.randomUUID().toString();
            }
            
            ServerHttpRequest modifiedRequest = request.mutate()
                    .header(CORRELATION_HEADER, correlationId)
                    .header(REQUEST_ID_HEADER, UUID.randomUUID().toString())
                    .build();
            
            ServerWebExchange modifiedExchange = exchange.mutate()
                    .request(modifiedRequest)
                    .build();
            
            return chain.filter(modifiedExchange);
        };
    }

    /**
     * Filtro que registra el tiempo de ejecución de cada petición.
     * Añade headers de timing para monitoreo de rendimiento.
     */
    @Bean
    public GatewayFilter addRequestTimingFilter() {
        return (exchange, chain) -> {
            Instant startTime = Instant.now();
            ServerHttpRequest request = exchange.getRequest();
            
            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> {
                        Instant endTime = Instant.now();
                        Duration duration = Duration.between(startTime, endTime);
                        
                        ServerHttpResponse response = exchange.getResponse();
                        response.getHeaders().add("X-Response-Time-Millis", String.valueOf(duration.toMillis()));
                        response.getHeaders().add("X-Response-Time-Nanos", String.valueOf(duration.toNanos()));
                        
                        // Log de la petición para auditoría
                        String correlationId = request.getHeaders().getFirst(CORRELATION_HEADER);
                        String method = request.getMethod().name();
                        String path = request.getURI().getPath();
                        int status = response.getStatusCode().value();
                        
                        System.out.printf("[GATEWAY] %s %s -> %d (%dms) [correlation: %s]%n", 
                                method, path, status, duration.toMillis(), correlationId);
                    }));
        };
    }

    /**
     * Filtro de seguridad que añade headers de protección contra
     * vulnerabilidades comunes en aplicaciones web.
     */
    @Bean
    public GatewayFilter addSecurityHeadersFilter() {
        return (exchange, chain) -> {
            ServerHttpResponse response = exchange.getResponse();
            
            // Headers de seguridad HTTPS
            response.getHeaders().add("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
            response.getHeaders().add("X-Content-Type-Options", "nosniff");
            response.getHeaders().add("X-Frame-Options", "DENY");
            response.getHeaders().add("X-XSS-Protection", "1; mode=block");
            response.getHeaders().add("Referrer-Policy", "strict-origin-when-cross-origin");
            response.getHeaders().add("Permissions-Policy", "geolocation=(), microphone=(), camera=()");
            
            // Content Security Policy básica
            response.getHeaders().add("Content-Security-Policy", 
                    "default-src 'self'; script-src 'self' 'unsafe-inline'; style-src 'self' 'unsafe-inline'");
            
            return chain.filter(exchange);
        };
    }

    /**
     * Filtro que valida el Content-Type de las peticiones POST/PUT.
     * Solo acepta application/json.
     */
    @Bean
    public GatewayFilter validateContentTypeFilter() {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            HttpMethod method = request.getMethod();
            
            if (method == HttpMethod.POST || method == HttpMethod.PUT) {
                MediaType contentType = request.getHeaders().getContentType();
                
                if (contentType == null || !contentType.includes(MediaType.APPLICATION_JSON)) {
                    ServerHttpResponse response = exchange.getResponse();
                    response.setStatusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
                    response.getHeaders().add("X-Error", "Content-Type must be application/json");
                    return response.setComplete();
                }
            }
            
            return chain.filter(exchange);
        };
    }

    /**
     * Filtro que reenvía el Correlation-ID al servicio destino
     * cuando la correlación viene de otro gateway o servicio.
     */
    @Bean
    public GatewayFilter forwardCorrelationIdFilter() {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String correlationId = request.getHeaders().getFirst(CORRELATION_HEADER);
            
            if (correlationId != null) {
                ServerHttpRequest modifiedRequest = request.mutate()
                        .header("X-Correlation-ID", correlationId)
                        .build();
                
                ServerWebExchange modifiedExchange = exchange.mutate()
                        .request(modifiedRequest)
                        .build();
                
                return chain.filter(modifiedExchange);
            }
            
            return chain.filter(exchange);
        };
    }

    /**
     * Filtro de transformación de cuerpo de respuesta.
     * Permite modificar la estructura de las respuestas antes de enviarlas al cliente.
     */
    @Bean
    public ModifyResponseBodyGatewayFilterFactory modifyResponseBodyGatewayFilterFactory() {
        return new ModifyResponseBodyGatewayFilterFactory();
    }

    /**
     * Filtro de transformación de cuerpo de solicitud.
     * Permite modificar la estructura de las peticiones antes de reenviarlas.
     */
    @Bean
    public ModifyRequestBodyGatewayFilterFactory modifyRequestBodyGatewayFilterFactory() {
        return new ModifyRequestBodyGatewayFilterFactory();
    }

    /**
     * WebClient configurado para comunicación reactiva entre servicios.
     * Configurado con timeouts apropiados y manejo de errores.
     */
    @Bean
    public WebClient webClient() {
        return webClientBuilder
                .clientConnector(new reactor.netty.http.client.HttpClient()
                        .responseTimeout(Duration.ofSeconds(30))
                        .option(io.netty.channel.ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                        .option(io.netty.channel.ChannelOption.SO_TIMEOUT_MILLIS, 30000))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    /**
     * Filtro para manejo de errores global del gateway.
     * Captura excepciones y retorna respuestas estructuradas.
     */
    @Bean
    public GatewayFilter errorHandlingFilter() {
        return (exchange, chain) -> {
            return chain.filter(exchange)
                    .onErrorResume(error -> {
                        ServerHttpResponse response = exchange.getResponse();
                        
                        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                        response.getHeaders().add("X-Error-Type", error.getClass().getSimpleName());
                        response.getHeaders().add("X-Error-Message", error.getMessage());
                        
                        String correlationId = exchange.getRequest().getHeaders().getFirst(CORRELATION_HEADER);
                        if (correlationId != null) {
                            response.getHeaders().add(CORRELATION_HEADER, correlationId);
                        }
                        
                        System.err.printf("[GATEWAY ERROR] %s - %s [correlation: %s]%n",
                                error.getClass().getSimpleName(),
                                error.getMessage(),
                                correlationId);
                        
                        return response.setComplete();
                    });
        };
    }

    /**
     * Filtro para rate limiting básico por IP de origen.
     * Implementación simple que evita ataques de fuerza bruta.
     */
    @Bean
    public GatewayFilter rateLimitFilter() {
        return new AbstractGatewayFilterFactory<>() {
            @Override
            public GatewayFilter apply(Object config) {
                return (exchange, chain) -> {
                    String clientIp = getClientIp(exchange.getRequest());
                    
                    // Aquí se implementaría la lógica de rate limiting
                    // Por simplicidad, se delega al servicio de autenticación
                    
                    return chain.filter(exchange);
                };
            }
        };
    }

    /**
     * Extrae la IP real del cliente, considerando proxys y load balancers.
     */
    private String getClientIp(ServerHttpRequest request) {
        String xForwardedFor = request.getHeaders().getFirst("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isBlank()) {
            return xForwardedFor.split(",")[0].trim();
        }
        
        String xRealIp = request.getHeaders().getFirst("X-Real-IP");
        if (xRealIp != null && !xRealIp.isBlank()) {
            return xRealIp;
        }
        
        if (request.getRemoteAddress() != null) {
            return request.getRemoteAddress().getAddress().getHostAddress();
        }
        
        return "unknown";
    }

    /**
     * Helper para formatear URLs de destino.
     */
    private static String format(String template, Object... args) {
        return String.format(template, args);
    }
}

// === ARCHIVO: payment-service/src/main/java/com/pragma/paymentservice/domain/event/PaymentCreatedEvent.java ===
package com.pragma.paymentservice.domain.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class PaymentCreatedEvent extends PaymentEvent {
    private final UUID orderId;
    private final String cardNumber;
    private final BigDecimal amount;
    private final String currency;
    private final String paymentMethod;
    private final String merchantId;

    public PaymentCreatedEvent(
            UUID eventId,
            UUID paymentId,
            LocalDateTime eventTime,
            UUID orderId,
            String cardNumber,
            BigDecimal amount,
            String currency,
            String paymentMethod,
            String merchantId) {
        super(eventId, paymentId, eventTime, EventType.CREATED);
        this.orderId = orderId;
        this.cardNumber = cardNumber;
        this.amount = amount;
        this.currency = currency;
        this.paymentMethod = paymentMethod;
        this.merchantId = merchantId;
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

    public String getMerchantId() {
        return merchantId;
    }

    public static PaymentCreatedEvent create(UUID paymentId, UUID orderId, String cardNumber, 
                                               BigDecimal amount, String currency, 
                                               String paymentMethod, String merchantId) {
        return new PaymentCreatedEvent(
            UUID.randomUUID(),
            paymentId,
            LocalDateTime.now(),
            orderId,
            cardNumber,
            amount,
            currency,
            paymentMethod,
            merchantId
        );
    }

    @Override
    public String toString() {
        return "PaymentCreatedEvent{" +
                "eventId=" + getEventId() +
                ", paymentId=" + getPaymentId() +
                ", eventTime=" + getEventTime() +
                ", orderId=" + orderId +
                ", cardNumber='****' + (cardNumber != null ? cardNumber.substring(Math.max(0, cardNumber.length() - 4)) : "") + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", merchantId='" + merchantId + '\'' +
                '}';
    }
}

// === ARCHIVO: payment-service/src/main/java/com/pragma/paymentservice/domain/service/PaymentService.java ===
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

// === ARCHIVO: payment-service/src/main/java/com/pragma/paymentservice/infrastructure/persistence/PaymentProjectionRepository.java ===
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

// === ARCHIVO: payment-service/src/main/java/com/pragma/paymentservice/application/command/CreatePaymentCommand.java ===
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

// === ARCHIVO: payment-service/src/main/java/com/pragma/paymentservice/application/command/CommandHandler.java ===
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

// === ARCHIVO: payment-service/src/main/java/com/pragma/paymentservice/application/query/PaymentQuery.java ===
package com.pragma.paymentservice.application.query;


import com.pragma.paymentservice.domain.event.PaymentEvent;
import com.pragma.paymentservice.domain.model.PaymentAggregate;
import com.pragma.paymentservice.domain.model.PaymentAggregate.PaymentStatus;
import com.pragma.paymentservice.infrastructure.persistence.EventStore;
import com.pragma.paymentservice.infrastructure.persistence.PaymentProjectionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class PaymentQuery {
    private static final Logger log = LoggerFactory.getLogger(PaymentQuery.class);

    private final EventStore eventStore;
    private final PaymentProjectionRepository projectionRepository;

    public PaymentQuery(EventStore eventStore, PaymentProjectionRepository projectionRepository) {
        this.eventStore = eventStore;
        this.projectionRepository = projectionRepository;
    }

    public Mono<PaymentQueryResult> getPaymentById(UUID paymentId) {
        log.debug("Consultando pago por ID: {}", paymentId);
        
        return projectionRepository.findByPaymentId(paymentId)
            .flatMap(projection -> {
                log.debug("Proyección encontrada para el pago: {}", paymentId);
                return Mono.just(mapToQueryResult(projection));
            })
            .switchIfEmpty(Mono.defer(() -> {
                log.debug("No se encontró proyección, reconstruyendo desde eventos para: {}", 
                          paymentId);
                return eventStore.findByPaymentId(paymentId)
                    .collectList()
                    .flatMap(events -> {
                        if (events.isEmpty()) {
                            log.warn("No se encontró ningún evento para el pago: {}", paymentId);
                            return Mono.empty();
                        }
                        return rebuildAggregateFromEvents(paymentId, events);
                    });
            }));
    }

    public Mono<PaymentQueryResult> getPaymentByOrderId(UUID orderId) {
        log.debug("Consultando pago por ID de pedido: {}", orderId);
        
        return projectionRepository.findByOrderId(orderId)
            .flatMap(projection -> {
                log.debug("Proyección encontrada para el pedido: {}", orderId);
                return Mono.just(mapToQueryResult(projection));
            })
            .switchIfEmpty(Mono.defer(() -> {
                log.debug("No se encontró proyección por orderId, buscando en eventos: {}", orderId);
                return eventStore.findByPaymentId(orderId)
                    .collectList()
                    .flatMap(events -> {
                        if (events.isEmpty()) {
                            log.warn("No se encontró ningún evento para el pedido: {}", orderId);
                            return Mono.empty();
                        }
                        return rebuildAggregateFromEvents(orderId, events);
                    });
            }));
    }

    public Flux<PaymentQueryResult> getPaymentsByStatus(PaymentStatus status) {
        log.debug("Consultando pagos con estado: {}", status);
        
        return projectionRepository.findByStatus(status.name())
            .flatMap(projection -> {
                PaymentQueryResult result = mapToQueryResult(projection);
                return Flux.just(result);
            })
            .switchIfEmpty(Flux.empty());
    }

    public Flux<PaymentQueryResult> getPaymentsByCustomer(String customerId) {
        log.debug("Consultando pagos para el cliente: {}", customerId);
        
        return projectionRepository.findByCustomerId(customerId)
            .flatMap(projection -> {
                PaymentQueryResult result = mapToQueryResult(projection);
                return Flux.just(result);
            })
            .switchIfEmpty(Flux.empty());
    }

    private Mono<PaymentQueryResult> rebuildAggregateFromEvents(UUID paymentId, 
                                                                  java.util.List<com.pragma.paymentservice.domain.event.PaymentEvent> events) {
        log.info("Reconstruyendo agregado desde {} eventos para el pago: {}", 
                 events.size(), paymentId);
        
        if (events.isEmpty()) {
            return Mono.empty();
        }

        com.pragma.paymentservice.domain.event.PaymentEvent firstEvent = events.get(0);
        PaymentAggregate aggregate = new PaymentAggregate(
            paymentId,
            firstEvent.getPaymentId(),
            "",
            java.math.BigDecimal.ZERO
        );

        return Flux.fromIterable(events)
            .flatMap(event -> aggregate.applyEvent(event))
            .last()
            .map(agg -> new PaymentQueryResult(
                paymentId,
                agg.getOrderId(),
                agg.getStatus(),
                agg.getAmount(),
                agg.getCreatedAt(),
                agg.getUpdatedAt(),
                "Reconstruido desde Event Store",
                true
            ));
    }

    private PaymentQueryResult mapToQueryResult(Object projection) {
        try {
            var method = projection.getClass().getMethod("getPaymentId");
            UUID paymentId = (UUID) method.invoke(projection);
            
            method = projection.getClass().getMethod("getOrderId");
            UUID orderId = (UUID) method.invoke(projection);
            
            method = projection.getClass().getMethod("getStatus");
            String statusStr = (String) method.invoke(projection);
            PaymentStatus status = PaymentStatus.valueOf(statusStr);
            
            method = projection.getClass().getMethod("getAmount");
            java.math.BigDecimal amount = (java.math.BigDecimal) method.invoke(projection);
            
            LocalDateTime createdAt = null;
            LocalDateTime updatedAt = null;
            
            try {
                method = projection.getClass().getMethod("getCreatedAt");
                createdAt = (LocalDateTime) method.invoke(projection);
            } catch (NoSuchMethodException e) {
                // Campo opcional
            }
            
            try {
                method = projection.getClass().getMethod("getUpdatedAt");
                updatedAt = (LocalDateTime) method.invoke(projection);
            } catch (NoSuchMethodException e) {
                // Campo opcional
            }

            return new PaymentQueryResult(
                paymentId,
                orderId,
                status,
                amount,
                createdAt,
                updatedAt,
                "Desde proyección",
                false
            );
        } catch (Exception e) {
            log.error("Error al mapear la proyección: {}", e.getMessage());
            throw new RuntimeException("Error al procesar la consulta de pago", e);
        }
    }

    public record PaymentQueryResult(
        UUID paymentId,
        UUID orderId,
        PaymentStatus status,
        java.math.BigDecimal amount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String source,
        boolean isReconstructed
    ) {
        public String getStatusDescription() {
            return switch (status) {
                case PENDING -> "Pago en proceso de validación";
                case AUTHORIZED -> "Pago autorizado";
                case FAILED -> "Pago fallido";
                case SETTLED -> "Pago liquidado";
                case CANCELLED -> "Pago cancelado";
            };
        }

        public boolean isFinalState() {
            return status == PaymentStatus.SETTLED || 
                   status == PaymentStatus.FAILED || 
                   status == PaymentStatus.CANCELLED;
        }
    }
}

// === ARCHIVO: payment-service/src/main/java/com/pragma/paymentservice/application/query/QueryHandler.java ===
package com.pragma.paymentservice.application.query;


import com.pragma.paymentservice.domain.model.PaymentStatus;
import com.pragma.paymentservice.domain.model.PaymentAggregate;
import com.pragma.paymentservice.infrastructure.persistence.EventStore;
import com.pragma.paymentservice.infrastructure.persistence.PaymentProjectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class QueryHandler {

    private final PaymentProjectionRepository projectionRepository;
    private final EventStore eventStore;

    public Mono<PaymentAggregate> getPaymentById(UUID paymentId) {
        return eventStore.readEvents(paymentId)
                .collectList()
                .filter(events -> !events.isEmpty())
                .flatMap(events -> {
                    PaymentAggregate aggregate = new PaymentAggregate(
                            paymentId,
                            UUID.fromString("00000000-0000-0000-0000-000000000000"),
                            "",
                            java.math.BigDecimal.ZERO
                    );
                    return Flux.fromIterable(events)
                            .flatMap(aggregate::applyEvent)
                            .last(aggregate);
                });
    }

    public Mono<PaymentQuery.PaymentSummary> getPaymentSummary(UUID paymentId) {
        return projectionRepository.findByPaymentId(paymentId)
                .map(entity -> new PaymentQuery.PaymentSummary(
                        entity.getPaymentId(),
                        entity.getOrderId(),
                        entity.getAmount(),
                        entity.getStatus().name(),
                        entity.getCreatedAt(),
                        entity.getUpdatedAt()
                ));
    }

    public Flux<PaymentQuery.PaymentListItem> getPaymentsByOrderId(UUID orderId) {
        return projectionRepository.findByOrderId(orderId)
                .map(entity -> new PaymentQuery.PaymentListItem(
                        entity.getPaymentId(),
                        entity.getAmount(),
                        entity.getStatus().name(),
                        entity.getCreatedAt()
                ));
    }

    public Flux<PaymentQuery.PaymentListItem> getPaymentsByStatus(String status) {
        return projectionRepository.findByStatus(com.pragma.paymentservice.domain.model.PaymentAggregate.PaymentStatus.valueOf(status))
                .map(entity -> new PaymentQuery.PaymentListItem(
                        entity.getPaymentId(),
                        entity.getAmount(),
                        entity.getStatus().name(),
                        entity.getCreatedAt()
                ));
    }

    public Mono<Long> countPaymentsByStatus(String status) {
        return projectionRepository.countByStatus(com.pragma.paymentservice.domain.model.PaymentAggregate.PaymentStatus.valueOf(status));
    }

    public Mono<PaymentQuery.PaymentDetails> getPaymentDetails(UUID paymentId) {
        return projectionRepository.findByPaymentId(paymentId)
                .map(entity -> new PaymentQuery.PaymentDetails(
                        entity.getPaymentId(),
                        entity.getOrderId(),
                        maskCardNumber(entity.getCardNumber()),
                        entity.getAmount(),
                        entity.getStatus().name(),
                        entity.getFraudCheckStatus(),
                        entity.getRiskAssessmentStatus(),
                        entity.getCreatedAt(),
                        entity.getUpdatedAt()
                ));
    }

    public Flux<PaymentQuery.PaymentListItem> getRecentPayments(int limit) {
        return projectionRepository.findTopByOrderByCreatedAtDesc(limit)
                .map(entity -> new PaymentQuery.PaymentListItem(
                        entity.getPaymentId(),
                        entity.getAmount(),
                        entity.getStatus().name(),
                        entity.getCreatedAt()
                ));
    }

    public Mono<PaymentQuery.PaymentStats> getPaymentStats() {
        return projectionRepository.countAll()
                .flatMap(total -> projectionRepository
                        .countByStatus(com.pragma.paymentservice.domain.model.PaymentAggregate.PaymentStatus.AUTHORIZED)
                        .map(authorized -> new PaymentQuery.PaymentStats(
                                total,
                                authorized,
                                total - authorized
                        )));
    }

    private String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4) {
            return "****";
        }
        return "****-****-****-" + cardNumber.substring(cardNumber.length() - 4);
    }
}

// === ARCHIVO: payment-service/src/main/java/com/pragma/paymentservice/infrastructure/persistence/EventStore.java ===
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

// === ARCHIVO: payment-service/src/main/java/com/pragma/paymentservice/infrastructure/messaging/PaymentEventPublisher.java ===
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

// === ARCHIVO: payment-service/src/main/java/com/pragma/paymentservice/infrastructure/client/FraudClient.java ===
package com.pragma.paymentservice.infrastructure.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Component
public class FraudClient {

    private final WebClient webClient;
    private final String fraudServiceBaseUrl;

    public FraudClient(
            WebClient webClient,
            @Value("${external.services.fraud-service.url:http://localhost:8082}") String fraudServiceBaseUrl) {
        this.webClient = webClient;
        this.fraudServiceBaseUrl = fraudServiceBaseUrl;
    }

    public Mono<FraudCheckResponse> checkFraud(UUID paymentId, String cardNumber, BigDecimal amount) {
        FraudCheckRequest request = new FraudCheckRequest(paymentId, cardNumber, amount);
        
        return webClient
                .post()
                .uri(fraudServiceBaseUrl + "/api/v1/fraud/check")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(FraudCheckResponse.class)
                .timeout(java.time.Duration.ofSeconds(5))
                .onErrorResume(e -> Mono.just(new FraudCheckResponse("ERROR", "Service unavailable: " + e.getMessage())));
    }

    public record FraudCheckRequest(UUID paymentId, String cardNumber, BigDecimal amount) {}

    public record FraudCheckResponse(String status, String riskLevel) {
        public boolean isApproved() {
            return "APPROVED".equalsIgnoreCase(status) || "LOW_RISK".equalsIgnoreCase(riskLevel);
        }
    }
}

// === ARCHIVO: payment-service/src/main/java/com/pragma/paymentservice/infrastructure/config/EventSourcingConfig.java ===
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

// === ARCHIVO: payment-service/src/test/java/com/pragma/paymentservice/application/command/CommandHandlerTest.java ===
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

// === ARCHIVO: docker-compose.yml ===
version: '3.8'

services:
  mongodb:
    image: mongo:7.0
    container_name: pragma-mongodb
    ports:
      - "27017:27017"
    environment:
      MONGO_INITDB_ROOT_USERNAME: admin
      MONGO_INITDB_ROOT_PASSWORD: admin123
      MONGO_INITDB_DATABASE: payments
    volumes:
      - mongodb_data:/data/db
    networks:
      - pragma-network
    healthcheck:
      test: echo 'db.runCommand("ping").ok' | mongosh localhost:27017/test --quiet
      interval: 10s
      timeout: 5s
      retries: 5
      start_period: 10s
    deploy:
      resources:
        limits:
          memory: 512M
        reservations:
          memory: 256M

  zookeeper:
    image: confluentinc/cp-zookeeper:7.5.0
    container_name: pragma-zookeeper
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
      ZOOKEEPER_TICK_TIME: 2000
    networks:
      - pragma-network
    healthcheck:
      test: nc -z localhost 2181 || exit 1
      interval: 10s
      timeout: 5s
      retries: 5

  kafka:
    image: confluentinc/cp-kafka:7.5.0
    container_name: pragma-kafka
    depends_on:
      zookeeper:
        condition: service_healthy
    ports:
      - "9092:9092"
      - "9093:9093"
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka:29092,PLAINTEXT_HOST://localhost:9092
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT,PLAINTEXT_HOST:PLAINTEXT
      KAFKA_INTER_BROKER_LISTENER_NAME: PLAINTEXT
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
      KAFKA_AUTO_CREATE_TOPICS_ENABLE: "true"
      KAFKA_LOG_RETENTION_HOURS: 168
      KAFKA_LOG_SEGMENT_BYTES: 1073741824
      KAFKA_NUM_PARTITIONS: 3
    networks:
      - pragma-network
    healthcheck:
      test: kafka-topics --bootstrap-server localhost:9092 --list
      interval: 10s
      timeout: 10s
      retries: 5
      start_period: 30s
    deploy:
      resources:
        limits:
          memory: 1G
        reservations:
          memory: 512M

  kafka-ui:
    image: provectuslabs/kafka-ui:latest
    container_name: pragma-kafka-ui
    depends_on:
      kafka:
        condition: service_healthy
    ports:
      - "8085:8080"
    environment:
      KAFKA_CLUSTERS_0_NAME: pragma-kafka
      KAFKA_CLUSTERS_0_BOOTSTRAPSERVERS: kafka:29092
      KAFKA_CLUSTERS_0_ZOOKEEPER: zookeeper:2181
    networks:
      - pragma-network

  payment-service:
    build:
      context: ./payment-service
      dockerfile: Dockerfile
    container_name: pragma-payment-service
    ports:
      - "8081:8081"
    environment:
      SPRING_PROFILES_ACTIVE: docker
      SPRING_DATA_MONGODB_URI: mongodb://admin:admin123@mongodb:27017/payments?authSource=admin
      SPRING_DATA_MONGODB_DATABASE: payments
      SPRING_KAFKA_BOOTSTRAP_SERVERS: kafka:29092
      SERVER_PORT: 8081
      EUREKA_CLIENT_ENABLED: "false"
      SPRING_CLOUD_GATEWAY_URI: http://api-gateway:8080
    depends_on:
      mongodb:
        condition: service_healthy
      kafka:
        condition: service_healthy
    networks:
      - pragma-network
    healthcheck:
      test: curl -f http://localhost:8081/actuator/health || exit 1
      interval: 15s
      timeout: 10s
      retries: 5
      start_period: 40s
    deploy:
      resources:
        limits:
          memory: 1G
        reservations:
          memory: 512M

  fraud-service:
    build:
      context: ./fraud-service
      dockerfile: Dockerfile
    container_name: pragma-fraud-service
    ports:
      - "8082:8082"
    environment:
      SPRING_PROFILES_ACTIVE: docker
      SPRING_DATA_MONGODB_URI: mongodb://admin:admin123@mongodb:27017/fraud?authSource=admin
      SPRING_DATA_MONGODB_DATABASE: fraud
      SPRING_KAFKA_BOOTSTRAP_SERVERS: kafka:29092
      SERVER_PORT: 8082
      EUREKA_CLIENT_ENABLED: "false"
    depends_on:
      mongodb:
        condition: service_healthy
      kafka:
        condition: service_healthy
    networks:
      - pragma-network
    healthcheck:
      test: curl -f http://localhost:8082/actuator/health || exit 1
      interval: 15s
      timeout: 10s
      retries: 5
      start_period: 40s
    deploy:
      resources:
        limits:
          memory: 512M
        reservations:
          memory: 256M

  api-gateway:
    build:
      context: ./api-gateway
      dockerfile: Dockerfile
    container_name: pragma-api-gateway
    ports:
      - "8080:8080"
    environment:
      SPRING_PROFILES_ACTIVE: docker
      SPRING_CLOUD_GATEWAY_ROUTES_0_ID: payment-service
      SPRING_CLOUD_GATEWAY_ROUTES_0_URI: http://payment-service:8081
      SPRING_CLOUD_GATEWAY_ROUTES_0_PREDICATHES_0: Path=/api/payments/**
      SPRING_CLOUD_GATEWAY_ROUTES_1_ID: fraud-service
      SPRING_CLOUD_GATEWAY_ROUTES_1_URI: http://fraud-service:8082
      SPRING_CLOUD_GATEWAY_ROUTES_1_PREDICATHES_1: Path=/api/fraud/**
      EUREKA_CLIENT_ENABLED: "false"
    depends_on:
      payment-service:
        condition: service_healthy
      fraud-service:
        condition: service_healthy
    networks:
      - pragma-network
    healthcheck:
      test: curl -f http://localhost:8080/actuator/health || exit 1
      interval: 15s
      timeout: 10s
      retries: 5
      start_period: 30s

volumes:
  mongodb_data:
    driver: local

networks:
  pragma-network:
    driver: bridge
    ipam:
      config:
        - subnet: 172.28.0.0/16
```
