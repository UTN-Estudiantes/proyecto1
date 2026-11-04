package cr.ac.utn.proyecto.tests;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.pages.CartPage;
import cr.ac.utn.proyecto.pages.CheckoutPage;
import cr.ac.utn.proyecto.pages.HomePage;
import cr.ac.utn.proyecto.pages.ProductPage;
import cr.ac.utn.proyecto.pages.RegisterPage;
import cr.ac.utn.proyecto.pages.RegisterResultPage;
import cr.ac.utn.proyecto.pages.SearchResultsPage;
import cr.ac.utn.proyecto.pages.AccountPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CompraHistorialTest extends BaseTest {
    @Test
    @DisplayName("CP-026 Compra completa exitosa")
    void compraCompletaExitosa() {
        // Arrange: crear una cuenta propia para esta ejecución
        driver.get(URL_BASE);

        String email = "cp026" + System.currentTimeMillis() + "@example.com";
        String password = "Prueba123!";

        RegisterPage registro = new HomePage(driver).irRegistro();
        RegisterResultPage resultadoRegistro = registro.registrarUsuarioValido(
                "Test", "Buyer", email, password, password);

        assertEquals("Your registration completed",
                resultadoRegistro.obtenerMensajeResultado());

        HomePage inicio = resultadoRegistro.clickContinuar();

        // Act: buscar producto, agregarlo al carrito y completar el checkout
        SearchResultsPage resultados = inicio.buscar("Computing and Internet");
        ProductPage producto = resultados.abrirProducto("Computing and Internet");

        producto.agregarCarrito();
        assertTrue(producto.obtenerMensajeNotificacion()
                .contains("The product has been added to your shopping cart"),
                "Notificación real: " + producto.obtenerMensajeNotificacion());

        CartPage carrito = producto.irCarrito();
        assertEquals(1, carrito.obtenerCantidadItemsDiferentes());

        carrito.aceptarTerminosDeServicio();
        CheckoutPage checkout = carrito.irCheckout();

        checkout.completarOrdenEstandar(
                "United States",
                "California",
                "Denver",
                "123 Test Street",
                "80202",
                "5551234567");

        // Assert: comprobar confirmación y número de orden
        String mensaje = checkout.obtenerMensajeExito();
        String numeroOrden = checkout.obtenerNumeroOrden();

        assertTrue(mensaje.contains("Your order has been successfully processed!"),
                "Mensaje real de checkout: " + mensaje);
        assertTrue(numeroOrden.matches(".*\\d+.*"),
                "Se esperaba un número de orden. Valor real: " + numeroOrden);
    }

    @Test
    @DisplayName("CP-029 Consultar historial de pedidos")
    void consultarHistorialPedidos() {
        // Arrange: crear una cuenta propia
        driver.get(URL_BASE);

        String email = "cp029" + System.currentTimeMillis() + "@example.com";
        String password = "Prueba123!";

        RegisterPage registro = new HomePage(driver).irRegistro();
        RegisterResultPage resultadoRegistro = registro.registrarUsuarioValido(
                "Test", "Buyer", email, password, password);

        assertEquals("Your registration completed",
                resultadoRegistro.obtenerMensajeResultado());

        HomePage inicio = resultadoRegistro.clickContinuar();

        // Act: completar una compra para que esta cuenta tenga un pedido
        SearchResultsPage resultados = inicio.buscar("Computing and Internet");
        ProductPage producto = resultados.abrirProducto("Computing and Internet");
        producto.agregarCarrito();

        CartPage carrito = producto.irCarrito();
        carrito.aceptarTerminosDeServicio();

        CheckoutPage checkout = carrito.irCheckout();
        checkout.completarOrdenEstandar(
                "United States",
                "California",
                "Denver",
                "123 Test Street",
                "80202",
                "5551234567");

        assertTrue(checkout.obtenerMensajeExito()
                .contains("Your order has been successfully processed!"),
                "La compra debe completarse antes de revisar el historial");

        // Consultar pedidos de la cuenta que acaba de comprar
        AccountPage cuenta = new HomePage(driver).irCuenta();
        assertEquals(email, cuenta.obtenerEmail());

        cuenta.irAHistorialPedidos();

        // Assert
        assertTrue(cuenta.obtenerCantidadPedidosEnHistorial() > 0,
                "El historial debe mostrar al menos el pedido recién creado");
    }
}
