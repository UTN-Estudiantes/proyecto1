package cr.ac.utn.proyecto.pages;

import cr.ac.utn.proyecto.base.Visual;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

/**
 * Producto configurable "Build your own computer" (RF-06).
 * Hereda de ProductoPage las acciones de carrito y avisos.
 */
public class ComputadoraPersonalizadaPage extends ProductoPage {

    @FindBy(id = "product_attribute_16_5_4")
    private WebElement listaProcesador;

    @FindBy(id = "product_attribute_16_6_5")
    private WebElement listaRam;

    @FindBy(name = "product_attribute_16_3_6")
    private List<WebElement> opcionesDisco;

    @FindBy(name = "product_attribute_16_4_7")
    private List<WebElement> opcionesSistemaOperativo;

    public ComputadoraPersonalizadaPage(WebDriver driver) {
        super(driver);
    }

    /** Elige el procesador cuyo texto contiene el valor dado (ej. "2.5 GHz"). */
    public ComputadoraPersonalizadaPage elegirProcesador(String textoParcial) {
        elegirOpcionQueContiene(listaProcesador, textoParcial);
        return this;
    }

    /** Elige la memoria RAM cuyo texto contiene el valor dado (ej. "8GB"). */
    public ComputadoraPersonalizadaPage elegirRam(String textoParcial) {
        elegirOpcionQueContiene(listaRam, textoParcial);
        return this;
    }

    /** Elige el disco duro por el texto de su etiqueta (ej. "320 GB" o "400 GB"). */
    public ComputadoraPersonalizadaPage elegirDisco(String textoEtiqueta) {
        marcarRadioPorEtiqueta(opcionesDisco, textoEtiqueta);
        return this;
    }

    /** Elige el sistema operativo por el texto de su etiqueta (ej. "Home"). */
    public ComputadoraPersonalizadaPage elegirSistemaOperativo(String textoEtiqueta) {
        marcarRadioPorEtiqueta(opcionesSistemaOperativo, textoEtiqueta);
        return this;
    }

    private void elegirOpcionQueContiene(WebElement lista, String textoParcial) {
        Select select = new Select(visible(lista));
        WebElement opcion = select.getOptions().stream()
                .filter(o -> sinEspacios(o.getText()).contains(sinEspacios(textoParcial)))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No existe la opcion: " + textoParcial));
        select.selectByVisibleText(opcion.getText());
        Visual.pausa();
    }

    /**
     * Cada radio tiene una etiqueta <label for="id-del-radio">. Como el id cambia segun la opcion,
     * se busca la etiqueta con driver.findElement + By (localizador con parametro).
     */
    private void marcarRadioPorEtiqueta(List<WebElement> radios, String textoEtiqueta) {
        for (WebElement radio : radios) {
            String id = radio.getAttribute("id");
            WebElement etiqueta = driver.findElement(By.cssSelector("label[for='" + id + "']"));
            if (sinEspacios(etiqueta.getText()).contains(sinEspacios(textoEtiqueta))) {
                clic(radio);
                return;
            }
        }
        throw new IllegalArgumentException("No existe la opcion: " + textoEtiqueta);
    }

    /** Compara sin espacios: el sitio escribe "8GB" en unas opciones y "320 GB" en otras. */
    private static String sinEspacios(String texto) {
        return texto.replace(" ", "").toLowerCase();
    }
}
