# Proyecto de automatización web — ISW-622

Proyecto académico de automatización de pruebas para [Demo Web Shop](https://demowebshop.tricentis.com/), usando Java, Selenium WebDriver, JUnit 5 y el patrón Page Object Model (POM) con Page Factory.

## Integrantes
-  Jorge Gonzalez
-  Alejandro Gomez
-  Alejandro Umana
-  Douglas Ordonez


## Requisitos

- Java JDK 17 o superior.
- Apache Maven.
- Google Chrome instalado.

Selenium Manager administra el controlador de Chrome al ejecutar las pruebas.

## Ejecutar las pruebas

Desde la carpeta raíz del proyecto, donde se encuentra `pom.xml`:

```powershell
# Ejecutar toda la suite
mvn test

# Ejecutar una clase de pruebas específica
mvn test "-Dtest=RegistroLoginTest"
mvn test "-Dtest=CarritoWishListTest"
mvn test "-Dtest=CompraHistorialTest"

# Ejecutar sin las pausas visuales configuradas en Visual
mvn test "-Dritmo=0"

# Ejecutar Chrome en modo headless
mvn test "-Dheadless=true"
```

Los parámetros `ritmo` y `headless` se pueden combinar:

```powershell
mvn test "-Dritmo=0" "-Dheadless=true"
```

## Estructura

```text
src/test/java/cr/ac/utn/proyecto/
├── base/
│   ├── BaseTest.java       # Configura Chrome para cada prueba y cierra el navegador
│   └── Visual.java         # Pausas visuales configurables
├── pages/                  # Page Objects con Page Factory
└── tests/                  # Pruebas automatizadas con JUnit 5
```

Cada prueba parte de un navegador nuevo mediante `BaseTest`. Las interacciones y los localizadores se mantienen en las clases de página; las verificaciones se realizan en las pruebas.

## Pruebas automatizadas incluidas

| Clase | Cobertura |
|---|---|
| `RegistroLoginTest` | CP-001–CP-008: registro, inicio de sesión y cierre de sesión |
| `CarritoWishListTest` | CP-018, CP-020–CP-023: carrito, producto configurable y wishlist |
| `CompraHistorialTest` | CP-026 y CP-029: compra completa e historial de pedidos |
| `BusquedaCatalogoTest` | CP-009–CP-012 y CP-016: búsqueda, orden por precio y boletín |




