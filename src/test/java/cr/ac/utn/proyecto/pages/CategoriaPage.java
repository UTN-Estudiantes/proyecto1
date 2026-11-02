package cr.ac.utn.proyecto.pages;

import cr.ac.utn.proyecto.base.Visual;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/** Listado de productos de una categoria (por ejemplo /books o /notebooks). Page Factory. */
public class CategoriaPage {

    private final WebDriverWait espera;

    @FindBy(id = "products-orderby")
    private WebElement listaOrden;

    @FindBy(css = "div.product-item")
    private List<WebElement> productos;

    // Solo el precio actual: cada producto tambien puede mostrar un precio viejo tachado.
    @FindBy(css = "div.product-item span.actual-price")
    private List<WebElement> precios;

    @FindBy(id = "bar-notification")
    private WebElement notificacion;

    public CategoriaPage(WebDriver driver) {
        this.espera = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public CategoriaPage ordenarPor(String textoVisible) {
        WebElement precioAnterior = precios.get(0);
        new Select(listaOrden).selectByVisibleText(textoVisible);
        // Al elegir una opcion la pagina se recarga: hay que esperar a que el listado viejo desaparezca.
        espera.until(ExpectedConditions.stalenessOf(precioAnterior));
        Visual.pausa();
        return this;
    }

    public List<Double> preciosProductos() {
        return precios.stream().map(precio -> Double.parseDouble(precio.getText())).toList();
    }

    /**
     * @FindBy no acepta parametros y CSS no busca por texto: se recorre el listado hasta
     * encontrar el producto por su nombre y dentro de el se busca el boton con By.
     */
    public CategoriaPage agregarAlCarrito(String nombreProducto) {
        for (WebElement producto : productos) {
            String nombre = producto.findElement(By.cssSelector("h2.product-title a")).getText().trim();
            if (nombre.equals(nombreProducto)) {
                producto.findElement(By.cssSelector("input.product-box-add-to-cart-button")).click();
                // El sitio responde sin recargar la pagina: se espera a que salga la notificacion.
                espera.until(ExpectedConditions.visibilityOf(notificacion));
                Visual.pausa();
                return this;
            }
        }
        throw new IllegalArgumentException("No existe el producto: " + nombreProducto);
    }

    public String mensajeNotificacion() {
        return notificacion.getText().trim();
    }
}
