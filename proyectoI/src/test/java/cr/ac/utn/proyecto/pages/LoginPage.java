package cr.ac.utn.proyecto.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.base.Visual;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage {
    private final WebDriver driver;

    // Elementos básicos
    @FindBy(id = "Email")
    private WebElement campoCorreo;

    @FindBy(id = "Password")
    private WebElement campoClave;

    @FindBy(css = "input.login-button")
    private WebElement botonLogin;

    // Errores
    @FindBy(css = "div.validation-summary-errors")
    private WebElement mensajeError;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public LoginPage abrir() {
        driver.get(BaseTest.URL_BASE + "login");
        Visual.pausa();
        return this;
    }

    public InicioPage iniciarSesion(String correo, String clave) {
        escribirYEnviar(correo, clave);
        return new InicioPage(driver);
    }

    public LoginPage iniciarSesionFallido(String correo, String clave) {
        escribirYEnviar(correo, clave);
        return this;
    }

    public String mensajeError() {
        return mensajeError.getText();
    }

    private void escribirYEnviar(String correo, String clave) {
        campoCorreo.sendKeys(correo);
        campoClave.sendKeys(clave);
        Visual.pausa();
        botonLogin.click();
        Visual.pausa();
    }

}
