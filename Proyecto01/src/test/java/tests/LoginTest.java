package tests;

import base.BaseTest;
import pages.LoginPage;
import pages.RegistroPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest extends BaseTest {

    private static final String CLAVE = "Prueba123";

    private String correoUnico() {
        return "qa" + System.currentTimeMillis() + "@mail.com";
    }

    // Preparacion propia de cada caso: crea un usuario nuevo y cierra sesion
    private void crearUsuarioYSalir(String correo) {
        RegistroPage registro = new RegistroPage(driver);
        registro.abrir()
                .registrar("Female", "Ana", "Prueba", correo, CLAVE, CLAVE);
        registro.obtenerMensajeResultado();
        new LoginPage(driver).cerrarSesion();
    }

    @Test
    @DisplayName("CP-007 Inicio de sesion con credenciales validas")
    public void loginValido() {
        String correo = correoUnico();
        crearUsuarioYSalir(correo);

        LoginPage login = new LoginPage(driver);
        login.abrir()
             .iniciarSesion(correo, CLAVE);

        assertEquals(correo, login.obtenerCorreoEncabezado());
        assertTrue(login.haySesionIniciada());
    }

    @Test
    @DisplayName("CP-008 Inicio de sesion con credenciales invalidas")
    public void loginInvalido() {
        String correo = correoUnico();
        crearUsuarioYSalir(correo);

        LoginPage login = new LoginPage(driver);

        // Caso 1: correo valido, clave incorrecta
        login.abrir()
             .iniciarSesion(correo, "ClaveMala1");
        assertEquals("Login was unsuccessful. Please correct the errors and try again.", login.obtenerMensajeError());
        assertEquals("The credentials provided are incorrect", login.obtenerDetalleError());
        assertFalse(login.haySesionIniciada());

        // Caso 2: correo que nunca se registro
        login.iniciarSesion("noexiste" + correo, CLAVE);
        assertEquals("No customer account found", login.obtenerDetalleError());
        assertFalse(login.haySesionIniciada());
    }
    
    @Test
    @DisplayName("CP-010 Cierre de sesion: vuelve a ser visitante y Mi cuenta deja de ser accesible")
    public void cierreDeSesion() {
        String correo = correoUnico();
        RegistroPage registro = new RegistroPage(driver);
        LoginPage login = new LoginPage(driver);

        // Preparacion propia del caso: registrar un usuario (queda con sesion iniciada)
        registro.abrir()
                .registrar("Female", "Ana", "Prueba", correo, CLAVE, CLAVE);
        registro.obtenerMensajeResultado();

        login.cerrarSesion();
        assertFalse(login.haySesionIniciada());

        login.abrirPagina(URL_SITIO + "/customer/info");
        assertTrue(login.estaEnPantallaDeLogin());
    }
}
