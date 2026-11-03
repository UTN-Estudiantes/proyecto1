/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tests;

import base.BaseTest;
import pages.BusquedaPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 *
 * @author artav
 */
public class BusquedaTest extends BaseTest {
    
    @Test
    @DisplayName("CP-015 Busqueda por texto con coincidencias")
    public void busquedaConCoincidencias() {
        BusquedaPage busqueda = new BusquedaPage(driver);
        busqueda.buscar("computer");
        String resultado = busqueda.obtenerResultados();
        assertTrue(resultado.toLowerCase().contains("computer"));

        busqueda.buscar("COMPUTER");
        resultado = busqueda.obtenerResultados();
        assertTrue(resultado.toLowerCase().contains("computer"));
    }
    
    @Test
    @DisplayName("CP-016 Busqueda sin coincidencias")
    public void busquedaSinCoincidencias() {
        BusquedaPage busqueda = new BusquedaPage(driver);
        busqueda.buscar("xyzproducto99999");

        String resultado = busqueda.obtenerResultados();
        assertTrue(resultado.contains("No products were found"),
                "Se esperaba un mensaje indicando que no hay productos");
    }
    
    @Test
    @DisplayName("CP-017 Busqueda con texto demasiado corto")
    public void busquedaTextoCorto() {
        BusquedaPage busqueda = new BusquedaPage(driver);
        
        String mensaje = busqueda.buscarVacio();
        assertTrue(mensaje.contains("Please enter some search keyword"));

        busqueda.buscar("ab");
        String resultado = busqueda.obtenerResultados();
        assertTrue(resultado.contains("Search term minimum length is 3 characters"));
        
        busqueda.buscar("lap");
        resultado = busqueda.obtenerResultados();
        assertFalse(resultado.contains("Search term minimum length is 3 characters"));
    }
}
