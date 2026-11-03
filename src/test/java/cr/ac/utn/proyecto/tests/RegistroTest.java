package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cr.ac.utn.proyecto.base.DatosPrueba;
import cr.ac.utn.proyecto.pages.InicioPage;
import cr.ac.utn.proyecto.pages.RegistroPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("RF-01 Registro de usuario")
class RegistroTest extends PruebaTienda {

    @Test
    @DisplayName("CP-001 Registro exitoso con datos validos")
    void registroExitoso() {
        String correo = DatosPrueba.correoUnico();
        RegistroPage registro = abrirTienda().irARegistro()
                .llenarFormulario("F", "Laura", "Mora", correo, DatosPrueba.CLAVE_VALIDA, DatosPrueba.CLAVE_VALIDA)
                .registrar();

        assertEquals("Your registration completed", registro.obtenerMensajeResultado());
        InicioPage inicio = registro.continuar();
        assertAll(
                () -> assertTrue(inicio.haySesionIniciada(), "Debe quedar con la sesion iniciada"),
                () -> assertEquals(correo, inicio.obtenerCuentaEnEncabezado()));
    }

    @Test
    @DisplayName("CP-002 Registro con campos obligatorios vacios")
    void registroConCamposVacios() {
        RegistroPage registro = abrirTienda().irARegistro()
                .registrarConErrores();

        assertAll(
                () -> assertTrue(registro.obtenerErrorNombre().contains("required"), "First name: " + registro.obtenerErrorNombre()),
                () -> assertTrue(registro.obtenerErrorApellido().contains("required"), "Last name: " + registro.obtenerErrorApellido()),
                () -> assertTrue(registro.obtenerErrorCorreo().contains("required"), "Email: " + registro.obtenerErrorCorreo()),
                () -> assertTrue(registro.obtenerErrorClave().contains("required"), "Password: " + registro.obtenerErrorClave()),
                () -> assertFalse(registro.haySesionIniciada()));
    }

    @Test
    @DisplayName("CP-003 Registro con correo de formato invalido")
    void registroConCorreoInvalido() {
        RegistroPage registro = abrirTienda().irARegistro()
                .llenarFormulario("M", "Luis", "Rojas", "correo-sin-arroba", DatosPrueba.CLAVE_VALIDA, DatosPrueba.CLAVE_VALIDA)
                .registrarConErrores();

        assertTrue(registro.obtenerErrorCorreo().contains("Wrong email"), registro.obtenerErrorCorreo());
        assertFalse(registro.haySesionIniciada());
    }

    @Test
    @DisplayName("CP-004 Registro con correo ya registrado")
    void registroConCorreoDuplicado() {
        Usuario existente = registrarUsuarioNuevo();
        InicioPage visitante = new InicioPage(driver).cerrarSesion();

        RegistroPage registro = visitante.irARegistro()
                .llenarFormulario("M", "Otro", "Usuario", existente.correo(), DatosPrueba.CLAVE_VALIDA, DatosPrueba.CLAVE_VALIDA)
                .registrarConErrores();

        assertTrue(registro.obtenerResumenErrores().contains("The specified email already exists"),
                "Debe rechazar el correo repetido");
        assertFalse(registro.haySesionIniciada());
    }

    @Test
    @DisplayName("CP-005 Registro con contrasena de 5 caracteres (limite inferior - 1)")
    void registroConClaveCorta() {
        RegistroPage registro = abrirTienda().irARegistro()
                .llenarFormulario("M", "Luis", "Rojas", DatosPrueba.correoUnico(), "12345", "12345")
                .registrarConErrores();

        assertTrue(registro.obtenerErrorClave().contains("at least 6 characters"),
                "Debe exigir minimo 6 caracteres, mensaje real: " + registro.obtenerErrorClave());
        assertFalse(registro.haySesionIniciada());
    }

    @Test
    @DisplayName("CP-006 Registro con contrasena de 6 caracteres (limite exacto)")
    void registroConClaveDeSeisCaracteres() {
        RegistroPage registro = abrirTienda().irARegistro()
                .llenarFormulario("F", "Sofia", "Vargas", DatosPrueba.correoUnico(), "123456", "123456")
                .registrar();

        assertEquals("Your registration completed", registro.obtenerMensajeResultado());
    }

    @Test
    @DisplayName("CP-007 Registro con confirmacion de contrasena distinta")
    void registroConConfirmacionDistinta() {
        RegistroPage registro = abrirTienda().irARegistro()
                .llenarFormulario("M", "Luis", "Rojas", DatosPrueba.correoUnico(), DatosPrueba.CLAVE_VALIDA, "Otra12345")
                .registrarConErrores();

        assertTrue(registro.obtenerErrorConfirmacion().contains("do not match"), registro.obtenerErrorConfirmacion());
        assertFalse(registro.haySesionIniciada());
    }
}
