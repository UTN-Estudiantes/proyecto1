package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.pages.PaginaCarrito;
import cr.ac.utn.proyecto.pages.PaginaCatalogo;
import cr.ac.utn.proyecto.pages.PaginaInicio;
import cr.ac.utn.proyecto.pages.PaginaProducto;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** RF-05: carrito de compras. */
class CarritoTest extends BaseTest {

    private static final String PRODUCTO = "14.1-inch Laptop";

    private PaginaProducto abrirProducto() {
        return new PaginaInicio(driver).abrir().buscar("laptop").abrirProducto(PRODUCTO);
    }

    private PaginaCarrito carritoConElProducto() {
        return abrirProducto().agregarAlCarrito().irAlCarrito();
    }

    @Test
    @DisplayName("CP-022 Agregar un producto desde su página de detalle")
    void agregarDesdeElDetalle() {
        PaginaProducto producto = abrirProducto().agregarAlCarrito();

        assertTrue(producto.obtenerMensajeDeNotificacion().contains("The product has been added to your shopping cart"));
        assertEquals("(1)", producto.obtenerIndicadorCarrito());
    }

    @Test
    @DisplayName("CP-023 Agregar un producto desde el listado")
    void agregarDesdeElListado() {
        PaginaCatalogo libros = new PaginaInicio(driver).abrir().irACategoria("Books");
        String primerProducto = libros.obtenerNombresDeProductos().get(0);

        libros.agregarAlCarritoElPrimerProducto();

        assertEquals("(1)", libros.obtenerIndicadorCarrito());
        assertTrue(libros.irAlCarrito().obtenerNombresDeProductos().contains(primerProducto));
    }

    @Test
    @DisplayName("CP-024 Cambiar la cantidad y recalcular el subtotal")
    void cambiarCantidadRecalculaSubtotal() {
        PaginaCarrito carrito = carritoConElProducto();
        BigDecimal precioUnitario = carrito.obtenerPrecioUnitarioDelProducto(PRODUCTO);

        carrito.cambiarCantidad(PRODUCTO, "2");

        assertEquals("2", carrito.obtenerCantidadDelProducto(PRODUCTO));
        assertEquals(0, precioUnitario.multiply(new BigDecimal("2")).compareTo(carrito.obtenerSubtotalDelProducto(PRODUCTO)));
    }

    @Test
    @DisplayName("CP-025 Quitar el único producto del carrito")
    void quitarElUnicoProducto() {
        PaginaCarrito carrito = carritoConElProducto().quitarProducto(PRODUCTO);

        assertTrue(carrito.estaVacio());
        assertTrue(carrito.obtenerMensajeDeCarritoVacio().contains("Your Shopping Cart is empty!"));
    }

    @Test
    @DisplayName("CP-026 Cantidad inválida con texto en el carrito")
    void cantidadConTexto() {
        PaginaCarrito carrito = carritoConElProducto();
        BigDecimal subtotalAnterior = carrito.obtenerSubtotalDelProducto(PRODUCTO);

        carrito.cambiarCantidad(PRODUCTO, "abc");

        assertTrue(carrito.obtenerNombresDeProductos().contains(PRODUCTO), "El producto no debe desaparecer");
        assertEquals(0, subtotalAnterior.compareTo(carrito.obtenerSubtotalDelProducto(PRODUCTO)),
                "El subtotal no debe cambiar con una cantidad que no es número");
    }

    @Test
    @DisplayName("CP-027 Cantidad 0 en el carrito")
    void cantidadCero() {
        PaginaCarrito carrito = carritoConElProducto().cambiarCantidad(PRODUCTO, "0");

        assertTrue(carrito.estaVacio());
        assertTrue(carrito.obtenerMensajeDeCarritoVacio().contains("Your Shopping Cart is empty!"));
    }
}
