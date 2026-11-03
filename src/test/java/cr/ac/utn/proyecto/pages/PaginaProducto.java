package cr.ac.utn.proyecto.pages;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PaginaProducto {

    private final WebDriver driver;
    private final WebDriverWait espera;

    @FindBy(css = "div.product-name h1")
    private WebElement nombre;

    @FindBy(css = "div.prices span[itemprop='price']")
    private WebElement precio;

    @FindBy(css = "input.qty-input")
    private WebElement campoCantidad;

    @FindBy(css = "input.add-to-cart-button")
    private WebElement botonAgregarAlCarrito;

    @FindBy(css = "div.attributes dt label.text-prompt")
    private List<WebElement> etiquetasDeOpciones;

    @FindBy(css = "div.attributes dd")
    private List<WebElement> contenedoresDeOpciones;

    @FindBy(id = "bar-notification")
    private WebElement notificacion;

    @FindBy(css = "span.cart-qty")
    private WebElement indicadorCarrito;

    @FindBy(css = "#topcartlink a.ico-cart")
    private WebElement enlaceCarrito;

    @FindBy(css = "#bar-notification span.close")
    private WebElement botonCerrarNotificacion;

    @FindBy(css = "input.add-to-wishlist-button")
    private List<WebElement> botonesListaDeseos;

    @FindBy(css = "input.add-to-compare-list-button")
    private WebElement botonComparar;

    @FindBy(css = "a.ico-wishlist")
    private WebElement enlaceListaDeseos;

    public PaginaProducto(WebDriver driver) {
        this.driver = driver;
        this.espera = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public String obtenerNombre() {
        return nombre.getText().trim();
    }

    public BigDecimal obtenerPrecio() {
        return new BigDecimal(precio.getText().trim());
    }

    public PaginaProducto indicarCantidad(String cantidad) {
        campoCantidad.clear();
        campoCantidad.sendKeys(cantidad);
        return this;
    }

    public PaginaProducto elegirOpcion(String atributo, String opcion) {
        for (int i = 0; i < etiquetasDeOpciones.size(); i++) {
            if (etiquetasDeOpciones.get(i).getText().trim().equalsIgnoreCase(atributo)) {
                WebElement contenedor = contenedoresDeOpciones.get(i);
                // Se usa findElements(By) porque las opciones se buscan dentro del contenedor del atributo pedido, que cambia según el parámetro.
                for (WebElement candidata : contenedor.findElements(By.cssSelector("option, label"))) {
                    if (candidata.getText().trim().startsWith(opcion)) {
                        candidata.click();
                        return this;
                    }
                }
                throw new IllegalArgumentException("No existe la opción '" + opcion + "' en " + atributo);
            }
        }
        throw new IllegalArgumentException("No existe el atributo: " + atributo);
    }

    public PaginaProducto agregarAlCarrito() {
        botonAgregarAlCarrito.click();
        espera.until(ExpectedConditions.visibilityOf(notificacion));
        return this;
    }

    public String obtenerMensajeDeNotificacion() {
        espera.until(ExpectedConditions.visibilityOf(notificacion));
        return notificacion.getText().trim();
    }

    public String obtenerIndicadorCarrito() {
        return indicadorCarrito.getText().trim();
    }

    public boolean permiteListaDeDeseos() {
        return !botonesListaDeseos.isEmpty();
    }

    public PaginaProducto agregarALaListaDeDeseos() {
        botonesListaDeseos.get(0).click();
        espera.until(ExpectedConditions.visibilityOf(notificacion));
        return this;
    }

    /** El sitio no muestra aviso: redirige directamente a la tabla de comparación. */
    public PaginaComparacion agregarALaComparacion() {
        botonComparar.click();
        espera.until(ExpectedConditions.urlContains("/compareproducts"));
        return new PaginaComparacion(driver);
    }

    public PaginaListaDeseos irALaListaDeDeseos() {
        enlaceListaDeseos.click();
        espera.until(ExpectedConditions.urlContains("/wishlist"));
        return new PaginaListaDeseos(driver);
    }

    public PaginaCarrito irAlCarrito() {
        cerrarNotificacionVisible();
        enlaceCarrito.click();
        espera.until(ExpectedConditions.urlContains("/cart"));
        return new PaginaCarrito(driver);
    }

    /** El aviso de "agregado al carrito" tapa el enlace del carrito hasta que se cierra. */
    private void cerrarNotificacionVisible() {
        if (notificacion.isDisplayed()) {
            botonCerrarNotificacion.click();
            espera.until(ExpectedConditions.invisibilityOf(notificacion));
        }
    }
}
