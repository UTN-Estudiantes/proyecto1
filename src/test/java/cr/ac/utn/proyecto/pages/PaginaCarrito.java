package cr.ac.utn.proyecto.pages;

import cr.ac.utn.proyecto.base.BaseTest;
import java.math.BigDecimal;
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

public class PaginaCarrito {

    private final WebDriver driver;
    private final WebDriverWait espera;

    @FindBy(css = "tr.cart-item-row a.product-name")
    private List<WebElement> nombresProductos;

    @FindBy(css = "tr.cart-item-row input.qty-input")
    private List<WebElement> camposCantidad;

    @FindBy(css = "tr.cart-item-row span.product-unit-price")
    private List<WebElement> preciosUnitarios;

    @FindBy(css = "tr.cart-item-row span.product-subtotal")
    private List<WebElement> subtotalesPorProducto;

    @FindBy(css = "tr.cart-item-row td.remove-from-cart input")
    private List<WebElement> casillasQuitar;

    @FindBy(css = "input.update-cart-button")
    private WebElement botonActualizar;

    @FindBy(css = "table.cart-total td.cart-total-right span.product-price")
    private List<WebElement> importesDelResumen;

    @FindBy(css = "span.product-price.order-total")
    private WebElement total;

    @FindBy(css = "div.order-summary-content")
    private WebElement contenidoDelCarrito;

    @FindBy(id = "termsofservice")
    private WebElement casillaTerminos;

    @FindBy(id = "checkout")
    private WebElement botonPagar;

    @FindBy(id = "terms-of-service-warning-box")
    private WebElement avisoDeTerminos;

    @FindBy(css = "input.checkout-as-guest-button")
    private WebElement botonPagarComoInvitado;

    @FindBy(css = "span.cart-qty")
    private WebElement indicadorCarrito;

    public PaginaCarrito(WebDriver driver) {
        this.driver = driver;
        this.espera = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public PaginaCarrito abrir() {
        driver.get(BaseTest.URL_BASE + "cart");
        return this;
    }

    public List<String> obtenerNombresDeProductos() {
        return textos(nombresProductos);
    }

    public int obtenerCantidadDeProductos() {
        return nombresProductos.size();
    }

    public String obtenerCantidadDelProducto(String nombreProducto) {
        return camposCantidad.get(posicionDe(nombreProducto)).getAttribute("value");
    }

    public BigDecimal obtenerPrecioUnitarioDelProducto(String nombreProducto) {
        return new BigDecimal(preciosUnitarios.get(posicionDe(nombreProducto)).getText().trim());
    }

    public BigDecimal obtenerSubtotalDelProducto(String nombreProducto) {
        return new BigDecimal(subtotalesPorProducto.get(posicionDe(nombreProducto)).getText().trim());
    }

    public BigDecimal obtenerSubtotalDelCarrito() {
        return new BigDecimal(importesDelResumen.get(0).getText().trim());
    }

    public BigDecimal obtenerTotal() {
        return new BigDecimal(total.getText().trim());
    }

    public String obtenerIndicadorCarrito() {
        return indicadorCarrito.getText().trim();
    }

    public PaginaCarrito cambiarCantidad(String nombreProducto, String cantidad) {
        WebElement campo = camposCantidad.get(posicionDe(nombreProducto));
        campo.clear();
        campo.sendKeys(cantidad);
        return actualizarCarrito();
    }

    public PaginaCarrito quitarProducto(String nombreProducto) {
        casillasQuitar.get(posicionDe(nombreProducto)).click();
        return actualizarCarrito();
    }

    public boolean estaVacio() {
        return nombresProductos.isEmpty();
    }

    public String obtenerMensajeDeCarritoVacio() {
        return contenidoDelCarrito.getText().trim();
    }

    public PaginaCarrito aceptarTerminos() {
        if (!casillaTerminos.isSelected()) {
            casillaTerminos.click();
        }
        return this;
    }

    public PaginaPago irAPago() {
        botonPagar.click();
        espera.until(ExpectedConditions.urlContains("/onepagecheckout"));
        return new PaginaPago(driver);
    }

    public PaginaPago irAPagoComoInvitado() {
        botonPagar.click();
        espera.until(ExpectedConditions.urlContains("/checkoutasguest"));
        botonPagarComoInvitado.click();
        espera.until(ExpectedConditions.urlContains("/onepagecheckout"));
        return new PaginaPago(driver);
    }

    public PaginaCarrito intentarIrAPago() {
        botonPagar.click();
        espera.until(ExpectedConditions.visibilityOf(avisoDeTerminos));
        return this;
    }

    public String obtenerAvisoDeTerminos() {
        espera.until(ExpectedConditions.visibilityOf(avisoDeTerminos));
        return avisoDeTerminos.getText().trim();
    }

    private PaginaCarrito actualizarCarrito() {
        WebElement botonAnterior = ((WrapsElement) botonActualizar).getWrappedElement();
        botonActualizar.click();
        espera.until(ExpectedConditions.stalenessOf(botonAnterior));
        return this;
    }

    private int posicionDe(String nombreProducto) {
        List<String> nombres = textos(nombresProductos);
        int posicion = nombres.indexOf(nombreProducto);
        if (posicion < 0) {
            throw new IllegalArgumentException("El producto no está en el carrito: " + nombreProducto);
        }
        return posicion;
    }

    private List<String> textos(List<WebElement> elementos) {
        List<String> textos = new ArrayList<>();
        for (WebElement elemento : elementos) {
            textos.add(elemento.getText().trim());
        }
        return textos;
    }
}
