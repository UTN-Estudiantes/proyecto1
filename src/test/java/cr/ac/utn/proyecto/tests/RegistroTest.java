package cr.ac.utn.proyecto.tests;

import static cr.ac.utn.proyecto.base.DatosDePrueba.APELLIDO;
import static cr.ac.utn.proyecto.base.DatosDePrueba.CLAVE_VALIDA;
import static cr.ac.utn.proyecto.base.DatosDePrueba.NOMBRE;
import static cr.ac.utn.proyecto.base.DatosDePrueba.correoUnico;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.pages.PaginaInicio;
import cr.ac.utn.proyecto.pages.PaginaRegistro;
import cr.ac.utn.proyecto.pages.PaginaRegistro.Genero;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** RF-01: registro de usuarios. */
class RegistroTest extends BaseTest {

    private PaginaRegistro abrirRegistro() {
        return new PaginaInicio(driver).abrir().irARegistro();
    }

    @Test
    @DisplayName("CP-001 Registro exitoso con datos válidos")
    void registroExitosoConDatosValidos() {
        String correo = correoUnico();

        PaginaRegistro registro = abrirRegistro()
                .registrarUsuario(Genero.MASCULINO, NOMBRE, APELLIDO, correo, CLAVE_VALIDA, CLAVE_VALIDA);

        assertEquals("Your registration completed", registro.obtenerMensajeRegistroExitoso());
        PaginaInicio inicio = registro.continuar();
        assertTrue(inicio.haySesionIniciada(), "Tras registrarse el sitio deja la sesión iniciada");
        assertEquals(correo, inicio.obtenerCuentaMostrada());
    }

    @Test
    @DisplayName("CP-002 Registro con todos los campos obligatorios vacíos")
    void registroConCamposVacios() {
        PaginaRegistro registro = abrirRegistro()
                .registrarUsuario(null, null, null, null, null, null);

        assertEquals("First name is required.", registro.obtenerErrorNombre());
        assertEquals("Last name is required.", registro.obtenerErrorApellido());
        assertEquals("Email is required.", registro.obtenerErrorCorreo());
        assertEquals("Password is required.", registro.obtenerErrorClave());
        assertEquals("Password is required.", registro.obtenerErrorConfirmacion());
    }

    @Test
    @DisplayName("CP-003 Registro con correo de formato inválido")
    void registroConCorreoInvalido() {
        PaginaRegistro registro = abrirRegistro()
                .registrarUsuario(Genero.MASCULINO, NOMBRE, APELLIDO, "correo-malo", CLAVE_VALIDA, CLAVE_VALIDA);

        assertEquals("Wrong email", registro.obtenerErrorCorreo());
    }

    @Test
    @DisplayName("CP-004 Registro con contraseña de 5 caracteres (bajo el mínimo)")
    void registroConClaveDeCincoCaracteres() {
        PaginaRegistro registro = abrirRegistro()
                .registrarUsuario(Genero.MASCULINO, NOMBRE, APELLIDO, correoUnico(), "Cl123", "Cl123");

        assertEquals("The password should have at least 6 characters.", registro.obtenerErrorClave());
    }

    @Test
    @DisplayName("CP-006 Registro con contraseña y confirmación distintas")
    void registroConConfirmacionDistinta() {
        PaginaRegistro registro = abrirRegistro()
                .registrarUsuario(Genero.MASCULINO, NOMBRE, APELLIDO, correoUnico(), "Clave123", "Clave124");

        assertEquals("The password and confirmation password do not match.", registro.obtenerErrorConfirmacion());
    }

    @Test
    @DisplayName("CP-007 Registro con un correo ya registrado")
    void registroConCorreoRepetido() {
        String correo = correoUnico();
        PaginaInicio inicio = abrirRegistro()
                .registrarUsuario(Genero.MASCULINO, NOMBRE, APELLIDO, correo, CLAVE_VALIDA, CLAVE_VALIDA)
                .continuar()
                .cerrarSesion();

        PaginaRegistro segundoRegistro = inicio.irARegistro()
                .registrarUsuario(Genero.MASCULINO, NOMBRE, APELLIDO, correo, CLAVE_VALIDA, CLAVE_VALIDA);

        assertTrue(segundoRegistro.obtenerResumenDeErrores().contains("The specified email already exists"));
        assertFalse(new PaginaInicio(driver).haySesionIniciada(), "No se crea una segunda cuenta ni se inicia sesión");
    }
}
