package cr.ac.utn.proyecto.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegisterResultPage {
    private WebDriver driver;

    @FindBy(css = ".result")
    private WebElement lblResultado;

    @FindBy(css = "input.register-continue-button")
    private WebElement btnContinue;

    public RegisterResultPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String obtenerMensajeResultado() {
        return lblResultado.getText();
    }
    public HomePage clickContinuar() {
        btnContinue.click();
        return new HomePage(driver);
    }
}
