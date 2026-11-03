package cr.ac.utn.proyecto.pages;

import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

/** Listado de productos de una categoria: orden, tamano de pagina y vista (RF-04). */
public class CategoriaPage extends BasePage {

    @FindBy(id = "products-orderby")
    private WebElement listaOrden;

    @FindBy(id = "products-pagesize")
    private WebElement listaTamanoPagina;

    @FindBy(id = "products-viewmode")
    private WebElement listaVista;

    @FindBy(css = ".product-item")
    private List<WebElement> productos;

    @FindBy(css = ".product-item .product-title a")
    private List<WebElement> titulos;

    @FindBy(css = ".product-item .actual-price")
    private List<WebElement> precios;

    @FindBy(css = ".product-list")
    private List<WebElement> vistaLista;

    @FindBy(css = ".product-grid")
    private List<WebElement> vistaCuadricula;

    @FindBy(css = "#bar-notification .content")
    private WebElement textoNotificacion;

    @FindBy(css = "#bar-notification .close")
    private WebElement cerrarNotificacion;

    public CategoriaPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Al cambiar una lista el sitio recarga la pagina con un parametro nuevo en la direccion
     * (orderby, pagesize o viewmode). Se espera ese cambio antes de devolver la pagina nueva.
     */
    private CategoriaPage elegirYEsperar(WebElement lista, String opcion, String parametroUrl) {
        new Select(visible(lista)).selectByVisibleText(opcion);
        espera.until(ExpectedConditions.urlContains(parametroUrl));
        return new CategoriaPage(driver);
    }

    /** Ejemplo: "Price: Low to High", "Name: A to Z". */
    public CategoriaPage ordenarPor(String opcionVisible) {
        return elegirYEsperar(listaOrden, opcionVisible, "orderby=");
    }

    /** Ejemplo: "4", "8", "12". */
    public CategoriaPage mostrarPorPagina(String cantidad) {
        return elegirYEsperar(listaTamanoPagina, cantidad, "pagesize=" + cantidad);
    }

    /** Ejemplo: "List" o "Grid". */
    public CategoriaPage cambiarVista(String vista) {
        return elegirYEsperar(listaVista, vista, "viewmode=" + vista.toLowerCase());
    }

    public int contarProductos() {
        return productos.size();
    }

    public List<String> obtenerNombres() {
        return titulos.stream().map(e -> e.getText().trim()).collect(Collectors.toList());
    }

    public List<Double> obtenerPrecios() {
        return precios.stream().map(e -> aNumero(e.getText())).collect(Collectors.toList());
    }

    public boolean seMuestraEnLista() {
        return !vistaLista.isEmpty() && vistaCuadricula.isEmpty();
    }

    /**
     * Agrega al carrito desde el listado el producto con el nombre indicado.
     * El boton "Add to cart" depende del producto, y @FindBy no acepta parametros;
     * por eso se usa driver.findElement + By con un XPath relativo construido con el nombre.
     */
    public CategoriaPage agregarAlCarritoDesdeListado(String nombreProducto) {
        String xpath = "//div[contains(@class,'product-item')]"
                + "[.//h2[contains(@class,'product-title')]/a[normalize-space()='" + nombreProducto + "']]"
                + "//input[contains(@class,'product-box-add-to-cart-button')]";
        clic(driver.findElement(By.xpath(xpath)));
        visible(textoNotificacion);
        clic(cerrarNotificacion);
        espera.until(ExpectedConditions.invisibilityOf(textoNotificacion));
        return this;
    }

    public ProductoPage abrirProducto(String nombreProducto) {
        WebElement enlace = titulos.stream()
                .filter(e -> e.getText().trim().equalsIgnoreCase(nombreProducto))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No esta en el listado: " + nombreProducto));
        clic(enlace);
        return new ProductoPage(driver);
    }
}
