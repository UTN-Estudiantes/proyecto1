package cr.ac.utn.proyecto.pages;

import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/** Pagina de inicio de sesion (RF-02). Tambien aparece al pagar sin sesion. */
public class LoginPage extends BasePage {

    @FindBy(id = "Email")
    private WebElement campoCorreo;

    @FindBy(id = "Password")
    private WebElement campoClave;

    @FindBy(css = ".login-button")
    private WebElement botonIniciarSesion;

    @FindBy(css = ".validation-summary-errors, .field-validation-error")
    private List<WebElement> mensajesError;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /** Inicio de sesion correcto: devuelve el inicio con la sesion abierta. */
    public InicioPage iniciarSesion(String correo, String clave) {
        escribir(campoCorreo, correo);
        escribir(campoClave, clave);
        clic(botonIniciarSesion);
        return new InicioPage(driver);
    }

    /** Inicio de sesion que debe fallar: se queda en esta pagina. */
    public LoginPage iniciarSesionConError(String correo, String clave) {
        escribir(campoCorreo, correo);
        escribir(campoClave, clave);
        clic(botonIniciarSesion);
        return this;
    }

    /** Une el texto de todos los mensajes de error visibles. */
    public String obtenerMensajeError() {
        espera.until(d -> mensajesError.stream().anyMatch(this::estaVisible));
        return mensajesError.stream()
                .filter(this::estaVisible)
                .map(e -> e.getText().trim())
                .collect(Collectors.joining(" | "));
    }
}
