package cr.ac.utn.proyecto.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Proceso de compra de una sola página: cada paso se carga de forma dinámica, por eso cada método espera su botón.
 */
public class PaginaPago {

    private final WebDriver driver;
    private final WebDriverWait espera;

    @FindBy(id = "BillingNewAddress_FirstName")
    private WebElement campoNombre;

    @FindBy(id = "BillingNewAddress_LastName")
    private WebElement campoApellido;

    @FindBy(id = "BillingNewAddress_Email")
    private WebElement campoCorreo;

    @FindBy(id = "BillingNewAddress_CountryId")
    private WebElement listaPaises;

    @FindBy(id = "BillingNewAddress_StateProvinceId")
    private WebElement listaEstados;

    @FindBy(id = "BillingNewAddress_City")
    private WebElement campoCiudad;

    @FindBy(id = "BillingNewAddress_Address1")
    private WebElement campoDireccion;

    @FindBy(id = "BillingNewAddress_ZipPostalCode")
    private WebElement campoCodigoPostal;

    @FindBy(id = "BillingNewAddress_PhoneNumber")
    private WebElement campoTelefono;

    @FindBy(css = "#billing-buttons-container input.new-address-next-step-button")
    private WebElement botonContinuarFacturacion;

    @FindBy(css = "#checkout-step-billing span.field-validation-error")
    private List<WebElement> erroresDeFacturacion;

    @FindBy(css = "#shipping-buttons-container input.new-address-next-step-button")
    private WebElement botonContinuarDireccionDeEnvio;

    @FindBy(css = "#checkout-step-shipping-method label")
    private List<WebElement> metodosDeEnvio;

    @FindBy(css = "#shipping-method-buttons-container input.shipping-method-next-step-button")
    private WebElement botonContinuarMetodoDeEnvio;

    @FindBy(css = "#checkout-step-payment-method label")
    private List<WebElement> metodosDePago;

    @FindBy(css = "#payment-method-buttons-container input.payment-method-next-step-button")
    private WebElement botonContinuarMetodoDePago;

    @FindBy(css = "#payment-info-buttons-container input.payment-info-next-step-button")
    private WebElement botonContinuarInformacionDePago;

    @FindBy(css = "#confirm-order-buttons-container input.confirm-order-next-step-button")
    private WebElement botonConfirmar;

    @FindBy(css = "div.order-completed div.title strong")
    private WebElement mensajeOrdenProcesada;

    @FindBy(css = "input.order-completed-continue-button")
    private WebElement botonContinuarTrasLaCompra;

    public PaginaPago(WebDriver driver) {
        this.driver = driver;
        this.espera = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public PaginaPago completarFacturacion(String nombre, String apellido, String correo, String pais,
                                           String ciudad, String direccion, String codigoPostal, String telefono) {
        espera.until(ExpectedConditions.visibilityOf(campoNombre));
        escribir(campoNombre, nombre);
        escribir(campoApellido, apellido);
        escribir(campoCorreo, correo);
        if (pais != null && !pais.isEmpty()) {
            new Select(listaPaises).selectByVisibleText(pais);
        }
        escribir(campoCiudad, ciudad);
        escribir(campoDireccion, direccion);
        escribir(campoCodigoPostal, codigoPostal);
        escribir(campoTelefono, telefono);
        return this;
    }

    public PaginaPago continuarDeFacturacion() {
        espera.until(ExpectedConditions.elementToBeClickable(botonContinuarFacturacion));
        botonContinuarFacturacion.click();
        return this;
    }

    public List<String> obtenerErroresDeFacturacion() {
        espera.until(d -> !erroresDeFacturacion.isEmpty());
        List<String> errores = new ArrayList<>();
        for (WebElement error : erroresDeFacturacion) {
            if (!error.getText().trim().isEmpty()) {
                errores.add(error.getText().trim());
            }
        }
        return errores;
    }

    public PaginaPago continuarDeDireccionDeEnvio() {
        espera.until(ExpectedConditions.elementToBeClickable(botonContinuarDireccionDeEnvio));
        botonContinuarDireccionDeEnvio.click();
        return this;
    }

    public PaginaPago elegirMetodoDeEnvio(String metodo) {
        return elegir(metodosDeEnvio, metodo);
    }

    public PaginaPago continuarDeMetodoDeEnvio() {
        espera.until(ExpectedConditions.elementToBeClickable(botonContinuarMetodoDeEnvio));
        botonContinuarMetodoDeEnvio.click();
        return this;
    }

    public PaginaPago elegirMetodoDePago(String metodo) {
        return elegir(metodosDePago, metodo);
    }

    public PaginaPago continuarDeMetodoDePago() {
        espera.until(ExpectedConditions.elementToBeClickable(botonContinuarMetodoDePago));
        botonContinuarMetodoDePago.click();
        return this;
    }

    public PaginaPago continuarDeInformacionDePago() {
        espera.until(ExpectedConditions.elementToBeClickable(botonContinuarInformacionDePago));
        botonContinuarInformacionDePago.click();
        return this;
    }

    public PaginaPago confirmarOrden() {
        espera.until(ExpectedConditions.elementToBeClickable(botonConfirmar));
        botonConfirmar.click();
        espera.until(ExpectedConditions.urlContains("/checkout/completed"));
        return this;
    }

    public String obtenerMensajeDeOrdenProcesada() {
        espera.until(ExpectedConditions.visibilityOf(mensajeOrdenProcesada));
        return mensajeOrdenProcesada.getText().trim();
    }

    public PaginaInicio continuarTrasLaCompra() {
        botonContinuarTrasLaCompra.click();
        espera.until(ExpectedConditions.not(ExpectedConditions.urlContains("/checkout")));
        return new PaginaInicio(driver);
    }

    private PaginaPago elegir(List<WebElement> opciones, String texto) {
        espera.until(d -> !opciones.isEmpty() && opciones.get(0).isDisplayed());
        for (WebElement opcion : opciones) {
            if (opcion.getText().trim().contains(texto)) {
                opcion.click();
                return this;
            }
        }
        throw new IllegalArgumentException("No existe la opción: " + texto);
    }

    private void escribir(WebElement campo, String valor) {
        if (valor != null && !valor.isEmpty()) {
            campo.clear();
            campo.sendKeys(valor);
        }
    }
}
