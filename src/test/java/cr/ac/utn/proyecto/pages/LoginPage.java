package cr.ac.utn.proyecto.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    private WebDriver driver;

    //Localizadores
    @FindBy(id = "Email")
    private WebElement txtEmail;

    @FindBy(id = "Password")
    private WebElement txtPassword;

    @FindBy(id = "RememberMe")
    private WebElement chkRememberMe;

    @FindBy(css = "input.login-button")
    private WebElement btnLogin;

    // Errores de validación
    @FindBy(css = ".validation-summary-errors")
    private WebElement lblErrorGeneral;

    @FindBy(css = "span[data-valmsg-for='Email']")
    private WebElement lblErrorEmail;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //Metodos Logica
    public void escribirEmail(String email) {
        txtEmail.clear();
        txtEmail.sendKeys(email);
    }

    public void escribirPassword(String password) {
        txtPassword.clear();
        txtPassword.sendKeys(password);
    }

    public void marcarRecordarme() {
        if (!chkRememberMe.isSelected()){
            chkRememberMe.click();
        }
    }

    public HomePage clickLoginExitoso() {
        btnLogin.click();
        return new HomePage(driver);
    }

    public LoginPage clickLoginConError() {
        btnLogin.click();
        return this;
    }

    public HomePage iniciarSesion(String email, String password) {
        escribirEmail(email);
        escribirPassword(password);
        return clickLoginExitoso();
    }

    //Metodos que obtienen mensajes de error
    public String obtenerErrorGeneral() {
        return lblErrorGeneral.getText();
    }
    public String obtenerErrorEmail() {
        return lblErrorEmail.getText();
    }

}
