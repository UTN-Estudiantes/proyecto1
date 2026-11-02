package cr.ac.utn.proyecto.pages;

import cr.ac.utn.proyecto.base.Visual;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/** Pagina del carrito de compras (/cart). Page Factory. */
public class CartPage {

    private final WebDriver driver;

    // Puede estar vacia si no hay productos en el carrito: por eso es una lista.
    @FindBy(css = "td.product a.product-name")
    private List<WebElement> nombresProductosEnCarrito;

    @FindBy(css = "input.qty-input")
    private WebElement campoCantidad;

    @FindBy(name = "removefromcart")
    private WebElement casillaEliminar;

    @FindBy(name = "updatecart")
    private WebElement botonActualizarCarrito;

    @FindBy(css = "span.product-subtotal")
    private WebElement subtotalProducto;

    @FindBy(css = "div.order-summary-content")
    private WebElement contenidoResumenCarrito;

    @FindBy(id = "termsofservice")
    private WebElement casillaTerminosServicio;

    @FindBy(id = "checkout")
    private WebElement botonCheckout;

    @FindBy(id = "terms-of-service-warning-box")
    private WebElement cuadroAdvertenciaTerminos;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public List<String> nombresEnCarrito() {
        List<String> productos = new ArrayList<>();
        for (WebElement nombre : nombresProductosEnCarrito) {
            productos.add(nombre.getText().trim());
        }
        return productos;
    }

    public CartPage cambiarCantidad(String nuevaCantidad) {
        campoCantidad.clear();
        campoCantidad.sendKeys(nuevaCantidad);
        Visual.pausa();
        botonActualizarCarrito.click();
        Visual.pausa();
        return new CartPage(driver);
    }

    public CartPage eliminarPrimerProducto() {
        casillaEliminar.click();
        Visual.pausa();
        botonActualizarCarrito.click();
        Visual.pausa();
        return new CartPage(driver);
    }

    public double subtotalPrimerProducto() {
        return Double.parseDouble(subtotalProducto.getText().trim());
    }

    public String mensajeCarritoVacio() {
        return contenidoResumenCarrito.getText().trim();
    }

    public CartPage aceptarTerminos() {
        if (!casillaTerminosServicio.isSelected()) {
            casillaTerminosServicio.click();
            Visual.pausa();
        }
        return this;
    }

    public CheckoutPage irACheckout() {
        botonCheckout.click();
        Visual.pausa();
        return new CheckoutPage(driver);
    }

    public CartPage intentarCheckoutSinTerminos() {
        botonCheckout.click();
        Visual.pausa();
        return this;
    }

    public String textoAdvertenciaTerminos() {
        return cuadroAdvertenciaTerminos.getText().trim();
    }
}