package cr.ac.utn.proyecto.pages;

import cr.ac.utn.proyecto.base.Visual;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/** Pagina del carrito (/cart). Los metodos trabajan con el unico producto que tiene el carrito. Page Factory. */
public class CarritoPage {

    private final WebDriver driver;

    @FindBy(css = "input.qty-input")
    private WebElement campoCantidad;

    @FindBy(css = "input[name='removefromcart']")
    private WebElement casillaQuitar;

    @FindBy(css = "input[name='updatecart']")
    private WebElement botonActualizar;

    @FindBy(css = "span.product-unit-price")
    private WebElement precioUnitario;

    @FindBy(css = "span.product-subtotal")
    private WebElement totalLinea;

    @FindBy(css = "div.order-summary-content")
    private WebElement contenidoCarrito;

    @FindBy(id = "termsofservice")
    private WebElement casillaTerminos;

    @FindBy(id = "checkout")
    private WebElement botonCheckout;

    @FindBy(id = "terms-of-service-warning-box")
    private WebElement cuadroTerminos;

    public CarritoPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public CarritoPage cambiarCantidad(int cantidad) {
        campoCantidad.clear();
        campoCantidad.sendKeys(String.valueOf(cantidad));
        Visual.pausa();
        return this;
    }

    public CarritoPage actualizar() {
        botonActualizar.click();
        Visual.pausa();
        return this;
    }

    /** Solo marca la casilla Remove: el producto se quita al llamar actualizar(). */
    public CarritoPage quitarProducto() {
        casillaQuitar.click();
        Visual.pausa();
        return this;
    }

    public double precioUnitario() {
        return Double.parseDouble(precioUnitario.getText());
    }

    public double totalLinea() {
        return Double.parseDouble(totalLinea.getText());
    }

    public String mensajeCarritoVacio() {
        return contenidoCarrito.getText().trim();
    }

    public CarritoPage aceptarTerminos() {
        casillaTerminos.click();
        Visual.pausa();
        return this;
    }

    /** Un visitante pasa primero por Log in. Sin aceptar los terminos se queda en el carrito. */
    public LoginPage irAlCheckout() {
        botonCheckout.click();
        Visual.pausa();
        return new LoginPage(driver);
    }

    /** El aviso es un cuadro de la pagina (no una alerta del navegador): se espera a que se vea y se lee. */
    public String avisoTerminos() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOf(cuadroTerminos));
        return cuadroTerminos.getText().trim();
    }
}
