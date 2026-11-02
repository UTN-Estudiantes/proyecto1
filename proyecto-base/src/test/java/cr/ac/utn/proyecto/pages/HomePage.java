package cr.ac.utn.proyecto.pages;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.base.Visual;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/** Pagina de inicio y barra de navegacion principal de Demo Web Shop. Page Factory. */
public class HomePage {

    private final WebDriver driver;

    @FindBy(css = "a.ico-register")
    private WebElement enlaceRegistro;

    @FindBy(css = "a.ico-login")
    private WebElement enlaceLogin;

    @FindBy(css = "a.ico-logout")
    private WebElement enlaceLogout;

    @FindBy(css = "div.header-links a.account")
    private WebElement enlaceCuentaUsuario;

    @FindBy(id = "small-searchterms")
    private WebElement campoBusqueda;

    @FindBy(css = "input.search-box-button")
    private WebElement botonBuscar;

    @FindBy(css = "#topcartlink a.ico-cart")
    private WebElement enlaceCarrito;

    @FindBy(css = "span.cart-qty")
    private WebElement cantidadCarrito;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public HomePage abrir() {
        driver.get(BaseTest.URL_BASE);
        Visual.pausa();
        return this;
    }

    public RegisterPage irARegistro() {
        enlaceRegistro.click();
        Visual.pausa();
        return new RegisterPage(driver);
    }

    public LoginPage irALogin() {
        enlaceLogin.click();
        Visual.pausa();
        return new LoginPage(driver);
    }

    public HomePage cerrarSesion() {
        enlaceLogout.click();
        Visual.pausa();
        return new HomePage(driver);
    }

    public SearchPage buscarProducto(String termino) {
        campoBusqueda.clear();
        campoBusqueda.sendKeys(termino);
        Visual.pausa();
        botonBuscar.click();
        Visual.pausa();
        return new SearchPage(driver);
    }

    public CategoryPage irACategoria(String nombreCategoria) {
        // Se usa driver.findElement con By.linkText porque el nombre de la categoria
        // llega por parametro y @FindBy no acepta variables.
        WebElement categoria = driver.findElement(By.linkText(nombreCategoria));
        categoria.click();
        Visual.pausa();
        return new CategoryPage(driver);
    }

    public CartPage irAlCarrito() {
        enlaceCarrito.click();
        Visual.pausa();
        return new CartPage(driver);
    }

    public String cuentaLogueada() {
        return enlaceCuentaUsuario.getText().trim();
    }

    public boolean estaVisibleLogout() {
        return enlaceLogout.isDisplayed();
    }

    public boolean estaVisibleLogin() {
        return enlaceLogin.isDisplayed();
    }

    public String cantidadEnInsignia() {
        return cantidadCarrito.getText().trim();
    }
}
