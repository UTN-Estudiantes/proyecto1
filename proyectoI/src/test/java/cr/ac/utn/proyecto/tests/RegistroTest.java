package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.base.GeneradorDatos;
import cr.ac.utn.proyecto.pages.InicioPage;
import cr.ac.utn.proyecto.pages.RegistroPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

public class RegistroTest extends BaseTest {

    private static final String CLAVE = "Clave1234";

    @Test
    @DisplayName("CP-001 Registro con datos validos")
    void registroValido() {

        // Arrange (Preparación)

        // Se genera un correo único para la prueba
        String correo = GeneradorDatos.correoUnico();

        // Act (Acción)
        RegistroPage registro = new RegistroPage(driver).abrir().registrar("Nombre", "Apellido", correo, CLAVE, CLAVE);

        // Assert (Comprobación)

        // Se verifica que el mensaje de confirmación sea el esperado
        assertEquals("Your registration completed", registro.mensajeConfirmacion(), "El mensaje de éxito no coincide");

        // Act (Acción)
        InicioPage inicio = registro.continuar();

        // Assert (Comprobación)

        // Se verifica que el encabezado muestre el correo de la cuenta registrada
        assertEquals(correo, inicio.textoCuenta(), "El correo no coincide con el de la cuenta registrada");
    }

    @Test
    @DisplayName("CP-002 Registro con campos obligatorios vacíos")
    void registroConCamposObligatoriosVacios() {

        // Act (Acción)

        //Se abre la página de registro y se agregan datos vacíos en cada campo
        RegistroPage registro = new RegistroPage(driver).abrir().registrar("", "", "", "", "");

        // Assert (Comprobación)

        //Se verifican los mensajes de error para cada campo obligatorio
        assertEquals("First name is required.", registro.errorNombre(), "El mensaje de error para el nombre no coincide");
        assertEquals("Last name is required.", registro.errorApellido(), "El mensaje de error para el apellido no coincide");
        assertEquals("Email is required.", registro.errorCorreo(), "El mensaje de error para el correo no coincide");
        assertEquals("Password is required.", registro.errorClave(), "El mensaje de error para la contraseña no coincide");
        assertEquals("Password is required.", registro.errorConfirmacion(), "El mensaje de error para la confirmación de contraseña no coincide");
    }

    @Test
    @DisplayName("CP-003 Registro con correo sin formato válido")
    void registroConCorreoInvalido() {

        // Act (Acción)

        //Se abre la página de registro y se agrega un correo sin formato válido
        RegistroPage registro = new RegistroPage(driver).abrir().registrar("Nombre", "Apellido", "cuentaejemplo.com", CLAVE, CLAVE);

        // Assert (Comprobación)
        assertEquals("Wrong email", registro.errorCorreo(), "El mensaje de error para el correo no coincide");
    }

    @Test
    @Disabled("Pendiente de implementar")
    @DisplayName("CP-004 Registro con correo ya registrado")
    void registroConCorreoYaRegistrado() {
        // Arrange (Preparación)

        // Act (Acción)

        // Assert (Comprobación)
    }

    @Test
    @Disabled("Pendiente de implementar")
    @DisplayName("CP-005 Registro con contraseña demasiado corta")
    void registroConContrasenaDemasiadoCorta() {
        // Arrange (Preparación)

        // Act (Acción)

        // Assert (Comprobación)
    }

    @Test
    @Disabled("Pendiente de implementar")
    @DisplayName("CP-006 Registro con confirmación distinta")
    void registroConConfirmacionDistinta() {
        // Arrange (Preparación)

        // Act (Acción)

        // Assert (Comprobación)
    }
}
