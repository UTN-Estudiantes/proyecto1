package base;

import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 *
 * @author ortiz
 */

public abstract class BaseTest {

    protected static final String URL_SITIO = "https://demowebshop.tricentis.com";

    protected WebDriver driver;

    @BeforeEach
    public void iniciarNavegador() {
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
        driver.get(URL_SITIO);
    }

    @AfterEach
    public void cerrarNavegador() {
        if (driver != null) {
            driver.quit();
        }
    }
}
