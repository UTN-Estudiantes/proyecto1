package cr.ac.utn.proyecto.pages;

import cr.ac.utn.proyecto.base.BaseTest;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Formulario de registro de usuario y la página de resultado que muestra el sitio al registrarse.
 */
public class PaginaRegistro {

    public enum Genero { MASCULINO, FEMENINO }

    private final WebDriver driver;
    private final WebDriverWait espera;

    @FindBy(id = "gender-male")
    private WebElement opcionMasculino;

    @FindBy(id = "gender-female")
    private WebElement opcionFemenino;

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

    // Lista: el resumen solo existe cuando el servidor rechaza el registro (por ejemplo, correo repetido).
    @FindBy(css = "div.validation-summary-errors")
    private List<WebElement> resumenErrores;

    @FindBy(css = "div.registration-result-page div.result")
    private WebElement mensajeResultado;

    @FindBy(css = "input.register-continue-button")
    private WebElement botonContinuar;

    public PaginaRegistro(WebDriver driver) {
        this.driver = driver;
        this.espera = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public PaginaRegistro abrir() {
        driver.get(BaseTest.URL_BASE + "register");
        return this;
    }

    /** Llena el formulario; un valor nulo o vacío deja el campo sin tocar, y un género nulo no se elige. */
    public PaginaRegistro completarDatos(Genero genero, String nombre, String apellido,
                                         String correo, String clave, String confirmacion) {
        if (genero == Genero.MASCULINO) {
            opcionMasculino.click();
        } else if (genero == Genero.FEMENINO) {
            opcionFemenino.click();
        }
        escribir(campoNombre, nombre);
        escribir(campoApellido, apellido);
        escribir(campoCorreo, correo);
        escribir(campoClave, clave);
        escribir(campoConfirmacion, confirmacion);
        return this;
    }

    /** Envía el formulario y espera a que el sitio responda con el resultado o con errores. */
    public PaginaRegistro enviar() {
        botonRegistrar.click();
        espera.until(d -> d.getCurrentUrl().contains("/registerresult") || hayErrores());
        return this;
    }

    public PaginaRegistro registrarUsuario(Genero genero, String nombre, String apellido,
                                           String correo, String clave, String confirmacion) {
        return completarDatos(genero, nombre, apellido, correo, clave, confirmacion).enviar();
    }

    public PaginaInicio continuar() {
        botonContinuar.click();
        espera.until(ExpectedConditions.not(ExpectedConditions.urlContains("/registerresult")));
        return new PaginaInicio(driver);
    }

    public String obtenerMensajeRegistroExitoso() {
        espera.until(ExpectedConditions.visibilityOf(mensajeResultado));
        return mensajeResultado.getText();
    }

    public String obtenerErrorNombre() {
        return errorNombre.getText();
    }

    public String obtenerErrorApellido() {
        return errorApellido.getText();
    }

    public String obtenerErrorCorreo() {
        return errorCorreo.getText();
    }

    public String obtenerErrorClave() {
        return errorClave.getText();
    }

    public String obtenerErrorConfirmacion() {
        return errorConfirmacion.getText();
    }

    /** Texto del resumen de errores del servidor, o cadena vacía si no hay resumen. */
    public String obtenerResumenDeErrores() {
        return resumenErrores.isEmpty() ? "" : resumenErrores.get(0).getText();
    }

    private boolean hayErrores() {
        return !errorNombre.getText().isEmpty()
                || !errorApellido.getText().isEmpty()
                || !errorCorreo.getText().isEmpty()
                || !errorClave.getText().isEmpty()
                || !errorConfirmacion.getText().isEmpty()
                || !resumenErrores.isEmpty();
    }

    private void escribir(WebElement campo, String valor) {
        if (valor != null && !valor.isEmpty()) {
            campo.clear();
            campo.sendKeys(valor);
        }
    }
}
