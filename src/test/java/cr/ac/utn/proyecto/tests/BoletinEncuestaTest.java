package cr.ac.utn.proyecto.tests;

import static cr.ac.utn.proyecto.base.DatosDePrueba.correoUnico;
import static org.junit.jupiter.api.Assertions.assertEquals;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.pages.PaginaInicio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** RF-11: boletín informativo y encuesta de la comunidad. */
class BoletinEncuestaTest extends BaseTest {

    @Test
    @DisplayName("CP-043 Suscribirse al boletín con un correo válido")
    void suscripcionConCorreoValido() {
        PaginaInicio inicio = new PaginaInicio(driver).abrir().suscribirseAlBoletin(correoUnico());

        assertEquals("Thank you for signing up! A verification email has been sent. We appreciate your interest.",
                inicio.obtenerResultadoDelBoletin());
    }

    @Test
    @DisplayName("CP-044 Suscribirse al boletín con un correo inválido")
    void suscripcionConCorreoInvalido() {
        PaginaInicio inicio = new PaginaInicio(driver).abrir().suscribirseAlBoletin("correo-malo");

        assertEquals("Enter valid email", inicio.obtenerResultadoDelBoletin());
    }

    @Test
    @DisplayName("CP-045 Votar en la encuesta como visitante (hallazgo)")
    void votarComoVisitante() {
        PaginaInicio inicio = new PaginaInicio(driver).abrir().votarEnLaEncuesta(0);

        assertEquals("Only registered users can vote.", inicio.obtenerErrorDeLaEncuesta());
    }
}
