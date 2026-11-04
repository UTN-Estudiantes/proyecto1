package cr.ac.utn.proyecto.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CartPage {
    private WebDriver driver;

    //Localizadores
    @FindBy(css = ".order-summary-content")
    private WebElement lblCarritoVacio;

    @FindBy(css = "tr.cart-item-row")
    private List<WebElement> listaFilasItems;

    @FindBy(css = "tr.cart-item-row .product-name")
    private List<WebElement> listaNombresProductos;

    @FindBy(name = "removefromcart")
    private List<WebElement> listaCheckboxesEliminar;

    @FindBy(css = "tr.cart-item-row input.qty-input")
    private WebElement txtPrimeraCantidad;

    @FindBy(name = "updatecart")
    private WebElement btnUpdateCart;

    @FindBy(css = "span.product-subtotal")
    private WebElement lblSubtotalItem;

    @FindBy(id = "termsofservice")
    private WebElement chkTermsOfService;

    @FindBy(id = "checkout")
    private WebElement btnCheckout;

    // Advertencia si intentas checkout sin marcar términos
    @FindBy(css = "#terms-of-service-warning-box p")
    private WebElement lblWarningTerms;

    //Constructor
    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //Metodos Logica
    public boolean estaVacio() {
        return lblCarritoVacio.getText().contains("Your Shopping Cart is empty!");
    }
    public String obtenerMensajeCarritoVacio() {
        return lblCarritoVacio.getText();
    }
    public int obtenerCantidadItemsDiferentes() {
        return listaFilasItems.size();
    }
    public void actualizarCantidadPrimerProducto(String nuevaCantidad) {
        String subtotalAnterior = obtenerSubtotalItem();
        txtPrimeraCantidad.clear();
        txtPrimeraCantidad.sendKeys(nuevaCantidad);
        btnUpdateCart.click();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> !d.findElements(By.cssSelector("span.product-subtotal")).isEmpty()
                        && !d.findElement(By.cssSelector("span.product-subtotal"))
                                .getText().equals(subtotalAnterior));
    }
    public void eliminarPrimerProducto() {
        if (!listaCheckboxesEliminar.isEmpty()) {
            listaCheckboxesEliminar.get(0).click();
            btnUpdateCart.click();
        }
    }
    public void aceptarTerminosDeServicio() {
        if (!chkTermsOfService.isSelected()) {
            chkTermsOfService.click();
        }
    }

    //Navega al checkout si se cumplen los requisitos
    //Devuelve CheckoutPage
    public CheckoutPage irCheckout() {
        btnCheckout.click();
        return new CheckoutPage(driver);
    }

    //Hace clic en checkout sin marcar los términos
    public void clickCheckoutSinTerminos() {
        btnCheckout.click();
    }
    public String obtenerMensajeAdvertenciaTerminos() {
        return lblWarningTerms.getText();
    }
    public String obtenerSubtotalItem() {
        return lblSubtotalItem.getText();
    }
}
