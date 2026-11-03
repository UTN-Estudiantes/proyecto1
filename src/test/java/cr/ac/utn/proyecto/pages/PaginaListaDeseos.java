package cr.ac.utn.proyecto.pages;

import cr.ac.utn.proyecto.base.BaseTest;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Lista de deseos (Wishlist): productos guardados, quitar productos y pasarlos al carrito.
 */
public class PaginaListaDeseos {

    private final WebDriver driver;
    private final WebDriverWait espera;

    @FindBy(css = "tr.cart-item-row td.product a")
    private List<WebElement> nombresProductos;

    @FindBy(css = "tr.cart-item-row td.remove-from-cart input")
    private List<WebElement> casillasQuitar;

    @FindBy(css = "tr.cart-item-row td.add-to-cart input")
    private List<WebElement> casillasAgregarAlCarrito;

    @FindBy(css = "input.update-wishlist-button")
    private List<WebElement> botonesActualizar;

    @FindBy(css = "input.wishlist-add-to-cart-button")
    private List<WebElement> botonesAgregarAlCarrito;

    @FindBy(css = "div.page-body")
    private WebElement contenido;

    @FindBy(css = "span.wishlist-qty")
    private WebElement indicadorListaDeseos;

    public PaginaListaDeseos(WebDriver driver) {
        this.driver = driver;
        this.espera = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public PaginaListaDeseos abrir() {
        driver.get(BaseTest.URL_BASE + "wishlist");
        return this;
    }

    public List<String> obtenerNombresDeProductos() {
        List<String> nombres = new ArrayList<>();
        for (WebElement nombre : nombresProductos) {
            nombres.add(nombre.getText().trim());
        }
        return nombres;
    }

    public int obtenerCantidadDeProductos() {
        return nombresProductos.size();
    }

    public boolean estaVacia() {
        return nombresProductos.isEmpty();
    }

    public String obtenerMensajeDeListaVacia() {
        return contenido.getText().trim();
    }

    public String obtenerIndicadorListaDeseos() {
        return indicadorListaDeseos.getText().trim();
    }

    public PaginaListaDeseos marcarParaCarrito(String nombreProducto) {
        WebElement casilla = casillasAgregarAlCarrito.get(posicionDe(nombreProducto));
        if (!casilla.isSelected()) {
            casilla.click();
        }
        return this;
    }

    public PaginaCarrito agregarMarcadosAlCarrito() {
        botonesAgregarAlCarrito.get(0).click();
        espera.until(ExpectedConditions.urlContains("/cart"));
        return new PaginaCarrito(driver);
    }

    public PaginaListaDeseos quitarProducto(String nombreProducto) {
        casillasQuitar.get(posicionDe(nombreProducto)).click();
        WebElement botonAnterior = ((WrapsElement) botonesActualizar.get(0)).getWrappedElement();
        botonesActualizar.get(0).click();
        espera.until(ExpectedConditions.stalenessOf(botonAnterior));
        return this;
    }

    private int posicionDe(String nombreProducto) {
        List<String> nombres = obtenerNombresDeProductos();
        int posicion = nombres.indexOf(nombreProducto);
        if (posicion < 0) {
            throw new IllegalArgumentException("El producto no está en la lista de deseos: " + nombreProducto);
        }
        return posicion;
    }
}
