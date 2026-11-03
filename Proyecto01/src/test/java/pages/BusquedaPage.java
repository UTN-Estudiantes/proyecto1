/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pages;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author artav
 */
public class BusquedaPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(id = "small-searchterms")
    private WebElement campoBusqueda;

    @FindBy(css = ".search-box-button")
    private WebElement botonBuscar;

    @FindBy(css = ".search-results")
    private WebElement resultadosBusqueda;

    public BusquedaPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public BusquedaPage buscar(String texto) {
        campoBusqueda.clear();
        campoBusqueda.sendKeys(texto);
        botonBuscar.click();
        wait.until(ExpectedConditions.visibilityOf(resultadosBusqueda));
        return this;
    }

    public String obtenerResultados() {
        return resultadosBusqueda.getText();
    }
    
    public String buscarVacio() {
        campoBusqueda.clear();
        botonBuscar.click();
        String mensaje = driver.switchTo().alert().getText();
        driver.switchTo().alert().accept();

        return mensaje;
    }

}
