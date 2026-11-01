package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.base.DatosPrueba;
import cr.ac.utn.proyecto.pages.InicioPage;
import cr.ac.utn.proyecto.pages.RegistroPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Pruebas de RF-01 Registro de usuario. */
class RegistroTest extends BaseTest {

    private static final String NOMBRE = "Juan";
    private static final String APELLIDO = "Prueba";
    private static final String CLAVE = "Prueba123";

    @Test
    @DisplayName("CP-001 Registro exitoso con datos válidos")
    void registroExitosoConDatosValidos() {
        String correo = DatosPrueba.correoUnico();

        RegistroPage registro = new InicioPage(driver).abrir()
                .irARegistro()
                .registrar(NOMBRE, APELLIDO, correo, CLAVE, CLAVE);

        assertEquals("Your registration completed", registro.mensajeResultado());
        InicioPage encabezado = new InicioPage(driver);
        assertEquals(correo, encabezado.correoEnEncabezado());
        assertTrue(encabezado.haySesionIniciada());
    }

    @Test
    @DisplayName("CP-004 Registro con un correo que ya existe")
    void registroConCorreoQueYaExiste() {
        String correo = DatosPrueba.correoUnico();
        new InicioPage(driver).abrir()
                .irARegistro()
                .registrar(NOMBRE, APELLIDO, correo, CLAVE, CLAVE);

        RegistroPage registro = new InicioPage(driver).cerrarSesion()
                .irARegistro()
                .registrar(NOMBRE, APELLIDO, correo, CLAVE, CLAVE);

        assertTrue(registro.errorResumen().contains("The specified email already exists"));
        assertFalse(new InicioPage(driver).haySesionIniciada());
    }
}
