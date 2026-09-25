# Diseño y Prototipado de Microservicios en un Sistema de Pagos

En un sistema de procesamiento de pagos de alta disponibilidad, se requiere implementar una arquitectura basada en microservicios que utilice Event Sourcing, CQRS y Domain Driven Design. El sistema debe manejar transacciones de pago, incluyendo la validación de la tarjeta, la autorización del pago y la confirmación de la transacción. Los microservicios deben comunicarse a través de patrones de integración empresarial. Los actores involucrados son el originador de créditos, el motor antifraude, el buró de riesgos, el core bancario, la gateway de pagos y el sistema de liquidación.

## Informacion General

| Campo | Valor |
|-------|-------|
| **Tema** | Arquitectura de Microservicios con Event Sourcing CQRS Domain Driven Design y Patrones de Integracion Empresarial en un Sistema Distribuido de Alta Disponibilidad |
| **Nivel** | senior-l1 |
| **Tipo** | practical |
| **Tiempo estimado** | 40 horas |

## Fases del Reto

### Fase 0: Configuración del Proyecto

**Objetivo:** Obtener el proyecto base funcional enviando el Código Base a un asistente de IA, que lo analizará, corregirá errores y generará un ZIP listo para usar.

**Tiempo estimado:** 15-30 minutos

**Instrucciones:**

- Asegúrate de tener instalado para ejecutar el proyecto: JDK 17+, Maven 3.9+, IDE con soporte Java.
- Copia todo el contenido del campo **Código Base** de este reto — incluyendo el texto de instrucciones que aparece al inicio.
- Abre un asistente de IA (Claude en claude.ai, ChatGPT o Gemini — se recomienda Claude), pega el contenido copiado en el chat y envíalo.
- El asistente analizará los archivos, corregirá errores y generará un archivo ZIP descargable. Descárgalo y extráelo en la carpeta donde quieras trabajar.
- Ejecuta `mvn compile` en la raíz. Si no hay errores, estás listo.

**Entregable:** El proyecto compila/arranca sin errores.

<details>
<summary>Pistas de conocimiento</summary>

- Copia el Código Base completo incluyendo el texto de instrucciones al inicio — esas instrucciones le indican al asistente exactamente qué hacer con los archivos.
- Si el asistente no genera el ZIP automáticamente al terminar el análisis, escríbele: "genera el ZIP ahora".
- Si el proyecto tiene errores al arrancar, comparte el mensaje de error con el mismo asistente para que lo corrija.

</details>

### Fase 1: Definición del Modelo de Dominio

**Objetivo:** Identificar y definir los agregados y entidades del dominio de pagos.

**Tiempo estimado:** 8 horas

**Instrucciones:**

- Identificar los agregados y entidades relevantes para el dominio de pagos.
- Definir las reglas de negocio y las validaciones necesarias para cada entidad.
- Establecer las relaciones entre los agregados y las entidades.

**Entregable:** Modelo de dominio definido con agregados, entidades, reglas de negocio y relaciones.

<details>
<summary>Pistas de conocimiento</summary>

- Considera los diferentes estados que puede tener una transacción de pago.
- Piensa en las posibles validaciones que se deben realizar antes de autorizar un pago.

</details>

### Fase 2: Implementación de Event Sourcing

**Objetivo:** Implementar el patrón de Event Sourcing para registrar los eventos de las transacciones de pago.

**Tiempo estimado:** 10 horas

**Instrucciones:**

- Diseñar el mecanismo para registrar los eventos de las transacciones de pago.
- Establecer la idempotencia del registro de eventos.
- Implementar la reconstrucción del estado de las transacciones a partir de los eventos registrados.

**Entregable:** Mecanismo de Event Sourcing implementado para registrar y reconstruir el estado de las transacciones de pago.

<details>
<summary>Pistas de conocimiento</summary>

- Considera cómo garantizar la idempotencia del registro de eventos.
- Piensa en cómo manejar los eventos en caso de fallos temporales.

</details>

### Fase 3: Implementación de CQRS

**Objetivo:** Implementar el patrón de CQRS para separar las responsabilidades de lectura y escritura en el sistema de pagos.

**Tiempo estimado:** 10 horas

**Instrucciones:**

- Diseñar los comandos y consultas para el sistema de pagos.
- Implementar los manejadores de comandos y consultas.
- Establecer la separación de responsabilidades entre los comandos y las consultas.

**Entregable:** Patrón de CQRS implementado con comandos y consultas separados para el sistema de pagos.

<details>
<summary>Pistas de conocimiento</summary>

- Considera cómo manejar las transacciones de pago en tiempo real.
- Piensa en cómo optimizar las consultas para obtener información del estado actual de las transacciones.

</details>

### Fase 4: Implementación de Patrones de Integración Empresarial

**Objetivo:** Implementar los patrones de integración empresarial para la comunicación entre los microservicios.

**Tiempo estimado:** 12 horas

**Instrucciones:**

- Identificar los patrones de integración empresarial relevantes para el sistema de pagos.
- Implementar la comunicación entre los microservicios utilizando los patrones de integración.
- Establecer la consistencia y la latencia esperadas en la comunicación entre los microservicios.

**Entregable:** Patrones de integración empresarial implementados para la comunicación entre los microservicios del sistema de pagos.

<details>
<summary>Pistas de conocimiento</summary>

- Considera cómo garantizar la consistencia en la comunicación entre los microservicios.
- Piensa en cómo manejar la latencia en la comunicación entre los microservicios.

</details>

## Dimensiones Evaluadas

- **queEs**: ¿Qué es Event Sourcing y cómo se aplica en el dominio de pagos?
- **paraQueSirve**: ¿Para qué sirve CQRS en el sistema de pagos?
- **comoSeUsa**: ¿Cómo se implementan los patrones de integración empresarial en el sistema de pagos?
- **erroresComunes**: ¿Cuáles son los errores comunes al implementar Event Sourcing y CQRS en el sistema de pagos?
- **queDecisionesImplica**: ¿Qué decisiones implica la implementación de los patrones de integración empresarial en el sistema de pagos?

## Criterios de Evaluacion

- Definición clara del modelo de dominio con agregados, entidades, reglas de negocio y relaciones.
- Implementación correcta del patrón de Event Sourcing con idempotencia y reconstrucción del estado.
- Implementación correcta del patrón de CQRS con separación de responsabilidades entre comandos y consultas.
- Implementación correcta de los patrones de integración empresarial con consistencia y latencia esperadas.

## Como trabajar con un asistente de IA

Hay dos caminos, elegi uno:

- **AGENTS.md** (recomendado) — instrucciones nativas del repo. Abri esta carpeta con tu agente local (Claude Code, Cursor, Codex, Copilot, Gemini) y las carga solo. Sabe que archivos faltan y con que comando se verifica, y completa el scaffold escribiendo en disco.
- **PROMPT_MEJORA.md** — para copiar y pegar en un chat (claude.ai, ChatGPT). Devuelve un ZIP con el proyecto. Sirve si no tenes un agente en el IDE.

Ninguno de los dos resuelve las fases del reto: eso es tu trabajo.

## Verificacion

El proyecto esta listo para trabajar cuando este comando corre sin errores:

```bash
mvn clean compile
```

---

*Reto generado automaticamente por Challenge Generator - Pragma*
