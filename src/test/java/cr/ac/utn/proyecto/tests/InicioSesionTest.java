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
import cr.ac.utn.proyecto.pages.PaginaInicioSesion;
import cr.ac.utn.proyecto.pages.PaginaRegistro.Genero;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** RF-02: inicio y cierre de sesión. */
class InicioSesionTest extends BaseTest {

    private static final String MENSAJE_FALLO = "Login was unsuccessful. Please correct the errors and try again.";

    /** Registra un usuario nuevo y deja el navegador como visitante, listo para iniciar sesión. */
    private PaginaInicio registrarUsuarioYSalir(String correo) {
        return new PaginaInicio(driver).abrir()
                .irARegistro()
                .registrarUsuario(Genero.MASCULINO, NOMBRE, APELLIDO, correo, CLAVE_VALIDA, CLAVE_VALIDA)
                .continuar()
                .cerrarSesion();
    }

    @Test
    @DisplayName("CP-008 Inicio de sesión con credenciales válidas")
    void inicioDeSesionConCredencialesValidas() {
        String correo = correoUnico();
        PaginaInicio inicio = registrarUsuarioYSalir(correo)
                .irAInicioSesion()
                .iniciarSesion(correo, CLAVE_VALIDA);

        assertTrue(inicio.haySesionIniciada(), "El encabezado debe mostrar Log out");
        assertEquals(correo, inicio.obtenerCuentaMostrada());
    }

    @Test
    @DisplayName("CP-009 Inicio de sesión con contraseña incorrecta")
    void inicioDeSesionConClaveIncorrecta() {
        String correo = correoUnico();
        PaginaInicioSesion inicioSesion = registrarUsuarioYSalir(correo)
                .irAInicioSesion()
                .intentarIniciarSesion(correo, "Incorrecta99");

        assertTrue(inicioSesion.obtenerMensajeDeError().contains(MENSAJE_FALLO));
        assertFalse(new PaginaInicio(driver).haySesionIniciada(), "El encabezado debe seguir mostrando Log in");
    }

    @Test
    @DisplayName("CP-010 Inicio de sesión con correo y contraseña vacíos")
    void inicioDeSesionConCamposVacios() {
        PaginaInicioSesion inicioSesion = new PaginaInicio(driver).abrir()
                .irAInicioSesion()
                .intentarIniciarSesion(null, null);

        assertTrue(inicioSesion.obtenerMensajeDeError().contains(MENSAJE_FALLO));
        assertFalse(new PaginaInicio(driver).haySesionIniciada(), "No se debe iniciar sesión");
    }

    @Test
    @DisplayName("CP-012 Cierre de sesión vuelve al estado de visitante")
    void cierreDeSesionVuelveAVisitante() {
        String correo = correoUnico();
        PaginaInicio inicio = registrarUsuarioYSalir(correo)
                .irAInicioSesion()
                .iniciarSesion(correo, CLAVE_VALIDA);
        assertTrue(inicio.haySesionIniciada(), "Precondición: la sesión debe estar iniciada");

        inicio.cerrarSesion();

        assertTrue(inicio.muestraOpcionesDeVisitante(), "El encabezado vuelve a mostrar Register y Log in");
        assertFalse(inicio.haySesionIniciada(), "Ya no debe mostrarse Log out");
    }
}
