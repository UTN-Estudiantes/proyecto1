package tests;

import base.BaseTest;
import pages.CuentaPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertEquals;
import pages.CheckoutPage;

/**
 *
 * @author ortiz
 */
public class CuentaTest extends BaseTest {

    @Test
    @DisplayName("CP-050 Ver y editar la informacion de la cuenta")
    public void editarInformacionCuenta() {
        CuentaPage cuenta = new CuentaPage(driver);

        String correo = "qa" + System.currentTimeMillis() + "@mail.com";

        cuenta.registrarUsuario("Ana", "Prueba", correo, "Prueba123")
                .irAInformacionCuenta()
                .editarApellido("Modificado")
                .irAInformacionCuenta();

        assertEquals("Modificado", cuenta.obtenerApellido());
    }

    @Test
    @DisplayName("CP-052 Cambiar contrasena con clave actual incorrecta")
    public void cambiarClaveConClaveActualIncorrecta() {
        
        CuentaPage cuenta = new CuentaPage(driver);
        
        String correo = "qa" + System.currentTimeMillis() + "@mail.com";
        
        cuenta.registrarUsuario("Ana", "Prueba", correo, "Prueba123")
                .irAInformacionCuenta()
                .irACambiarClave()
                .cambiarClave("Incorrecta1", "Nueva1234", "Nueva1234");

        assertEquals("Old password doesn't match", cuenta.obtenerError());
    }
    
    @Test
    @DisplayName("CP-043 Compra completa como usuario registrado")
    public void compraComoUsuarioRegistrado(){
        CuentaPage cuenta = new CuentaPage(driver);
        CheckoutPage checkout = new CheckoutPage(driver);
        
        String correo = "qa" + System.currentTimeMillis() + "@mail.com";
        
        cuenta.registrarUsuario("Ana", "Prueba", correo, "Prueba123");
        
        checkout.agregarProductoAlCarrito(URL_SITIO + "/141-inch-laptop", 31)
                .aceptarTerminosYContinuar()
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
