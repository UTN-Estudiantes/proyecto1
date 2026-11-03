# Automatización de pruebas de una tienda en línea (ISW-622)

**Universidad Técnica Nacional** · Ingeniería de Software · ISW-622 Pruebas de Software · III Cuatrimestre 2026
**Profesor:** Jose David Carvajal Jimenez
**Sitio bajo prueba:** [Demo Web Shop](https://demowebshop.tricentis.com)

**Integrantes:** Kristel Bravo Rivera · Lary Dias Artavia · Andrey Araya Quiroz · Jose Andres Ortiz Marin

Pruebas funcionales de **caja negra** sobre la interfaz web de Demo Web Shop, automatizadas en Java con Selenium y JUnit 5 siguiendo el patrón **Page Object Model** con **Page Factory**.

## Resumen

| Elemento | Resultado |
|---|---|
| Casos de prueba escritos | 62 (42 % negativos o de límite) |
| Pruebas automatizadas | 33, que cubren los 11 requerimientos (RF-01 a RF-11) |
| Clases de página (Page Factory) | 11 |
| Última ejecución completa | 33 de 33 aprobadas (ver `docs/evidencia/`) |

## Requisitos

- Java 17
- Maven 3.x
- Google Chrome (Selenium Manager descarga automáticamente el driver correspondiente)

Dependencias (en `Proyecto01/pom.xml`): Selenium 4, JUnit 5 (Jupiter) y Maven Surefire.

## Cómo ejecutar

Desde la carpeta del proyecto Maven:

```bash
cd Proyecto01
mvn test
```

| Comando | Para qué |
|---|---|
| `mvn test` | Ejecuta todas las pruebas |
| `mvn test -Dtest=CompraTest` | Ejecuta solo una clase de pruebas |
| `mvn test -Dheadless=true` | Ejecuta sin abrir la ventana del navegador |
| `mvn test -Dritmo=0` | Desactiva las pausas de observación de `Visual` (por defecto no se usan) |

También se pueden ejecutar desde NetBeans con **Test Project**. El reporte queda en `Proyecto01/target/surefire-reports/`.

## Organización del repositorio

```
.
├─ README.md
├─ casos-de-prueba/
│  └─ Casos_de_Prueba_S7.xlsx      casos escritos, estado y hoja de trazabilidad
├─ Proyecto01/                     proyecto Maven (Java 17, Selenium 4, JUnit 5)
│  ├─ pom.xml
│  └─ src/test/java/
│     ├─ base/                     BaseTest (navegador limpio por prueba) y Visual
│     ├─ pages/                    clases de página con Page Factory
│     └─ tests/                    pruebas JUnit 5
└─ docs/
   ├─ informe/                     informe técnico (APA 7)
   └─ evidencia/                   capturas de la ejecución y de los hallazgos
```

### Reglas que sigue el código

- **Páginas (`pages`)**: localizadores con `@FindBy` y `PageFactory.initElements`, métodos con nombres de negocio que devuelven la página siguiente; sin aserciones ni datos de prueba fijos. Cuando un localizador depende de un parámetro (por ejemplo, el botón *Add to cart* de cada producto) se usa `driver.findElement` con `By`, justificado con un comentario.
- **Pruebas (`tests`)**: heredan de `BaseTest`, contienen las aserciones y no usan `By`, `@FindBy` ni `driver.findElement`. Cada una se identifica con su caso en `@DisplayName` (por ejemplo, `CP-042 Compra completa como invitado`).
- **Independencia**: cada prueba abre un navegador nuevo y genera sus propios datos (correo único con `System.currentTimeMillis()`), por lo que no depende del orden ni de ejecuciones anteriores.
- **Esperas**: solo esperas explícitas (`WebDriverWait`); no se usa `Thread.sleep`. `Visual.pausa()` es la única pausa permitida y solo sirve para observar.

## Responsables por módulo

| Integrante | Requerimientos | Páginas | Clases de prueba | Pruebas |
|---|---|---|---|---|
| Kristel Bravo Rivera | RF-01 Registro, RF-02 Sesión, RF-11 Boletín y encuesta | RegistroPage, LoginPage, BoletinEncuestaPage | RegistroTest, LoginTest, BoletinEncuestaTest | 7 |
| Lary Dias Artavia | RF-03 Búsqueda, RF-04 Catálogo, RF-08 Comparación | BusquedaPage, CatalogoPage, ComparacionPage | BusquedaTest, CatalogoTest, ComparacionTest | 7 |
| Andrey Araya Quiroz | RF-05 Carrito, RF-06 Productos con opciones, RF-07 Lista de deseos | ProductoPage, CarritoPage, WishlistPage | CarritoTest, ProductoOpcionesTest, WishlistTest | 14 |
| Jose Andres Ortiz Marin | RF-09 Proceso de compra, RF-10 Cuenta del usuario | CheckoutPage, CuentaPage (y base del proyecto) | CompraTest, CuentaTest | 5 |

## Hallazgos principales

| ID | Requerimiento | Resumen |
|---|---|---|
| H-K01 | RF-11 | La encuesta solo permite votar a usuarios registrados ("Only registered users can vote."). |
| H-L01 | RF-03 | Una búsqueda vacía muestra una alerta del navegador distinta del aviso para textos de dos caracteres. |
| H-J01 | RF-06 | En *Build your own computer* las opciones obligatorias vienen marcadas por defecto. |

El detalle, con pasos para reproducirlos, está en el informe (`docs/informe/`).

## Flujo de trabajo con Git

- Rama compartida del equipo: `team-LKJA`.
- Antes de empezar y antes de cada push: `git pull --rebase origin team-LKJA`. No se usa `git push --force`.
- Commits pequeños con el formato `tipo(rf-xx): descripción`, por ejemplo `test(rf-09): CP-042 compra completa como invitado`.
