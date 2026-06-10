# automation-e2e-bancolombia-credits

Automatización de pruebas E2E para los simuladores de crédito de Bancolombia, desarrollada en Java con Serenity BDD, Screenplay y Cucumber para aplicar BDD con escenarios basados en datos; diseñada bajo principios SOLID y FIRST, asegurando cohesión, bajo acoplamiento y fácil mantenibilidad.

## Simuladores automatizados

1. Crédito de libre inversión
2. Crédito de libranza para empleados
3. Cupo rotativo Crediágil

## Requisitos previos

- **Java 17** (JDK).
- **Google Chrome** instalado (cualquier versión reciente). El proyecto descarga
  automáticamente el `chromedriver` que corresponda a la versión de Chrome de la
  máquina usando WebDriverManager, así que no hace falta instalarlo a mano.
- Conexión a internet (para resolver dependencias y descargar el driver).

No es necesario instalar Gradle: el proyecto incluye el wrapper (`./gradlew`).

## Cómo ejecutar las pruebas

Ejecutar todos los escenarios y generar el reporte:

```bash
./gradlew clean test aggregate
```

Ejecutar un solo flujo (por nombre de escenario):

```bash
./gradlew clean test -Dcucumber.filter.name="libranza"
```

## Reporte de resultados

Al finalizar, el reporte de Serenity queda en:

```text
target/site/serenity/index.html
```

## Notas

- Las pruebas abren un navegador real (modo no headless) para observar el paso a paso.
- En algunos simuladores, al pulsar "Simular" el portal puede mostrar una verificación
  reCAPTCHA ("No soy un robot"). Como esa validación es manual, la prueba se considera
  exitosa cuando el formulario se diligenció y se envió correctamente.
