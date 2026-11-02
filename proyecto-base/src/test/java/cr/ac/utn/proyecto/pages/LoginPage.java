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
    private WebElement campoEmail;

    @FindBy(id = "Password")
    private WebElement campoPassword;

    @FindBy(css = "input.login-button")
    private WebElement botonLogin;

    @FindBy(css = "div.validation-summary-errors")
    private WebElement bloqueErrorLogin;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /** Login con credenciales validas: lleva a la pagina de inicio. */
    public HomePage iniciarSesion(String correo, String password) {
        escribirYEnviar(correo, password);
        return new HomePage(driver);
    }

    /** Login que se espera que falle: se permanece en la pagina de login. */
    public LoginPage iniciarSesionFallido(String correo, String password) {
        escribirYEnviar(correo, password);
        return this;
    }

    public String textoError() {
        return bloqueErrorLogin.getText().trim();
    }

    private void escribirYEnviar(String correo, String password) {
        campoEmail.clear();
        campoEmail.sendKeys(correo);
        campoPassword.clear();
        campoPassword.sendKeys(password);
        Visual.pausa();
        botonLogin.click();
        Visual.pausa();
    }
}