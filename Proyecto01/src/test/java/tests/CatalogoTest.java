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
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    
    @Test
    @DisplayName("CP-022 Ordenar productos por precio")
    public void ordenarPorPrecio() {
        CatalogoPage catalogo = new CatalogoPage(driver);
        catalogo.abrirCategoria();
        catalogo.abrirSubcategoria();

        // Menor a mayor
        catalogo.ordenarPorPrecio("Price: Low to High");
        double primero = catalogo.obtenerPrimerPrecio();
        double ultimo = catalogo.obtenerUltimoPrecio();
        assertTrue(primero <= ultimo);

        // Mayor a menor
        catalogo.ordenarPorPrecio("Price: High to Low");
        primero = catalogo.obtenerPrimerPrecio();
        ultimo = catalogo.obtenerUltimoPrecio();
        assertTrue(primero >= ultimo);
    }
}
