package cr.ac.utn.proyecto.tests;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.base.Visual;
import cr.ac.utn.proyecto.pages.CartPage;
import cr.ac.utn.proyecto.pages.ProductPage;
import cr.ac.utn.proyecto.pages.WishlistPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CarritoWishListTest extends BaseTest {

    @Test
    @DisplayName("CP-018 Agregar producto simple al carrito y verificar indicador")

    void cp018AgregarProductoSimpleAlCarrito() {
        driver.get(URL_BASE + "computing-and-internet");
        Visual.pausa();

        ProductPage productPage = new ProductPage(driver);
        productPage.agregarCarrito();
        Visual.pausa();

        String notificacion = productPage.obtenerMensajeNotificacion();
        Assertions.assertTrue(notificacion.contains("The product has been added to your shopping cart"));
        Assertions.assertEquals("(1)", productPage.obtenerCantidadIndicadorCarrito());
    }

    @Test
    @DisplayName("CP-020 Agregar Build your own computer con todas las opciones obligatorias")

    void cp020AgregarComputadoraConOpciones() {
        driver.get(URL_BASE + "build-your-own-computer");
        Visual.pausa();

        ProductPage productPage = new ProductPage(driver);
        productPage.seleccionarOpcionesComputadora();
        Visual.pausa();

        productPage.agregarCarrito();
        Visual.pausa();

        String notificacion = productPage.obtenerMensajeNotificacion();
        Assertions.assertTrue(notificacion.contains("The product has been added to your shopping cart"));
    }

    @Test
    @DisplayName("CP-021 Intentar agregar producto configurable sin opciones obligatorias")
    void cp021AgregarComputadoraSinOpciones() {
        driver.get(URL_BASE + "build-your-own-computer");
        Visual.pausa();

        ProductPage productPage = new ProductPage (driver);
        productPage.agregarCarrito();
        Visual.pausa();

        String notificacion = productPage.obtenerMensajeNotificacion();
        Assertions.assertTrue(notificacion.contains("Please select HDD"));
        Assertions.assertEquals("(0)", productPage.obtenerCantidadIndicadorCarrito());
    }

    @Test
    @DisplayName("CP-022 Agregar a wishlist y mover al carrito")
    void cp022AgregarProductoWishlistYMoverACarrito() {
        driver.get(URL_BASE + "50s-rockabilly-polka-dot-top-jr-plus-size");
        Visual.pausa();

        ProductPage productPage = new ProductPage(driver);
        productPage.agregarWishlist();
        Visual.pausa();

        driver.get(URL_BASE + "wishlist");
        Visual.pausa();

        WishlistPage wishlistPage = new WishlistPage(driver);
        Assertions.assertEquals(1, wishlistPage.obtenerCantidadProductos());

        CartPage cartPage = wishlistPage.moverPrimerProductoCarrito();
        Visual.pausa();

        Assertions.assertEquals(1, cartPage.obtenerCantidadItemsDiferentes());
    }

    @Test
    @DisplayName("CP-023 Cambiar cantidad y rexalcular carrito")
    void cp023CambiarCantidadYRecalcularCarrito() {
        driver.get(URL_BASE + "computing-and-internet");
        Visual.pausa();

        ProductPage productPage = new ProductPage(driver);
        productPage.agregarCarrito();
        Visual.pausa();
        driver.get(URL_BASE + "cart");
        Visual.pausa();

        CartPage cartPage = new CartPage(driver);
        Assertions.assertEquals("10.00", cartPage.obtenerSubtotalItem());

        cartPage.actualizarCantidadPrimerProducto("3");
        Visual.pausa();

        Assertions.assertEquals("30.00", cartPage.obtenerSubtotalItem());
    }
    
}