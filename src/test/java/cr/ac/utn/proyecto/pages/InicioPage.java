package cr.ac.utn.proyecto.pages;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.base.Visual;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Pagina de inicio y encabezado del sitio. Page Factory.
 */
public class InicioPage {

    private final WebDriver driver;

    @FindBy(css = "a.ico-register")
    private WebElement enlaceRegistro;

    @FindBy(css = "a.ico-login")
    private WebElement enlaceLogin;

    // Solo existe con la sesion iniciada
    @FindBy(css = "a.ico-logout")
    private List<WebElement> enlaceSalir;

    // Solo existe con la sesion iniciada
    // Se limita al encabezado porque el pie de pagina tambien tiene un a.account ("My account").
    @FindBy(css = "div.header-links a.account")
    private List<WebElement> enlaceCuenta;

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

    public InicioPage cerrarSesion() {
        enlaceSalir.get(0).click();
        Visual.pausa();
        return this;
    }

    /** Correo que sale arriba; vacio si no hay sesion. */
    public String correoEnEncabezado() {
        return enlaceCuenta.isEmpty() ? "" : enlaceCuenta.get(0).getText().trim();
    }

    public boolean haySesionIniciada() {
        return !enlaceSalir.isEmpty();
    }
}
