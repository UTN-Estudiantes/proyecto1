package tests;

import base.BaseTest;
import pages.CuentaPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
    
    

}
