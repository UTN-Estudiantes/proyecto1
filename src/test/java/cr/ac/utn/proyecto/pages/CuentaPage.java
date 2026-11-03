package cr.ac.utn.proyecto.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/** Mi cuenta: informacion del cliente y menu lateral (RF-10). */
public class CuentaPage extends BasePage {

    @FindBy(id = "FirstName")
    private WebElement campoNombre;

    @FindBy(id = "LastName")
    private WebElement campoApellido;

    @FindBy(css = ".save-customer-info-button")
    private WebElement botonGuardar;

    @FindBy(css = ".block-account-navigation a[href='/customer/changepassword']")
    private WebElement enlaceCambiarClave;

    @FindBy(css = ".block-account-navigation a[href='/customer/orders']")
    private WebElement enlacePedidos;

    public CuentaPage(WebDriver driver) {
        super(driver);
    }

    public String obtenerNombre() {
        return visible(campoNombre).getAttribute("value");
    }

    public CuentaPage cambiarNombre(String nuevoNombre, String nuevoApellido) {
        escribir(campoNombre, nuevoNombre);
        escribir(campoApellido, nuevoApellido);
        clic(botonGuardar);
        return new CuentaPage(driver);
    }

    public CambiarClavePage irACambiarClave() {
        clic(enlaceCambiarClave);
        return new CambiarClavePage(driver);
    }
}
