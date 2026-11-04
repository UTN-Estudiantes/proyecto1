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
https://demowebshop.tricentis.com

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
                            │   ├── CuentaPage.java
                            │   ├── CatalogoPage.java
                            │   ├── ListadoProductosPage.java
                            │   ├── ProductoPage.java
                            │   ├── CarritoPage.java
                            │   ├── ListaDeseosPage.java
                            │   ├── PagoPage.java
                            │   ├── ComparacionPage.java
                            │   └── BoletinEncuestaPage.java
                            │
                            └── tests/
                                ├── LoginTest.java
                                ├── RegistroTest.java
                                ├── BusquedaTest.java
                                ├── CatalogoTest.java
                                ├── CuentaTest.java
                                ├── CarritoTest.java
                                ├── ListaDeseosTest.java
                                ├── CompraTest.java
                                ├── ComparacionTest.java
                                └── BoletinTest.java
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
- `ResultadosBusquedaPage.java`: devuelve los títulos y el aviso de búsqueda.
- `CuentaPage.java`: información de la cuenta y cambio de contraseña.
- `CatalogoPage.java`: lista desplegable de productos por página y conteo.
- `ListadoProductosPage.java`: listado de productos y enlaces a los detalles.
- `ProductoPage.java`: página de detalle del producto, con opciones de compra y lista de deseos.
- `CarritoPage.java`: página del carrito de compras, con opciones de actualización y finalización de la compra.
- `ListaDeseosPage.java`: página de la lista de deseos, con opciones de movimiento al carrito y eliminación.
- `PagoPage.java`: gestiona los pasos del proceso de compra, desde la facturación hasta la confirmación de la orden.
- `ComparacionPage.java`: gestiona la comparación de productos, la eliminación de productos y la limpieza de la lista.
- `BoletinEncuestaPage.java`: bloque de suscripción al boletín en el pie de página y su aviso de resultado.

### tests

- `RegistroTest.java`: CP-001 a CP-006, registro válido y casos de error.
- `LoginTest.java`: CP-007 a CP-010, inicio de sesión válido, errores y cierre de sesión.
- `BusquedaTest.java`: CP-011 y CP-013, búsqueda con resultados y con texto demasiado corto.
- `CatalogoTest.java`: CP-017, cambio de productos por página.
- `CuentaTest.java`: CP-018 y CP-019, edición de la información y cambio de contraseña con confirmación distinta.
- `CarritoTest.java`: CP-021, CP-024, CP-028, gestión del carrito de compras.
- `ListaDeseosTest.java`: CP-029, gestión de la lista de deseos.
- `CompraTest.java`: CP-031 a CP-033, automatización del proceso de compra y confirmación de órdenes.
- `ComparacionTest.java`: CP-036 a CP-038, comparación de dos productos, eliminación de un producto y limpieza de la lista de comparación.
- `BoletinTest.java`: CP-039 y CP-040, suscripción al boletín con correo válido y con correo inválido.

## Cómo ejecutar las pruebas

Los comandos deben ejecutarse desde la carpeta que contiene el archivo `pom.xml`.

### Registro y login

```bash
mvn test -Dtest=RegistroTest
mvn test -Dtest=LoginTest
```

### Búsqueda, catálogo y cuenta

```bash
mvn test -Dtest=BusquedaTest
mvn test -Dtest=CatalogoTest 
mvn test -Dtest=CuentaTest
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

### Boletines

```bash
mvn test -Dtest=BoletinTest
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
