/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tests;

import base.BaseTest;
import pages.CatalogoPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author artav
 */
public class CatalogoTest extends BaseTest {
    @Test
    @DisplayName("CP-020 Navegar a categoria y subcategoria")
    public void navegarCategoria() {
        CatalogoPage catalogo = new CatalogoPage(driver);
        catalogo.abrirCategoria();
        catalogo.abrirSubcategoria();

        String titulo = catalogo.obtenerTitulo();
        assertEquals("Notebooks", titulo);
    }
}
