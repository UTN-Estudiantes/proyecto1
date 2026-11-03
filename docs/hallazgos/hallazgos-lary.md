# Hallazgos — RF-03, RF-04 y RF-08

**Proyecto:** Automatización de pruebas de Demo Web Shop (ISW-622)  
**Responsable de esta sección:** Módulo Lary en la guía de distribución

## Hallazgo 1: La búsqueda vacía muestra una alerta diferente

- **Requerimiento / caso:** RF-03 / CP-017.
- **Pasos:** Abrir Demo Web Shop, dejar vacío el campo de búsqueda y presionar **Search**.
- **Resultado observado:** Aparece una alerta del navegador con el texto `Please enter some search keyword`.
- **Diferencia con el caso original:** La versión inicial del Excel esperaba el mensaje `Search term minimum length is 3 characters` tanto para vacío como para dos caracteres.
- **Tratamiento:** Se actualizó el resultado esperado del Excel para distinguir ambas situaciones y se verificó la alerta en la prueba automatizada.

## Observaciones durante la automatización (no son defectos confirmados del sitio)

- **RF-03 / CP-015:** Al ejecutar búsquedas sucesivas, Selenium presentó `StaleElementReferenceException`. Se ajustó la espera en la clase de página para localizar los resultados después de actualizar la búsqueda.
- **RF-04 / CP-020:** El enlace de subcategoría aparecía más de una vez en el documento; el primero no era interactuable. La página se ajustó para hacer clic en el enlace visible.
- **RF-08 / CP-025:** La espera de una notificación visible produjo un `TimeoutException`. Se quitó esa espera y la prueba de comparación confirmó los dos productos en la tabla.

**Evidencia de ejecución:** En NetBeans, las 32 pruebas del proyecto pasaron juntas en la ejecución observada. Conviene conservar la captura en `docs/evidencia/`.

**Pendiente:** Los casos CP-018, CP-019, CP-021, CP-023, CP-024, CP-027 y CP-028 no se ejecutaron como parte de este módulo automatizado y conservan estado pendiente.
