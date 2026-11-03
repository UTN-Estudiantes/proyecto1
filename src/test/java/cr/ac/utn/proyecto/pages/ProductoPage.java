package cr.ac.utn.proyecto.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/** Detalle de un producto: carrito, lista de deseos y comparacion (RF-05, RF-07, RF-08). */
public class ProductoPage extends BasePage {

    @FindBy(css = ".product-essential .product-name h1")
    private WebElement nombre;

    @FindBy(css = ".product-essential .product-price")
    private WebElement precio;

    @FindBy(css = ".product-essential .qty-input")
    private WebElement campoCantidad;

    @FindBy(css = ".product-essential .add-to-cart-button")
    private WebElement botonAgregarCarrito;

    @FindBy(css = ".product-essential .add-to-wishlist-button")
    private WebElement botonAgregarDeseos;

    @FindBy(css = ".product-essential .add-to-compare-list-button")
    private WebElement botonComparar;

    @FindBy(id = "bar-notification")
    private WebElement barraNotificacion;

    @FindBy(css = "#bar-notification .content")
    private WebElement textoNotificacion;

    @FindBy(css = "#bar-notification .close")
    private WebElement cerrarNotificacion;

    private String ultimaNotificacion = "";
    private boolean ultimaNotificacionEsError;

    public ProductoPage(WebDriver driver) {
        super(driver);
    }

    public String obtenerNombre() {
        return visible(nombre).getText().trim();
    }

    public double obtenerPrecio() {
        return aNumero(visible(precio).getText());
    }

    public ProductoPage indicarCantidad(int cantidad) {
        escribir(campoCantidad, String.valueOf(cantidad));
        return this;
    }

    /** Agrega al carrito (AJAX), guarda el aviso que muestra el sitio y lo cierra. */
    public ProductoPage agregarAlCarrito() {
        clic(botonAgregarCarrito);
        leerYCerrarNotificacion();
        return this;
    }

    public ProductoPage agregarAListaDeseos() {
        clic(botonAgregarDeseos);
        leerYCerrarNotificacion();
        return this;
    }

    /** El boton de comparar lleva directamente a la tabla comparativa. */
    public ComparacionPage agregarAComparacion() {
        clic(botonComparar);
        return new ComparacionPage(driver);
    }

    /** Aviso de la barra superior de la ultima accion (exito o error). */
    public String obtenerUltimaNotificacion() {
        return ultimaNotificacion;
    }

    public boolean ultimaNotificacionFueError() {
        return ultimaNotificacionEsError;
    }

    /**
     * La barra de avisos aparece por AJAX y tapa el encabezado; se lee en el momento en que
     * aparece y se cierra para que no bloquee clics posteriores.
     */
    private void leerYCerrarNotificacion() {
        visible(textoNotificacion);
        espera.until(d -> !textoNotificacion.getText().isBlank());
        ultimaNotificacion = textoNotificacion.getText().trim();
        String clases = barraNotificacion.getAttribute("class");
        ultimaNotificacionEsError = clases != null && clases.contains("error");
        clic(cerrarNotificacion);
        espera.until(ExpectedConditions.invisibilityOf(textoNotificacion));
    }
}
