package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.base.GeneradorDatos;
import cr.ac.utn.proyecto.pages.InicioPage;
import cr.ac.utn.proyecto.pages.LoginPage;
import cr.ac.utn.proyecto.pages.RegistroPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

        //Iniciar sesión con el correo registrado
        InicioPage inicioSesion = inicio.irALogin().iniciarSesion(correo, CLAVE);

        // Assert (Comprobación)

        // Se verifica que el encabezado muestre el correo de la cuenta registrada
        assertEquals(correo, inicioSesion.textoCuenta(), "El correo no coincide con el de la cuenta registrada");
    }
    
    @Test
    @Disabled("Pendiente de implementar")
    @DisplayName("CP-008 Login con credenciales inválidas")
    void loginConCredencialesInvalidas() {
        // Arrange (Preparación)

        // Act (Acción)

        // Assert (Comprobación)
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
