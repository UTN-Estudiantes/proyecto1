package tests;

import base.BaseTest;
import pages.RegistroPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistroTest extends BaseTest {

    private String correoUnico() {
        return "qa" + System.currentTimeMillis() + "@mail.com";
    }

    @Test
    @DisplayName("CP-001 Registro exitoso con todos los datos validos")
    public void registroExitoso() {
        RegistroPage registro = new RegistroPage(driver);
        registro.abrir()
                .registrar("Female", "Ana", "Prueba", correoUnico(), "Prueba123", "Prueba123");

        assertEquals("Your registration completed", registro.obtenerMensajeResultado());
    }
}