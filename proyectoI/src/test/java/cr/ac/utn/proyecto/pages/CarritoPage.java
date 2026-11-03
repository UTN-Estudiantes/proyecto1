
// Responsable: Katlyn Yiliana Fernández Ramos
package cr.ac.utn.proyecto.pages;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.base.Visual;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

// Página del carrito de compras (/cart).
public class CarritoPage {

    private final WebDriver driver;

    @FindBy(css = "td.product a.product-name")
    private List<WebElement> nombresProductos;

    @FindBy(css = "input[name='removefromcart']")
    private List<WebElement> casillasQuitar;

    @FindBy(css = "input[name='updatecart']")
    private WebElement botonActualizar;

    // Contenedor donde el sitio escribe "Your Shopping Cart is empty!"
    @FindBy(css = "div.order-summary-content")
    private WebElement mensajeCarrito;

    @FindBy(css = "span.cart-qty")
    private WebElement indicadorCarrito;

    public CarritoPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public CarritoPage abrir() {
        driver.get(BaseTest.URL_BASE + "cart");
        Visual.pausa();
        return this;
    }

    public List<String> nombresProductos() {
        return nombresProductos.stream().map(WebElement::getText).toList();
    }

    // Las pruebas siempre trabajan con un único producto en el carrito, por eso se usa la primera casilla
    public CarritoPage quitarPrimerProducto() {
        casillasQuitar.get(0).click();
        botonActualizar.click();
        Visual.pausa();
        return this;
    }

    public String mensajeCarrito() {
        return mensajeCarrito.getText();
    }

    public String indicadorCarrito() {
        return indicadorCarrito.getText();
    }
}