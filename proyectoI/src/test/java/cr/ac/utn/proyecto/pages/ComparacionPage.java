// Responsable: Melanni Michel Díaz Ugalde
package cr.ac.utn.proyecto.pages;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.base.Visual;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

// Comparación de productos: botón Add to compare list de la página del producto y tabla comparativa (/compareproducts).
// El botón de comparar está aquí y no en ProductoPage para no editar la página de otra integrante.
public class ComparacionPage {

    private final WebDriver driver;

    // ---- Página del producto ----
    @FindBy(css = "input.add-to-compare-list-button")
    private WebElement botonAgregarComparar;

    // Barra de notificación que aparece arriba después de agregar
    @FindBy(css = "#bar-notification p.content")
    private WebElement mensajeNotificacion;

    // ---- Tabla comparativa ----
    @FindBy(css = "tr.product-name a")
    private List<WebElement> nombresProductos;

    @FindBy(css = "tr.remove-product .remove-button")
    private List<WebElement> botonesQuitar;

    @FindBy(css = "a.clear-list")
    private WebElement enlaceLimpiarLista;

    // Mensaje que el sitio escribe cuando no hay productos para comparar
    @FindBy(css = "div.no-data")
    private WebElement mensajeListaVacia;

    public ComparacionPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // La ruta del producto (por ejemplo "141-inch-laptop") la entrega la prueba: la
    // página no guarda datos fijos
    public ComparacionPage agregarProductoAComparar(String rutaProducto) {
        driver.get(BaseTest.URL_BASE + rutaProducto);
        botonAgregarComparar.click();
        // El agregado es asíncrono: se espera a que la barra sea visible, que es cuando
        // el producto ya quedó en la lista
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOf(mensajeNotificacion));
        Visual.pausa();
        return this;
    }

    public ComparacionPage abrirComparacion() {
        driver.get(BaseTest.URL_BASE + "compareproducts");
        Visual.pausa();
        return this;
    }

    public List<String> nombresProductos() {
        return nombresProductos.stream().map(WebElement::getText).toList();
    }

    // Las pruebas siempre comparan dos productos, por eso se quita el primero de la
    // tabla
    public ComparacionPage quitarPrimerProducto() {
        // Se guarda el primer producto antes del cambio: un @FindBy se vuelve a buscar
        // cada vez
        // y nunca quedaría obsoleto, así que la espera necesita esta referencia previa
        WebElement primero = nombresProductos.get(0);
        botonesQuitar.get(0).click();
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.stalenessOf(primero));
        Visual.pausa();
        return this;
    }

    public ComparacionPage limpiarLista() {
        enlaceLimpiarLista.click();
        Visual.pausa();
        return this;
    }

    public String mensajeListaVacia() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOf(mensajeListaVacia));
        return mensajeListaVacia.getText();
    }
}
