package cr.ac.utn.proyecto.pages;

import cr.ac.utn.proyecto.base.Visual;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/** Pagina de registro de usuario (/register). Page Factory. */
public class RegisterPage {

    private final WebDriver driver;

    @FindBy(id = "gender-male")
    private WebElement radioGeneroMasculino;

    @FindBy(id = "gender-female")
    private WebElement radioGeneroFemenino;

    @FindBy(id = "FirstName")
    private WebElement campoNombre;

    @FindBy(id = "LastName")
    private WebElement campoApellido;

    @FindBy(id = "Email")
    private WebElement campoEmail;

    @FindBy(id = "Password")
    private WebElement campoPassword;

    @FindBy(id = "ConfirmPassword")
    private WebElement campoConfirmarPassword;

    @FindBy(id = "register-button")
    private WebElement botonRegistrar;

    @FindBy(css = "div.result")
    private WebElement mensajeRegistroExitoso;

    @FindBy(css = "div.validation-summary-errors")
    private WebElement mensajeErrorGeneral;

    @FindBy(css = "span.field-validation-error")
    private WebElement mensajeErrorCampo;

    @FindBy(css = "div.header-links a.account")
    private WebElement enlaceCuentaUsuario;

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public RegisterPage seleccionarGeneroMasculino() {
        radioGeneroMasculino.click();
        Visual.pausa();
        return this;
    }

    public RegisterPage llenarDatos(
            String nombre, String apellido, String correo, String password, String confirmacion) {
        campoNombre.clear();
        campoNombre.sendKeys(nombre);
        campoApellido.clear();
        campoApellido.sendKeys(apellido);
        campoEmail.clear();
        campoEmail.sendKeys(correo);
        campoPassword.clear();
        campoPassword.sendKeys(password);
        campoConfirmarPassword.clear();
        campoConfirmarPassword.sendKeys(confirmacion);
        Visual.pausa();
        return this;
    }

    public RegisterPage enviarRegistro() {
        botonRegistrar.click();
        Visual.pausa();
        return this;
    }

    public RegisterPage registrarUsuario(
            String nombre, String apellido, String correo, String password, String confirmacion) {
        seleccionarGeneroMasculino();
        llenarDatos(nombre, apellido, correo, password, confirmacion);
        enviarRegistro();
        return this;
    }

    public String mensajeExito() {
        return mensajeRegistroExitoso.getText().trim();
    }

    public String cuentaEnEncabezado() {
        return enlaceCuentaUsuario.getText().trim();
    }

    public String textoErrorGeneral() {
        return mensajeErrorGeneral.getText().trim();
    }

    public String textoErrorCampo() {
        return mensajeErrorCampo.getText().trim();
    }
}