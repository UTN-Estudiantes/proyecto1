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
import org.openqa.selenium.By;

/**
 *
 * @author artav
 */
public class ComparacionPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(css = ".add-to-compare-list-button")
    private WebElement botonComparar;

    @FindBy(id = "bar-notification")
    private WebElement notificacion;

    @FindBy(css = ".compare-products-table")
    private WebElement tablaComparacion;

    public ComparacionPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public ComparacionPage abrirProducto(String url) {
        driver.get(url);
        return this;
    }

    public ComparacionPage agregarAComparacion() {
        botonComparar.click();
        return this;
    }

    public ComparacionPage abrirComparacion(String urlSitio) {
        driver.get(urlSitio + "/compareproducts");
        return this;
    }

    public String obtenerTablaComparacion() {
        wait.until(ExpectedConditions.visibilityOf(tablaComparacion));
        return tablaComparacion.getText();
    }
    
    public ComparacionPage quitarPrimerProducto() {
        WebElement boton = driver.findElement(By.cssSelector(".remove-button"));
        boton.click();
        return this;
    }

    public int cantidadProductosComparados() {
        return driver.findElements(By.cssSelector(".compare-products-table .product-name")).size();
    }
    
    public ComparacionPage limpiarComparacion() {
        WebElement boton = driver.findElement(By.cssSelector(".clear-list"));
        boton.click();
        return this;
    }
}
