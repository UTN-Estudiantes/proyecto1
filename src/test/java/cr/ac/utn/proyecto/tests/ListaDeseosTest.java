package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cr.ac.utn.proyecto.pages.CarritoPage;
import cr.ac.utn.proyecto.pages.ListaDeseosPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("RF-07 Lista de deseos")
class ListaDeseosTest extends PruebaTienda {

    /*
     * Se usa un producto de joyeria porque el "14.1-inch Laptop" no tiene el boton
     * "Add to wishlist" en el sitio (la lista de deseos se habilita por producto).
     */
    private static final String PRODUCTO_SLUG = "black-white-diamond-heart";
    private static final String PRODUCTO = "Black & White Diamond Heart";

    @Test
    @DisplayName("CP-030 Agregar un producto a la lista de deseos y moverlo al carrito")
    void agregarYMoverAlCarrito() {
        ListaDeseosPage deseos = abrirTienda().abrirProducto(PRODUCTO_SLUG)
                .agregarAListaDeseos()
                .irAListaDeseos();
        assertTrue(deseos.obtenerNombres().contains(PRODUCTO), "Lista: " + deseos.obtenerNombres());

        CarritoPage carrito = deseos.moverTodoAlCarrito();
        assertAll(
                () -> assertTrue(carrito.contieneProducto(PRODUCTO)),
                () -> assertEquals(1, carrito.esperarCantidadEnCarrito(1)));
    }

    @Test
    @DisplayName("CP-031 Quitar el producto de la lista de deseos")
    void quitarDeLaLista() {
        ListaDeseosPage deseos = abrirTienda().abrirProducto(PRODUCTO_SLUG)
                .agregarAListaDeseos()
                .irAListaDeseos();
        assertEquals(1, deseos.contarProductos(), "Antes de quitar debe haber un producto");

        ListaDeseosPage despues = deseos.quitarTodo();
        assertEquals(0, despues.contarProductos());
    }
}
