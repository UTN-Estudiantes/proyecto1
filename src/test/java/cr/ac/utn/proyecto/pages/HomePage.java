package cr.ac.utn.proyecto.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class HomePage {
    protected WebDriver driver;

    // Localizadores
    @FindBy(linkText = "Register")
    private WebElement lnkRegister;

    @FindBy(linkText = "Log in")
    private WebElement lnkLogin;

    @FindBy(linkText = "Log out")
    private WebElement lnkLogout;

    @FindBy(className = "account")
    private WebElement lnkAccount;

    @FindBy(id = "small-searchterms")
    private WebElement txtSearch;

    @FindBy(css = "input.search-box-button")
    private WebElement btnSearch;

    @FindBy(css = "#topcartlink .cart-label")
    private WebElement lnkCart;

    @FindBy(css = "#topcartlink .cart-qty")
    private WebElement lblCartQty;

    @FindBy(linkText = "Log in")
    private List<WebElement> enlacesLogin;

    @FindBy(linkText = "Log out")
    private List<WebElement> enlacesLogout;

    @FindBy(id = "newsletter-email")
    private WebElement txtNewsletterEmail;

    @FindBy(id = "newsletter-subscribe-button")
    private WebElement btnNewsletterSuscribir;

    @FindBy(id = "newsletter-result-block")
    private WebElement lblNewsletterResultado;

    // Contructor
    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Metodos Logica
    public RegisterPage irRegistro() {
        lnkRegister.click();
        return new RegisterPage(driver);
    }

    public LoginPage irLogin() {
        lnkLogin.click();
        return new LoginPage(driver);
    }

    public HomePage cerrarSesion() {
        lnkLogout.click();
        return this;
    }

    public String obtenerTextoCuenta() {
        return lnkAccount.getText();
    }

    public SearchResultsPage buscar(String texto) {
        txtSearch.clear();
        txtSearch.sendKeys(texto);
        btnSearch.click();
        return new SearchResultsPage(driver);
    }

    public boolean estaComoVisitante() {
        return !enlacesLogin.isEmpty() && enlacesLogout.isEmpty();
    }

    public CartPage irCarrito() {
        lnkCart.click();
        return new CartPage(driver);
    }

    public AccountPage irCuenta() {
        lnkAccount.click();
        return new AccountPage(driver);
    }

    // Suscribe un correo al boletin; el resultado llega por AJAX
    public HomePage suscribirBoletin(String email) {
        txtNewsletterEmail.clear();
        txtNewsletterEmail.sendKeys(email);
        btnNewsletterSuscribir.click();
        return this;
    }

    // Espera a que el bloque de resultado tenga texto y lo devuelve
    public String obtenerMensajeBoletin() {
        WebDriverWait espera = new WebDriverWait(driver, Duration.ofSeconds(10));
        espera.until(d -> !lblNewsletterResultado.getAttribute("textContent").trim().isEmpty());
        return lblNewsletterResultado.getAttribute("textContent").trim();
    }

}
