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

    // Lista para consultar la ausencia del enlace sin que falle la búsqueda cuando no existe.
    @FindBy(css = "div.header-links a.ico-logout")
    private List<WebElement> opcionesCerrarSesion;

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
