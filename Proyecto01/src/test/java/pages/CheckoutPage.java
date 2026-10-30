package pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author ortiz
 */
public class CheckoutPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(id = "termsofservice")
    private WebElement casillaTerminos;

    @FindBy(id = "checkout")
    private WebElement botonCheckout;

    @FindBy(id = "terms-of-service-warning-box")
    private WebElement avisoTerminos;

    @FindBy(className = "checkout-as-guest-button")
    private WebElement botonInvitado;

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

    @FindBy(css = "#billing-buttons-container .new-address-next-step-button")
    private WebElement botonContinuarFacturacion;

    @FindBy(css = "#shipping-buttons-container .new-address-next-step-button")
    private WebElement botonContinuarEnvio;

    @FindBy(className = "shipping-method-next-step-button")
    private WebElement botonContinuarMetodoEnvio;

    @FindBy(className = "payment-method-next-step-button")
    private WebElement botonContinuarMetodoPago;

    @FindBy(className = "payment-info-next-step-button")
    private WebElement botonContinuarInfoPago;

    @FindBy(className = "confirm-order-next-step-button")
    private WebElement botonConfirmar;

    @FindBy(css = ".order-completed .title strong")
    private WebElement mensajeExito;
    
    @FindBy(className = "cart-label")
    private WebElement enlaceCarrito;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }
    
    public CheckoutPage aceptarTerminosYContinuar(){
        this.casillaTerminos.click();
        this.botonCheckout.click();
        return this;
    }
    
    public CheckoutPage continuarComoInvitado(){
        wait.until(ExpectedConditions.elementToBeClickable(botonInvitado));
        this.botonInvitado.click();
        return this;
    }
    
    public CheckoutPage completarFacturacion(String nombre, String apellido, String correo,
            String pais, String ciudad, String direccion, String codigoPostal, String telefono){
        
        wait.until(ExpectedConditions.visibilityOf(campoNombre));
        Select selectorPais = new Select(listaPais);
        this.campoNombre.sendKeys(nombre);
        this.campoApellido.sendKeys(apellido);
        this.campoCorreo.sendKeys(correo);
        selectorPais.selectByVisibleText(pais);
        this.campoCiudad.sendKeys(ciudad);
        this.campoDireccion.sendKeys(direccion);
        this.campoCodigoPostal.sendKeys(codigoPostal);
        this.campoTelefono.sendKeys(telefono);
        this.botonContinuarFacturacion.click();
        return this;
    }
    
    public CheckoutPage continuarDireccionEnvio(){
        wait.until(ExpectedConditions.elementToBeClickable(botonContinuarEnvio));
        this.botonContinuarEnvio.click();
        return this;
    }
    
    public CheckoutPage continuarMetodoEnvio(){
        wait.until(ExpectedConditions.elementToBeClickable(botonContinuarMetodoEnvio));
        this.botonContinuarMetodoEnvio.click();
        return this;
    }
    
    public CheckoutPage continuarMetodoPago(){
        wait.until(ExpectedConditions.elementToBeClickable(botonContinuarMetodoPago));
        this.botonContinuarMetodoPago.click();
        return this;
    }
    
    public CheckoutPage continuarInfoPago(){
        wait.until(ExpectedConditions.elementToBeClickable(botonContinuarInfoPago));
        this.botonContinuarInfoPago.click();
        return this;
    }
    
    public CheckoutPage confirmarOrden(){
        wait.until(ExpectedConditions.elementToBeClickable(botonConfirmar));
        this.botonConfirmar.click();
        return this;
    }
    
    public String obtenerMensajeExito(){
        wait.until(ExpectedConditions.visibilityOf(mensajeExito));
        return this.mensajeExito.getText();
    }
    
    public String obtenerAvisoTerminos(){
        wait.until(ExpectedConditions.visibilityOf(avisoTerminos));
        return this.avisoTerminos.getText();
    }
    
    public CheckoutPage agregarProductoAlCarrito(String urlProducto, int idProducto){
        driver.get(urlProducto);
        // findElement + By porque el id del boton depende del producto y @FindBy no acepta parametros
        driver.findElement(By.id("add-to-cart-button-" + idProducto)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("bar-notification")));
        enlaceCarrito.click();
        return this;
    }
}
