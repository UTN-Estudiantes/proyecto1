package cr.ac.utn.proyecto.pages;

import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/** Carrito de compras (RF-05) y punto de partida del pago (RF-09). */
public class CarritoPage extends BasePage {

    @FindBy(css = ".cart .cart-item-row")
    private List<WebElement> filas;

    @FindBy(css = ".cart .cart-item-row .product-name")
    private List<WebElement> nombres;

    @FindBy(css = ".cart .cart-item-row .product-unit-price")
    private List<WebElement> preciosUnitarios;

    @FindBy(css = ".cart .cart-item-row .product-subtotal")
    private List<WebElement> subtotales;

    @FindBy(name = "updatecart")
    private WebElement botonActualizar;

    @FindBy(css = ".order-summary-content")
    private WebElement contenidoResumen;

    @FindBy(id = "termsofservice")
    private WebElement casillaTerminos;

    @FindBy(id = "checkout")
    private WebElement botonPagar;

    @FindBy(id = "terms-of-service-warning-box")
    private WebElement avisoTerminos;

    public CarritoPage(WebDriver driver) {
        super(driver);
    }

    public CarritoPage abrir() {
        driver.get(URL_BASE + "cart");
        return new CarritoPage(driver);
    }

    public int contarProductos() {
        return filas.size();
    }

    public List<String> obtenerNombres() {
        return nombres.stream().map(e -> e.getText().trim()).collect(Collectors.toList());
    }

    public boolean contieneProducto(String nombreProducto) {
        return obtenerNombres().stream().anyMatch(n -> n.equalsIgnoreCase(nombreProducto));
    }

    public double obtenerPrecioUnitario(int fila) {
        return aNumero(preciosUnitarios.get(fila).getText());
    }

    public double obtenerSubtotal(int fila) {
        return aNumero(subtotales.get(fila).getText());
    }

    /**
     * Fila del producto: depende del nombre (parametro), por eso se usa driver.findElement + By.
     * XPath relativo: busca la fila que contiene un enlace con ese nombre.
     */
    private WebElement filaDe(String nombreProducto) {
        return driver.findElement(By.xpath("//tr[contains(@class,'cart-item-row')]"
                + "[.//a[contains(@class,'product-name') and normalize-space()='" + nombreProducto + "']]"));
    }

    /** Escribe la nueva cantidad del producto y pulsa "Update shopping cart". */
    public CarritoPage cambiarCantidad(String nombreProducto, String cantidad) {
        WebElement campo = filaDe(nombreProducto).findElement(By.cssSelector(".qty-input"));
        escribir(campo, cantidad);
        clic(botonActualizar);
        return new CarritoPage(driver);
    }

    /** Marca la casilla "Remove" del producto y actualiza el carrito. */
    public CarritoPage quitarProducto(String nombreProducto) {
        clic(filaDe(nombreProducto).findElement(By.name("removefromcart")));
        clic(botonActualizar);
        return new CarritoPage(driver);
    }

    public int obtenerCantidad(String nombreProducto) {
        String valor = filaDe(nombreProducto).findElement(By.cssSelector(".qty-input")).getAttribute("value");
        return Integer.parseInt(valor.trim());
    }

    public String obtenerMensajeResumen() {
        return visible(contenidoResumen).getText().trim();
    }

    public CarritoPage aceptarTerminos() {
        if (!casillaTerminos.isSelected()) {
            clic(casillaTerminos);
        }
        return this;
    }

    /** Usuario con sesion: pasa directo al pago en una pagina. */
    public CheckoutPage pagar() {
        clic(botonPagar);
        return new CheckoutPage(driver);
    }

    /** Intento de pago sin aceptar terminos: se queda en el carrito. */
    public CarritoPage pagarSinAceptarTerminos() {
        clic(botonPagar);
        return this;
    }

    public String obtenerAvisoTerminos() {
        return visible(avisoTerminos).getText().trim();
    }
}
