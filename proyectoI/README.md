# Proyecto S7 ISW-622
## Automatización de pruebas de una tienda en línea con Selenium, Page Factory y POM

### Profesor
José David Carvajal Jiménez

### Integrantes
- Emily María Alfaro González
- Katlyn Yiliana Fernández Ramos
- Melanni Michel Díaz Ugalde
- Yesenia Michelle Pérez Rodríguez

### Sitio bajo prueba
[https://demowebshop.tricentis.com]

---

## Estructura del proyecto

```text
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
                            │   ├── PagoPage.java
                            │   ├── ComparacionPage.java
                            │   └── README.md
                            │
                            └── tests/
                                ├── LoginTest.java
                                ├── RegistroTest.java
                                ├── CarritoTest.java
                                ├── ListaDeseosTest.java
                                ├── CompraTest.java
                                ├── ComparacionTest.java
                                └── README.md
```

## Descripción de los archivos

### base

- `BaseTest.java`: abre un Chrome limpio antes de cada prueba y lo cierra al terminar.
- `GeneradorDatos.java`: genera datos únicos, como el correo de cada cuenta nueva.
- `Visual.java`: pausa corta para poder observar la ejecución (se desactiva con `-Dritmo=0`).

### pages

- `InicioPage.java`: página principal; enlaces de registro, login y cierre de sesión.
- `LoginPage.java`: formulario de inicio de sesión y su mensaje de error.
- `RegistroPage.java`: formulario de registro, mensaje de confirmación y errores por campo.
- `ResultadosBusquedaPage.java`: resultados de búsqueda y enlaces a los productos.
- `ListadoProductosPage.java`: listado de productos y enlaces a los detalles.
- `ProductoPage.java`: página de detalle del producto, con opciones de compra y lista de deseos.
- `CarritoPage.java`: página del carrito de compras, con opciones de actualización y finalización de la compra.
- `ListaDeseosPage.java`: página de la lista de deseos, con opciones de movimiento al carrito y eliminación.
- `PagoPage.java`: gestiona los pasos del proceso de compra, desde la facturación hasta la confirmación de la orden.
- `ComparacionPage.java`: gestiona la comparación de productos, la eliminación de productos y la limpieza de la lista.

### tests

- `RegistroTest.java`: CP-001 a CP-006, registro válido y casos de error.
- `LoginTest.java`: CP-007 a CP-010, inicio de sesión válido, errores y cierre de sesión.
- `CarritoTest.java`: CP-011 a CP-015, gestión del carrito de compras.
- `ListaDeseosTest.java`: CP-016 a CP-020, gestión de la lista de deseos.
- `CompraTest.java`: CP-031 a CP-033, automatización del proceso de compra y confirmación de órdenes.
- `ComparacionTest.java`: CP-036 a CP-038, comparación de dos productos, eliminación de un producto y limpieza de la lista de comparación.

## Cómo ejecutar las pruebas

Los comandos deben ejecutarse desde la carpeta que contiene el archivo `pom.xml`.

### Registro y login

```bash
mvn test -Dtest=RegistroTest
mvn test -Dtest=LoginTest
```

### Carrito y lista de deseos

```bash
mvn test -Dtest=CarritoTest
mvn test -Dtest=ListaDeseosTest
```

### Flujo de compra

```bash
mvn test -Dtest=CompraTest
```

### Comparación de productos

```bash
mvn test -Dtest=ComparacionTest
```

### Opciones generales

```bash
mvn test                  # Ejecuta todas las pruebas
mvn clean test            # Limpia y ejecuta todas las pruebas
mvn test -Dritmo=0        # Ejecuta sin pausas visuales
mvn test -Dheadless=true  # Ejecuta sin mostrar la ventana del navegador
```

## Tecnologías utilizadas

- Java
- Maven
- Selenium WebDriver
- Selenium Page Factory
- JUnit 5
- ChromeDriver
- Page Object Model (POM)
