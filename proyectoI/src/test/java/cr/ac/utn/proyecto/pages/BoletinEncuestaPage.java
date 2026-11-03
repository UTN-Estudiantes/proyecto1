// Responsable: Melanni Michel Díaz Ugalde
package cr.ac.utn.proyecto.pages;

import cr.ac.utn.proyecto.base.Visual;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

// Bloque Newsletter de la columna izquierda (aparece en todas las páginas del sitio).
public class BoletinEncuestaPage {

    private final WebDriver driver;

    @FindBy(id = "newsletter-email")
    private WebElement campoCorreo;

    @FindBy(id = "newsletter-subscribe-button")
    private WebElement botonSuscribir;

    // El contenedor existe vacío y el sitio escribe el aviso después de pulsar
    // Subscribe
    @FindBy(id = "newsletter-result-block")
    private WebElement bloqueResultado;

    public BoletinEncuestaPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // El correo lo entrega la prueba: la página no guarda datos fijos
    public BoletinEncuestaPage escribirCorreoBoletin(String correo) {
        campoCorreo.clear();
        campoCorreo.sendKeys(correo);
        return this;
    }

    public BoletinEncuestaPage suscribirse() {
        botonSuscribir.click();
        Visual.pausa();
        return this;
    }

    // El aviso es dinámico: se espera a que el contenedor tenga texto antes de
    // leerlo
    public String mensajeBoletin() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(d -> !bloqueResultado.getText().isBlank());
        return bloqueResultado.getText();
    }
}
