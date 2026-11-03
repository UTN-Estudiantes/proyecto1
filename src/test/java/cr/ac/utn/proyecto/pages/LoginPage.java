package cr.ac.utn.proyecto.pages;

import cr.ac.utn.proyecto.base.Visual;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/** Pagina de inicio de sesion (/login). Page Factory. */
public class LoginPage {

    private final WebDriver driver;

    @FindBy(id = "Email")
    private WebElement campoCorreo;

    @FindBy(id = "Password")
    private WebElement campoClave;

    @FindBy(css = "input.login-button")
    private WebElement botonLogin;

    @FindBy(css = "div.validation-summary-errors")
    private WebElement resumenErrores;

    @FindBy(css = "input.checkout-as-guest-button")
    private WebElement botonInvitado;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /** Login con credenciales validas: lleva a la pagina de inicio. */
    public InicioPage iniciarSesion(String correo, String clave) {
        escribirYEnviar(correo, clave);
        return new InicioPage(driver);
    }

    /** Login que se espera que falle: se permanece en esta pagina. */
    public LoginPage iniciarSesionFallido(String correo, String clave) {
        escribirYEnviar(correo, clave);
        return this;
    }

    public String mensajeError() {
        return resumenErrores.getText().trim();
    }

    public CheckoutPage continuarComoInvitado() {
        botonInvitado.click();
        Visual.pausa();
        return new CheckoutPage(driver);
    }
    private void escribirYEnviar(String correo, String clave) {
        campoCorreo.sendKeys(correo);
        campoClave.sendKeys(clave);
        Visual.pausa();
        botonLogin.click();
        Visual.pausa();
    }
}
