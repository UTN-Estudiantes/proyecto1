package pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegistroPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(className = "ico-register")
    private WebElement enlaceRegistro;

    @FindBy(id = "gender-male")
    private WebElement radioMasculino;

    @FindBy(id = "gender-female")
    private WebElement radioFemenino;

    @FindBy(id = "FirstName")
    private WebElement campoNombre;

    @FindBy(id = "LastName")
    private WebElement campoApellido;

    @FindBy(id = "Email")
    private WebElement campoCorreo;

    @FindBy(id = "Password")
    private WebElement campoClave;

    @FindBy(id = "ConfirmPassword")
    private WebElement campoConfirmacion;

    @FindBy(id = "register-button")
    private WebElement botonRegistrar;

    @FindBy(css = ".page-body .result")
    private WebElement mensajeResultado;

    @FindBy(css = ".validation-summary-errors li")
    private WebElement errorResumen;

    public RegistroPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public RegistroPage abrir() {
        enlaceRegistro.click();
        wait.until(ExpectedConditions.visibilityOf(campoNombre));
        return this;
    }

    public RegistroPage registrar(String genero, String nombre, String apellido,
            String correo, String clave, String confirmacion) {
        if ("Female".equalsIgnoreCase(genero)) {
            radioFemenino.click();
        } else if ("Male".equalsIgnoreCase(genero)) {
            radioMasculino.click();
        }
        escribir(campoNombre, nombre);
        escribir(campoApellido, apellido);
        escribir(campoCorreo, correo);
        escribir(campoClave, clave);
        escribir(campoConfirmacion, confirmacion);
        botonRegistrar.click();
        return this;
    }

    public String obtenerMensajeResultado() {
        wait.until(ExpectedConditions.visibilityOf(mensajeResultado));
        return mensajeResultado.getText();
    }

    public String obtenerErrorResumen() {
        wait.until(ExpectedConditions.visibilityOf(errorResumen));
        return errorResumen.getText();
    }

    public String obtenerErrorDeCampo(String idCampo) {
        // findElement + By porque el mensaje depende del campo (parametro) y @FindBy no acepta parametros
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("span[data-valmsg-for='" + idCampo + "']")));
        return error.getText();
    }

    private void escribir(WebElement campo, String texto) {
        campo.clear();
        campo.sendKeys(texto);
    }
}
