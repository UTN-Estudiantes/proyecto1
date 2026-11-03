// Responsable: Melanni Michel Díaz Ugalde
package cr.ac.utn.proyecto.pages;

import cr.ac.utn.proyecto.base.Visual;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

// Flujo de compra: desde los términos de servicio del carrito hasta la pantalla de orden procesada.
// El checkout del sitio es una sola página con pasos que se abren de forma dinámica (Ajax)
public class PagoPage {

    private final WebDriver driver;

    // ---- Carrito: términos y botón Checkout ----
    @FindBy(id = "termsofservice")
    private WebElement casillaTerminos;

    @FindBy(id = "checkout")
    private WebElement botonCheckout;

    // Cuadro de aviso que se abre si se pulsa Checkout sin aceptar los términos
    @FindBy(id = "terms-of-service-warning-box")
    private WebElement avisoTerminos;

    // ---- Inicio del checkout ----
    @FindBy(css = "input.checkout-as-guest-button")
    private WebElement botonComprarComoInvitado;

    // ---- Billing address ----
    @FindBy(id = "BillingNewAddress_FirstName")
    private WebElement campoNombre;

    @FindBy(id = "BillingNewAddress_LastName")
    private WebElement campoApellido;

    @FindBy(id = "BillingNewAddress_Email")
    private WebElement campoCorreo;

    @FindBy(id = "BillingNewAddress_CountryId")
    private WebElement listaPais;

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

    // Avisos de campo obligatorio (solo existen los que fallaron)
    @FindBy(css = "#billing-new-address-form span.field-validation-error")
    private List<WebElement> erroresFacturacion;

    // ---- Pasos siguientes ----
    @FindBy(css = "#shipping-method-buttons-container input.shipping-method-next-step-button")
    private WebElement botonContinuarEnvio;

    @FindBy(css = "#payment-method-buttons-container input.payment-method-next-step-button")
    private WebElement botonContinuarMetodoPago;

    @FindBy(css = "#payment-info-buttons-container input.payment-info-next-step-button")
    private WebElement botonContinuarInfoPago;

    @FindBy(css = "#confirm-order-buttons-container input.confirm-order-next-step-button")
    private WebElement botonConfirmar;

    // ---- Orden procesada ----
    @FindBy(css = "div.order-completed div.title strong")
    private WebElement mensajeExito;

    @FindBy(css = "div.order-completed ul.details li")
    private List<WebElement> detallesOrden;

    public PagoPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // ---- Carrito ----

    public PagoPage aceptarTerminos() {
        casillaTerminos.click();
        return this;
    }

    public PagoPage pulsarCheckout() {
        botonCheckout.click();
        Visual.pausa();
        return this;
    }

    // El aviso es un cuadro dinámico: se espera a que sea visible antes de leerlo
    public String avisoTerminos() {
        esperar(ExpectedConditions.visibilityOf(avisoTerminos));
        return avisoTerminos.getText();
    }

    public String urlActual() {
        return driver.getCurrentUrl();
    }

    // ---- Checkout ----

    public PagoPage elegirComprarComoInvitado() {
        esperar(ExpectedConditions.elementToBeClickable(botonComprarComoInvitado));
        botonComprarComoInvitado.click();
        Visual.pausa();
        return this;
    }

    // Todos los datos los entrega la prueba: la página no guarda datos fijos
    public PagoPage llenarFacturacion(String nombre, String apellido, String correo, String pais,
            String ciudad, String direccion, String codigoPostal, String telefono) {
        esperar(ExpectedConditions.visibilityOf(campoNombre));
        campoNombre.sendKeys(nombre);
        campoApellido.sendKeys(apellido);
        campoCorreo.sendKeys(correo);
        new Select(listaPais).selectByVisibleText(pais);
        campoCiudad.sendKeys(ciudad);
        campoDireccion.sendKeys(direccion);
        campoCodigoPostal.sendKeys(codigoPostal);
        campoTelefono.sendKeys(telefono);
        return this;
    }

    public PagoPage continuarFacturacion() {
        esperar(ExpectedConditions.elementToBeClickable(botonContinuarFacturacion));
        botonContinuarFacturacion.click();
        Visual.pausa();
        return this;
    }

    // Método de envío: se deja Ground, que es la opción que el sitio trae marcada
    public PagoPage continuarMetodoEnvio() {
        esperar(ExpectedConditions.elementToBeClickable(botonContinuarEnvio));
        botonContinuarEnvio.click();
        Visual.pausa();
        return this;
    }

    // Método de pago: se deja Cash On Delivery, que es la opción que el sitio trae
    // marcada
    public PagoPage continuarMetodoPago() {
        esperar(ExpectedConditions.elementToBeClickable(botonContinuarMetodoPago));
        botonContinuarMetodoPago.click();
        Visual.pausa();
        return this;
    }

    public PagoPage continuarInformacionPago() {
        esperar(ExpectedConditions.elementToBeClickable(botonContinuarInfoPago));
        botonContinuarInfoPago.click();
        Visual.pausa();
        return this;
    }

    public PagoPage confirmarOrden() {
        esperar(ExpectedConditions.elementToBeClickable(botonConfirmar));
        botonConfirmar.click();
        Visual.pausa();
        return this;
    }

    // ---- Lectura de resultados (sin aserciones) ----

    public String mensajeCompraExitosa() {
        esperar(ExpectedConditions.visibilityOf(mensajeExito));
        return mensajeExito.getText();
    }

    public String numeroOrden() {
        esperar(ExpectedConditions.visibilityOf(mensajeExito));
        return detallesOrden.get(0).getText();
    }

    // Los avisos de campos obligatorios aparecen al pulsar Continue: se espera a
    // que haya al menos uno
    public List<String> avisosFacturacion() {
        esperar(d -> !erroresFacturacion.isEmpty());
        return erroresFacturacion.stream()
                .map(WebElement::getText)
                .filter(texto -> !texto.isBlank())
                .toList();
    }

    private <T> void esperar(java.util.function.Function<? super WebDriver, T> condicion) {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(condicion);
    }
}
