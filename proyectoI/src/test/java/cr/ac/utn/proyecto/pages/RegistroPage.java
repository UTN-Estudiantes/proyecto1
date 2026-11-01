package cr.ac.utn.proyecto.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.base.Visual;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegistroPage {

    private final WebDriver driver;

    //Elementos básicos
    @FindBy(id = "gender-male")
    private WebElement generoMasculino;

    @FindBy(id = "gender-female")
    private WebElement generoFemenino;

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
    private WebElement mensajeConfirmacion;

    @FindBy(css = "input.register-continue-button")
    private WebElement botonContinuar;

    //Errores
    @FindBy(css = "span[data-valmsg-for='FirstName']")
    private WebElement errorNombre;

    @FindBy(css = "span[data-valmsg-for='LastName']")
    private WebElement errorApellido;

    @FindBy(css = "span[data-valmsg-for='Email']")
    private WebElement errorCorreo;

    @FindBy(css = "span[data-valmsg-for='Password']")
    private WebElement errorClave;

    @FindBy(css = "span[data-valmsg-for='ConfirmPassword']")
    private WebElement errorConfirmacion;

    @FindBy(css = "div.validation-summary-errors")
    private WebElement errorGeneral;

    public RegistroPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public RegistroPage abrir() {
        driver.get(BaseTest.URL_BASE + "register");
        Visual.pausa();
        return this;
    }
}
