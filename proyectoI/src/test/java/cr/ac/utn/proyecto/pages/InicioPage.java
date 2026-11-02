package cr.ac.utn.proyecto.pages;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.base.Visual;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/** Pagina principal con el encabezado del sitio. */
public class InicioPage {

    private final WebDriver driver;

    @FindBy(css = "a.ico-register")
    private WebElement enlaceRegistro;

    @FindBy(css = "a.ico-login")
    private WebElement enlaceLogin;

    @FindBy(css = "a.account")
    private WebElement enlaceCuenta;

    @FindBy(css = "a.ico-logout")
    private WebElement enlaceCerrarSesion;

    @FindBy(id = "small-searchterms")
    private WebElement campoBusqueda;

    @FindBy(css = "input.search-box-button")
    private WebElement botonBusqueda;

    public InicioPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this); 
    }

    public InicioPage abrir() {
        driver.get(BaseTest.URL_BASE);
        Visual.pausa();
        return this;
    }

    public RegistroPage irARegistro() {
        enlaceRegistro.click();
        Visual.pausa();
        return new RegistroPage(driver);
    }

    public LoginPage irALogin() {
        enlaceLogin.click();
        Visual.pausa();
        return new LoginPage(driver);
    }

    public ResultadosBusquedaPage buscar(String texto) {
        campoBusqueda.sendKeys(texto);
        botonBusqueda.click();
        Visual.pausa();
        return new ResultadosBusquedaPage(driver);
    }

    public InicioPage cerrarSesion() {
        enlaceCerrarSesion.click();
        Visual.pausa();
        return this;
    }

    public String textoCuenta() {
        return enlaceCuenta.getText();
    }

    public String textoEnlaceRegistro() {
        return enlaceRegistro.getText();
    }

    public String textoEnlaceLogin() {
        return enlaceLogin.getText();
    }
}
