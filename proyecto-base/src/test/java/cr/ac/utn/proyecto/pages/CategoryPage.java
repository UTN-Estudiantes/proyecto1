package cr.ac.utn.proyecto.pages;

import cr.ac.utn.proyecto.base.Visual;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

/** Pagina de categoria y listado del catalogo. Page Factory. */
public class CategoryPage {

    private final WebDriver driver;

    @FindBy(css = "div.page-title h1")
    private WebElement tituloCategoria;

    @FindBy(id = "products-orderby")
    private WebElement selectorOrden;

    @FindBy(css = "div.product-item")
    private List<WebElement> tarjetasProductos;

    @FindBy(css = "span.price.actual-price")
    private List<WebElement> preciosProductos;

    @FindBy(id = "bar-notification")
    private WebElement barraNotificacion;

    @FindBy(css = "#bar-notification p.content")
    private WebElement textoNotificacion;

    @FindBy(css = "#bar-notification span.close")
    private WebElement botonCerrarNotificacion;

    @FindBy(css = "#topcartlink a.ico-cart")
    private WebElement enlaceCarrito;

    @FindBy(css = "span.cart-qty")
    private WebElement cantidadCarrito;

    public CategoryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public CategoryPage ordenarPor(String opcionVisible) {
        new Select(selectorOrden).selectByVisibleText(opcionVisible);
        Visual.pausa();
        return new CategoryPage(driver);
    }

    public List<Double> preciosProductos() {
        List<Double> precios = new ArrayList<>();
        for (WebElement precio : preciosProductos) {
            precios.add(Double.parseDouble(precio.getText().trim()));
        }
        return precios;
    }

    /**
     * Busca la tarjeta del producto por su nombre y hace clic en su boton Add to cart.
     */
    public CategoryPage agregarAlCarrito(String nombreProducto) {
        // Se recorren las tarjetas (@FindBy) y dentro de la que coincide con el parametro
        // se localiza su boton con findElement(By.cssSelector), igual que en TablePage del Lab 2.
        for (WebElement tarjeta : tarjetasProductos) {
            WebElement titulo = tarjeta.findElement(By.cssSelector("h2.product-title a"));
            if (titulo.getText().trim().equals(nombreProducto)) {
                tarjeta.findElement(By.cssSelector("input.product-box-add-to-cart-button")).click();
                Visual.pausa();
                return this;
            }
        }
        throw new IllegalArgumentException("No existe el producto: " + nombreProducto);
    }

    public ProductPage abrirProducto(String nombreProducto) {
        // Se usa driver.findElement con By.linkText porque el nombre del producto
        // se recibe por parametro.
        driver.findElement(By.linkText(nombreProducto)).click();
        Visual.pausa();
        return new ProductPage(driver);
    }

    public String mensajeNotificacion() {
        return textoNotificacion.getText().trim();
    }

    public String cantidadEnInsignia() {
        return cantidadCarrito.getText().trim();
    }

    public CartPage irAlCarrito() {
        if (barraNotificacion.isDisplayed()) {
            botonCerrarNotificacion.click();
            Visual.pausa();
        }
        enlaceCarrito.click();
        Visual.pausa();
        return new CartPage(driver);
    }
}
