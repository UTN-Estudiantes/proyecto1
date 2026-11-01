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
    @Disabled("Pendiente de implementar")
    @DisplayName("CP-002 Registro con campos obligatorios vacíos")
    void registroConCamposObligatoriosVacios() {
        // Arrange (Preparación)

        // Act (Acción)

        // Assert (Comprobación)
    }

    @Test
    @Disabled("Pendiente de implementar")
    @DisplayName("CP-003 Registro con correo sin formato válido")
    void registroConCorreoInvalido() {
        // Arrange (Preparación)

        // Act (Acción)

        // Assert (Comprobación)
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
