# Proyecto S7 ISW-622
Automatización de pruebas de una tienda en línea con Selenium, Page Factory y POM

## Profesor

- José David Carvajal Jiménez

## Integrantes

- Emily María Alfaro González
- Katlyn Yiliana Fernández Ramos
- Melanni Michel Díaz Ugalde
- Yesenia Michelle Pérez Rodríguez

## Sitio bajo prueba: 

https://demowebshop.tricentis.com

## Estructura
```
src/test/java/cr/ac/utn/proyecto/
├── base/
│   ├── BaseTest.java
│   ├── GeneradorDatos.java
│   └── Visual.java
├── pages/
│   ├── InicioPage.java
│   ├── LoginPage.java
│   └── RegistroPage.java
└── tests/
    ├── LoginTest.java
    └── RegistroTest.java
```
## Descripción de los archivos

### base

- `BaseTest.java`: abre un Chrome limpio antes de cada prueba y lo cierra al terminar.
- `GeneradorDatos.java`: genera datos únicos, como el correo de cada cuenta nueva.
- `Visual.java`: pausa corta solo para poder observar la ejecución (se desactiva con `-Dritmo=0`).

### pages

- `InicioPage.java`: página principal; enlaces de registro, login y cierre de sesión.
- `LoginPage.java`: formulario de inicio de sesión y su mensaje de error.
- `RegistroPage.java`: formulario de registro, mensaje de confirmación y errores por campo.

### tests

- `RegistroTest.java`: CP-001 a CP-006, registro válido y casos de error.
- `LoginTest.java`: CP-007 a CP-010, login válido, errores y cierre de sesión.

## Cómo ejecutar

Desde la carpeta que contiene `pom.xml`:

```
mvn test                     # todas las pruebas
mvn test -Dtest=RegistroTest # una sola clase
mvn test -Dritmo=0           # sin pausas visuales
mvn test -Dheadless=true     # sin ventana
```
