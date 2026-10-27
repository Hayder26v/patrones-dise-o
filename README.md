# Taller profesional de patrones de diseño — Java + Maven

## Objetivo
Resolver nueve retos de ingeniería de software usando patrones GoF, con pruebas unitarias JUnit 5. Los escenarios son profesionales: notificaciones empresariales, generación de reportes, configuración compartida, integración de proveedores, cálculo de tarifas, checkout, pagos, alertas y ciclo de vida de solicitudes.



## Requisitos
- JDK 17 o superior
- Apache Maven 3.9 o superior
- IDE opcional: IntelliJ IDEA, Eclipse o VS Code

## Ejecutar
Desde la carpeta raíz del proyecto (donde está `pom.xml`):
```bash
mvn clean test
```
Las pruebas unitarias se ejecutan con JUnit 5 a través de Maven.

## Retos por categoría

### Creacionales
1. **Factory Method — Enrutamiento de notificaciones:** crear notificadores Email y SMS según el canal solicitado, sin acoplar al cliente a clases concretas.
2. **Builder — Generación de reporte ejecutivo:** construir reportes con título, periodo y secciones opcionales, validando campos obligatorios.
3. **Singleton — Configuración de plataforma:** mantener una única instancia de configuración de plataforma en la JVM.

### Estructurales
4. **Adapter — Pasarela de pagos externa:** adaptar una API heredada que expresa el importe en centavos a una interfaz interna que recibe pesos.
5. **Decorator — Tarifas de envío:** añadir seguro y entrega prioritaria al costo base sin crear una clase por combinación.
6. **Facade — Proceso de checkout:** coordinar inventario, pago y emisión de confirmación desde una operación de alto nivel.

### Comportamiento
7. **Strategy — Cálculo de tarifa de envío:** intercambiar estrategias de tarifa estándar, exprés y nocturna en tiempo de ejecución.
8. **Observer — Alertas de umbral de inventario:** notificar suscriptores cuando el stock llega al umbral configurado.
9. **State — Ciclo de vida de solicitud:** controlar transiciones válidas entre RECIBIDA, EN_REVISION, APROBADA y RECHAZADA.

## Entregables y criterios sugeridos
No uses `System.out.println` como mecanismo de lógica de negocio. 
Mantén la lógica testeable y lanza excepciones claras ante entradas o transiciones inválidas.

## Reglas de referencia de esta implementación
- Las tarifas de envío usan STANDARD = 1x, EXPRESS = 1.8x y NIGHT = 2.5x sobre la tarifa base.
- El envío estándar cuesta 12.000; el seguro agrega 6.000 y la prioridad agrega 3.500. Los decoradores se pueden combinar.
- La pasarela heredada recibe centavos: el adaptador convierte pesos multiplicando por 100.
- El Singleton ofrece la configuración predeterminada `environment=production`.
- El ciclo de solicitudes usa en Java los estados `RECEIVED`, `IN_REVIEW`, `APPROVED` y `REJECTED`, equivalentes a los nombres en español de la consigna.

Ejecuta `mvn clean test` desde la carpeta raíz para validar todos los retos.

## Desarrollo y explicación de la solución

En este taller implementé nueve patrones de diseño en Java. Mi objetivo fue separar responsabilidades, facilitar que los componentes se puedan probar y validar explícitamente las entradas inválidas. Para comprobar el comportamiento escribí y ejecuté las pruebas unitarias JUnit 5 incluidas en `src/test/java`.

### Mapa: dónde está cada patrón

| # | Patrón | Implementación | Qué implementa | Prueba |
|---|---|---|---|---|
| 1 | Factory Method (fábrica de notificadores) | `src/main/java/co/edu/taller/creational/NotificationFactory.java` | `create` selecciona un notificador Email o SMS mediante `Channel`; el cliente usa la interfaz `Notifier`. | `src/test/java/co/edu/taller/creational/NotificationFactoryTest.java` |
| 2 | Builder | `src/main/java/co/edu/taller/creational/ExecutiveReport.java` | Construcción gradual de reportes; exige título y periodo y deja las secciones inmutables. | `src/test/java/co/edu/taller/creational/ExecutiveReportTest.java` |
| 3 | Singleton | `src/main/java/co/edu/taller/creational/PlatformConfig.java` | Una instancia compartida con configuración de plataforma y validación de claves. | `src/test/java/co/edu/taller/creational/PlatformConfigTest.java` |
| 4 | Adapter | `src/main/java/co/edu/taller/structural/PaymentGatewayAdapter.java` | Convierte pesos a centavos y delega el cobro en la pasarela heredada. | `src/test/java/co/edu/taller/structural/PaymentGatewayAdapterTest.java` |
| 5 | Decorator | `src/main/java/co/edu/taller/structural/ShippingCost.java` | Compone el costo estándar con recargos de seguro y prioridad. | `src/test/java/co/edu/taller/structural/ShippingCostTest.java` |
| 6 | Facade | `src/main/java/co/edu/taller/structural/CheckoutFacade.java` | Coordina validación, reserva de inventario, pago y confirmación. | `src/test/java/co/edu/taller/structural/CheckoutFacadeTest.java` |
| 7 | Strategy | `src/main/java/co/edu/taller/behavioral/ShippingRateStrategy.java` | Selecciona entre cálculos estándar, exprés y nocturno según el modo. | `src/test/java/co/edu/taller/behavioral/ShippingRateStrategyTest.java` |
| 8 | Observer | `src/main/java/co/edu/taller/behavioral/InventoryAlerts.java` | Mantiene suscriptores y les avisa cuando el stock está en el umbral o por debajo. | `src/test/java/co/edu/taller/behavioral/InventoryAlertsTest.java` |
| 9 | State | `src/main/java/co/edu/taller/behavioral/RequestLifecycle.java` | Conserva el estado actual y rechaza transiciones no permitidas. | `src/test/java/co/edu/taller/behavioral/RequestLifecycleTest.java` |

Las clases de implementación también tienen una breve anotación en su declaración que identifica el patrón y su responsabilidad.

### Patrones creacionales

#### 1. Factory Method: creación de notificadores

En `NotificationFactory` definí una interfaz común llamada `Notifier`, con la operación `send`. El método `create` recibe un canal y entrega el notificador correspondiente: Email o SMS. De esta manera, el código que solicita una notificación trabaja con la abstracción `Notifier` y no necesita construir directamente una clase concreta. También rechazo un canal nulo con `IllegalArgumentException`, porque no existe una implementación que se pueda seleccionar en ese caso. Esta solución sigue el enfoque simplificado de fábrica que propone la plantilla del reto; en la definición estricta de GoF, Factory Method suele delegar la creación a subclases.

En `NotificationFactoryTest` comprobé que cada canal genera el formato esperado y que un canal nulo se rechaza.

#### 2. Builder: construcción de reportes

En `ExecutiveReport` utilicé la clase interna `Builder` para crear un reporte gradualmente. El título y el periodo son obligatorios; las secciones se agregan de forma opcional. Al ejecutar `build`, valido los campos obligatorios y construyo el reporte solo si están completos. Además, copio las secciones a una lista inmutable para evitar que el contenido del reporte cambie accidentalmente después de crearlo.

En `ExecutiveReportTest` comprobé la creación de un reporte con secciones, el rechazo de los datos obligatorios incompletos y la inmutabilidad de la lista.

#### 3. Singleton configuración compartida

En `PlatformConfig` creé una única instancia estática y oculté el constructor para impedir que se creen instancias adicionales desde fuera de la clase. La inicialización estática de Java hace que este enfoque sea seguro frente al acceso concurrente. La configuración de ejemplo incluye `environment=production`. El método `get` valida la clave y comunica claramente si está vacía o no existe.

En `PlatformConfigTest` comprobé que `instance()` devuelve siempre el mismo objeto, que la configuración predeterminada está disponible y que una clave vacía se rechaza.

### Patrones estructurales

#### 4. Adapter: adaptación de la pasarela de pago

La pasarela heredada recibe el valor del cobro en centavos, mientras que la interfaz de la solución recibe pesos. En `PaymentGatewayAdapter` hice de puente entre ambos formatos: valido el identificador de la orden y que el valor sea positivo, convierto pesos a centavos multiplicando por 100 y delego el cobro en `LegacyGateway`.

En `PaymentGatewayAdapterTest` comprobé la conversión de 1.250 pesos a 125.000 centavos, además del rechazo de importes no positivos y órdenes vacías.

#### 5. Decorator: composición de costos de envío

En `ShippingCost` definí la interfaz común del costo, una implementación estándar y dos decoradores: `Insurance` y `Priority`. Cada decorador recibe otro `ShippingCost`, calcula su costo y agrega su propio recargo. Así puedo combinar seguro y prioridad sin crear una clase específica para cada combinación. Los decoradores validan que reciban un costo base.

Para este ejemplo, el costo estándar es 12.000, el seguro agrega 6.000 y la prioridad agrega 3.500. En `ShippingCostTest` comprobé el costo estándar, la combinación de los dos decoradores y el rechazo de una dependencia nula.

#### 6. Facade: coordinación del checkout

En `CheckoutFacade` reuní el flujo de compra detrás de una sola operación `checkout`. La fachada valida la orden, el SKU, la cantidad y el importe; después solicita la reserva del inventario y, si esta tiene éxito, intenta cobrar el pago. Solo devuelve la confirmación cuando ambas operaciones resultan exitosas. Si no hay inventario o el pago es rechazado, lanza `IllegalStateException` en vez de devolver una confirmación engañosa.

En `CheckoutFacadeTest` comprobé el flujo exitoso y que el proceso se detiene cuando falla la reserva o el cobro.

### Patrones de comportamiento

#### 7. Strategy: selección de tarifa de envío

En `ShippingRateStrategy` asocié cada modo con una estrategia de cálculo distinta. El método `quote` valida la tarifa base y selecciona la operación correspondiente a STANDARD, EXPRESS o NIGHT, sin mezclar la selección con la responsabilidad de calcular cada tarifa.

Esta es una versión compacta de Strategy: las estrategias son funciones internas seleccionadas por el modo, no clases públicas que se puedan inyectar desde fuera.

En este ejercicio usé los valores de referencia STANDARD = 1x, EXPRESS = 1,8x y NIGHT = 2,5x. En `ShippingRateStrategyTest` comprobé los resultados estándar y exprés, y que una tarifa base no válida se rechaza.

#### 8. Observer: alertas de inventario

En `InventoryAlerts` mantuve una lista de listeners que se pueden suscribir. Cuando se actualiza el stock, notifico a los suscriptores si la cantidad es igual o inferior al umbral. Valido el umbral inicial, el SKU, la cantidad y que el listener no sea nulo. Itero sobre una copia de la lista de suscriptores para que las notificaciones usen una instantánea estable.

En `InventoryAlertsTest` comprobé que se notifica al llegar al umbral, que no se notifica cuando el stock está por encima de él y que no se aceptan listeners nulos.

#### 9. State: ciclo de vida de una solicitud

En `RequestLifecycle` representé el estado actual de la solicitud y centralicé la validación de transiciones. Una solicitud inicia en `RECEIVED`, puede pasar a `IN_REVIEW` y desde allí puede terminar en `APPROVED` o `REJECTED`. Cualquier transición que no respete ese flujo genera `IllegalStateException`.

La implementación modela los estados con un `enum` y valida las transiciones en un método común; una versión GoF más extensa podría tener una clase por estado.

En `RequestLifecycleTest` comprobé los caminos válidos de aprobación y rechazo, así como el rechazo de una aprobación realizada antes de la revisión.

### Ejecución de las pruebas y conclusión

Ejecuté `mvn clean test` desde la carpeta que contiene `pom.xml`. El resultado fue **27 pruebas ejecutadas, 0 fallidas y 0 errores**. Estas pruebas cubren tanto los flujos principales como varios casos de entrada inválida.

Con este trabajo aprendí que los patrones no son un objetivo por sí mismos: los utilicé para resolver problemas concretos de creación, composición y coordinación de objetos. También comprobé que las pruebas unitarias ayudan a verificar esas decisiones y a detectar regresiones.

**Nota sobre el checkout:** las interfaces entregadas no incluyen una operación para liberar el inventario reservado si el pago falla. Por ese motivo, la solución informa del fallo del pago, pero no puede revertir la reserva. En un sistema real ampliaría el contrato de inventario para soportar esa compensación.
