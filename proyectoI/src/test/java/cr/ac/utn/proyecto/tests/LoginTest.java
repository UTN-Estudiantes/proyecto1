package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.base.GeneradorDatos;
import cr.ac.utn.proyecto.pages.InicioPage;
import cr.ac.utn.proyecto.pages.LoginPage;
import cr.ac.utn.proyecto.pages.RegistroPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest extends BaseTest {

    private static final String CLAVE = "Clave1234";

    @Test
    @DisplayName("CP-007 Login con credenciales válidas")
    void loginConCredencialesValidas() {

        // Arrange (Preparación)

        // Se genera un correo único para la prueba
        String correo = GeneradorDatos.correoUnico();

        // Se registra un correo nuevo previamente para poder iniciar sesión con él
        RegistroPage registro = new RegistroPage(driver).abrir().registrar("Nombre", "Apellido", correo, CLAVE, CLAVE);

        InicioPage inicio = registro.continuar();

        // Se cierra la sesión para poder iniciar sesión con el correo registrado
        inicio.cerrarSesion();

        // Act (Acción)

        // Iniciar sesión con el correo registrado
        InicioPage inicioSesion = inicio.irALogin().iniciarSesion(correo, CLAVE);

        // Assert (Comprobación)

        // Se verifica que el encabezado muestre el correo de la cuenta registrada
        assertEquals(correo, inicioSesion.textoCuenta(), "El correo no coincide con el de la cuenta registrada");
    }

    @Test
    @DisplayName("CP-008 Login con clave incorrecta")
    void loginConClaveIncorrecta() {

        // Arrange (Preparación)

        // Se genera un correo único para la prueba
        String correo = GeneradorDatos.correoUnico();

        // Se registra un correo nuevo previamente para poder iniciar sesión con él
        RegistroPage registro = new RegistroPage(driver).abrir().registrar("Nombre", "Apellido", correo, CLAVE, CLAVE);

        InicioPage inicio = registro.continuar();

        // Se cierra la sesión para poder iniciar sesión con el correo registrado
        inicio.cerrarSesion();

        String claveIncorrecta = "ClaveIncorrecta";

        // Se guarda la variable con el mensaje de error
        String mensajeEsperado = "The credentials provided are incorrect";

        // Act (Acción)

        // Iniciar sesión con clave inválida
        LoginPage login = new LoginPage(driver).abrir().iniciarSesionFallido(correo, claveIncorrecta);

        // Assert (Comprobación)

        // Se verifica que el mensaje de error sea el esperado
        assertTrue(login.mensajeError().contains(mensajeEsperado), "El mensaje de error no coincide. Texto real: " + login.mensajeError());
    }

    @Test
    @Disabled("Pendiente de implementar")
    @DisplayName("CP-009 Login con credenciales vacías")
    void loginConCredencialesVacias() {
        // Arrange (Preparación)

        // Act (Acción)

        // Assert (Comprobación)
    }

    @Test
    @Disabled("Pendiente de implementar")
    @DisplayName("CP-010 Cerrar sesión vuelve a visitante")
    void cerrarSesionVuelveAVisitante() {
        // Arrange (Preparación)

        // Act (Acción)

        // Assert (Comprobación)
    }
}
