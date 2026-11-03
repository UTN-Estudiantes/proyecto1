// Responsable: Melanni Michel Díaz Ugalde
package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.base.GeneradorDatos;
import cr.ac.utn.proyecto.pages.BoletinEncuestaPage;
import cr.ac.utn.proyecto.pages.InicioPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BoletinTest extends BaseTest {

    private static final String CORREO_SIN_ARROBA = "cuentaejemplo.com";

    @Test
    @DisplayName("CP-039 Suscripción al boletín con correo válido")
    void suscripcionConCorreoValido() {

        // Arrange (Preparación)

        // Se abre la página de inicio, donde está el bloque Newsletter
        new InicioPage(driver).abrir();
        BoletinEncuestaPage boletin = new BoletinEncuestaPage(driver);

        // Act (Acción)

        // Se escribe un correo único y se pulsa Subscribe
        boletin.escribirCorreoBoletin(GeneradorDatos.correoUnico()).suscribirse();

        // Assert (Comprobación)

        // Se verifica el aviso de agradecimiento por suscribirse
        String mensaje = boletin.mensajeBoletin();
        assertTrue(mensaje.contains("Thank you for signing up"),
                "El aviso de suscripción no coincide. Texto real: " + mensaje);
    }

    @Test
    @DisplayName("CP-040 Suscripción al boletín con correo inválido")
    void suscripcionConCorreoInvalido() {

        // Arrange (Preparación)

        new InicioPage(driver).abrir();
        BoletinEncuestaPage boletin = new BoletinEncuestaPage(driver);

        // Act (Acción)

        // Se escribe un correo sin @ y se pulsa Subscribe
        boletin.escribirCorreoBoletin(CORREO_SIN_ARROBA).suscribirse();

        // Assert (Comprobación)

        // Se verifica que el sitio pida un correo válido y no confirme la suscripción
        String mensaje = boletin.mensajeBoletin();
        assertTrue(mensaje.contains("Enter valid email"),
                "El aviso de correo inválido no coincide. Texto real: " + mensaje);
    }
}
