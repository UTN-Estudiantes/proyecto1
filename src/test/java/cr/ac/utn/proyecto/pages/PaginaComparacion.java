package cr.ac.utn.proyecto.pages;

import cr.ac.utn.proyecto.base.BaseTest;
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
 * Tabla de comparación de productos: un producto por columna y una característica por fila.
 */
public class PaginaComparacion {

    private final WebDriver driver;
    private final WebDriverWait espera;

    @FindBy(css = "tr.product-name td a")
    private List<WebElement> nombresProductos;

    @FindBy(css = "tr.product-price td.a-center")
    private List<WebElement> preciosProductos;

    @FindBy(css = "tr.overview input.remove-button")
    private List<WebElement> botonesQuitar;

    @FindBy(css = "tr.specification td:first-child")
    private List<WebElement> nombresDeCaracteristicas;

    @FindBy(css = "a.clear-list")
    private List<WebElement> enlacesLimpiarLista;

    @FindBy(css = "div.page-body")
    private WebElement contenido;

    public PaginaComparacion(WebDriver driver) {
        this.driver = driver;
        this.espera = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public PaginaComparacion abrir() {
        driver.get(BaseTest.URL_BASE + "compareproducts");
        return this;
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

    public List<String> obtenerNombresDeCaracteristicas() {
        List<String> nombres = new ArrayList<>();
        for (WebElement nombre : nombresDeCaracteristicas) {
            nombres.add(nombre.getText().trim());
        }
        return nombres;
    }

    public int obtenerCantidadDeProductos() {
        return nombresProductos.size();
    }

    public boolean estaVacia() {
        return nombresProductos.isEmpty();
    }

    public String obtenerMensajeDeListaVacia() {
        return contenido.getText().trim();
    }

    public PaginaComparacion quitarProducto(String nombreProducto) {
        int posicion = obtenerNombresDeProductos().indexOf(nombreProducto);
        if (posicion < 0) {
            throw new IllegalArgumentException("El producto no está en la comparación: " + nombreProducto);
        }
        int cantidadAnterior = nombresProductos.size();
        botonesQuitar.get(posicion).click();
        espera.until(d -> nombresProductos.size() < cantidadAnterior);
        return this;
    }

    public PaginaComparacion limpiarLista() {
        enlacesLimpiarLista.get(0).click();
        espera.until(d -> nombresProductos.isEmpty());
        return this;
    }
}
