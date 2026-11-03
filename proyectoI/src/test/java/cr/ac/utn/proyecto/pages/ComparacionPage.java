
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

public class ComparacionPage {

    private final WebDriver driver;

    @FindBy(css = "input.add-to-compare-list-button")
    private WebElement botonAgregarComparar;

    @FindBy(css = "tr.product-name a")
    private List<WebElement> nombresProductos;

    @FindBy(css = "a.clear-list")
    private WebElement enlaceLimpiarLista;

    public ComparacionPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public ComparacionPage agregarProductoAComparar(String rutaProducto) {

        driver.get(BaseTest.URL_BASE + rutaProducto);

        botonAgregarComparar.click();

        WebDriverWait espera = new WebDriverWait(driver, Duration.ofSeconds(15));

        espera.until(ExpectedConditions.urlMatches("compareproducts/?$"));

        espera.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("tr.product-name a")));

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

    public ComparacionPage quitarPrimerProducto() {

        WebDriverWait espera = new WebDriverWait(driver, Duration.ofSeconds(20));

        espera.until(ExpectedConditions.numberOfElementsToBe(
                By.cssSelector("tr.product-name a"), 2));

        // By se usa aquí porque el botón Remove se repite por cada producto; se toma el segundo, que es el de la laptop (el primer producto agregado).
        WebElement botonLaptop = driver.findElements(
                By.cssSelector("tr.overview .remove-button")).get(1);

        botonLaptop.click();

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

        WebElement mensaje = espera.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(),'You have no items to compare.')]")));

        return mensaje.getText();
    }
}
