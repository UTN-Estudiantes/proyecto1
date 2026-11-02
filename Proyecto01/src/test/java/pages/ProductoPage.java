package pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductoPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(id = "bar-notification")
    private WebElement barraNotificacion;

    @FindBy(css = ".cart-qty")
    private WebElement cantidadCarritoEncabezado;

    @FindBy(css = ".wishlist-qty")
    private WebElement cantidadWishlistEncabezado;

    @FindBy(id = "product_attribute_16_5_4")
    private WebElement selectProcesador;

    @FindBy(id = "product_attribute_16_6_5")
    private WebElement selectRam;

    public ProductoPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public ProductoPage abrirUrl(String url) {
        driver.get(url);
        return this;
    }

    public ProductoPage ingresarCantidad(String idCampoCantidad, String cantidad) {
        WebElement campoCantidad = driver.findElement(By.id(idCampoCantidad));
        campoCantidad.clear();
        campoCantidad.sendKeys(cantidad);
        return this;
    }

    public ProductoPage agregarAlCarrito(String idBoton) {
        WebElement boton = driver.findElement(By.id(idBoton));
        boton.click();
        wait.until(ExpectedConditions.visibilityOf(barraNotificacion));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {}
        return this;
    }

    public ProductoPage agregarAWishlist(String idBoton) {
        WebElement boton = driver.findElement(By.id(idBoton));
        boton.click();
        wait.until(ExpectedConditions.visibilityOf(barraNotificacion));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {}
        return this;
    }

    public ProductoPage seleccionarProcesador(String textoOValor) {
        Select select = new Select(selectProcesador);
        try {
            select.selectByValue(textoOValor);
        } catch (Exception e1) {
            select.selectByVisibleText(textoOValor);
        }
        return this;
    }

    public ProductoPage seleccionarRam(String textoOValor) {
        Select select = new Select(selectRam);
        try {
            select.selectByValue(textoOValor);
        } catch (Exception e1) {
            select.selectByVisibleText(textoOValor);
        }
        return this;
    }

    public ProductoPage seleccionarHdd(String idRadio) {
        WebElement radio = driver.findElement(By.id(idRadio));
        if (!radio.isSelected()) {
            radio.click();
        }
        return this;
    }

    public ProductoPage seleccionarSistemaOperativo(String idRadio) {
        WebElement radio = driver.findElement(By.id(idRadio));
        if (!radio.isSelected()) {
            radio.click();
        }
        return this;
    }

    public ProductoPage configurarSoftware(String idCheckbox, boolean marcar) {
        WebElement checkbox = driver.findElement(By.id(idCheckbox));
        if (checkbox.isSelected() != marcar) {
            checkbox.click();
        }
        return this;
    }

    public String obtenerTextoNotificacion() {
        wait.until(ExpectedConditions.visibilityOf(barraNotificacion));
        return barraNotificacion.getText();
    }

    public String obtenerCantidadCarritoEncabezado() {
        return cantidadCarritoEncabezado.getText();
    }

    public String obtenerCantidadWishlistEncabezado() {
        return cantidadWishlistEncabezado.getText();
    }
}
