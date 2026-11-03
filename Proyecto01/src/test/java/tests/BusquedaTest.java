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

/**
 *
 * @author artav
 */
public class BusquedaTest extends BaseTest {
    @Test
    @DisplayName("CP-015 Busqueda con coincidencias")
    public void busquedaConCoincidencias() {
        BusquedaPage busqueda = new BusquedaPage(driver);
        busqueda.buscar("computer");

        String resultado = busqueda.obtenerResultados();
        assertTrue(resultado.toLowerCase().contains("computer"),
                "Se esperaba encontrar productos relacionados con computer");
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
}
