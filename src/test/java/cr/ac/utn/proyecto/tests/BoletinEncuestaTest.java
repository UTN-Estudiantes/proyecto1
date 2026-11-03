package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cr.ac.utn.proyecto.base.DatosPrueba;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("RF-11 Boletin y encuesta")
class BoletinEncuestaTest extends PruebaTienda {

    @Test
    @DisplayName("CP-040 Suscripcion al boletin con correo valido")
    void suscripcionValida() {
        String mensaje = abrirTienda()
                .suscribirseAlBoletin(DatosPrueba.correoUnico())
                .obtenerMensajeBoletin();

        assertTrue(mensaje.contains("Thank you for signing up"), "Mensaje real: " + mensaje);
    }

    @Test
    @DisplayName("CP-041 Suscripcion al boletin con correo invalido")
    void suscripcionInvalida() {
        String mensaje = abrirTienda()
                .suscribirseAlBoletin("correo-invalido")
                .obtenerMensajeBoletin();

        assertFalse(mensaje.contains("Thank you"), "No debe aceptar el correo: " + mensaje);
        assertTrue(mensaje.toLowerCase().contains("valid email"), "Mensaje real: " + mensaje);
    }

    @Test
    @DisplayName("CP-042 Un visitante no puede votar en la encuesta")
    void visitanteNoPuedeVotar() {
        String error = abrirTienda()
                .votarEnEncuesta()
                .obtenerErrorEncuesta();

        assertTrue(error.contains("Only registered users can vote"), "Mensaje real: " + error);
    }

    @Test
    @DisplayName("CP-043 Un usuario registrado vota en la encuesta y ve los resultados")
    void usuarioRegistradoVota() {
        registrarUsuarioNuevo();
        boolean hayResultados = abrirTienda()
                .votarEnEncuesta()
                .seMuestranResultadosEncuesta();

        assertTrue(hayResultados, "Despues de votar se deben ver los resultados");
    }
}
