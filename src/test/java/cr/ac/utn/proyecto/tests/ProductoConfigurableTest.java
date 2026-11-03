package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cr.ac.utn.proyecto.pages.CarritoPage;
import cr.ac.utn.proyecto.pages.ComputadoraPersonalizadaPage;
import cr.ac.utn.proyecto.pages.ProductoPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("RF-06 Productos con opciones")
class ProductoConfigurableTest extends PruebaTienda {

    @Test
    @DisplayName("CP-028 Sin elegir el disco duro obligatorio no se agrega al carrito")
    void faltaOpcionObligatoria() {
        ProductoPage computadora = abrirTienda().abrirComputadoraPersonalizada()
                .agregarAlCarrito();

        assertAll(
                () -> assertTrue(computadora.ultimaNotificacionFueError(),
                        "Debe mostrar un aviso de error: " + computadora.obtenerUltimaNotificacion()),
                () -> assertFalse(computadora.obtenerUltimaNotificacion().isBlank()),
                () -> assertEquals(0, computadora.obtenerCantidadEnCarrito(), "No debe agregarse al carrito"));
    }

    @Test
    @DisplayName("CP-029 Las opciones elegidas aumentan el precio del producto")
    void opcionesCambianElPrecio() {
        ComputadoraPersonalizadaPage computadora = abrirTienda().abrirComputadoraPersonalizada();
        double precioBase = computadora.obtenerPrecio();

        computadora.elegirProcesador("2.5 GHz")
                .elegirRam("8GB")
                .elegirDisco("400 GB")
                .agregarAlCarrito();
        CarritoPage carrito = computadora.irAlCarrito();

        double precioEnCarrito = carrito.obtenerPrecioUnitario(0);
        assertAll(
                () -> assertTrue(computadora.obtenerUltimaNotificacion()
                        .contains("The product has been added to your shopping cart")),
                () -> assertEquals(1, carrito.contarProductos()),
                () -> assertTrue(precioEnCarrito > precioBase,
                        "Precio base " + precioBase + " vs. precio con opciones " + precioEnCarrito));
    }
}
