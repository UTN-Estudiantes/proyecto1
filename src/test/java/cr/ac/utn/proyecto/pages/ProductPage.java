package cr.ac.utn.proyecto.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductPage {
    private WebDriver driver;

    // Localizadores
    @FindBy(css = ".product-name h1")
    private WebElement lblNombreProducto;

    @FindBy(css = ".product-price span")
    private WebElement lblPrecio;

    @FindBy(css = "input.qty-input")
    private WebElement txtCantidad;

    // Botones de acción principales
    @FindBy(css = "input[value='Add to cart']")
    private WebElement btnAddToCart;

    @FindBy(css = "input[value='Add to wishlist']")
    private WebElement btnAddToWishlist;

    // Barra de notificación dinámica se ve verde cuando es éxito, roja cuando
    // faltan opciones
    @FindBy(css = "#bar-notification p")
    private WebElement lblNotificacion;
    @FindBy(css = "#bar-notification .close")
    private WebElement btnCerrarNotificacion;

    // Opciones de armar un producto "Build your own computer" - RF-06
    // Procesador Rápido (Fast +100.00)
    @FindBy(css = "input[value='65'], select[id='product_attribute_16_5_4'] option[value='14']")
    private WebElement rdoProcessorFast;
    // RAM 8GB (+60.00)
    @FindBy(css = "input[name*='product_attribute'][value*='8GB'], input[id*='product_attribute_'][value*='58'], input[value='91'], select[id='product_attribute_16_6_5'] option[value='17']")
    private WebElement rdoRam8GB;
    // HDD 400 GB (+100.00)
    @FindBy(css = "input[value='58'], input[value='19']")
    private WebElement rdoHdd400GB;
    // Indicador Del Carrito
    @FindBy(css = "#topcartlink .cart-qty")
    private WebElement lblCartQty;

    @FindBy(css = "#topcartlink .cart-label")
    private WebElement lnkCart;

    // Constructor
    public ProductPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Metodos de LOgica
    public String obtenerNombreProducto() {
        return lblNombreProducto.getText();
    }

    public String obtenerPrecio() {
        return lblPrecio.getText();
    }

    public void ingresarCantidad(String cantidad) {
        txtCantidad.clear();
        txtCantidad.sendKeys(cantidad);
    }

    public void agregarCarrito() {
        btnAddToCart.click();
    }

    public void agregarWishlist() {
        btnAddToWishlist.click();
    }

    // Armar las opciones obligatorias del computador del reto RF-06.
    public void seleccionarOpcionesComputadora() {
        if (rdoProcessorFast.isDisplayed() && !rdoProcessorFast.isSelected()) {
            rdoProcessorFast.click();
        }
        if (rdoRam8GB.isDisplayed() && !rdoRam8GB.isSelected()) {
            rdoRam8GB.click();
        }
        if (rdoHdd400GB.isDisplayed() && !rdoHdd400GB.isSelected()) {
            rdoHdd400GB.click();
        }
    }

    // Lee el mensaje del banner superior
    public String obtenerMensajeNotificacion() {
        return lblNotificacion.getText();
    }

    public void cerrarNotificacion() {
        if (btnCerrarNotificacion.isDisplayed()) {
            btnCerrarNotificacion.click();
        }
    }

    public String obtenerCantidadIndicadorCarrito() {
        return lblCartQty.getText();
    }

    public CartPage irCarrito() {
        lnkCart.click();
        return new CartPage(driver);
    }
}
