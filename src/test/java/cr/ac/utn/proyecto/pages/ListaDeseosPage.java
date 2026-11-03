package cr.ac.utn.proyecto.pages;

import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/** Lista de deseos (RF-07). */
public class ListaDeseosPage extends BasePage {

    @FindBy(css = ".cart-item-row")
    private List<WebElement> filas;

    @FindBy(css = ".cart-item-row .product a")
    private List<WebElement> nombres;

    @FindBy(name = "addtocart")
    private List<WebElement> casillasAgregarCarrito;

    @FindBy(name = "removefromcart")
    private List<WebElement> casillasQuitar;

    @FindBy(css = ".wishlist-add-to-cart-button")
    private WebElement botonAgregarAlCarrito;

    @FindBy(name = "updatecart")
    private WebElement botonActualizar;

    @FindBy(css = ".page-body")
    private WebElement contenido;

    public ListaDeseosPage(WebDriver driver) {
        super(driver);
    }

    public int contarProductos() {
        return filas.size();
    }

    public List<String> obtenerNombres() {
        return nombres.stream().map(e -> e.getText().trim()).collect(Collectors.toList());
    }

    /** Marca todos los productos y los mueve al carrito; el sitio abre el carrito. */
    public CarritoPage moverTodoAlCarrito() {
        casillasAgregarCarrito.forEach(this::clic);
        clic(botonAgregarAlCarrito);
        return new CarritoPage(driver);
    }

    /** Quita todos los productos de la lista. */
    public ListaDeseosPage quitarTodo() {
        casillasQuitar.forEach(this::clic);
        clic(botonActualizar);
        return new ListaDeseosPage(driver);
    }

    public String obtenerContenido() {
        return visible(contenido).getText().trim();
    }
}
