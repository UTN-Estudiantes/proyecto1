package pages;

import java.time.Duration;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author ortiz
 */
public class CuentaPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(className = "ico-register")
    private WebElement enlaceRegistro;

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

    @FindBy(css = ".header-links .account")
    private WebElement enlaceCuenta;

    @FindBy(className = "save-customer-info-button")
    private WebElement botonGuardarInfo;

    @FindBy(css = "a[href='/customer/changepassword']")
    private WebElement enlaceCambiarClave;

    @FindBy(id = "OldPassword")
    private WebElement campoClaveActual;

    @FindBy(id = "NewPassword")
    private WebElement campoClaveNueva;

    @FindBy(id = "ConfirmNewPassword")
    private WebElement campoConfirmarClaveNueva;

    @FindBy(className = "change-password-button")
    private WebElement botonCambiarClave;

    @FindBy(css = ".validation-summary-errors li")
    private WebElement errorResumen;

    public CuentaPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public CuentaPage registrarUsuario(String nombre, String apellido, String correo, String clave) {
        enlaceRegistro.click();
        wait.until(ExpectedConditions.visibilityOf(campoNombre));
        radioFemenino.click();
        this.campoNombre.sendKeys(nombre);
        this.campoApellido.sendKeys(apellido);
        this.campoCorreo.sendKeys(correo);
        this.campoClave.sendKeys(clave);
        this.campoConfirmacion.sendKeys(clave);
        this.botonRegistrar.click();
        wait.until(ExpectedConditions.visibilityOf(mensajeResultado));
        return this;
    }

    public CuentaPage irAInformacionCuenta() {
        wait.ignoring(StaleElementReferenceException.class).until(d -> {
            enlaceCuenta.click();
            return true;
        });
        wait.until(ExpectedConditions.visibilityOf(botonGuardarInfo));
        return this;
    }

    public CuentaPage editarApellido(String nuevoApellido) {
        this.campoApellido.clear();
        this.campoApellido.sendKeys(nuevoApellido);
        this.botonGuardarInfo.click();
        return this;
    }

    public String obtenerApellido() {
        wait.until(ExpectedConditions.visibilityOf(campoApellido));
        return this.campoApellido.getDomProperty("value");
    }

    public CuentaPage irACambiarClave() {
        this.enlaceCambiarClave.click();
        wait.until(ExpectedConditions.visibilityOf(campoClaveActual));
        return this;
    }

    public CuentaPage cambiarClave(String claveActual, String claveNueva, String confirmacion) {
        this.campoClaveActual.sendKeys(claveActual);
        this.campoClaveNueva.sendKeys(claveNueva);
        this.campoConfirmarClaveNueva.sendKeys(confirmacion);
        this.botonCambiarClave.click();
        return this;
    }
    
    public String obtenerError() {
        wait.until(ExpectedConditions.visibilityOf(errorResumen));
        return this.errorResumen.getText();
    }

}
