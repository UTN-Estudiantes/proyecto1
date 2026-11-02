package cr.ac.utn.proyecto.pages;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/** Pagina de resultados de busqueda (/search). Page Factory. */
public class ResultadosBusquedaPage {

    // Puede quedar vacia (busqueda sin resultados): por eso es una lista.
    @FindBy(css = "div.product-item h2.product-title a")
    private List<WebElement> nombres;

    @FindBy(css = "strong.result")
    private WebElement sinResultados;

    @FindBy(css = "strong.warning")
    private WebElement avisoBusqueda;

    public ResultadosBusquedaPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public List<String> nombresProductos() {
        return nombres.stream().map(WebElement::getText).toList();
    }

    public String mensajeSinResultados() {
        return sinResultados.getText().trim();
    }

    public String aviso() {
        return avisoBusqueda.getText().trim();
    }
}