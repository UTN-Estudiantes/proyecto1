
// Responsable: Katlyn Yiliana Fernández Ramos
package cr.ac.utn.proyecto.pages;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.base.Visual;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/** Página de la lista de deseos (/wishlist). */
public class ListaDeseosPage {

    private final WebDriver driver;

    @FindBy(css = "td.product a")
    private List<WebElement> nombresProductos;;

    @FindBy(css = "input[name='addtocart']")
    private List<WebElement> casillasMoverAlCarrito;

    @FindBy(css = "input[name='addtocartbutton']")
    private WebElement botonMoverAlCarrito;

    @FindBy(css = "div.wishlist-content")
    private WebElement contenidoLista;

    public ListaDeseosPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public ListaDeseosPage abrir() {
        driver.get(BaseTest.URL_BASE + "wishlist");
        Visual.pausa();
        return this;
    }

    public List<String> nombresProductos() {
        return nombresProductos.stream().map(WebElement::getText).toList();
    }

    // El sitio redirige al carrito después de mover el producto, por eso devuelve CarritoPage
    public CarritoPage moverPrimerProductoAlCarrito() {
        casillasMoverAlCarrito.get(0).click();
        botonMoverAlCarrito.click();
        Visual.pausa();
        return new CarritoPage(driver);
    }

    public String contenidoLista() {
        return contenidoLista.getText();
    }
}