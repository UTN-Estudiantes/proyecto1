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
}
