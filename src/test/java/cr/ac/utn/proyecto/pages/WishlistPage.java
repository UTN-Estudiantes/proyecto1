package cr.ac.utn.proyecto.pages;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WishlistPage {
    private WebDriver driver;

    // --- Localizadores ---
    @FindBy(css = ".wishlist-content")
    private WebElement lblWishlistVacia;

    @FindBy(css = "tr.cart-item-row td.product a")
    private List<WebElement> listaNombresWishlist;

    @FindBy(name = "addtocart")
    private List<WebElement> listaCheckboxesMoverAlCarrito;

    @FindBy(name = "addtocartbutton")
    private WebElement btnMoverAlCarrito;

    @FindBy(name = "removefromcart")
    private List<WebElement> listaCheckboxesEliminar;

    @FindBy(name = "updatecart")
    private WebElement btnActualizarWishlist;

    //Constructor
    public WishlistPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //Metodos Logica
    public boolean estaVacia() {
        return lblWishlistVacia.getText().contains("The wishlist is empty!");
    }
    public int obtenerCantidadProductos() {
        return listaNombresWishlist.size();
    }

    // Marca el primer producto de la wishlist y espera a que aparezca en el carrito.
    public CartPage moverPrimerProductoCarrito() {
        if (listaCheckboxesMoverAlCarrito.isEmpty()) {
            throw new NoSuchElementException("La wishlist no contiene productos para mover al carrito");
        }

        listaCheckboxesMoverAlCarrito.get(0).click();
        btnMoverAlCarrito.click();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> d.getCurrentUrl().contains("/cart"));
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> !d.findElements(By.cssSelector("tr.cart-item-row")).isEmpty());

        return new CartPage(driver);
    }
    public void eliminarPrimerProducto() {
        if (!listaCheckboxesEliminar.isEmpty()) {
            listaCheckboxesEliminar.get(0).click();
            btnActualizarWishlist.click();
        }
    }
}
