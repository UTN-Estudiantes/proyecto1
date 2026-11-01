package cr.ac.utn.proyecto.pages;

import cr.ac.utn.proyecto.base.BaseTest;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Formulario de inicio de sesión.
 */
public class PaginaInicioSesion {

    private final WebDriver driver;
    private final WebDriverWait espera;

    @FindBy(id = "Email")
    private WebElement campoCorreo;

    @FindBy(id = "Password")
    private WebElement campoClave;

    @FindBy(css = "input.login-button")
    private WebElement botonIniciarSesion;

    @FindBy(css = "div.validation-summary-errors")
    private WebElement resumenErrores;

    public PaginaInicioSesion(WebDriver driver) {
        this.driver = driver;
        this.espera = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public PaginaInicioSesion abrir() {
        driver.get(BaseTest.URL_BASE + "login");
        return this;
    }

    /** Camino feliz: con credenciales válidas el sitio lleva a la página de inicio. */
    public PaginaInicio iniciarSesion(String correo, String clave) {
        completarCredenciales(correo, clave);
        botonIniciarSesion.click();
        espera.until(ExpectedConditions.not(ExpectedConditions.urlContains("/login")));
        return new PaginaInicio(driver);
    }

    /** Camino de error: con credenciales inválidas o vacías el sitio se queda en el formulario. */
    public PaginaInicioSesion intentarIniciarSesion(String correo, String clave) {
        completarCredenciales(correo, clave);
        botonIniciarSesion.click();
        espera.until(ExpectedConditions.visibilityOf(resumenErrores));
        return this;
    }

    public String obtenerMensajeDeError() {
        espera.until(ExpectedConditions.visibilityOf(resumenErrores));
        return resumenErrores.getText();
    }

    private void completarCredenciales(String correo, String clave) {
        escribir(campoCorreo, correo);
        escribir(campoClave, clave);
    }

    private void escribir(WebElement campo, String valor) {
        if (valor != null && !valor.isEmpty()) {
            campo.clear();
            campo.sendKeys(valor);
        }
    }
}
