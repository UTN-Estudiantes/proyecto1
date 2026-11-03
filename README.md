# ISW-622 · Proyecto de automatización · Demo Web Shop

Automatización de pruebas funcionales sobre https://demowebshop.tricentis.com con Java 17, Maven, Selenium 4, JUnit 5, Page Factory y Page Object Model.

**Grupo:** Grupo JSSK
**Integrantes:** Kendall Rodríguez Sáenz - Sebastián Rojas Hernandez - Sebastián Salazar Díaz - Jordan Herrera.

## Qué se probó

34 pruebas automatizadas, elegidas entre los 45 casos escritos en `docs/Plantilla_Casos_de_Prueba_S7.xlsx`. Cada prueba lleva en `@DisplayName` el ID de su caso (por ejemplo, `CP-009 Inicio de sesión con contraseña incorrecta`). Los 11 casos restantes quedaron como manuales (columna Automatizable = No).

| RF | Requerimiento | Clase de prueba | Casos automatizados |
|----|---------------|-----------------|---------------------|
| RF-01 | Registro | `RegistroTest` | CP-001, 002, 003, 004, 006, 007 |
| RF-02 | Inicio y cierre de sesión | `InicioSesionTest` | CP-008, 009, 010, 012 |
| RF-03 | Búsqueda | `BusquedaTest` | CP-013, 014, 015, 017 |
| RF-04 | Catálogo | `CatalogoTest` | CP-018, 019 |
| RF-05 | Carrito | `CarritoTest` | CP-022 a 027 |
| RF-06 | Opciones de producto | `OpcionesProductoTest` | CP-028, 029, 030 |
| RF-07 | Lista de deseos | `ListaDeseosTest` | CP-031 |
| RF-08 | Comparación | `ComparacionTest` | CP-034 |
| RF-09 | Compra (checkout) | `CompraTest` | CP-036, 038, 039 |
| RF-10 | Cuenta | `CuentaTest` | CP-040 |
| RF-11 | Boletín y encuesta | `BoletinEncuestaTest` | CP-043, 044, 045 |

## Cómo ejecutar

Requisitos: JDK 17, Maven 3.9 y Google Chrome instalado. Selenium Manager descarga el driver automáticamente.

Desde la carpeta que contiene `pom.xml`:

```
mvn test                   # todas las pruebas
mvn test -Dtest=RegistroTest   # una sola clase
mvn test -Dritmo=0         # sin pausas visuales
mvn test -Dheadless=true   # sin ventana de navegador
```

La ejecución completa tarda unos 5 minutos. El reporte queda en `target/surefire-reports` (archivos `.txt` y `.xml` por clase).

Notas sobre la ejecución:

- El sitio es público y compartido. Cada prueba que necesita un usuario registra uno nuevo con un correo `qa.<nanosegundos>@example.com`, por lo que las pruebas son independientes entre sí y repetibles.
- CP-036 completa una compra real como invitado, con datos ficticios. El sitio demo no cobra ni envía nada.
- Como el sitio es externo, una prueba puede fallar si cambian sus textos o si no hay conexión.

## Organización

```
docs/                 Casos de prueba (Excel) e informe
src/test/java/cr/ac/utn/proyecto/
├── base/   BaseTest (Chrome limpio por prueba), DatosDePrueba (correo único) y Visual (pausa solo para observar)
├── pages/  Una clase por página, con @FindBy y PageFactory.initElements
└── tests/  Pruebas JUnit 5; no contienen localizadores, solo llaman a las páginas y verifican
```

Clases de página: `PaginaInicio`, `PaginaRegistro`, `PaginaInicioSesion`, `PaginaBusqueda`, `PaginaBusquedaAvanzada`, `PaginaCatalogo`, `PaginaProducto`, `PaginaCarrito`, `PaginaPago`, `PaginaListaDeseos`, `PaginaComparacion` y `PaginaCuenta`.

Convenciones:

- Los métodos de las páginas tienen nombres de negocio y devuelven la página siguiente cuando hay navegación.
- Las aserciones están solo en las pruebas; las páginas no tienen datos de prueba fijos.
- Las esperas son explícitas (`WebDriverWait`); no se usa `Thread.sleep`.
- `driver.findElement` con `By` aparece solo donde el localizador depende de un parámetro (`PaginaCatalogo` y `PaginaProducto`) y cada uso lleva un comentario que lo justifica.
