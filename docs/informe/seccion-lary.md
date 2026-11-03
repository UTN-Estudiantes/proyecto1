# Aporte del módulo de búsqueda, catálogo y comparación

**Requerimientos:** RF-03, RF-04 y RF-08.

## Estrategia

Se automatizaron siete casos de prueba del Excel: CP-015, CP-016 y CP-017 (búsqueda de productos); CP-020 y CP-022 (navegación y ordenamiento del catálogo); CP-025 y CP-026 (comparación de productos). Se eligieron porque son flujos frecuentes, verificables con resultados visibles y adecuados para repetirlos. Los demás casos de estos requerimientos quedaron pendientes de validación; no se reportan como aprobados.

## Implementación y retos

Se utilizaron Selenium 4, JUnit 5 y el patrón Page Object Model con Page Factory. Las acciones de las páginas se organizaron en `BusquedaPage`, `CatalogoPage` y `ComparacionPage`; las aserciones quedaron en las clases de pruebas. Se realizaron ajustes sencillos a la sincronización de búsqueda, selección de un enlace visible en catálogo y manejo de comparación, sin utilizar `Thread.sleep`.

## Hallazgos

En CP-017, buscar sin ingresar texto mostró una alerta con el mensaje `Please enter some search keyword`, mientras que buscar dos caracteres mostró `Search term minimum length is 3 characters`. El Excel se ajustó para reflejar el comportamiento observado. Los otros inconvenientes encontrados correspondieron a la automatización y no se registran como defectos confirmados del sitio.

## Resultado y aprendizaje

Las siete pruebas del módulo pasaron en sus ejecuciones por archivo. Posteriormente, la ejecución conjunta de todo el proyecto en NetBeans mostró **32 pruebas aprobadas de 32**. Se comprobó la importancia de validar el resultado real de la tienda, mantener los localizadores en las páginas y ejecutar las pruebas integradas antes de entregar.
