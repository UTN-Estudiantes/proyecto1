package cr.ac.utn.proyecto.pages;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Resultados de una búsqueda por texto, con el cuadro de búsqueda del encabezado.
 */
public class PaginaBusqueda {

    private final WebDriver driver;
    private final WebDriverWait espera;

    @FindBy(id = "small-searchterms")
    private WebElement campoBusqueda;

    @FindBy(css = "div.search-box input.search-box-button")
    private WebElement botonBuscar;

    @FindBy(css = "div.search-results div.product-item h2.product-title a")
    private List<WebElement> nombresProductos;

    @FindBy(css = "div.search-results div.product-item span.price.actual-price")
    private List<WebElement> preciosProductos;

    @FindBy(css = "div.search-results strong.warning")
    private List<WebElement> avisos;

    @FindBy(css = "div.search-results strong.result")
    private List<WebElement> mensajesSinResultados;

    public PaginaBusqueda(WebDriver driver) {
        this.driver = driver;
        this.espera = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public PaginaBusqueda buscar(String texto) {
        String urlAnterior = driver.getCurrentUrl();
        campoBusqueda.clear();
        campoBusqueda.sendKeys(texto);
        botonBuscar.click();
        espera.until(d -> !d.getCurrentUrl().equals(urlAnterior));
        return this;
    }

    public PaginaProducto abrirProducto(String nombre) {
        for (WebElement enlace : nombresProductos) {
            if (enlace.getText().trim().equalsIgnoreCase(nombre)) {
                String destino = enlace.getAttribute("href");
                enlace.click();
                espera.until(ExpectedConditions.urlToBe(destino));
                return new PaginaProducto(driver);
            }
        }
        throw new IllegalArgumentException("No existe el producto en los resultados: " + nombre);
    }

    public List<String> obtenerNombresDeProductos() {
        List<String> nombres = new ArrayList<>();
        for (WebElement nombre : nombresProductos) {
            nombres.add(nombre.getText().trim());
        }
        return nombres;
    }

    public List<BigDecimal> obtenerPreciosDeProductos() {
        List<BigDecimal> precios = new ArrayList<>();
        for (WebElement precio : preciosProductos) {
            precios.add(new BigDecimal(precio.getText().trim()));
        }
        return precios;
    }

    public int obtenerCantidadDeResultados() {
        return nombresProductos.size();
    }

    public String obtenerAvisoDeBusqueda() {
        return avisos.isEmpty() ? "" : avisos.get(0).getText().trim();
    }

    public String obtenerMensajeSinResultados() {
        return mensajesSinResultados.isEmpty() ? "" : mensajesSinResultados.get(0).getText().trim();
    }
}
