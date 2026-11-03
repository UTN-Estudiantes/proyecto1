package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.pages.CartPage;
import cr.ac.utn.proyecto.pages.CategoryPage;
import cr.ac.utn.proyecto.pages.HomePage;
import cr.ac.utn.proyecto.pages.ProductPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Pruebas automatizadas con POM para RF-05 (Carrito) y RF-06 (Productos con opciones).
 */
class CarritoYCompraTest extends BaseTest {

    private static final String LIBRO = "Computing and Internet";
    private static final String COMPUTADORA = "Build your own computer";
    private static final String PROCESADOR = "2.2 GHz Intel Pentium Dual-Core E2200";
    private static final String RAM = "2 GB";

    @Test
    @DisplayName("CP-019 Agregar un producto simple al carrito actualiza el contador y el subtotal")
    void agregarProductoActualizaInsigniaYCarrito() {
        // Arrange
        HomePage home = new HomePage(driver).abrir();

        // Act
        CategoryPage categoria = home.irACategoria("Books")
                .agregarAlCarrito(LIBRO);

        // Assert
        assertTrue(categoria.mensajeNotificacion()
                .contains("The product has been added to your shopping cart"));
        assertEquals("(1)", categoria.cantidadEnInsignia());

        CartPage carrito = categoria.irAlCarrito();
        assertTrue(carrito.nombresEnCarrito().contains(LIBRO));
    }

    @Test
    @DisplayName("CP-020 Actualizar la cantidad de un producto en el carrito recalcula el subtotal")
    void actualizarCantidadEnCarritoRecalculaSubtotal() {
        // Arrange
        CartPage carrito = new HomePage(driver).abrir()
                .irACategoria("Books")
                .agregarAlCarrito(LIBRO)
                .irAlCarrito();
        double subtotalUnitario = carrito.subtotalPrimerProducto();

        // Act
        CartPage carritoActualizado = carrito.cambiarCantidad("3");

        // Assert
        assertEquals(subtotalUnitario * 3, carritoActualizado.subtotalPrimerProducto(), 0.01);
    }

    @Test
    @DisplayName("CP-021 Eliminar producto del carrito lo deja vacio y muestra mensaje informativo")
    void eliminarProductoDejaCarritoVacio() {
        // Arrange
        CartPage carrito = new HomePage(driver).abrir()
                .irACategoria("Books")
                .agregarAlCarrito(LIBRO)
                .irAlCarrito();

        // Act
        CartPage carritoVacio = carrito.eliminarPrimerProducto();

        // Assert
        assertTrue(carritoVacio.mensajeCarritoVacio()
                .contains("Your Shopping Cart is empty!"));
    }

    @Test
    @DisplayName("CP-024 Agregar producto configurable seleccionando todas las opciones obligatorias")
    void agregarProductoConfigurableConOpcionesObligatorias() {
        // Arrange
        ProductPage producto = new HomePage(driver).abrir()
                .buscarProducto(COMPUTADORA)
                .abrirProducto(COMPUTADORA);

        // Act
        producto.seleccionarProcesador(PROCESADOR)
                .seleccionarRam(RAM)
                .seleccionarHdd320Gb()
                .agregarAlCarrito();

        // Assert
        assertTrue(producto.mensajeNotificacion()
                .contains("The product has been added to your shopping cart"));

        CartPage carrito = producto.irAlCarrito();
        assertTrue(carrito.nombresEnCarrito().contains(COMPUTADORA));
    }

    @Test
    @DisplayName("CP-025 Rechazo al agregar producto configurable sin seleccionar opcion obligatoria (HDD)")
    void productoConfigurableSinHddMuestraError() {
        // Arrange
        ProductPage producto = new HomePage(driver).abrir()
                .buscarProducto(COMPUTADORA)
                .abrirProducto(COMPUTADORA);

        // Act
        producto.seleccionarProcesador(PROCESADOR)
                .seleccionarRam(RAM)
                .agregarAlCarrito();

        // Assert
        assertTrue(producto.mensajeNotificacion().contains("Please select HDD"));
    }
}