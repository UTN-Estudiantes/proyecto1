package cr.ac.utn.proyecto.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/** Pantalla final del pago: confirma que la orden fue procesada (RF-09). */
public class OrdenCompletadaPage extends BasePage {

    @FindBy(css = ".order-completed .title")
    private WebElement titulo;

    @FindBy(css = ".order-completed .details")
    private WebElement detalles;

    public OrdenCompletadaPage(WebDriver driver) {
        super(driver);
    }

    public String obtenerMensaje() {
        return visible(titulo).getText().trim();
    }

    /** Devuelve el texto con el numero de orden, por ejemplo "Order number: 123456". */
    public String obtenerDetalles() {
        return visible(detalles).getText().trim();
    }
}
