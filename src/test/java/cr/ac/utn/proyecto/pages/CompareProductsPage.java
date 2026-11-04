package cr.ac.utn.proyecto.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CompareProductsPage {
    private WebDriver driver;

    //Localizadores
    @FindBy(css = ".no-data")
    private WebElement lblSinDatos;

    @FindBy(css = "tr.product-name td a")
    private List<WebElement> listaNombresComparados;

    @FindBy(css = "input.remove-button")
    private List<WebElement> listaBotonesQuitar;

    @FindBy(css = "a.clear-list")
    private WebElement lnkLimpiarLista;

    //Construtor
    public CompareProductsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //Metodos Logica
    public boolean estaVacia() {
        return lblSinDatos.getText().contains("You have no items to compare.");
    }

    public int obtenerCantidadProductosComparados() {
        return listaNombresComparados.size();
    }

    public void quitarPrimerProducto() {
        if (!listaBotonesQuitar.isEmpty()) {
            listaBotonesQuitar.get(0).click();
        }
    }

    public void limpiarLista() {
        lnkLimpiarLista.click();
    }

    public String obtenerMensajeVacio() {
        return lblSinDatos.getText();
    }
}
