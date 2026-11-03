package cr.ac.utn.proyecto.pages;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.base.Visual;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/** Pagina de un producto, por ejemplo /build-your-own-computer. Page Factory. */
public class ProductoPage {

    private final WebDriver driver;

    // Etiquetas de los botones de opcion y de las casillas (HDD, OS, Software).
    @FindBy(css = "div.attributes ul.option-list label")
    private List<WebElement> opciones;

    @FindBy(css = "input.add-to-cart-button")
    private WebElement botonAgregar;

    @FindBy(id = "bar-notification")
    private WebElement notificacion;

    public ProductoPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public ProductoPage abrir(String slugProducto) {
        driver.get(BaseTest.URL_BASE + slugProducto);
        Visual.pausa();
        return this;
    }

    public ProductoPage elegirOpcion(String texto) {
        for (WebElement opcion : opciones) {
            if (opcion.getText().trim().equals(texto)) {
                opcion.click();
                Visual.pausa();
                return this;
            }
        }
        throw new IllegalArgumentException("No existe la opcion: " + texto);
    }

    public ProductoPage agregarAlCarrito() {
        botonAgregar.click();
        // El sitio responde sin recargar la pagina: se espera a que salga la notificacion.
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOf(notificacion));
        Visual.pausa();
        return this;
    }

    public String mensajeNotificacion() {
        return notificacion.getText().trim();
    }
}
