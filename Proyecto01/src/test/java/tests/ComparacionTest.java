/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tests;

import base.BaseTest;
import pages.ComparacionPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author artav
 */
public class ComparacionTest extends BaseTest {
    @Test
    @DisplayName("CP-025 Agregar dos productos a comparar y ver la tabla")
    public void compararDosProductos() {
        ComparacionPage comparacion = new ComparacionPage(driver);
        
        comparacion.abrirProducto(URL_SITIO + "/141-inch-laptop");
        comparacion.agregarAComparacion();
        comparacion.abrirProducto(URL_SITIO + "/computing-and-internet");
        comparacion.agregarAComparacion();
        comparacion.abrirComparacion(URL_SITIO);

        String tabla = comparacion.obtenerTablaComparacion();

        assertTrue(tabla.contains("14.1-inch Laptop"));
        assertTrue(tabla.contains("Computing and Internet"));
    }
    
    @Test
    @DisplayName("CP-026 Quitar producto y limpiar lista de comparacion")
    public void quitarProductoComparacion() {
        ComparacionPage comparacion = new ComparacionPage(driver);

        comparacion.abrirProducto(URL_SITIO + "/141-inch-laptop");
        comparacion.agregarAComparacion();

        comparacion.abrirProducto(URL_SITIO + "/computing-and-internet");
        comparacion.agregarAComparacion();

        comparacion.abrirComparacion(URL_SITIO);
        comparacion.quitarPrimerProducto();
        assertEquals(1, comparacion.cantidadProductosComparados());

        comparacion.limpiarComparacion();
        assertEquals(0, comparacion.cantidadProductosComparados());
    }
}
