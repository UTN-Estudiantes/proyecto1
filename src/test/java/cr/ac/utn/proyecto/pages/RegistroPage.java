package cr.ac.utn.proyecto.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/** Formulario de registro de usuario (RF-01). */
public class RegistroPage extends BasePage {

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

    @FindBy(css = ".page-body .result")
    private WebElement mensajeResultado;

    @FindBy(css = ".register-continue-button")
    private WebElement botonContinuar;

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

    @FindBy(css = ".validation-summary-errors")
    private WebElement resumenErrores;

    public RegistroPage(WebDriver driver) {
        super(driver);
    }

    /** Llena el formulario completo. genero: "M" o "F". */
    public RegistroPage llenarFormulario(String genero, String nombre, String apellido,
                                         String correo, String clave, String confirmacion) {
        if ("F".equalsIgnoreCase(genero)) {
            clic(generoFemenino);
        } else if ("M".equalsIgnoreCase(genero)) {
            clic(generoMasculino);
        }
        escribir(campoNombre, nombre);
        escribir(campoApellido, apellido);
        escribir(campoCorreo, correo);
        escribir(campoClave, clave);
        escribir(campoConfirmacion, confirmacion);
        return this;
    }

    /** Envia el formulario esperando que el registro falle (la pagina no cambia). */
    public RegistroPage registrarConErrores() {
        clic(botonRegistrar);
        return this;
    }

    /** Envia el formulario esperando un registro exitoso. */
    public RegistroPage registrar() {
        clic(botonRegistrar);
        return this;
    }

    /** Flujo completo de registro exitoso; devuelve el inicio con la sesion abierta. */
    public InicioPage registrarUsuario(String nombre, String apellido, String correo, String clave) {
        llenarFormulario("M", nombre, apellido, correo, clave, clave);
        registrar();
        visible(mensajeResultado);
        clic(botonContinuar);
        return new InicioPage(driver);
    }

    public String obtenerMensajeResultado() {
        return visible(mensajeResultado).getText().trim();
    }

    public InicioPage continuar() {
        clic(botonContinuar);
        return new InicioPage(driver);
    }

    public String obtenerErrorNombre() {
        return textoSiVisible(errorNombre);
    }

    public String obtenerErrorApellido() {
        return textoSiVisible(errorApellido);
    }

    public String obtenerErrorCorreo() {
        return textoSiVisible(errorCorreo);
    }

    public String obtenerErrorClave() {
        return textoSiVisible(errorClave);
    }

    public String obtenerErrorConfirmacion() {
        return textoSiVisible(errorConfirmacion);
    }

    /** Errores que devuelve el servidor (por ejemplo, correo ya registrado). */
    public String obtenerResumenErrores() {
        return visible(resumenErrores).getText().trim();
    }

    private String textoSiVisible(WebElement elemento) {
        return estaVisible(elemento) ? elemento.getText().trim() : "";
    }
}
