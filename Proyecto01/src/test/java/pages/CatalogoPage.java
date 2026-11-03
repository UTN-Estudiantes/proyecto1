/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pages;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

/**
 *
 * @author artav
 */
public class CatalogoPage {
    private WebDriver driver;

    @FindBy(css = "a[href='/computers']")
    private WebElement categoriaComputers;

    @FindBy(css = "a[href='/desktops']")
    private List<WebElement> subcategoriaDesktops;

    @FindBy(css = ".page-title h1")
    private WebElement tituloPagina;

    @FindBy(id = "products-orderby")
    private WebElement ordenarPor;

    @FindBy(css = ".product-item .actual-price")
    private List<WebElement> precios;

    public CatalogoPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public CatalogoPage abrirCategoria() {
        categoriaComputers.click();
        return this;
    }
    
    public CatalogoPage abrirSubcategoria() {
        for (WebElement desktop : subcategoriaDesktops) {
            if (desktop.isDisplayed()) {
                desktop.click();
                break;
            }
        }
        return this;
    }

    public String obtenerTitulo() {
        return tituloPagina.getText();
    }

    public CatalogoPage ordenarPorPrecio(String opcion) {
        Select select = new Select(ordenarPor);
        select.selectByVisibleText(opcion);
        return this;
    }

    public double obtenerPrimerPrecio() {
        return Double.parseDouble( precios.get(0).getText().replace(",", ""));
    }

    public double obtenerUltimoPrecio() {
        return Double.parseDouble( precios.get(precios.size() - 1).getText().replace(",", ""));
    }
}
