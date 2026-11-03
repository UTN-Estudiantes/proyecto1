
// Responsable: Katlyn Yiliana Fernández Ramos
package cr.ac.utn.proyecto.pages;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.base.Visual;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

// Página de detalle de un producto (simple o configurable).
public class ProductoPage {

    private final WebDriver driver;

    // El id termina en el número del producto (add-to-cart-button-31), por eso se usa ^=
    @FindBy(css = "input[id^='add-to-cart-button-']")
    private WebElement botonAgregarCarrito;

    @FindBy(css = "input.add-to-wishlist-button")
    private WebElement botonAgregarDeseos;

    // Barra de notificación que aparece arriba después de agregar
    @FindBy(css = "#bar-notification p.content")
    private WebElement mensajeNotificacion;

    @FindBy(css = "span.cart-qty")
    private WebElement indicadorCarrito;

    public ProductoPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // La ruta (por ejemplo "141-inch-laptop") la entrega la prueba: la página no guarda datos fijos
    public ProductoPage abrir(String ruta) {
        driver.get(BaseTest.URL_BASE + ruta);
        Visual.pausa();
        return this;
    }

    public ProductoPage agregarAlCarrito() {
        botonAgregarCarrito.click();
        esperarNotificacion();
        Visual.pausa();
        return this;
    }

    public ProductoPage agregarALaListaDeDeseos() {
        botonAgregarDeseos.click();
        esperarNotificacion();
        Visual.pausa();
        return this;
    }

    public String mensajeNotificacion() {
        return mensajeNotificacion.getText();
    }

    public String indicadorCarrito() {
        return indicadorCarrito.getText();
    }

    public CarritoPage irAlCarrito() {
        // Se navega por URL: la barra de notificación puede tapar el enlace "Shopping cart" del encabezado
        return new CarritoPage(driver).abrir();
    }

    public ListaDeseosPage irAListaDeseos() {
        // Misma razón que irAlCarrito: la barra puede tapar el enlace "Wishlist"
        return new ListaDeseosPage(driver).abrir();
    }

    // El agregado es asíncrono: se espera a que la barra sea visible, que es cuando el contador ya se actualizó
    private void esperarNotificacion() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOf(mensajeNotificacion));
    }
}