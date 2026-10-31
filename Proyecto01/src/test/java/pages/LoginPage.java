package pages;

import java.time.Duration;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(className = "ico-login")
    private WebElement enlaceLogin;

    @FindBy(className = "ico-logout")
    private WebElement enlaceLogout;

    @FindBy(css = ".header-links .account")
    private WebElement cuentaEncabezado;

    @FindBy(id = "Email")
    private WebElement campoCorreo;

    @FindBy(id = "Password")
    private WebElement campoClave;

    @FindBy(css = "input.login-button")
    private WebElement botonLogin;

    @FindBy(css = ".validation-summary-errors span")
    private WebElement mensajeError;

    @FindBy(css = ".validation-summary-errors li")
    private WebElement detalleError;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public LoginPage abrir() {
        enlaceLogin.click();
        wait.until(ExpectedConditions.visibilityOf(campoCorreo));
        return this;
    }

    public LoginPage iniciarSesion(String correo, String clave) {
        campoCorreo.clear();
        campoCorreo.sendKeys(correo);
        campoClave.clear();
        campoClave.sendKeys(clave);
        botonLogin.click();
        return this;
    }

    public LoginPage cerrarSesion() {
        enlaceLogout.click();
        wait.until(ExpectedConditions.visibilityOf(enlaceLogin));
        return this;
    }

    public String obtenerCorreoEncabezado() {
        wait.until(ExpectedConditions.visibilityOf(cuentaEncabezado));
        return cuentaEncabezado.getText();
    }

    public boolean haySesionIniciada() {
        try {
            return enlaceLogout.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String obtenerMensajeError() {
        wait.until(ExpectedConditions.visibilityOf(mensajeError));
        return mensajeError.getText();
    }

    public String obtenerDetalleError() {
        wait.until(ExpectedConditions.visibilityOf(detalleError));
        return detalleError.getText();
    }
}