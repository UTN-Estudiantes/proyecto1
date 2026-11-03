package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cr.ac.utn.proyecto.pages.CarritoPage;
import cr.ac.utn.proyecto.pages.CategoriaPage;
import cr.ac.utn.proyecto.pages.ProductoPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("RF-05 Carrito de compras")
class CarritoTest extends PruebaTienda {

    private static final String LAPTOP_SLUG = "141-inch-laptop";
    private static final String LAPTOP = "14.1-inch Laptop";
    private static final String LIBRO = "Computing and Internet";
    private static final String CARRITO_VACIO = "Your Shopping Cart is empty!";

    @Test
    @DisplayName("CP-021 Agregar producto desde el listado actualiza el indicador del carrito")
    void agregarDesdeListado() {
        CategoriaPage libros = abrirTienda().irACategoria("books")
                .agregarAlCarritoDesdeListado(LIBRO);

        assertEquals(1, libros.esperarCantidadEnCarrito(1));
        assertTrue(libros.irAlCarrito().contieneProducto(LIBRO));
    }

    @Test
    @DisplayName("CP-022 Agregar producto desde su pagina con el aviso de confirmacion")
    void agregarDesdePaginaDeProducto() {
        ProductoPage laptop = abrirTienda().abrirProducto(LAPTOP_SLUG)
                .agregarAlCarrito();

        assertAll(
                () -> assertTrue(laptop.obtenerUltimaNotificacion()
                        .contains("The product has been added to your shopping cart")),
                () -> assertEquals(1, laptop.esperarCantidadEnCarrito(1)));
    }

    @Test
    @DisplayName("CP-023 Cambiar la cantidad a 3 recalcula el subtotal")
    void cambiarCantidadRecalculaSubtotal() {
        CarritoPage carrito = abrirTienda().abrirProducto(LAPTOP_SLUG)
                .agregarAlCarrito()
                .irAlCarrito()
                .cambiarCantidad(LAPTOP, "3");

        double unitario = carrito.obtenerPrecioUnitario(0);
        assertAll(
                () -> assertEquals(3, carrito.obtenerCantidad(LAPTOP)),
                () -> assertEquals(unitario * 3, carrito.obtenerSubtotal(0), 0.01),
                () -> assertEquals(3, carrito.esperarCantidadEnCarrito(3)));
    }

    @Test
    @DisplayName("CP-024 Quitar el unico producto deja el carrito vacio")
    void quitarProducto() {
        CarritoPage carrito = abrirTienda().abrirProducto(LAPTOP_SLUG)
                .agregarAlCarrito()
                .irAlCarrito()
                .quitarProducto(LAPTOP);

        assertAll(
                () -> assertEquals(0, carrito.contarProductos()),
                () -> assertTrue(carrito.obtenerMensajeResumen().contains(CARRITO_VACIO)));
    }

    @Test
    @DisplayName("CP-025 Cantidad 0 (limite) quita el producto del carrito")
    void cantidadCeroQuitaProducto() {
        CarritoPage carrito = abrirTienda().abrirProducto(LAPTOP_SLUG)
                .agregarAlCarrito()
                .irAlCarrito()
                .cambiarCantidad(LAPTOP, "0");

        assertAll(
                () -> assertEquals(0, carrito.contarProductos()),
                () -> assertTrue(carrito.obtenerMensajeResumen().contains(CARRITO_VACIO)));
    }

    @Test
    @DisplayName("CP-026 Carrito de un visitante nuevo muestra el mensaje de vacio")
    void carritoVacio() {
        CarritoPage carrito = abrirTienda().irAlCarrito();

        assertAll(
                () -> assertEquals(0, carrito.obtenerCantidadEnCarrito()),
                () -> assertTrue(carrito.obtenerMensajeResumen().contains(CARRITO_VACIO)));
    }
}
