# Pruebas automatizadas

Este paquete contiene pruebas de interfaz con JUnit 5 para Demo Web Shop. Todas las clases extienden `BaseTest`, que inicia un navegador Chrome nuevo antes de cada prueba y lo cierra al terminar.

## Clases y cobertura actual

| Clase | Casos | Funcionalidad |
|---|---|---|
| `RegistroLoginTest` | CP-001–CP-008 | Registro, autenticación, validaciones y cierre de sesión |
| `CarritoWishListTest` | CP-018, CP-020–CP-023 | Carrito, producto configurable y wishlist |
| `CompraHistorialTest` | CP-026, CP-029 | Compra completa e historial de pedidos |
| `BusquedaCatalogoTest` | CP-009–CP-012, CP-016 | Búsqueda, orden del catálogo y boletín |



## Ejecución

Desde la raíz del proyecto:

```powershell
# Toda la suite
mvn test

# Una clase
mvn test "-Dtest=RegistroLoginTest"
mvn test "-Dtest=CarritoWishListTest"
mvn test "-Dtest=CompraHistorialTest"

# Sin pausas visuales
mvn test "-Dritmo=0"
```

