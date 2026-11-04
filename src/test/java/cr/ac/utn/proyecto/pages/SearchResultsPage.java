package cr.ac.utn.proyecto.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import java.util.List;
import java.util.NoSuchElementException;

public class SearchResultsPage {
    private WebDriver driver;

    // Localizadores
    // Lista de todos los productos que salen en el resultado
    @FindBy(css = ".product-title a")
    private List<WebElement> listaTitulosProductos;

    // Mensaje sin resultados
    @FindBy(css = "strong.result")
    private WebElement lblSinResultados;

    // Mensaje de advertencia para busquedas cortas
    @FindBy(css = ".search-results .warning")
    private WebElement lblAdvertenciaTextoCorto;

    // Checkbox búsqueda avanzada
    @FindBy(id = "As")
    private WebElement chkAdvancedSearch;

    // Constructor
    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Metodos Logica
    // Devuelve la cantidad de productos en la busqueda
    public int obtenerCantidadResultados() {
        return listaTitulosProductos.size();
    }

    // Verificacion de la busqueda
    public boolean existeProductoConTexto(String textoBuscado) {
        return listaTitulosProductos.stream()
                .anyMatch(p -> p.getText().toLowerCase().contains(textoBuscado.toLowerCase()));
    }

    // Obtiene mensjes sin resultados
    public String obtenerMensajeSinResultados() {
        return lblSinResultados.getText();
    }

    // Obtiene el mensaje de advertencia por término muy corto
    public String obtenerMensajeAdvertencia() {
        return lblAdvertenciaTextoCorto.getText();
    }

    public ProductPage abrirProducto(String nombreProducto) {
        WebDriverWait espera = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement producto = espera.until(
                ExpectedConditions.elementToBeClickable(By.linkText(nombreProducto)));

        producto.click();
        return new ProductPage(driver);
    }
}
