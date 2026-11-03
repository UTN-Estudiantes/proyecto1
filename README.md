# ISW-622 · Proyecto de automatización S7 · Demo Web Shop

**Universidad Técnica Nacional, Sede San Carlos · ISW-622 Pruebas de Software · III Cuatrimestre 2026**
**Docente:** Jose David Carvajal Jiménez
**Integrantes:** Daniela Rojas · Brandon Prado

Sitio bajo prueba: https://demowebshop.tricentis.com

Pruebas automáticas de una tienda en línea con **Java 17, Maven, JUnit 5 y Selenium 4**, usando
**Page Object Model** con **Page Factory** (`@FindBy` + `PageFactory.initElements`).

## Requisitos para ejecutar

| Herramienta | Versión | Cómo comprobarla |
|---|---|---|
| JDK | 17 o superior | `java -version` |
| Maven | 3.9 o superior | `mvn -v` |
| Google Chrome | cualquiera reciente | Chrome › Ayuda › Información |

No hace falta descargar ChromeDriver: Selenium Manager (incluido en Selenium 4) descarga el que corresponde a su Chrome.

## Cómo ejecutar

Desde la carpeta que contiene `pom.xml`:

```
mvn test                              # todas las pruebas (con pausas visuales de 700 ms)
mvn test -Dritmo=0                    # todas, sin pausas (más rápido)
mvn test -Dtest=CompraTest            # solo una clase
mvn test -Dtest=LoginTest#loginValido # solo una prueba
mvn test -Dheadless=true -Dritmo=0    # sin abrir ventana
```

El resultado queda en `target/surefire-reports/` (un `.txt` y un `.xml` por clase de prueba).
La evidencia de la última ejecución completa está en la carpeta `evidencia/`.

## Organización

```
src/test/java/cr/ac/utn/proyecto/
├── base/
│   ├── BaseTest.java            Chrome limpio antes de cada prueba y cierre al terminar
│   ├── Visual.java              Pausa solo para observar (-Dritmo); única pausa permitida
│   └── DatosPrueba.java         Correos únicos por ejecución (hora en ms + contador)
├── pages/                       Páginas con Page Factory (sin aserciones ni datos fijos)
│   ├── BasePage.java            Encabezado común + esperas explícitas (WebDriverWait)
│   ├── InicioPage.java          Inicio, menú de categorías, boletín y encuesta
│   ├── RegistroPage.java        Registro de usuario
│   ├── LoginPage.java           Inicio de sesión
│   ├── ResultadosBusquedaPage   Búsqueda simple y avanzada
│   ├── CategoriaPage.java       Listado: orden, productos por página y vista
│   ├── ProductoPage.java        Detalle de producto: carrito, deseos, comparar
│   ├── ComputadoraPersonalizadaPage  "Build your own computer" (opciones obligatorias)
│   ├── CarritoPage.java         Carrito: cantidades, quitar, términos, pagar
│   ├── CheckoutPage.java        Pago en una página (6 pasos AJAX)
│   ├── OrdenCompletadaPage.java Confirmación de la orden
│   ├── ListaDeseosPage.java     Lista de deseos
│   ├── ComparacionPage.java     Comparación de productos
│   ├── CuentaPage.java          Mi cuenta
│   └── CambiarClavePage.java    Cambio de contraseña
└── tests/                       Pruebas JUnit 5 (sin By, @FindBy ni driver.findElement)
    ├── PruebaTienda.java        Pasos comunes: abrir la tienda y registrar un usuario nuevo
    ├── RegistroTest             RF-01  (7 pruebas)
    ├── LoginTest                RF-02  (5)
    ├── BusquedaTest             RF-03  (4)
    ├── CatalogoTest             RF-04  (4)
    ├── CarritoTest              RF-05  (6)
    ├── ProductoConfigurableTest RF-06  (2)
    ├── ListaDeseosTest          RF-07  (2)
    ├── ComparacionTest          RF-08  (2)
    ├── CompraTest               RF-09  (3, incluye el flujo completo de compra)
    ├── CuentaTest               RF-10  (2)
    └── BoletinEncuestaTest      RF-11  (4)
```

Cada prueba se identifica con su caso escrito mediante `@DisplayName("CP-xxx ...")`.
Los casos completos y la matriz de trazabilidad están en `docs/Casos_de_Prueba_S7_DanielaRojas_BrandonPrado.xlsx`.

## Decisiones de diseño

- **Independencia:** cada prueba abre un Chrome limpio (`BaseTest`) y, si necesita un usuario,
  registra uno nuevo con correo único. Ninguna prueba depende de otra ni del orden de ejecución.
- **Esperas explícitas, no `Thread.sleep`:** `BasePage` usa `WebDriverWait` para esperar que un
  elemento sea visible o clickeable, que cambie la URL o que llegue un aviso AJAX.
- **Localizadores con parámetro:** cuando el localizador depende de un dato (nombre del producto,
  categoría, etiqueta de una opción) se usa `driver.findElement(By...)` dentro de la página, con un
  comentario que lo justifica, porque `@FindBy` solo acepta valores fijos.
- **Localizadores:** se prefieren `id`, `name` y CSS. Los XPath que se usan son relativos y se apoyan
  en el texto del producto, nunca en la posición.
- **Navegación fluida:** los métodos que navegan devuelven la página siguiente
  (`irALogin()` → `LoginPage`, `pagar()` → `CheckoutPage`, ...).
