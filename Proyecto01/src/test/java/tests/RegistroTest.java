package tests;

import base.BaseTest;
import pages.LoginPage;
import pages.RegistroPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistroTest extends BaseTest {

    private static final String CLAVE = "Prueba123";

    private String correoUnico() {
        return "qa" + System.currentTimeMillis() + "@mail.com";
    }

    @Test
    @DisplayName("CP-001 Registro exitoso con todos los datos validos")
    public void registroExitoso() {
        String correo = correoUnico();
        RegistroPage registro = new RegistroPage(driver);

        registro.abrir()
                .registrar("Female", "Ana", "Prueba", correo, CLAVE, CLAVE);

        assertEquals("Your registration completed", registro.obtenerMensajeResultado());
        assertEquals(correo, new LoginPage(driver).obtenerCorreoEncabezado());
    }

    @Test
    @DisplayName("CP-002 Registro con correo ya registrado")
    public void registroConCorreoRepetido() {
        String correo = correoUnico();
        RegistroPage registro = new RegistroPage(driver);
        LoginPage login = new LoginPage(driver);

        // Preparacion propia del caso: crear el usuario y cerrar sesion
        registro.abrir()
                .registrar("Female", "Ana", "Prueba", correo, CLAVE, CLAVE);
        registro.obtenerMensajeResultado();
        login.cerrarSesion();

        // Intentar registrarse otra vez con el mismo correo
        registro.abrir()
                .registrar("Female", "Ana", "Prueba", correo, CLAVE, CLAVE);

        assertEquals("The specified email already exists", registro.obtenerErrorResumen());
    }
}