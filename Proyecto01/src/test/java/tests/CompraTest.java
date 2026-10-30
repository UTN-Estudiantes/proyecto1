package tests;

import base.BaseTest;
import pages.CheckoutPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
