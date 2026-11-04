package cr.ac.utn.proyecto.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

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

     //Marca el primer producto de la wishlist y lo transfiere al carrito
     //Debe devolver la página del carrito
    public CartPage moverPrimerProductoCarrito() {
        if (!listaCheckboxesMoverAlCarrito.isEmpty()) {
            listaCheckboxesMoverAlCarrito.get(0).click();
            btnMoverAlCarrito.click();
        }
        return new CartPage(driver);
    }
    public void eliminarPrimerProducto() {
        if (!listaCheckboxesEliminar.isEmpty()) {
            listaCheckboxesEliminar.get(0).click();
            btnActualizarWishlist.click();
        }
    }
}
