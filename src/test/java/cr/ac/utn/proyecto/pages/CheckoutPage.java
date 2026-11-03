package cr.ac.utn.proyecto.pages;

import cr.ac.utn.proyecto.base.Visual;
import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

/**
 * Pago en una pagina (one page checkout) de Demo Web Shop (RF-09).
 * Cada paso se carga por AJAX: antes de usar un paso se espera a que su boton
 * "Continue" sea clickeable. Ese boton esta dentro del contenedor propio del paso,
 * por eso los localizadores CSS empiezan con el id del contenedor.
 */
public class CheckoutPage extends BasePage {

    // ---------- Paso 1: direccion de facturacion ----------
    @FindBy(id = "BillingNewAddress_FirstName")
    private WebElement nombre;

    @FindBy(id = "BillingNewAddress_LastName")
    private WebElement apellido;

    @FindBy(id = "BillingNewAddress_Email")
    private WebElement correo;

    @FindBy(id = "BillingNewAddress_CountryId")
    private WebElement listaPais;

    @FindBy(id = "BillingNewAddress_StateProvinceId")
    private WebElement listaEstado;

    @FindBy(id = "BillingNewAddress_City")
    private WebElement ciudad;

    @FindBy(id = "BillingNewAddress_Address1")
    private WebElement direccion;

    @FindBy(id = "BillingNewAddress_ZipPostalCode")
    private WebElement codigoPostal;

    @FindBy(id = "BillingNewAddress_PhoneNumber")
    private WebElement telefono;

    @FindBy(css = "#billing-buttons-container .new-address-next-step-button")
    private WebElement continuarFacturacion;

    @FindBy(css = "#opc-billing .field-validation-error")
    private List<WebElement> erroresFacturacion;

    // ---------- Paso 2: direccion de envio ----------
    @FindBy(css = "#shipping-buttons-container .new-address-next-step-button")
    private WebElement continuarEnvio;

    // ---------- Paso 3: metodo de envio ----------
    @FindBy(css = "#shipping-method-buttons-container .shipping-method-next-step-button")
    private WebElement continuarMetodoEnvio;

    // ---------- Paso 4: metodo de pago ----------
    @FindBy(css = "#payment-method-buttons-container .payment-method-next-step-button")
    private WebElement continuarMetodoPago;

    // ---------- Paso 5: informacion de pago ----------
    @FindBy(css = "#payment-info-buttons-container .payment-info-next-step-button")
    private WebElement continuarInfoPago;

    // ---------- Paso 6: confirmacion ----------
    @FindBy(css = "#confirm-order-buttons-container .confirm-order-next-step-button")
    private WebElement confirmarOrden;

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Llena la direccion de facturacion. Nombre, apellido y correo vienen llenos para un
     * usuario con sesion; se escriben solo si estan vacios. Si ciudad viene vacia, el campo
     * queda vacio (sirve para probar la validacion).
     */
    public CheckoutPage llenarFacturacion(String pais, String ciudadTexto, String direccionTexto,
                                          String codigo, String telefonoTexto) {
        visible(listaPais);
        new Select(listaPais).selectByVisibleText(pais);
        // La lista de estados se recarga por AJAX al cambiar el pais.
        espera.until(d -> !new Select(listaEstado).getOptions().isEmpty());
        Visual.pausa();
        escribir(ciudad, ciudadTexto);
        escribir(direccion, direccionTexto);
        escribir(codigoPostal, codigo);
        escribir(telefono, telefonoTexto);
        return this;
    }

    /** Completa el nombre/correo si el sitio no los trae llenos (compra como invitado). */
    public CheckoutPage completarDatosPersonales(String nombreTexto, String apellidoTexto, String correoTexto) {
        if (visible(nombre).getAttribute("value").isBlank()) {
            escribir(nombre, nombreTexto);
        }
        if (apellido.getAttribute("value").isBlank()) {
            escribir(apellido, apellidoTexto);
        }
        if (correo.getAttribute("value").isBlank()) {
            escribir(correo, correoTexto);
        }
        return this;
    }

    public CheckoutPage continuarDesdeFacturacion() {
        clic(continuarFacturacion);
        return this;
    }

    /**
     * Despues de continuar con datos incompletos, el sitio muestra el error en el campo
     * o en una alerta del navegador. Se espera cualquiera de los dos y se devuelve su texto.
     */
    public String obtenerErrorFacturacion() {
        espera.until(d -> hayAlerta() || erroresFacturacion.stream().anyMatch(this::estaVisible));
        if (hayAlerta()) {
            Alert alerta = driver.switchTo().alert();
            String texto = alerta.getText();
            alerta.accept();
            return texto.trim();
        }
        return erroresFacturacion.stream().filter(this::estaVisible)
                .map(e -> e.getText().trim()).collect(Collectors.joining(" | "));
    }

    /** Indica si el pago sigue en el paso de facturacion (no avanzo al envio). */
    public boolean sigueEnFacturacion() {
        return estaVisible(continuarFacturacion) && !estaVisible(continuarEnvio);
    }

    private boolean hayAlerta() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    /** Pasos 2 a 6 con las opciones por defecto (misma direccion, envio y pago contra entrega). */
    public OrdenCompletadaPage completarPasosRestantes() {
        clic(continuarEnvio);
        clic(continuarMetodoEnvio);
        clic(continuarMetodoPago);
        clic(continuarInfoPago);
        clic(confirmarOrden);
        espera.until(ExpectedConditions.urlContains("/checkout/completed"));
        return new OrdenCompletadaPage(driver);
    }
}
