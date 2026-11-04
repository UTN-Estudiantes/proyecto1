package cr.ac.utn.proyecto.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class AccountPage {
    private WebDriver driver;

    //Localizadores
    @FindBy(id = "Email")
    private WebElement txtEmail;

    @FindBy(id = "FirstName")
    private WebElement txtFirstName;

    @FindBy(id = "LastName")
    private WebElement txtLastName;

    @FindBy(linkText = "Customer info")
    private WebElement lnkCustomerInfo;

    @FindBy(linkText = "Orders")
    private WebElement lnkOrders;

    @FindBy(linkText = "Change password")
    private WebElement lnkChangePassword;

    //Lista de tarjetas de órdenes en el historial de pedidos
    @FindBy(css = ".order-item")
    private List<WebElement> listaOrdenesHistorial;

    //Constructor
    public AccountPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //Metodos logicos
    public String obtenerEmail() {
        return txtEmail.getAttribute("value");
    }

    public String obtenerNombre() {
        return txtFirstName.getAttribute("value");
    }

    public void irAHistorialPedidos() {
        lnkOrders.click();
    }

    public int obtenerCantidadPedidosEnHistorial() {
        return listaOrdenesHistorial.size();
    }
}
