package tests;

import base.BaseTest;
import pages.CheckoutPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author ortiz
 */
public class CompraTest extends BaseTest {

    @Test
    @DisplayName("CP-042 Compra completa como invitado")
    public void compraComoInvitado() {
        CheckoutPage checkout = new CheckoutPage(driver);
        checkout.agregarProductoAlCarrito(URL_SITIO + "/141-inch-laptop", 31)
                .aceptarTerminosYContinuar()
                .continuarComoInvitado()
                .completarFacturacion("Ana", "Prueba", "qa"
                        + System.currentTimeMillis() + "@mail.com", "Costa Rica", "San Jose", "Calle 1", "10101", "88888888")
                .continuarDireccionEnvio()
                .continuarMetodoEnvio()
                .continuarMetodoPago()
                .continuarInfoPago()
                .confirmarOrden();

        assertEquals("Your order has been successfully processed!", checkout.obtenerMensajeExito());
    }
    
    @Test
    @DisplayName("CP-044 Checkout sin aceptar los terminos de servicio")
    public void checkoutSinAceptarTerminos(){
        CheckoutPage checkout = new CheckoutPage(driver);
        checkout.agregarProductoAlCarrito(URL_SITIO + "/141-inch-laptop", 31)
                .irAlCheckoutSinAceptarTerminos();
        
        assertEquals("Please accept the terms of service before the next step.",
                checkout.obtenerAvisoTerminos());
        
        assertTrue(driver.getCurrentUrl().contains("/cart"));
    }
}
