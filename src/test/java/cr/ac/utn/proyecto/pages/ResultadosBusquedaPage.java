package cr.ac.utn.proyecto.pages;

import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

/** Pagina de busqueda: resultados, avisos y busqueda avanzada (RF-03). */
public class ResultadosBusquedaPage extends BasePage {

    @FindBy(id = "Q")
    private WebElement campoTexto;

    @FindBy(id = "As")
    private WebElement casillaBusquedaAvanzada;

    @FindBy(id = "Cid")
    private WebElement listaCategorias;

    @FindBy(id = "Isc")
    private WebElement casillaIncluirSubcategorias;

    @FindBy(id = "Pf")
    private WebElement campoPrecioDesde;

    @FindBy(id = "Pt")
    private WebElement campoPrecioHasta;

    @FindBy(css = ".page-body .search-button")
    private WebElement botonBuscarAvanzado;

    @FindBy(css = ".search-results .product-item .product-title a")
    private List<WebElement> titulosResultados;

    @FindBy(css = ".search-results .product-item .actual-price")
    private List<WebElement> preciosResultados;

    @FindBy(css = ".search-results .result")
    private WebElement mensajeSinResultados;

    @FindBy(css = ".search-results .warning")
    private WebElement mensajeAdvertencia;

    public ResultadosBusquedaPage(WebDriver driver) {
        super(driver);
    }

    /** Busqueda avanzada: texto + categoria (texto visible de la lista) + rango de precio. */
    public ResultadosBusquedaPage buscarAvanzado(String texto, String categoria,
                                                 String precioDesde, String precioHasta) {
        escribir(campoTexto, texto);
        if (!casillaBusquedaAvanzada.isSelected()) {
            clic(casillaBusquedaAvanzada);
        }
        new Select(visible(listaCategorias)).selectByVisibleText(categoria);
        if (!casillaIncluirSubcategorias.isSelected()) {
            clic(casillaIncluirSubcategorias);
        }
        escribir(campoPrecioDesde, precioDesde);
        escribir(campoPrecioHasta, precioHasta);
        clic(botonBuscarAvanzado);
        return new ResultadosBusquedaPage(driver);
    }

    public int contarResultados() {
        return titulosResultados.size();
    }

    public List<String> obtenerNombresResultados() {
        return titulosResultados.stream().map(e -> e.getText().trim()).collect(Collectors.toList());
    }

    public List<Double> obtenerPreciosResultados() {
        return preciosResultados.stream().map(e -> aNumero(e.getText())).collect(Collectors.toList());
    }

    public String obtenerMensajeSinResultados() {
        return visible(mensajeSinResultados).getText().trim();
    }

    public String obtenerAdvertencia() {
        return visible(mensajeAdvertencia).getText().trim();
    }

    /** Abre el resultado cuyo nombre coincide exactamente. */
    public ProductoPage abrirResultado(String nombreProducto) {
        WebElement enlace = titulosResultados.stream()
                .filter(e -> e.getText().trim().equalsIgnoreCase(nombreProducto))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No aparece en resultados: " + nombreProducto));
        clic(enlace);
        return new ProductoPage(driver);
    }
}
