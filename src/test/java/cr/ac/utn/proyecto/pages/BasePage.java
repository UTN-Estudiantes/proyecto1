package cr.ac.utn.proyecto.pages;

import cr.ac.utn.proyecto.base.Visual;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Clase padre de todas las paginas.
 * - Inicializa los @FindBy con PageFactory (tambien los heredados del encabezado).
 * - Contiene el encabezado del sitio, que aparece en todas las paginas.
 * - Ofrece esperas explicitas reutilizables (no se usa Thread.sleep).
 * No contiene aserciones ni datos de prueba.
 */
public abstract class BasePage {

    protected static final String URL_BASE = "https://demowebshop.tricentis.com/";

    protected final WebDriver driver;
    protected final WebDriverWait espera;

    // ---------- Encabezado (comun a todas las paginas) ----------
    @FindBy(css = ".header-links .ico-register")
    private WebElement enlaceRegistro;

    @FindBy(css = ".header-links .ico-login")
    private WebElement enlaceLogin;

    @FindBy(css = ".header-links .ico-logout")
    private WebElement enlaceLogout;

    @FindBy(css = ".header-links .account")
    private WebElement enlaceCuenta;

    @FindBy(css = ".header-links .ico-cart")
    private WebElement enlaceCarrito;

    @FindBy(css = ".header-links .cart-qty")
    private WebElement cantidadCarrito;

    @FindBy(css = ".header-links .ico-wishlist")
    private WebElement enlaceListaDeseos;

    @FindBy(id = "small-searchterms")
    private WebElement cajaBusqueda;

    @FindBy(css = ".search-box .search-box-button")
    private WebElement botonBuscar;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.espera = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    // ---------- Utilidades de espera (protegidas: solo para las paginas) ----------
    protected WebElement visible(WebElement elemento) {
        return espera.until(ExpectedConditions.visibilityOf(elemento));
    }

    protected WebElement clickeable(WebElement elemento) {
        return espera.until(ExpectedConditions.elementToBeClickable(elemento));
    }

    protected void clic(WebElement elemento) {
        clickeable(elemento).click();
        Visual.pausa();
    }

    protected void escribir(WebElement campo, String texto) {
        WebElement c = visible(campo);
        c.clear();
        c.sendKeys(texto);
        Visual.pausa();
    }

    protected boolean estaVisible(WebElement elemento) {
        try {
            return elemento.isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException | org.openqa.selenium.StaleElementReferenceException e) {
            return false;
        }
    }

    /** Convierte un texto de precio del sitio ("1590.00", "$1,590.00") en numero. */
    protected static double aNumero(String texto) {
        String limpio = texto.replaceAll("[^0-9.]", "");
        return limpio.isEmpty() ? 0 : Double.parseDouble(limpio);
    }

    // ---------- Acciones del encabezado (navegacion de negocio) ----------
    public RegistroPage irARegistro() {
        clic(enlaceRegistro);
        return new RegistroPage(driver);
    }

    public LoginPage irALogin() {
        clic(enlaceLogin);
        return new LoginPage(driver);
    }

    public InicioPage cerrarSesion() {
        clic(enlaceLogout);
        return new InicioPage(driver);
    }

    public CuentaPage irAMiCuenta() {
        clic(enlaceCuenta);
        return new CuentaPage(driver);
    }

    public CarritoPage irAlCarrito() {
        clic(enlaceCarrito);
        return new CarritoPage(driver);
    }

    public ListaDeseosPage irAListaDeseos() {
        clic(enlaceListaDeseos);
        return new ListaDeseosPage(driver);
    }

    public ResultadosBusquedaPage buscar(String texto) {
        escribir(cajaBusqueda, texto);
        clic(botonBuscar);
        return new ResultadosBusquedaPage(driver);
    }

    // ---------- Consultas del encabezado (las aserciones se hacen en las pruebas) ----------
    public boolean haySesionIniciada() {
        return estaVisible(enlaceLogout);
    }

    public boolean esVisitante() {
        return estaVisible(enlaceLogin) && estaVisible(enlaceRegistro);
    }

    public String obtenerCuentaEnEncabezado() {
        return visible(enlaceCuenta).getText().trim();
    }

    /** Lee el numero que muestra el indicador del carrito, por ejemplo "(2)" devuelve 2. */
    public int obtenerCantidadEnCarrito() {
        return (int) aNumero(visible(cantidadCarrito).getText());
    }

    /** Espera a que el indicador del carrito muestre la cantidad indicada (se actualiza por AJAX). */
    public int esperarCantidadEnCarrito(int cantidadEsperada) {
        try {
            espera.until(ExpectedConditions.textToBePresentInElement(cantidadCarrito, "(" + cantidadEsperada + ")"));
        } catch (org.openqa.selenium.TimeoutException e) {
            // Se devuelve el valor real para que la asercion de la prueba muestre la diferencia.
        }
        return obtenerCantidadEnCarrito();
    }

    public String obtenerTitulo() {
        return driver.getTitle();
    }

    public String obtenerUrl() {
        return driver.getCurrentUrl();
    }
}
