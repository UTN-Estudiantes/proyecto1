package cr.ac.utn.proyecto.pages;

import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/** Comparacion de productos (RF-08). */
public class ComparacionPage extends BasePage {

    @FindBy(css = ".compare-products-table")
    private List<WebElement> tabla;

    @FindBy(css = ".compare-products-table .product-name a")
    private List<WebElement> nombres;

    @FindBy(css = ".compare-products-table .remove-button")
    private List<WebElement> botonesQuitar;

    @FindBy(css = ".clear-list")
    private WebElement enlaceLimpiar;

    @FindBy(css = ".page-body")
    private WebElement cuerpo;

    public ComparacionPage(WebDriver driver) {
        super(driver);
    }

    public boolean seMuestraTabla() {
        return !tabla.isEmpty() && tabla.get(0).isDisplayed();
    }

    public List<String> obtenerNombres() {
        return nombres.stream().map(e -> e.getText().trim()).collect(Collectors.toList());
    }

    /** Vuelve a un producto para agregar otro a la comparacion. */
    public ProductoPage abrirProducto(String slugProducto) {
        driver.get(URL_BASE + slugProducto);
        return new ProductoPage(driver);
    }

    public ComparacionPage quitarPrimerProducto() {
        clic(botonesQuitar.get(0));
        return new ComparacionPage(driver);
    }

    public ComparacionPage limpiarLista() {
        clic(enlaceLimpiar);
        return new ComparacionPage(driver);
    }

    public String obtenerContenido() {
        return visible(cuerpo).getText().trim();
    }
}
