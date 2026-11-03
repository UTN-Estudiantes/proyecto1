package cr.ac.utn.proyecto.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.base.Visual;

public class CuentaPage {
    private final WebDriver driver;

    @FindBy(id = "FirstName")
    private WebElement campoNombre;

    @FindBy(id = "LastName")
    private WebElement campoApellido;

    @FindBy(css = "input.button-1.save-customer-info-button")
    private WebElement botonGuardar;

    @FindBy(id = "OldPassword")
    private WebElement campoClaveActual;

    @FindBy(id = "NewPassword")
    private WebElement campoClaveNueva;

    @FindBy(id = "ConfirmNewPassword")
    private WebElement campoConfirmarClave;

    @FindBy(css = "input.button-1.change-password-button")
    private WebElement botonCambiarClave;

    @FindBy(css = "span[data-valmsg-for='ConfirmNewPassword']")
    private WebElement errorConfirmacion;

    public CuentaPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public CuentaPage abrirInformacion() {
        driver.get(BaseTest.URL_BASE + "customer/info");
        Visual.pausa();
        return this;
    }

    public CuentaPage abrirCambioClave() {     
        driver.get(BaseTest.URL_BASE + "customer/changepassword");
        Visual.pausa();
        return this;    
    }

    public CuentaPage editarNombreYApellido(String nombre, String apellido) {
        campoNombre.clear();
        campoNombre.sendKeys(nombre);
        campoApellido.clear();
        campoApellido.sendKeys(apellido);
        
        botonGuardar.click();
        Visual.pausa();
        return this;
    }

    public String nombreGuardado() {
        return campoNombre.getAttribute("value");
    }

    public String apellidoGuardado() {
        return campoApellido.getAttribute("value");
    }

    public CuentaPage cambiarClave(String claveActual, String claveNueva, String confirmacion) {
        campoClaveActual.sendKeys(claveActual);
        campoClaveNueva.sendKeys(claveNueva);
        campoConfirmarClave.sendKeys(confirmacion);
        
        botonCambiarClave.click();
        Visual.pausa();
        return this;
    }

    public String errorConfirmacion() {
        return errorConfirmacion.getText();
    }
}