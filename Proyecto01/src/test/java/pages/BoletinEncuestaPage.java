package pages;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BoletinEncuestaPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(id = "newsletter-email")
    private WebElement campoCorreoBoletin;

    @FindBy(id = "newsletter-subscribe-button")
    private WebElement botonSuscribir;

    @FindBy(id = "newsletter-result-block")
    private WebElement mensajeBoletin;

    public BoletinEncuestaPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public BoletinEncuestaPage suscribirseAlBoletin(String correo) {
        campoCorreoBoletin.clear();
        campoCorreoBoletin.sendKeys(correo);
        botonSuscribir.click();
        return this;
    }

    public String obtenerMensajeBoletin() {
        // El mensaje aparece por AJAX: se espera a que sea visible y tenga texto
        wait.until(ExpectedConditions.visibilityOf(mensajeBoletin));
        wait.until(d -> !mensajeBoletin.getText().isEmpty());
        return mensajeBoletin.getText();
    }
}