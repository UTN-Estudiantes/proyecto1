package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.pages.CuentaPage;
import cr.ac.utn.proyecto.pages.RegistroPage;
import cr.ac.utn.proyecto.base.GeneradorDatos;

public class CuentaTest extends BaseTest {

    private static final String CLAVE = "Clave1234";

    @Test
    @DisplayName("CP-018 Editar la información de la cuenta")
    void editarInformacionCuenta() {

        // Arrange (Preparación)
        RegistroPage registro = new RegistroPage(driver).abrir().registrar("Prueba", "Equipo", GeneradorDatos.correoUnico(), CLAVE, CLAVE);

        // Act (Acción)
        // Se realiza la edición de la información de la cuenta
        registro.continuar();
        CuentaPage cuenta = new CuentaPage(driver).abrirInformacion().editarNombreYApellido("Pruebas", "Equipos");

        // Assert (Comprobación)
        // Se verifica que los cambios se hayan guardado correctamente
        cuenta.abrirInformacion();
        assertEquals("Pruebas", cuenta.nombreGuardado(), "El nombre no coincide con el esperado");
        assertEquals("Equipos", cuenta.apellidoGuardado(), "El apellido no coincide con el esperado");
    }

    @Test
    @DisplayName("CP-019 Cambiar contraseña con confirmación distinta")
    void cambiarContrasenaConfirmacionDistinta() {
        // Arrange (Preparación)
        RegistroPage registro = new RegistroPage(driver).abrir().registrar("Prueba", "Equipo", GeneradorDatos.correoUnico(), CLAVE, CLAVE);

        String claveActual = CLAVE; // "Clave1234"
        String claveNueva = "Prueba123";
        String confirmacion = "Prueba456";

        // Act (Acción)
        // Se realiza el cambio de contraseña con una confirmación distinta
        CuentaPage cuenta = new CuentaPage(driver).abrirCambioClave().cambiarClave(claveActual, claveNueva, confirmacion);

        // Assert (Comprobación)
        // Se verifican los mensajes de error para el cambio de contraseña
        assertEquals("The new password and confirmation password do not match.", cuenta.errorConfirmacion(), 
        "El aviso de contraseña no coinciden no coincide.");
    }
}