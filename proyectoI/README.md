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
proyectoI/
│
├── pom.xml
├── README.md
│
└── src/
    └── test/
        └── java/
            └── cr/
                └── ac/
                    └── utn/
                        └── proyecto/
                            │
                            ├── base/
                            │   ├── BaseTest.java
                            │   ├── GeneradorDatos.java
                            │   └── Visual.java
                            │
                            ├── pages/
                            │   ├── InicioPage.java
                            │   ├── LoginPage.java
                            │   ├── RegistroPage.java
                            │   ├── ResultadosBusquedaPage.java
                            │   ├── ListadoProductosPage.java
                            │   ├── ProductoPage.java
                            │   ├── CarritoPage.java
                            │   ├── ListaDeseosPage.java
                            │   └── README.md
                            │
                            └── tests/
                                ├── LoginTest.java
                                ├── RegistroTest.java
                                ├── CarritoTest.java
                                ├── ListaDeseosTest.java
                                └── README.md
## Descripción de los archivos

### base

- `BaseTest.java`: abre un Chrome limpio antes de cada prueba y lo cierra al terminar.
- `GeneradorDatos.java`: genera datos únicos, como el correo de cada cuenta nueva.
- `Visual.java`: pausa corta solo para poder observar la ejecución (se desactiva con `-Dritmo=0`).

### pages

- `InicioPage.java`: página principal; enlaces de registro, login y cierre de sesión.
- `LoginPage.java`: formulario de inicio de sesión y su mensaje de error.
- `RegistroPage.java`: formulario de registro, mensaje de confirmación y errores por campo.
- `ResultadosBusquedaPage.java`: resultados de búsqueda y enlaces a los productos.
- `ListadoProductosPage.java`: listado de productos y enlaces a los detalles.
- `ProductoPage.java`: página de detalle del producto, con opciones de compra y lista de deseos.
- `CarritoPage.java`: página del carrito de compras, con opciones de actualización y finalización de la compra.
- `ListaDeseosPage.java`: página de la lista de deseos, con opciones de movimiento a carrito y eliminación. 

### tests

- `RegistroTest.java`: CP-001 a CP-006, registro válido y casos de error.
- `LoginTest.java`: CP-007 a CP-010, login válido, errores y cierre de sesión.
- `CarritoTest.java`: CP-011 a CP-015, gestión del carrito de compras.
- `ListaDeseosTest.java`: CP-016 a CP-020, gestión de la lista de deseos.

## Cómo ejecutar

Desde la carpeta que contiene `pom.xml`:
## Registro y login

```
mvn test                     # todas las pruebas
mvn test -Dtest=RegistroTest # una sola clase
mvn test -Dritmo=0           # sin pausas visuales
mvn test -Dheadless=true     # sin ventana
```

## Carrito y lista de deseos

``
mvn test -Dtest=CarritoTest        # Pruebas del carrito
mvn test -Dtest=ListaDeseosTest    # Pruebas de la lista de deseos
mvn test -Dritmo=0                 # sin pausas visuales
mvn test -Dheadless=true           # sin ventana
mvn clean test                     # limpia y ejecuta todas las pruebas
```
