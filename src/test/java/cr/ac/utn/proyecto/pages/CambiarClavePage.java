package cr.ac.utn.proyecto.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/** Cambio de contrasena del usuario (RF-10). */
public class CambiarClavePage extends BasePage {

    @FindBy(id = "OldPassword")
    private WebElement campoClaveActual;

    @FindBy(id = "NewPassword")
    private WebElement campoClaveNueva;

    @FindBy(id = "ConfirmNewPassword")
    private WebElement campoConfirmacion;

    @FindBy(css = ".change-password-button")
    private WebElement botonCambiar;

    @FindBy(css = ".page-body .result")
    private WebElement mensajeResultado;

    public CambiarClavePage(WebDriver driver) {
        super(driver);
    }

    public CambiarClavePage cambiarClave(String actual, String nueva) {
        escribir(campoClaveActual, actual);
        escribir(campoClaveNueva, nueva);
        escribir(campoConfirmacion, nueva);
        clic(botonCambiar);
        return this;
    }

    public String obtenerMensajeResultado() {
        return visible(mensajeResultado).getText().trim();
    }
}
