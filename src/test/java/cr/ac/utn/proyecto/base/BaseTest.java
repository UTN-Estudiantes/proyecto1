package cr.ac.utn.proyecto.base;

import java.time.Duration;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Clase base del proyecto: abre Chrome limpio antes de cada prueba y lo cierra al terminar.
 * Todas las clases de prueba del proyecto deben extender BaseTest.
 */
public abstract class BaseTest {

    /** Sitio bajo prueba. */
    public static final String URL_BASE = "https://demowebshop.tricentis.com/";

    protected WebDriver driver;

    @BeforeEach
    void abrirNavegador() {
        ChromeOptions opciones = new ChromeOptions();
        opciones.addArguments("--window-size=1280,900");
        opciones.setExperimentalOption("prefs", Map.of(
                "credentials_enable_service", false,
                "profile.password_manager_enabled", false,
                "profile.password_manager_leak_detection", false));
        if (Boolean.getBoolean("headless")) {
            opciones.addArguments("--headless=new");
        }
        driver = new ChromeDriver(opciones);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
    }

    @AfterEach
    void cerrarNavegador() {
        if (driver != null) {
            driver.quit();
        }
    }
}
