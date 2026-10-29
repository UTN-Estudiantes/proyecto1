# Proyecto: Automatizacion de pruebas de una tienda en linea (ISW-622)

Universidad Tecnica Nacional - Pruebas de Software - III Cuatrimestre 2026.
Sitio bajo prueba: https://demowebshop.tricentis.com

**Integrantes:** Kristel, Lary, Andrey, Jose

## Requisitos
Java 17, Maven, JUnit 5, Selenium 4 y un navegador compatible (ver pom.xml del proyecto base).

## Como ejecutar
```
cd Proyecto01
mvn test
```
El reporte queda en `Proyecto01/target/surefire-reports`. Una copia de la ejecucion completa se guarda en `docs/evidencia/`.

## Organizacion del repositorio
| Carpeta | Contenido |
|---|---|
| `casos-de-prueba/` | Excel maestro con los casos (`Casos_de_Prueba_S7.xlsx`) y archivos de trabajo por persona (`por-persona/`) |
| `Proyecto01/src/test/java/base/` | Clases base del proyecto (driver, configuracion) |
| `Proyecto01/src/test/java/pages/` | Clases de pagina con Page Factory (sin aserciones ni datos fijos) |
| `Proyecto01/src/test/java/tests/` | Pruebas JUnit 5 con el patron POM (con aserciones, sin localizadores) |
| `docs/enunciado/` | Enunciado original |
| `docs/guia-equipo/` | Guia de trabajo y distribucion de tareas |
| `docs/hallazgos/` | Hallazgos por integrante |
| `docs/informe/` | Secciones del informe (el PDF final se entrega en el campus) |
| `docs/evidencia/` | Reporte surefire / capturas de la ejecucion completa |

## Responsables por modulo
| Integrante | Requerimientos | Paginas | Pruebas |
|---|---|---|---|
| Kristel | RF-01, RF-02, RF-11 | RegistroPage, LoginPage, BoletinEncuestaPage | RegistroTest, LoginTest, BoletinEncuestaTest |
| Lary | RF-03, RF-04, RF-08 | BusquedaPage, CatalogoPage, ComparacionPage | BusquedaTest, CatalogoTest, ComparacionTest |
| Andrey | RF-05, RF-06, RF-07 | ProductoPage, CarritoPage, WishlistPage | CarritoTest, ProductoOpcionesTest, WishlistTest |
| Jose | RF-09, RF-10 | CheckoutPage, CuentaPage | CompraTest, CuentaTest |

## Flujo de trabajo (resumen)
- Todos trabajan en la rama compartida del equipo: `team-LKJA`. Cada quien solo modifica sus propios archivos.
- Commits pequenos y frecuentes: `tipo(rf-xx): descripcion` (ej. `feat(rf-01): LoginPage con Page Factory`).
- Antes de empezar y antes de cada push: `git pull --rebase origin team-LKJA`. No usar `git push --force`.
- Detalle completo en `docs/guia-equipo/Guia_y_Distribucion_de_Tareas.docx`.

> Nota: no hay proyecto base del docente; el proyecto Maven del equipo esta en `Proyecto01/` (Java 17, Selenium 4, JUnit 5).
