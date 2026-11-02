package pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WishlistPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(css = ".wishlist-content")
    private WebElement contenidoWishlist;

    @FindBy(css = ".product a, .product-name a, td.product")
    private WebElement nombreProducto;

    @FindBy(css = "input[name='updatecart'], input[name='updatewishlist'], input[value*='Update']")
    private WebElement botonActualizar;

    @FindBy(css = ".wishlist-add-to-cart-button")
    private WebElement botonAgregarAlCarrito;

    @FindBy(css = ".wishlist-qty")
    private WebElement cantidadWishlistEncabezado;

    public WishlistPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public WishlistPage abrir(String urlSitio) {
        driver.get(urlSitio + "/wishlist");
        return this;
    }

    public boolean estaVacia() {
        wait.until(ExpectedConditions.visibilityOf(contenidoWishlist));
        return contenidoWishlist.getText().contains("The wishlist is empty!");
    }

    public String obtenerNombreProducto() {
        wait.until(ExpectedConditions.visibilityOf(nombreProducto));
        return nombreProducto.getText();
    }

    public WishlistPage eliminarPrimerProducto() {
        WebElement checkboxEliminar = driver.findElement(By.cssSelector("input[name='removefromcart'], input[name='removefromwishlist']"));
        checkboxEliminar.click();
        botonActualizar.click();
        wait.until(ExpectedConditions.visibilityOf(contenidoWishlist));
        return this;
    }

    public WishlistPage moverAlCarrito() {
        WebElement checkboxAgregar = driver.findElement(By.name("addtocart"));
        checkboxAgregar.click();
        botonAgregarAlCarrito.click();
        return this;
    }

    public String obtenerCantidadEncabezado() {
        return cantidadWishlistEncabezado.getText();
    }

    public String obtenerTextoContenido() {
        wait.until(ExpectedConditions.visibilityOf(contenidoWishlist));
        return contenidoWishlist.getText();
    }
}
