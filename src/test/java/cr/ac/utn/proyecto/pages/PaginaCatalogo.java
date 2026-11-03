package cr.ac.utn.proyecto.pages;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Listado de una categoría: subcategorías, orden, productos por página, vista y agregar al carrito desde el listado.
 */
public class PaginaCatalogo {

    public enum Orden {
        POSICION("Position"),
        NOMBRE_A_Z("Name: A to Z"),
        NOMBRE_Z_A("Name: Z to A"),
        PRECIO_MENOR_A_MAYOR("Price: Low to High"),
        PRECIO_MAYOR_A_MENOR("Price: High to Low");

        private final String textoVisible;

        Orden(String textoVisible) {
            this.textoVisible = textoVisible;
        }
    }

    public enum Vista {
        CUADRICULA("Grid"),
        LISTA("List");

        private final String textoVisible;

        Vista(String textoVisible) {
            this.textoVisible = textoVisible;
        }
    }

    private final WebDriver driver;
    private final WebDriverWait espera;

    @FindBy(css = "div.page-title h1")
    private WebElement titulo;

    @FindBy(id = "products-orderby")
    private WebElement listaOrden;

    @FindBy(id = "products-pagesize")
    private WebElement listaProductosPorPagina;

    @FindBy(id = "products-viewmode")
    private WebElement listaVista;

    @FindBy(css = "div.sub-category-item h2.title a")
    private List<WebElement> subcategorias;

    @FindBy(css = "div.product-item")
    private List<WebElement> productos;

    @FindBy(css = "div.product-item h2.product-title a")
    private List<WebElement> nombresProductos;

    @FindBy(css = "div.product-item span.price.actual-price")
    private List<WebElement> preciosProductos;

    @FindBy(css = "div.product-item input.product-box-add-to-cart-button")
    private List<WebElement> botonesAgregarAlCarrito;

    @FindBy(css = "span.cart-qty")
    private WebElement indicadorCarrito;

    @FindBy(id = "bar-notification")
    private WebElement notificacion;

    @FindBy(css = "#topcartlink a.ico-cart")
    private WebElement enlaceCarrito;

    public PaginaCatalogo(WebDriver driver) {
        this.driver = driver;
        this.espera = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public String obtenerTitulo() {
        return titulo.getText().trim();
    }

    public PaginaCatalogo abrirSubcategoria(String nombre) {
        String urlAnterior = driver.getCurrentUrl();
        for (WebElement subcategoria : subcategorias) {
            if (subcategoria.getText().trim().equalsIgnoreCase(nombre)) {
                subcategoria.click();
                espera.until(d -> !d.getCurrentUrl().equals(urlAnterior));
                return new PaginaCatalogo(driver);
            }
        }
        throw new IllegalArgumentException("No existe la subcategoría: " + nombre);
    }

    public PaginaCatalogo ordenarPor(Orden orden) {
        return cambiarOpcion(listaOrden, orden.textoVisible);
    }

    public PaginaCatalogo mostrarProductosPorPagina(int cantidad) {
        return cambiarOpcion(listaProductosPorPagina, String.valueOf(cantidad));
    }

    public PaginaCatalogo cambiarVista(Vista vista) {
        return cambiarOpcion(listaVista, vista.textoVisible);
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

    public int obtenerCantidadDeProductos() {
        return productos.size();
    }

    public PaginaCatalogo agregarAlCarritoElPrimerProducto() {
        botonesAgregarAlCarrito.get(0).click();
        espera.until(ExpectedConditions.visibilityOf(notificacion));
        return this;
    }

    public PaginaCatalogo agregarAlCarritoElProducto(String nombre) {
        for (WebElement producto : productos) {
            // Se usa findElement(By) porque el nombre y el botón se buscan dentro de cada producto del listado, y @FindBy no permite buscar dentro de un elemento variable.
            String nombreProducto = producto.findElement(By.cssSelector("h2.product-title a")).getText().trim();
            if (nombreProducto.equalsIgnoreCase(nombre)) {
                // Mismo motivo: el botón pertenece al producto cuyo nombre coincidió.
                producto.findElement(By.cssSelector("input.product-box-add-to-cart-button")).click();
                espera.until(ExpectedConditions.visibilityOf(notificacion));
                return this;
            }
        }
        throw new IllegalArgumentException("No existe el producto en el listado: " + nombre);
    }

    public String obtenerMensajeDeNotificacion() {
        espera.until(ExpectedConditions.visibilityOf(notificacion));
        return notificacion.getText().trim();
    }

    public String obtenerIndicadorCarrito() {
        return indicadorCarrito.getText().trim();
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
        throw new IllegalArgumentException("No existe el producto en el listado: " + nombre);
    }

    public PaginaCarrito irAlCarrito() {
        enlaceCarrito.click();
        espera.until(ExpectedConditions.urlContains("/cart"));
        return new PaginaCarrito(driver);
    }

    private PaginaCatalogo cambiarOpcion(WebElement lista, String textoVisible) {
        String urlAnterior = driver.getCurrentUrl();
        new Select(lista).selectByVisibleText(textoVisible);
        espera.until(d -> !d.getCurrentUrl().equals(urlAnterior));
        return this;
    }
}
