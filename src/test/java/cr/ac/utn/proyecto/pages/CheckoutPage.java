package cr.ac.utn.proyecto.pages;

import cr.ac.utn.proyecto.base.Visual;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Checkout (/onepagecheckout). Es una sola pagina con pasos que se cargan dinamicamente:
 * antes de cada Continue hay que esperar a que el boton de ese paso este listo. Page Factory.
 */
public class CheckoutPage {

    private final WebDriver driver;
    private final WebDriverWait espera;

    @FindBy(id = "BillingNewAddress_FirstName")
    private WebElement campoNombre;

    @FindBy(id = "BillingNewAddress_LastName")
    private WebElement campoApellido;

    @FindBy(id = "BillingNewAddress_Email")
    private WebElement campoCorreo;

    @FindBy(id = "BillingNewAddress_CountryId")
    private WebElement listaPais;

    @FindBy(id = "BillingNewAddress_StateProvinceId")
    private WebElement listaEstado;

    @FindBy(id = "BillingNewAddress_City")
    private WebElement campoCiudad;

    @FindBy(id = "BillingNewAddress_Address1")
    private WebElement campoDireccion;

    @FindBy(id = "BillingNewAddress_ZipPostalCode")
    private WebElement campoZip;

    @FindBy(id = "BillingNewAddress_PhoneNumber")
    private WebElement campoTelefono;

    @FindBy(css = "#billing-buttons-container input.new-address-next-step-button")
    private WebElement botonFacturacion;

    @FindBy(css = "#shipping-buttons-container input.new-address-next-step-button")
    private WebElement botonDireccionEnvio;

    @FindBy(css = "#shipping-method-buttons-container input.shipping-method-next-step-button")
    private WebElement botonMetodoEnvio;

    @FindBy(css = "#payment-method-buttons-container input.payment-method-next-step-button")
    private WebElement botonMetodoPago;

    @FindBy(css = "#payment-info-buttons-container input.payment-info-next-step-button")
    private WebElement botonInformacionPago;

    @FindBy(css = "#confirm-order-buttons-container input.confirm-order-next-step-button")
    private WebElement botonConfirmar;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.espera = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    public CheckoutPage llenarFacturacion(String nombre, String apellido, String correo, String pais, String estado,
            String ciudad, String direccion, String zip, String telefono) {
        campoNombre.sendKeys(nombre);
        campoApellido.sendKeys(apellido);
        campoCorreo.sendKeys(correo);
        new Select(listaPais).selectByVisibleText(pais);
        // Los estados se cargan despues de elegir el pais: se espera a que el estado ya este en la lista.
        espera.until(ExpectedConditions.textToBePresentInElement(listaEstado, estado));
        new Select(listaEstado).selectByVisibleText(estado);
        campoCiudad.sendKeys(ciudad);
        campoDireccion.sendKeys(direccion);
        campoZip.sendKeys(zip);
        campoTelefono.sendKeys(telefono);
        Visual.pausa();
        return this;
    }

    public CheckoutPage continuarFacturacion() {
        hacerClicCuandoEsteListo(botonFacturacion);
        return this;
    }

    /** Se deja la direccion que ya viene elegida (la de facturacion). */
    public CheckoutPage continuarDireccionEnvio() {
        hacerClicCuandoEsteListo(botonDireccionEnvio);
        return this;
    }

    /** Se deja el metodo que ya viene marcado: Ground. */
    public CheckoutPage continuarMetodoEnvio() {
        hacerClicCuandoEsteListo(botonMetodoEnvio);
        return this;
    }

    /** Se deja el metodo que ya viene marcado: Cash On Delivery. */
    public CheckoutPage continuarMetodoPago() {
        hacerClicCuandoEsteListo(botonMetodoPago);
        return this;
    }

    public CheckoutPage continuarInformacionPago() {
        hacerClicCuandoEsteListo(botonInformacionPago);
        return this;
    }

    public PedidoCompletadoPage confirmar() {
        hacerClicCuandoEsteListo(botonConfirmar);
        // Al confirmar, el sitio guarda la orden y despues cambia de pagina.
        espera.until(ExpectedConditions.urlContains("/checkout/completed"));
        return new PedidoCompletadoPage(driver);
    }

    private void hacerClicCuandoEsteListo(WebElement boton) {
        espera.until(ExpectedConditions.elementToBeClickable(boton));
        boton.click();
        Visual.pausa();
    }
}
