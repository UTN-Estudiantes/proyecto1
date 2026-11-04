package cr.ac.utn.proyecto.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class CheckoutPage {
    private WebDriver driver;

    // Paso 1: la facturacion
    @FindBy(id = "BillingNewAddress_CountryId")
    private WebElement cboBillingCountry;

    @FindBy(id = "BillingNewAddress_City")
    private WebElement txtBillingCity;

    @FindBy(id = "BillingNewAddress_Address1")
    private WebElement txtBillingAddress1;

    @FindBy(id = "BillingNewAddress_ZipPostalCode")
    private WebElement txtBillingZip;

    @FindBy(id = "BillingNewAddress_PhoneNumber")
    private WebElement txtBillingPhone;

    @FindBy(css = "#billing-buttons-container input.new-address-next-step-button")
    private WebElement btnBillingContinue;

    // Paso 2: Envío
    @FindBy(css = "#shipping-buttons-container input.new-address-next-step-button")
    private WebElement btnShippingContinue;

    // Paso 3: Metodo de envio
    @FindBy(id = "shippingoption_0")
    private WebElement rdoShippingGround;

    @FindBy(css = "#shipping-method-buttons-container input.shipping-method-next-step-button")
    private WebElement btnShippingMethodContinue;

    // Paso 4: Metodo de pago
    @FindBy(id = "paymentmethod_0")
    private WebElement rdoPaymentCOD; // Pago contra entrega

    @FindBy(css = "#payment-method-buttons-container input.payment-method-next-step-button")
    private WebElement btnPaymentMethodContinue;

    // Paso 5: Payment Information
    @FindBy(css = "#payment-info-buttons-container input.payment-info-next-step-button")
    private WebElement btnPaymentInfoContinue;

    // Paso 6: Confirmar orden
    @FindBy(css = "#confirm-order-buttons-container input.confirm-order-next-step-button")
    private WebElement btnConfirmOrder;

    // Paso 7: Orden completada resultado
    @FindBy(css = ".section.order-completed .title")
    private WebElement lblOrderSuccessTitle;

    @FindBy(css = "ul.details li")
    private WebElement lblOrderNumber;

    @FindBy(id = "BillingNewAddress_StateProvinceId")
    private WebElement cboBillingState;

    // Constructor
    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Metodos Logica}
    public void llenarDireccionFacturacion(
            String pais, String estado, String ciudad,
            String direccion, String zip, String telefono) {

        new Select(cboBillingCountry).selectByVisibleText(pais);
        new Select(cboBillingState).selectByVisibleText(estado);

        txtBillingCity.clear();
        txtBillingCity.sendKeys(ciudad);
        txtBillingAddress1.clear();
        txtBillingAddress1.sendKeys(direccion);
        txtBillingZip.clear();
        txtBillingZip.sendKeys(zip);
        txtBillingPhone.clear();
        txtBillingPhone.sendKeys(telefono);

        btnBillingContinue.click();
    }

    public void confirmarDireccionEnvio() {
        btnShippingContinue.click();
    }

    public void seleccionarMetodoEnvioGround() {
        if (!rdoShippingGround.isSelected()) {
            rdoShippingGround.click();
        }
        btnShippingMethodContinue.click();
    }

    public void seleccionarPagoContraEntrega() {
        if (!rdoPaymentCOD.isSelected()) {
            rdoPaymentCOD.click();
        }
        btnPaymentMethodContinue.click();
    }

    public void confirmarInformacionPago() {
        btnPaymentInfoContinue.click();
    }

    public void confirmarOrden() {
        btnConfirmOrder.click();
    }

    // Flujo completo de compra que recorre los 6 pasos seguidos
    public void completarOrdenEstandar(String pais, String estado, String ciudad, String direccion, String zip,
            String telefono) {
        llenarDireccionFacturacion(pais, estado, ciudad, direccion, zip, telefono);
        confirmarDireccionEnvio();
        seleccionarMetodoEnvioGround();
        seleccionarPagoContraEntrega();
        confirmarInformacionPago();
        confirmarOrden();
    }

    public String obtenerMensajeExito() {
        return lblOrderSuccessTitle.getText();
    }

    public String obtenerNumeroOrden() {
        return lblOrderNumber.getText();
    }
}
