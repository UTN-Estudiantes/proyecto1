package cr.ac.utn.proyecto.tests;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.pages.HomePage;
import cr.ac.utn.proyecto.pages.LoginPage;
import cr.ac.utn.proyecto.pages.RegisterPage;
import cr.ac.utn.proyecto.pages.RegisterResultPage;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RegistroLoginTest extends BaseTest {
    @Test
    @DisplayName("CP-001 Registro exitoso")
    void registroExitoso() {
        // arange
        driver.get(URL_BASE);
        HomePage inicio = new HomePage(driver);
        RegisterPage registro = inicio.irRegistro();

        String email = "qa" + System.currentTimeMillis() + "@example.com";
        String password = "Prueba123!";

        // act
        RegisterResultPage resultado = registro.registrarUsuarioValido("Ana", "Prueba", email, password, password);

        // assert
        String mensaje = resultado.obtenerMensajeResultado();
        assertTrue(mensaje.contains("Your registration completed"), "mensaje real:" + mensaje);
    }

    @Test
    @DisplayName("CP-002 Registro con correo duplicado")
    void registroCorreoDuplicado() {
        // arange
        driver.get(URL_BASE);

        String email = "qa" + System.currentTimeMillis() + "@example.com";
        String password = "Prueba123!";
        HomePage inicio = registrarYcerrarSesion(email, password);
        RegisterPage segundoRegistro = inicio.irRegistro();

        // act
        segundoRegistro.selecionarGeneroMasculino();
        segundoRegistro.escribirNombre("Ana");
        segundoRegistro.escribirApellidos("Prueba");
        segundoRegistro.escribirEmail(email);
        segundoRegistro.escribirPassword(password);
        segundoRegistro.escribirConfirmacionPassword(password);
        segundoRegistro.clickRegistroError();

        // assert
        String error = segundoRegistro.obtenerErrorGeneral();
        assertTrue(error.contains("The specified email already exists"),
                "Mensaje real: " + error);
    }

    @Test
    @DisplayName("CP-003 Inicio sesion exitoso")
    void inicioSesionExitoso() {
        // arange
        driver.get(URL_BASE);

        String email = "qa" + System.currentTimeMillis() + "@example.com";
        String password = "Prueba123!";
        HomePage inicio = registrarYcerrarSesion(email, password);
        LoginPage login = inicio.irLogin();
        // act
        HomePage inicioConSesion = login.iniciarSesion(email, password);
        // assert

        assertEquals(email, inicioConSesion.obtenerTextoCuenta());

    }

    @Test
    @DisplayName("CP-004 Inicio de sesión con contraseña incorrecta")
    void inicioSesionConPasswordIncorrecta() {
        // arange
        driver.get(URL_BASE);

        String email = "qa" + System.currentTimeMillis() + "@example.com";
        String password = "Prueba123!";
        HomePage inicio = registrarYcerrarSesion(email, password);
        LoginPage login = inicio.irLogin();
        // act
        login.escribirEmail(email);
        login.escribirPassword("ClaveIncorrecta123!");
        login.clickLoginConError();
        // assert
        String error = login.obtenerErrorGeneral();

        assertTrue(error.contains("The credentials provided are incorrect"),
                "Mensaje real: " + error);
        assertTrue(driver.getCurrentUrl().endsWith("/login"),
                "El login no debería completarse. URL real: " + driver.getCurrentUrl());
    }

    @Test
    @DisplayName("CP-005 Registro sin campos obligatorios")
    void registroSinCamposObligatorios() {
        // arange
        driver.get(URL_BASE);
        RegisterPage registro = new HomePage(driver).irRegistro();

        // act
        registro.clickRegistroError();

        // assert
        assertAll("Mensajes de campos obligatorios",
                () -> assertEquals("First name is required.",
                        registro.obtenerErrorNombre()),
                () -> assertEquals("Last name is required.",
                        registro.obtenerErrorApellidos()),
                () -> assertEquals("Email is required.",
                        registro.obtenerErrorEmail()),
                () -> assertEquals("Password is required.",
                        registro.obtenerErrorPassword()),
                () -> assertEquals("Password is required.",
                        registro.obtenerErrorConfirmacionPassword()));

    }

    @Test
    @DisplayName("CP-006 Registro con contraseñas diferentes")
    void registroConPasswordsDiferentes() {
        // Arrange
        driver.get(URL_BASE);
        RegisterPage registro = new HomePage(driver).irRegistro();

        registro.selecionarGeneroMasculino();
        registro.escribirNombre("Ana");
        registro.escribirApellidos("Prueba");
        registro.escribirEmail("qa" + System.currentTimeMillis() + "@example.com");
        registro.escribirPassword("Prueba123!");
        registro.escribirConfirmacionPassword("OtraClave123!");

        // Act
        registro.clickRegistroError();

        // Assert
        assertEquals(
                "The password and confirmation password do not match.",
                registro.obtenerErrorConfirmacionPassword());
    }

    @Test
    @DisplayName("CP-007 Registro con email inválido")
    void registroConEmailInvalido() {
        // arrange
        driver.get(URL_BASE);
        RegisterPage registro = new HomePage(driver).irRegistro();
        registro.selecionarGeneroMasculino();
        registro.escribirNombre("ana");
        registro.escribirApellidos("prueba");
        registro.escribirEmail("email-invalido");
        registro.escribirPassword("Prueba123!");
        registro.escribirConfirmacionPassword("Prueba123!");

        // act
        registro.clickRegistroError();

        // assert
        assertEquals("Wrong email", registro.obtenerErrorEmail());
    }

    @Test
    @DisplayName("CP-008 Cierre de sesión exitoso")
    void cierreSesionExitoso() {
        // Arrange
        driver.get(URL_BASE);

        String email = "qa" + System.currentTimeMillis() + "@example.com";
        String password = "Prueba123!";

        HomePage visitante = registrarYcerrarSesion(email, password);
        HomePage usuarioAutenticado = visitante.irLogin().iniciarSesion(email, password);

        // Act
        HomePage visitanteTrasLogout = usuarioAutenticado.cerrarSesion();

        // Assert
        assertTrue(visitanteTrasLogout.estaComoVisitante(),
                "Después del logout debe aparecer Log in y desaparecer Log out");
    }

    private HomePage registrarYcerrarSesion(String email, String password) {
        RegisterPage registro = new HomePage(driver).irRegistro();
        RegisterResultPage resultado = registro.registrarUsuarioValido("Ana", "Prueba", email, password, password);
        HomePage inicio = resultado.clickContinuar();
        return inicio.cerrarSesion();

    }
}