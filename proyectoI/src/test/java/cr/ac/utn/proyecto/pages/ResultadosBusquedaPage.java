// Responsable: Emily María Alfaro González
package cr.ac.utn.proyecto.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;

// Clase que representa la página de resultados de búsqueda
public class ResultadosBusquedaPage {
    private final WebDriver driver;

    @FindBy(css = "strong.warning")
    private WebElement avisoBusqueda;

    @FindBy(css = "h2.product-title a")
    private List<WebElement> titulosProductos;

    public ResultadosBusquedaPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public List<String> titulosProductos() {
        return titulosProductos.stream().map(WebElement::getText).collect(java.util.stream.Collectors.toList());
    }
    // Método para obtener el texto del aviso de búsqueda
    public String avisoBusqueda() {
        return avisoBusqueda.getText();
    }
}