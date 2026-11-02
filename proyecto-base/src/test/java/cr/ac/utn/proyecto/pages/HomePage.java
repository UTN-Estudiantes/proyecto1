package cr.ac.utn.proyecto.pages;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.base.Visual;
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

    public String cuentaLogueada() {
        return enlaceCuentaUsuario.getText().trim();
    }

    public boolean estaVisibleLogout() {
        return enlaceLogout.isDisplayed();
    }

    public boolean estaVisibleLogin() {
        return enlaceLogin.isDisplayed();
    }
}