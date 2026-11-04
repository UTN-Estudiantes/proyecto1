package cr.ac.utn.proyecto.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegisterPage {
    private WebDriver driver;

    // Localizadores
    @FindBy(id = "gender-male")
    private WebElement rdoGenderMale;

    @FindBy(id = "gender-female")
    private WebElement rdoGenderFemale;

    @FindBy(id = "FirstName")
    private WebElement txtFirstName;

    @FindBy(id = "LastName")
    private WebElement txtLastName;

    @FindBy(id = "Email")
    private WebElement txtEmail;

    @FindBy(id = "Password")
    private WebElement txtPassword;

    @FindBy(id = "ConfirmPassword")
    private WebElement txtConfirmPassword;

    @FindBy(id = "register-button")
    private WebElement btnRegister;

    // Errores de Validacion de campos
    @FindBy(css = ".validation-summary-errors")
    private WebElement lblErrorGeneral;

    @FindBy(css = "span[data-valmsg-for='FirstName']")
    private WebElement lblErrorFirstName;

    @FindBy(css = "span[data-valmsg-for='LastName']")
    private WebElement lblErrorLastName;

    @FindBy(css = "span[data-valmsg-for='Email']")
    private WebElement lblErrorEmail;

    @FindBy(css = "span[data-valmsg-for='Password']")
    private WebElement lblErrorPassword;

    @FindBy(css = "span[data-valmsg-for='ConfirmPassword']")
    private WebElement lblErrorConfirmPassword;

    // Constructor
    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Metodos Logica
    public void selecionarGeneroMasculino() {
        rdoGenderMale.click();
    }

    public void selecionarGeneroFemenino() {
        rdoGenderFemale.click();
    }

    public void escribirNombre(String nombre) {
        txtFirstName.clear();
        txtFirstName.sendKeys(nombre);
    }

    public void escribirApellidos(String apellidos) {
        txtLastName.clear();
        txtLastName.sendKeys(apellidos);
    }

    public void escribirEmail(String email) {
        txtEmail.clear();
        txtEmail.sendKeys(email);
    }

    public void escribirPassword(String password) {
        txtPassword.clear();
        txtPassword.sendKeys(password);
    }

    public void escribirConfirmacionPassword(String confirmPassword) {
        txtConfirmPassword.clear();
        txtConfirmPassword.sendKeys(confirmPassword);
    }

    public RegisterResultPage clickRegistroExitoso() {
        btnRegister.click();
        return new RegisterResultPage(driver);
    }

    public RegisterPage clickRegistroError() {
        btnRegister.click();
        return this;
    }

    public String obtenerErrorApellidos() {
        return lblErrorLastName.getText();
    }

    public RegisterResultPage registrarUsuarioValido(String nombre, String apellidos, String email,
            String password, String confirmPassword) {
        selecionarGeneroMasculino();
        escribirNombre(nombre);
        escribirApellidos(apellidos);
        escribirEmail(email);
        escribirPassword(password);
        escribirConfirmacionPassword(confirmPassword);
        return clickRegistroExitoso();

    }

    //
    public String obtenerErrorGeneral() {
        return lblErrorGeneral.getText();
    }

    public String obtenerErrorNombre() {
        return lblErrorFirstName.getText();
    }

    public String obtenerErrorEmail() {
        return lblErrorEmail.getText();
    }

    public String obtenerErrorPassword() {
        return lblErrorPassword.getText();
    }

    public String obtenerErrorConfirmacionPassword() {
        return lblErrorConfirmPassword.getText();
    }

}
