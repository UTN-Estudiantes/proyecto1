package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.pages.HomePage;
import cr.ac.utn.proyecto.pages.LoginPage;
import cr.ac.utn.proyecto.pages.RegisterPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Pruebas automatizadas con POM para RF-01 (Registro) y RF-02 (Inicio y cierre de sesion).
 */
class AutenticacionTest extends BaseTest {

    private static final String CLAVE = "Password123";

    private String generarCorreoUnico() {
        return "qa.utn." + System.currentTimeMillis() + "@test.com";
    }

    @Test
    @DisplayName("CP-001 Registro exitoso de usuario con datos validos y correo unico")
    void registroExitosoConCorreoUnico() {
        // Arrange
        String correo = generarCorreoUnico();

        // Act
        RegisterPage registro = new HomePage(driver).abrir()
                .irARegistro()
                .registrarUsuario("Juan", "Perez", correo, CLAVE, CLAVE);

        // Assert
        assertEquals("Your registration completed", registro.mensajeExito());
        assertEquals(correo, registro.cuentaEnEncabezado());
    }

    @Test
    @DisplayName("CP-002 Registro rechazado con correo electronico ya registrado")
    void registroRechazadoConCorreoExistente() {
        // Arrange
        String correo = generarCorreoUnico();
        new HomePage(driver).abrir()
                .irARegistro()
                .registrarUsuario("Juan", "Perez", correo, CLAVE, CLAVE);
        HomePage homeVisitante = new HomePage(driver).abrir().cerrarSesion();
        // Act
        RegisterPage segundoIntento = homeVisitante.irARegistro()
                .registrarUsuario("Juan", "Perez", correo, CLAVE, CLAVE);
        // Assert
        assertTrue(segundoIntento.textoErrorGeneral().contains("The specified email already exists"));
    }
    
    @Test
    @DisplayName("CP-003 Registro rechazado cuando la contrasena y su confirmacion no coinciden")
    void registroRechazadoConfirmacionDistinta() {
        // Arrange
        String correo = "ana.utn@test.com";
        // Act
        RegisterPage registro = new HomePage(driver).abrir()
                .irARegistro()
                .registrarUsuario("Ana", "Gomez", correo, CLAVE, "Diferente999");
        // Assert
        assertTrue(registro.textoErrorCampo()
                .contains("The password and confirmation password do not match."));
    }

    @Test
    @DisplayName("CP-004 Registro rechazado con contrasena de 5 caracteres (bajo el limite minimo de 6)")
    void registroRechazadoPasswordCorta() {
        // Arrange
        String correo = "luis.utn@test.com";
        // Act
        RegisterPage registro = new HomePage(driver).abrir()
                .irARegistro()
                .registrarUsuario("Luis", "Mora", correo, "12345", "12345");
        // Assert
        assertTrue(registro.textoErrorCampo()
                .contains("The password should have at least 6 characters."));
    }

    @Test
    @DisplayName("CP-006 Inicio de sesion exitoso con credenciales validas")
    void loginExitosoMuestraCuentaYLogout() {
        // Arrange
        String correo = generarCorreoUnico();
        new HomePage(driver).abrir()
                .irARegistro()
                .registrarUsuario("Carlos", "Soto", correo, CLAVE, CLAVE);
        HomePage homeVisitante = new HomePage(driver).abrir().cerrarSesion();

        // Act
        HomePage homeLogueado = homeVisitante.irALogin()
                .iniciarSesion(correo, CLAVE);

        // Assert
        assertEquals(correo, homeLogueado.cuentaLogueada());
        assertTrue(homeLogueado.estaVisibleLogout());
    }

    @Test
    @DisplayName("CP-007 Inicio de sesion fallido con contrasena incorrecta")
    void loginConClaveIncorrectaMuestraError() {
        // Arrange
        String correo = generarCorreoUnico();
        new HomePage(driver).abrir()
                .irARegistro()
                .registrarUsuario("Elena", "Rojas", correo, CLAVE, CLAVE);
        HomePage homeVisitante = new HomePage(driver).abrir().cerrarSesion();

        // Act
        LoginPage login = homeVisitante.irALogin()
                .iniciarSesionFallido(correo, "mala");

        // Assert
        assertTrue(login.textoError().contains("The credentials provided are incorrect"));
    }

    @Test
    @DisplayName("CP-009 Cierre de sesion exitoso retorna al estado de visitante")
    void cerrarSesionVuelveAVisitante() {
        // Arrange
        String correo = generarCorreoUnico();
        new HomePage(driver).abrir()
                .irARegistro()
                .registrarUsuario("Sofia", "Vargas", correo, CLAVE, CLAVE);

        // Act
        HomePage homeVisitante = new HomePage(driver).abrir()
                .cerrarSesion();

        // Assert
        assertTrue(homeVisitante.estaVisibleLogin());
    }
}