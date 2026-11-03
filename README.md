# ISW-622 · Proyecto de automatización · Proyecto base

Pruebas automatizadas con Selenium y JUnit 5, usando Page Object Model y Page Factory.

Sitio bajo prueba: https://demowebshop.tricentis.com

## Integrantes
- Juan Diego Matute
- Joseph Moya
- Alejandro Corrales
- Samuel Fernandez

## Requisitos
- Java 17
- Maven
- Google Chrome (Selenium descarga el driver solo)
- Conexión a internet

## Cómo ejecutar
Desde la carpeta que contiene `pom.xml`:
```
mvn test                   # todas las pruebas
mvn test -Dritmo=0         # sin pausas visuales
mvn test -Dheadless=true   # sin ventana del navegador
mvn test -Dtest=LoginTest  # una sola clase de prueba
```
Los reportes de la ejecución quedan en `target/surefire-reports`.

## Cómo está organizado
```
src/test/java/cr/ac/utn/proyecto/
├── base/   BaseTest (Chrome limpio por prueba), Visual (pausa solo para observar) y DatosPrueba (correo único)
├── pages/  InicioPage, RegistroPage, LoginPage, ResultadosBusquedaPage, CategoriaPage,
│           ProductoPage, CarritoPage, CheckoutPage y PedidoCompletadoPage
└── tests/  RegistroTest, LoginTest, BusquedaTest, CatalogoTest, CarritoTest,
            ProductoConfigurableTest y CompraTest
```

## Pruebas automatizadas
| Caso | Título | Tipo | Clase de prueba |
|---|---|---|---|
| CP-001 | Registro exitoso con datos válidos | Positivo | RegistroTest |
| CP-004 | Registro con un correo que ya existe | Negativo | RegistroTest |
| CP-007 | Login con correo y clave correctos | Positivo | LoginTest |
| CP-008 | Login con clave incorrecta | Negativo | LoginTest |
| CP-011 | Cerrar sesión | Positivo | LoginTest |
| CP-012 | Búsqueda de un producto que existe | Positivo | BusquedaTest |
| CP-013 | Búsqueda sin resultados | Negativo | BusquedaTest |
| CP-014 | Búsqueda con 2 caracteres | Límite | BusquedaTest |
| CP-018 | Ordenar por precio de menor a mayor | Positivo | CatalogoTest |
| CP-022 | Agregar al carrito desde el listado | Positivo | CarritoTest |
| CP-024 | Cambiar la cantidad en el carrito | Positivo | CarritoTest |
| CP-026 | Quitar el único producto del carrito | Positivo | CarritoTest |
| CP-029 | Build your own computer sin elegir el HDD | Negativo | ProductoConfigurableTest |
| CP-030 | Build your own computer con las opciones obligatorias | Positivo | ProductoConfigurableTest |
| CP-032 | Compra completa como invitado | Positivo | CompraTest |
| CP-034 | Checkout sin aceptar los términos de servicio | Negativo | CompraTest |
