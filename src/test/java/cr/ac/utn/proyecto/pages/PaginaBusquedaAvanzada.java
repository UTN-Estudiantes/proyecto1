package cr.ac.utn.proyecto.pages;

import cr.ac.utn.proyecto.base.BaseTest;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Formulario de búsqueda avanzada (texto, categoría, subcategorías y rango de precio).
 */
public class PaginaBusquedaAvanzada {

    private final WebDriver driver;
    private final WebDriverWait espera;

    @FindBy(id = "Q")
    private WebElement campoTexto;

    @FindBy(id = "As")
    private WebElement casillaBusquedaAvanzada;

    @FindBy(id = "advanced-search-block")
    private WebElement bloqueAvanzado;

    @FindBy(id = "Cid")
    private WebElement listaCategorias;

    @FindBy(id = "Isc")
    private WebElement casillaSubcategorias;

    @FindBy(id = "Pf")
    private WebElement campoPrecioDesde;

    @FindBy(id = "Pt")
    private WebElement campoPrecioHasta;

    @FindBy(css = "input.search-button")
    private WebElement botonBuscar;

    public PaginaBusquedaAvanzada(WebDriver driver) {
        this.driver = driver;
        this.espera = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public PaginaBusquedaAvanzada abrir() {
        driver.get(BaseTest.URL_BASE + "search");
        return this;
    }

    public PaginaBusquedaAvanzada activarBusquedaAvanzada() {
        if (!casillaBusquedaAvanzada.isSelected()) {
            casillaBusquedaAvanzada.click();
        }
        espera.until(ExpectedConditions.visibilityOf(bloqueAvanzado));
        return this;
    }

    public PaginaBusquedaAvanzada escribirTexto(String texto) {
        campoTexto.clear();
        campoTexto.sendKeys(texto);
        return this;
    }

    public PaginaBusquedaAvanzada seleccionarCategoria(String categoria) {
        new Select(listaCategorias).selectByVisibleText(categoria);
        return this;
    }

    public PaginaBusquedaAvanzada incluirSubcategorias(boolean incluir) {
        if (casillaSubcategorias.isSelected() != incluir) {
            casillaSubcategorias.click();
        }
        return this;
    }

    public PaginaBusquedaAvanzada indicarRangoDePrecio(String desde, String hasta) {
        campoPrecioDesde.clear();
        campoPrecioDesde.sendKeys(desde);
        campoPrecioHasta.clear();
        campoPrecioHasta.sendKeys(hasta);
        return this;
    }

    public PaginaBusqueda buscar() {
        botonBuscar.click();
        espera.until(ExpectedConditions.urlContains("/search?"));
        return new PaginaBusqueda(driver);
    }

    public PaginaBusqueda buscarConFiltros(String texto, String categoria, boolean incluirSubcategorias,
                                           String precioDesde, String precioHasta) {
        return escribirTexto(texto)
                .activarBusquedaAvanzada()
                .seleccionarCategoria(categoria)
                .incluirSubcategorias(incluirSubcategorias)
                .indicarRangoDePrecio(precioDesde, precioHasta)
                .buscar();
    }
}
