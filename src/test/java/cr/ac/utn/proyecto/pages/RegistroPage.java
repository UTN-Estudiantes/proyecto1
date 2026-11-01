package cr.ac.utn.proyecto.pages;

import cr.ac.utn.proyecto.base.Visual;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/** Pagina de registro (/register). Page Factory. */
public class RegistroPage {

    @FindBy(id = "gender-male")
    private WebElement opcionMasculino;

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

    @FindBy(css = "div.result")
    private WebElement resultado;

    @FindBy(css = "div.validation-summary-errors")
    private WebElement resumenErrores;

    public RegistroPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public RegistroPage registrar(String nombre, String apellido, String correo, String clave, String confirmacion) {
        opcionMasculino.click();
        campoNombre.sendKeys(nombre);
        campoApellido.sendKeys(apellido);
        campoCorreo.sendKeys(correo);
        campoClave.sendKeys(clave);
        campoConfirmacion.sendKeys(confirmacion);
        Visual.pausa();
        botonRegistrar.click();
        Visual.pausa();
        return this;
    }

    public String mensajeResultado() {
        return resultado.getText().trim();
    }

    public String errorResumen() {
        return resumenErrores.getText().trim();
    }
}
