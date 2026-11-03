package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cr.ac.utn.proyecto.base.DatosPrueba;
import cr.ac.utn.proyecto.pages.InicioPage;
import cr.ac.utn.proyecto.pages.LoginPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("RF-02 Inicio y cierre de sesion")
class LoginTest extends PruebaTienda {

    @Test
    @DisplayName("CP-008 Login con credenciales validas")
    void loginValido() {
        Usuario usuario = registrarUsuarioNuevo();
        InicioPage inicio = new InicioPage(driver).cerrarSesion()
                .irALogin()
                .iniciarSesion(usuario.correo(), usuario.clave());

        assertAll(
                () -> assertTrue(inicio.haySesionIniciada(), "Debe mostrar la opcion Log out"),
                () -> assertEquals(usuario.correo(), inicio.obtenerCuentaEnEncabezado()));
    }

    @Test
    @DisplayName("CP-009 Login con contrasena incorrecta")
    void loginConClaveIncorrecta() {
        Usuario usuario = registrarUsuarioNuevo();
        LoginPage login = new InicioPage(driver).cerrarSesion()
                .irALogin()
                .iniciarSesionConError(usuario.correo(), "ClaveMala999");

        assertTrue(login.obtenerMensajeError().contains("Login was unsuccessful"));
        assertFalse(login.haySesionIniciada());
    }

    @Test
    @DisplayName("CP-010 Login con correo no registrado")
    void loginConCorreoNoRegistrado() {
        LoginPage login = abrirTienda().irALogin()
                .iniciarSesionConError(DatosPrueba.correoUnico(), DatosPrueba.CLAVE_VALIDA);

        String mensaje = login.obtenerMensajeError();
        assertAll(
                () -> assertTrue(mensaje.contains("Login was unsuccessful"), mensaje),
                () -> assertTrue(mensaje.contains("No customer account found"), mensaje),
                () -> assertFalse(login.haySesionIniciada()));
    }

    @Test
    @DisplayName("CP-011 Login con campos vacios")
    void loginConCamposVacios() {
        LoginPage login = abrirTienda().irALogin()
                .iniciarSesionConError("", "");

        assertFalse(login.obtenerMensajeError().isBlank(), "Debe mostrar un mensaje de error");
        assertFalse(login.haySesionIniciada());
    }

    @Test
    @DisplayName("CP-012 Cerrar sesion vuelve a ser visitante")
    void cerrarSesion() {
        registrarUsuarioNuevo();
        InicioPage inicio = new InicioPage(driver).cerrarSesion();

        assertAll(
                () -> assertTrue(inicio.esVisitante(), "Deben verse Register y Log in"),
                () -> assertFalse(inicio.haySesionIniciada()));
    }
}
