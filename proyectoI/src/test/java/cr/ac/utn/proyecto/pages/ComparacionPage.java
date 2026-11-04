
package cr.ac.utn.proyecto.pages;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.base.Visual;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

// Se usa By en las esperas de cantidad porque ExpectedConditions.numberOfElementsToBe
// recibe un By, no un @FindBy. Es el mismo selector de nombresProductos.
public class ComparacionPage {

    private final WebDriver driver;

    @FindBy(css = "input.add-to-compare-list-button")
    private WebElement botonAgregarComparar;

    @FindBy(css = "tr.product-name a")
    private List<WebElement> nombresProductos;

    @FindBy(css = "a.clear-list")
    private WebElement enlaceLimpiarLista;

    @FindBy(css = "div.compare-products-page div.page-body")
    private WebElement avisoListaVacia;

    public ComparacionPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public ComparacionPage agregarProductoAComparar(String rutaProducto) {

        driver.get(BaseTest.URL_BASE + rutaProducto);

        botonAgregarComparar.click();

        WebDriverWait espera = new WebDriverWait(driver, Duration.ofSeconds(15));

        espera.until(ExpectedConditions.urlMatches("compareproducts/?$"));

        espera.until(driver -> !nombresProductos.isEmpty());

        Visual.pausa();

        return this;
    }

    public ComparacionPage abrirComparacion() {

        if (!driver.getCurrentUrl().matches(".*compareproducts/?$")) {
            driver.get(BaseTest.URL_BASE + "compareproducts");
        }

        WebDriverWait espera = new WebDriverWait(driver, Duration.ofSeconds(20));

        espera.until(ExpectedConditions.numberOfElementsToBe(
                By.cssSelector("tr.product-name a"), 2));

        Visual.pausa();

        return this;
    }

    public List<String> nombresProductos() {

        return nombresProductos.stream()
                .map(WebElement::getText)
                .toList();
    }

    public ComparacionPage quitarProducto(String idProducto) {

        WebDriverWait espera = new WebDriverWait(driver, Duration.ofSeconds(20));

        espera.until(ExpectedConditions.numberOfElementsToBe(
                By.cssSelector("tr.product-name a"), 2));

        // Localizador con parámetro: el botón Remove es igual para todos los productos y solo
        // cambia el id al final de su onclick; @FindBy no acepta parámetros.
        driver.findElement(By.cssSelector(
                "input.remove-button[onclick$=\"/remove/" + idProducto + "')\"]")).click();

        // Esperar hasta que quede solamente un producto.
        espera.until(ExpectedConditions.numberOfElementsToBe(
                By.cssSelector("tr.product-name a"), 1));

        Visual.pausa();

        return this;
    }

    public ComparacionPage limpiarLista() {

        enlaceLimpiarLista.click();

        WebDriverWait espera = new WebDriverWait(driver, Duration.ofSeconds(20));

        espera.until(ExpectedConditions.numberOfElementsToBe(
                By.cssSelector("tr.product-name a"), 0));

        Visual.pausa();

        return this;
    }

    public String mensajeListaVacia() {

        WebDriverWait espera = new WebDriverWait(driver, Duration.ofSeconds(15));

        espera.until(ExpectedConditions.visibilityOf(avisoListaVacia));

        return avisoListaVacia.getText();
    }
}
