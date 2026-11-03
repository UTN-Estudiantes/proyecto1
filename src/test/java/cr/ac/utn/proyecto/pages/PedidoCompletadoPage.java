package cr.ac.utn.proyecto.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/** Pagina final de la compra (/checkout/completed). Page Factory. */
public class PedidoCompletadoPage {

    @FindBy(css = "div.order-completed div.title strong")
    private WebElement mensaje;

    // El primer elemento de la lista de detalles es el numero de orden.
    @FindBy(css = "div.order-completed ul.details li")
    private WebElement detalleOrden;

    public PedidoCompletadoPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public String mensajeConfirmacion() {
        return mensaje.getText().trim();
    }

    /** El sitio muestra "Order number: 123": se devuelven solo los digitos. */
    public String numeroOrden() {
        return detalleOrden.getText().replaceAll("\\D", "");
    }
}
