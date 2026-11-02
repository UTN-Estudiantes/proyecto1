package cr.ac.utn.proyecto.pages;

import cr.ac.utn.proyecto.base.Visual;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/** Pagina de resultados de busqueda de productos (/search). Page Factory. */
public class SearchPage {

    private final WebDriver driver;

    // Puede estar vacia cuando no hay coincidencias: por eso es una lista.
    @FindBy(css = "h2.product-title a")
    private List<WebElement> titulosProductos;

    @FindBy(css = "div.search-results")
    private WebElement contenedorResultados;

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public List<String> nombresProductos() {
        List<String> nombres = new ArrayList<>();
        for (WebElement titulo : titulosProductos) {
            nombres.add(titulo.getText().trim());
        }
        return nombres;
    }

    public String mensajeAviso() {
        return contenedorResultados.getText().trim();
    }

    public ProductPage abrirProducto(String nombreProducto) {
        // Se usa driver.findElement con By.linkText porque el nombre del producto
        // se recibe como parametro y @FindBy no acepta parametros.
        WebElement enlace = driver.findElement(By.linkText(nombreProducto));
        enlace.click();
        Visual.pausa();
        return new ProductPage(driver);
    }
}
