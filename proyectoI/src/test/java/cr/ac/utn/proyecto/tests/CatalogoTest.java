
// Responsable: Emily María Alfaro González
package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.pages.CatalogoPage;

public class CatalogoTest extends BaseTest {

    @Test
    @DisplayName("CP-017 Cambiar productos por página al valor mínimo")
    void cambiarProductosPorPagina() {

        // Arrange (Preparación)
        //Categoría books y cantidad mínima por página
        String categoria = "books";
        int cantidadEsperada = 4;

        // Act (Acción)
        // Se realiza el cambio de la cantidad de productos por página al valor mínimo
        CatalogoPage catalogoPage = new CatalogoPage(driver).abrirCategoria(categoria).mostrarPorPagina(String.valueOf(cantidadEsperada));

        // Assert (Comprobación)
        // Se verifica que la cantidad de productos mostrados en la página sea menor o igual a la cantidad esperada y mayor a cero
        assertTrue(catalogoPage.cantidadProductos() <= cantidadEsperada, "La cantidad de productos mostrados supera el valor esperado");
        assertTrue(catalogoPage.cantidadProductos() > 0, "No se muestra ningún producto");
    }
}