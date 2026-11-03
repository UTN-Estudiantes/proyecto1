
// Responsable: Katlyn Yiliana Fernández Ramos
package cr.ac.utn.proyecto.pages;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.base.Visual;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/** Listado de productos (por ejemplo una categoría) con el botón Add to cart de cada tarjeta. */
public class ListadoProductosPage {

    private final WebDriver driver;

    @FindBy(css = "div.product-item")
    private List<WebElement> tarjetasProducto;

    @FindBy(css = "#bar-notification p.content")
    private WebElement mensajeNotificacion;

    @FindBy(css = "span.cart-qty")
    private WebElement indicadorCarrito;

    public ListadoProductosPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // La ruta de la categoría (por ejemplo "notebooks") la entrega la prueba
    public ListadoProductosPage abrirCategoria(String ruta) {
        driver.get(BaseTest.URL_BASE + ruta);
        Visual.pausa();
        return this;
    }

    public ListadoProductosPage agregarAlCarrito(String nombreProducto) {
        // Localizador con parámetro: @FindBy no acepta parámetros y el botón depende del producto,
        // por eso se busca la tarjeta por su título y se usa findElement + By dentro de ella
        for (WebElement tarjeta : tarjetasProducto) {
            String titulo = tarjeta.findElement(By.cssSelector("h2.product-title a")).getText();
            if (titulo.equals(nombreProducto)) {
                tarjeta.findElement(By.cssSelector("input.product-box-add-to-cart-button")).click();
                esperarNotificacion();
                Visual.pausa();
                return this;
            }
        }
        throw new NoSuchElementException("No se encontró el producto en el listado: " + nombreProducto);
    }

    public String mensajeNotificacion() {
        return mensajeNotificacion.getText();
    }

    public String indicadorCarrito() {
        return indicadorCarrito.getText();
    }

    public CarritoPage irAlCarrito() {
        // Se navega por URL: la barra de notificación puede tapar el enlace "Shopping cart"
        return new CarritoPage(driver).abrir();
    }

    private void esperarNotificacion() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOf(mensajeNotificacion));
    }
}