// Responsable: Emily María Alfaro González
package cr.ac.utn.proyecto.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.base.Visual;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class CatalogoPage {
    private final WebDriver driver;

    @FindBy(id = "products-pagesize")
    private WebElement listaDesplegable;

    @FindBy(css = "div.product-item")
    private List<WebElement> listaProductos;

    public CatalogoPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public CatalogoPage abrirCategoria(String ruta) {
        driver.get(BaseTest.URL_BASE + ruta);
        Visual.pausa();
        return this;
    }

    //En este método el primer producto se guarda en una variable local 
    //antes de elegir la opción, porque un @FindBy se vuelve a buscar cada vez, 
    // y se espera a que ese elemento quede obsoleto
    public CatalogoPage mostrarPorPagina(String cantidad) {
        WebElement primerProducto = listaProductos.get(0);
        Select select = new Select(listaDesplegable);
        select.selectByVisibleText(cantidad);
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.stalenessOf(primerProducto));
        return this;
    }

    public int cantidadProductos() {
        return listaProductos.size();
    }
}