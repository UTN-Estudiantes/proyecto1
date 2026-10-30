package base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author ortiz
 */

public abstract class BaseTest {

    protected static final String URL_SITIO = "https://demowebshop.tricentis.com";

    protected WebDriver driver;

    @BeforeEach
    public void iniciarNavegador() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(URL_SITIO);
    }

    @AfterEach
    public void cerrarNavegador() {
        if (driver != null) {
            driver.quit();
        }
    }
}
