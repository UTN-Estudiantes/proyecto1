package cr.ac.utn.proyecto.pages;

import cr.ac.utn.proyecto.base.BaseTest;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Página de inicio de Demo Web Shop. Representa también el encabezado, que se repite en todo el sitio.
 */
public class PaginaInicio {

    private final WebDriver driver;
    private final WebDriverWait espera;

    @FindBy(css = "div.header-links a.ico-register")
    private WebElement enlaceRegistro;

    @FindBy(css = "div.header-links a.ico-login")
    private WebElement enlaceInicioSesion;

    @FindBy(css = "div.header-links a.ico-logout")
    private WebElement enlaceCerrarSesion;

    @FindBy(css = "div.header-links a.account")
    private WebElement enlaceCuenta;

    @FindBy(css = "div.header-links a.ico-logout")
    private List<WebElement> opcionesCerrarSesion;

    @FindBy(id = "small-searchterms")
    private WebElement campoBusqueda;

    @FindBy(css = "div.search-box input.search-box-button")
    private WebElement botonBuscar;

    @FindBy(css = "ul.top-menu > li > a")
    private List<WebElement> enlacesCategorias;

    @FindBy(css = "div.footer a[href='/search']")
    private WebElement enlaceBusquedaAvanzada;

    @FindBy(css = "#topcartlink a.ico-cart")
    private WebElement enlaceCarrito;

    @FindBy(css = "div.header-links a.ico-wishlist")
    private WebElement enlaceListaDeseos;

    public PaginaInicio(WebDriver driver) {
        this.driver = driver;
        this.espera = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public PaginaInicio abrir() {
        driver.get(BaseTest.URL_BASE);
        return this;
    }

    public PaginaRegistro irARegistro() {
        enlaceRegistro.click();
        espera.until(ExpectedConditions.urlContains("/register"));
        return new PaginaRegistro(driver);
    }

    public PaginaInicioSesion irAInicioSesion() {
        enlaceInicioSesion.click();
        espera.until(ExpectedConditions.urlContains("/login"));
        return new PaginaInicioSesion(driver);
    }

    public PaginaBusqueda buscar(String texto) {
        campoBusqueda.clear();
        campoBusqueda.sendKeys(texto);
        botonBuscar.click();
        espera.until(ExpectedConditions.urlContains("/search"));
        return new PaginaBusqueda(driver);
    }

    public PaginaCarrito irAlCarrito() {
        enlaceCarrito.click();
        espera.until(ExpectedConditions.urlContains("/cart"));
        return new PaginaCarrito(driver);
    }

    public PaginaListaDeseos irALaListaDeDeseos() {
        enlaceListaDeseos.click();
        espera.until(ExpectedConditions.urlContains("/wishlist"));
        return new PaginaListaDeseos(driver);
    }

    public PaginaCuenta irAMiCuenta() {
        enlaceCuenta.click();
        espera.until(ExpectedConditions.urlContains("/customer/info"));
        return new PaginaCuenta(driver);
    }

    public PaginaBusquedaAvanzada irABusquedaAvanzada() {
        enlaceBusquedaAvanzada.click();
        espera.until(ExpectedConditions.urlContains("/search"));
        return new PaginaBusquedaAvanzada(driver);
    }

    public PaginaCatalogo irACategoria(String nombre) {
        String urlAnterior = driver.getCurrentUrl();
        for (WebElement enlace : enlacesCategorias) {
            if (enlace.getText().trim().equalsIgnoreCase(nombre)) {
                enlace.click();
                espera.until(d -> !d.getCurrentUrl().equals(urlAnterior));
                return new PaginaCatalogo(driver);
            }
        }
        throw new IllegalArgumentException("No existe la categoría en el menú: " + nombre);
    }

    public PaginaInicio cerrarSesion() {
        enlaceCerrarSesion.click();
        espera.until(ExpectedConditions.visibilityOf(enlaceInicioSesion));
        return this;
    }

    public boolean haySesionIniciada() {
        return !opcionesCerrarSesion.isEmpty();
    }

    public boolean muestraOpcionesDeVisitante() {
        return enlaceRegistro.isDisplayed() && enlaceInicioSesion.isDisplayed();
    }

    public String obtenerCuentaMostrada() {
        espera.until(ExpectedConditions.visibilityOf(enlaceCuenta));
        return enlaceCuenta.getText();
    }
}
