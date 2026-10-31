package tests;

import base.BaseTest;
import pages.BoletinEncuestaPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoletinEncuestaTest extends BaseTest {

    @Test
    @DisplayName("CP-011 Suscripcion al boletin con correo valido")
    public void suscripcionBoletinValida() {
        String correo = "qa" + System.currentTimeMillis() + "@mail.com";
        BoletinEncuestaPage boletin = new BoletinEncuestaPage(driver);

        boletin.suscribirseAlBoletin(correo);

        assertEquals("Thank you for signing up! A verification email has been sent. We appreciate your interest.",
                boletin.obtenerMensajeBoletin());
    }
}