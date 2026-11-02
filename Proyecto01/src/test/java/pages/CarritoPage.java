package pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CarritoPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(css = ".order-summary-content")
    private WebElement contenidoCarrito;

    @FindBy(name = "updatecart")
    private WebElement botonActualizar;

    @FindBy(css = ".product-name a")
    private WebElement nombreProducto;

    @FindBy(css = ".unit-price .product-unit-price")
    private WebElement precioUnitario;

    @FindBy(css = ".subtotal .product-subtotal")
    private WebElement subtotalProducto;

    @FindBy(css = ".order-total .value-summary strong")
    private WebElement totalOrden;

    public CarritoPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public CarritoPage abrir(String urlSitio) {
        driver.get(urlSitio + "/cart");
        return this;
    }

    public CarritoPage cambiarCantidad(String nuevaCantidad) {
        WebElement campoCantidad = driver.findElement(By.cssSelector(".qty-input"));
        campoCantidad.clear();
        campoCantidad.sendKeys(nuevaCantidad);
        return this;
    }

    public CarritoPage actualizarCarrito() {
        botonActualizar.click();
        wait.until(ExpectedConditions.visibilityOf(contenidoCarrito));
        return this;
    }

    public CarritoPage eliminarPrimerProducto() {
        WebElement checkboxEliminar = driver.findElement(By.name("removefromcart"));
        checkboxEliminar.click();
        botonActualizar.click();
        wait.until(ExpectedConditions.visibilityOf(contenidoCarrito));
        return this;
    }

    public boolean estaVacio() {
        wait.until(ExpectedConditions.visibilityOf(contenidoCarrito));
        return contenidoCarrito.getText().contains("Your Shopping Cart is empty!");
    }

    public String obtenerNombreProducto() {
        wait.until(ExpectedConditions.visibilityOf(nombreProducto));
        return nombreProducto.getText();
    }

    public String obtenerPrecioUnitario() {
        wait.until(ExpectedConditions.visibilityOf(precioUnitario));
        return precioUnitario.getText();
    }

    public String obtenerSubtotalProducto() {
        wait.until(ExpectedConditions.visibilityOf(subtotalProducto));
        return subtotalProducto.getText();
    }

    public String obtenerTotalOrden() {
        wait.until(ExpectedConditions.visibilityOf(totalOrden));
        return totalOrden.getText();
    }

    public String obtenerTextoContenido() {
        wait.until(ExpectedConditions.visibilityOf(contenidoCarrito));
        return contenidoCarrito.getText();
    }
}
