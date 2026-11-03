package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.pages.CarritoPage;
import cr.ac.utn.proyecto.pages.CategoriaPage;
import cr.ac.utn.proyecto.pages.InicioPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Pruebas de RF-05 Carrito de compras. */
class CarritoTest extends BaseTest {

    private static final String CATEGORIA = "notebooks";
    private static final String PRODUCTO = "14.1-inch Laptop";

    @Test
    @DisplayName("CP-022 Agregar al carrito desde el listado")
    void agregarAlCarritoDesdeElListado() {
        CategoriaPage categoria = new InicioPage(driver).abrir()
                .irACategoria(CATEGORIA)
                .agregarAlCarrito(PRODUCTO);

        assertEquals("The product has been added to your shopping cart", categoria.mensajeNotificacion());
        assertEquals(1, new InicioPage(driver).cantidadEnCarrito());
    }

    @Test
    @DisplayName("CP-024 Cambiar la cantidad en el carrito")
    void cambiarLaCantidadEnElCarrito() {
        new InicioPage(driver).abrir()
                .irACategoria(CATEGORIA)
                .agregarAlCarrito(PRODUCTO);
        CarritoPage carrito = new InicioPage(driver).irAlCarrito();
        double precio = carrito.precioUnitario();

        carrito.cambiarCantidad(3).actualizar();

        assertEquals(precio * 3, carrito.totalLinea(), 0.01);
        assertEquals(3, new InicioPage(driver).cantidadEnCarrito());
    }

    @Test
    @DisplayName("CP-026 Quitar el único producto del carrito")
    void quitarElUnicoProductoDelCarrito() {
        new InicioPage(driver).abrir()
                .irACategoria(CATEGORIA)
                .agregarAlCarrito(PRODUCTO);

        CarritoPage carrito = new InicioPage(driver).irAlCarrito()
                .quitarProducto()
                .actualizar();

        assertTrue(carrito.mensajeCarritoVacio().contains("Your Shopping Cart is empty!"));
        assertEquals(0, new InicioPage(driver).cantidadEnCarrito());
    }
}
