package cr.ac.utn.proyecto.pages;

import cr.ac.utn.proyecto.base.Visual;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

/** Pagina de detalle de producto y opciones configurables. Page Factory. */
public class ProductPage {

    private final WebDriver driver;

    @FindBy(css = "div.product-name h1")
    private WebElement nombreProducto;

    @FindBy(id = "product_attribute_16_5_4")
    private WebElement selectorProcesador;

    @FindBy(id = "product_attribute_16_6_5")
    private WebElement selectorRam;

    @FindBy(id = "product_attribute_16_3_6_18")
    private WebElement radioHdd320Gb;

    @FindBy(css = "input.add-to-cart-button")
    private WebElement botonAgregarAlCarrito;

    @FindBy(id = "bar-notification")
    private WebElement barraNotificacion;

    @FindBy(css = "#bar-notification p.content")
    private WebElement textoNotificacion;

    @FindBy(css = "#bar-notification span.close")
    private WebElement botonCerrarNotificacion;

    @FindBy(css = "#topcartlink a.ico-cart")
    private WebElement enlaceCarrito;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public ProductPage seleccionarProcesador(String textoVisible) {
        new Select(selectorProcesador).selectByVisibleText(textoVisible);
        Visual.pausa();
        return this;
    }

    public ProductPage seleccionarRam(String textoVisible) {
        new Select(selectorRam).selectByVisibleText(textoVisible);
        Visual.pausa();
        return this;
    }

    public ProductPage seleccionarHdd320Gb() {
        radioHdd320Gb.click();
        Visual.pausa();
        return this;
    }

    public ProductPage agregarAlCarrito() {
        botonAgregarAlCarrito.click();
        Visual.pausa();
        return this;
    }

    public String mensajeNotificacion() {
        return textoNotificacion.getText().trim();
    }

    public CartPage irAlCarrito() {
        if (barraNotificacion.isDisplayed()) {
            botonCerrarNotificacion.click();
            Visual.pausa();
        }
        enlaceCarrito.click();
        Visual.pausa();
        return new CartPage(driver);
    }
}