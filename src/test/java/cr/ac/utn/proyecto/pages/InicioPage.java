package cr.ac.utn.proyecto.pages;

import cr.ac.utn.proyecto.base.Visual;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/** Pagina de inicio: punto de entrada, boletin, encuesta y menu de categorias. */
public class InicioPage extends BasePage {

    @FindBy(id = "newsletter-email")
    private WebElement campoCorreoBoletin;

    @FindBy(id = "newsletter-subscribe-button")
    private WebElement botonSuscribirse;

    @FindBy(id = "newsletter-result-block")
    private WebElement resultadoBoletin;

    @FindBy(css = ".block-poll input[type='radio']")
    private WebElement primeraRespuestaEncuesta;

    @FindBy(css = ".block-poll .vote-poll-button")
    private WebElement botonVotar;

    @FindBy(css = ".block-poll .poll-vote-error")
    private WebElement errorEncuesta;

    @FindBy(css = ".block-poll .poll-results")
    private WebElement resultadosEncuesta;

    public InicioPage(WebDriver driver) {
        super(driver);
    }

    public InicioPage abrir() {
        driver.get(URL_BASE);
        Visual.pausa();
        return new InicioPage(driver);
    }

    /**
     * Navega a una categoria del menu superior, por ejemplo "books" o "computers".
     * Se usa driver.findElement + By porque el localizador depende del parametro (la categoria)
     * y @FindBy solo acepta valores fijos.
     */
    public CategoriaPage irACategoria(String slugCategoria) {
        WebElement enlace = driver.findElement(By.cssSelector(".top-menu a[href='/" + slugCategoria + "']"));
        clic(enlace);
        return new CategoriaPage(driver);
    }

    /** Abre directamente la pagina de un producto por su direccion amigable (ej. "141-inch-laptop"). */
    public ProductoPage abrirProducto(String slugProducto) {
        driver.get(URL_BASE + slugProducto);
        Visual.pausa();
        return new ProductoPage(driver);
    }

    public ComputadoraPersonalizadaPage abrirComputadoraPersonalizada() {
        driver.get(URL_BASE + "build-your-own-computer");
        Visual.pausa();
        return new ComputadoraPersonalizadaPage(driver);
    }

    public InicioPage suscribirseAlBoletin(String correo) {
        escribir(campoCorreoBoletin, correo);
        clic(botonSuscribirse);
        return this;
    }

    /** El aviso del boletin se carga por AJAX: se espera a que tenga texto. */
    public String obtenerMensajeBoletin() {
        espera.until(d -> estaVisible(resultadoBoletin) && !resultadoBoletin.getText().isBlank());
        return resultadoBoletin.getText().trim();
    }

    public InicioPage votarEnEncuesta() {
        clic(primeraRespuestaEncuesta);
        clic(botonVotar);
        return this;
    }

    /** Devuelve el error de la encuesta (por ejemplo, cuando un visitante intenta votar). */
    public String obtenerErrorEncuesta() {
        espera.until(d -> estaVisible(errorEncuesta) && !errorEncuesta.getText().isBlank());
        return errorEncuesta.getText().trim();
    }

    public boolean seMuestranResultadosEncuesta() {
        try {
            espera.until(d -> estaVisible(resultadosEncuesta));
            return true;
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }
}
