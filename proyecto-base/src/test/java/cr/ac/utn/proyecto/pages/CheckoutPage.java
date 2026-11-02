package cr.ac.utn.proyecto.pages;

import cr.ac.utn.proyecto.base.Visual;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

/** Pagina del proceso de compra de varios pasos (/onepagecheckout). Page Factory. */
public class CheckoutPage {

    private final WebDriver driver;

    @FindBy(id = "BillingNewAddress_CountryId")
    private WebElement selectorPais;

    @FindBy(id = "BillingNewAddress_City")
    private WebElement campoCiudad;

    @FindBy(id = "BillingNewAddress_Address1")
    private WebElement campoDireccion1;

    @FindBy(id = "BillingNewAddress_ZipPostalCode")
    private WebElement campoCodigoPostal;

    @FindBy(id = "BillingNewAddress_PhoneNumber")
    private WebElement campoTelefono;

    @FindBy(css = "#billing-buttons-container input.new-address-next-step-button")
    private WebElement botonContinuarFacturacion;

    @FindBy(css = "#shipping-buttons-container input.new-address-next-step-button")
    private WebElement botonContinuarDireccionEnvio;

    @FindBy(css = "#shipping-method-buttons-container input.shipping-method-next-step-button")
    private WebElement botonContinuarMetodoEnvio;

    @FindBy(id = "paymentmethod_0")
    private WebElement radioPagoContraEntrega;

    @FindBy(css = "#payment-method-buttons-container input.payment-method-next-step-button")
    private WebElement botonContinuarMetodoPago;

    @FindBy(css = "#payment-info-buttons-container input.payment-info-next-step-button")
    private WebElement botonContinuarInfoPago;

    @FindBy(css = "#confirm-order-buttons-container input.confirm-order-next-step-button")
    private WebElement botonConfirmarOrden;

    @FindBy(css = "div.order-completed div.title strong")
    private WebElement tituloOrdenCompletada;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public CheckoutPage llenarDireccionFacturacion(
            String pais, String ciudad, String direccion, String codigoPostal, String telefono) {
        new Select(selectorPais).selectByVisibleText(pais);
        campoCiudad.clear();
        campoCiudad.sendKeys(ciudad);
        campoDireccion1.clear();
        campoDireccion1.sendKeys(direccion);
        campoCodigoPostal.clear();
        campoCodigoPostal.sendKeys(codigoPostal);
        campoTelefono.clear();
        campoTelefono.sendKeys(telefono);
        Visual.pausa();
        botonContinuarFacturacion.click();
        Visual.pausa();
        return this;
    }

    public CheckoutPage continuarDireccionEnvio() {
        botonContinuarDireccionEnvio.click();
        Visual.pausa();
        return this;
    }

    public CheckoutPage continuarMetodoEnvio() {
        botonContinuarMetodoEnvio.click();
        Visual.pausa();
        return this;
    }

    public CheckoutPage elegirPagoContraEntregaYContinuar() {
        radioPagoContraEntrega.click();
        Visual.pausa();
        botonContinuarMetodoPago.click();
        Visual.pausa();
        return this;
    }

    public CheckoutPage continuarInformacionPago() {
        botonContinuarInfoPago.click();
        Visual.pausa();
        return this;
    }

    public CheckoutPage finalizar() {
        botonConfirmarOrden.click();
        Visual.pausa();
        return this;
    }

    public String mensajeConfirmacion() {
        return tituloOrdenCompletada.getText().trim();
    }
}