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
 * Área "My account": información del cliente, cambio de contraseña y pedidos.
 * Los selectores son los estándar de nopCommerce y se confirman al automatizar las pruebas de RF-10.
 */
public class PaginaCuenta {

    private final WebDriver driver;
    private final WebDriverWait espera;

    @FindBy(css = "div.block-account-navigation a[href='/customer/info']")
    private WebElement enlaceInformacion;

    @FindBy(css = "div.block-account-navigation a[href='/customer/changepassword']")
    private WebElement enlaceCambioDeContrasena;

    @FindBy(css = "div.block-account-navigation a[href='/customer/orders']")
    private WebElement enlacePedidos;

    @FindBy(id = "FirstName")
    private WebElement campoNombre;

    @FindBy(id = "LastName")
    private WebElement campoApellido;

    @FindBy(id = "Email")
    private WebElement campoCorreo;

    @FindBy(css = "input.save-customer-info-button")
    private WebElement botonGuardar;

    @FindBy(id = "OldPassword")
    private WebElement campoContrasenaAnterior;

    @FindBy(id = "NewPassword")
    private WebElement campoContrasenaNueva;

    @FindBy(id = "ConfirmNewPassword")
    private WebElement campoConfirmacion;

    @FindBy(css = "input.change-password-button")
    private WebElement botonCambiarContrasena;

    @FindBy(css = "div.page-body")
    private WebElement contenido;

    public PaginaCuenta(WebDriver driver) {
        this.driver = driver;
        this.espera = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public PaginaCuenta abrir() {
        driver.get(BaseTest.URL_BASE + "customer/info");
        return this;
    }

    public PaginaCuenta abrirInformacionDelCliente() {
        return navegarA(enlaceInformacion, "/customer/info");
    }

    public PaginaCuenta abrirCambioDeContrasena() {
        return navegarA(enlaceCambioDeContrasena, "/customer/changepassword");
    }

    public PaginaCuenta abrirPedidos() {
        return navegarA(enlacePedidos, "/customer/orders");
    }

    public PaginaCuenta cambiarNombre(String nombre) {
        espera.until(ExpectedConditions.visibilityOf(campoNombre));
        campoNombre.clear();
        campoNombre.sendKeys(nombre);
        return this;
    }

    public PaginaCuenta guardarInformacion() {
        botonGuardar.click();
        espera.until(ExpectedConditions.urlContains("/customer/info"));
        return new PaginaCuenta(driver);
    }

    public String obtenerNombre() {
        espera.until(ExpectedConditions.visibilityOf(campoNombre));
        return campoNombre.getAttribute("value");
    }

    public String obtenerApellido() {
        return campoApellido.getAttribute("value");
    }

    public String obtenerCorreo() {
        return campoCorreo.getAttribute("value");
    }

    public PaginaCuenta cambiarContrasena(String anterior, String nueva, String confirmacion) {
        espera.until(ExpectedConditions.visibilityOf(campoContrasenaAnterior));
        campoContrasenaAnterior.sendKeys(anterior);
        campoContrasenaNueva.sendKeys(nueva);
        campoConfirmacion.sendKeys(confirmacion);
        botonCambiarContrasena.click();
        return new PaginaCuenta(driver);
    }

    public String obtenerMensajeDeContrasena() {
        espera.until(ExpectedConditions.visibilityOf(contenido));
        return contenido.getText().trim();
    }

    public String obtenerMensajeDePedidos() {
        espera.until(ExpectedConditions.visibilityOf(contenido));
        return contenido.getText().trim();
    }

    private PaginaCuenta navegarA(WebElement enlace, String fragmentoDeUrl) {
        enlace.click();
        espera.until(ExpectedConditions.urlContains(fragmentoDeUrl));
        return new PaginaCuenta(driver);
    }
}
