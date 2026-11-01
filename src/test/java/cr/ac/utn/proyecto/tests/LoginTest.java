package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.base.DatosPrueba;
import cr.ac.utn.proyecto.pages.InicioPage;
import cr.ac.utn.proyecto.pages.LoginPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Pruebas de RF-02 Inicio y cierre de sesion. Cada prueba registra su propio usuario. */
class LoginTest extends BaseTest {

    private static final String NOMBRE = "Juan";
    private static final String APELLIDO = "Prueba";
    private static final String CLAVE = "Prueba123";

    @Test
    @DisplayName("CP-007 Login con correo y clave correctos")
    void loginConCorreoYClaveCorrectos() {
        String correo = DatosPrueba.correoUnico();
        new InicioPage(driver).abrir()
                .irARegistro()
                .registrar(NOMBRE, APELLIDO, correo, CLAVE, CLAVE);

        InicioPage inicio = new InicioPage(driver).cerrarSesion()
                .irALogin()
                .iniciarSesion(correo, CLAVE);

        assertEquals(correo, inicio.correoEnEncabezado());
        assertTrue(inicio.haySesionIniciada());
    }

    @Test
    @DisplayName("CP-008 Login con clave incorrecta")
    void loginConClaveIncorrecta() {
        String correo = DatosPrueba.correoUnico();
        new InicioPage(driver).abrir()
                .irARegistro()
                .registrar(NOMBRE, APELLIDO, correo, CLAVE, CLAVE);

        LoginPage login = new InicioPage(driver).cerrarSesion()
                .irALogin()
                .iniciarSesionFallido(correo, "ClaveMala1");

        String error = login.mensajeError();
        assertTrue(error.contains("Login was unsuccessful. Please correct the errors and try again."));
        assertTrue(error.contains("The credentials provided are incorrect"));
        assertFalse(new InicioPage(driver).haySesionIniciada());
    }

    @Test
    @DisplayName("CP-011 Cerrar sesión")
    void cerrarSesion() {
        String correo = DatosPrueba.correoUnico();
        new InicioPage(driver).abrir()
                .irARegistro()
                .registrar(NOMBRE, APELLIDO, correo, CLAVE, CLAVE);

        InicioPage inicio = new InicioPage(driver).cerrarSesion();

        assertFalse(inicio.haySesionIniciada());
        assertEquals("", inicio.correoEnEncabezado());
        inicio.irARegistro();
        inicio.irALogin();
    }
}
