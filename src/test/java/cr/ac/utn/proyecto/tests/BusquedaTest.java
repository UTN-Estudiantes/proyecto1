package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.pages.InicioPage;
import cr.ac.utn.proyecto.pages.ResultadosBusquedaPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Pruebas de RF-03 Busqueda de productos. */
class BusquedaTest extends BaseTest {

    @Test
    @DisplayName("CP-012 Búsqueda de un producto que existe")
    void busquedaDeUnProductoQueExiste() {
        ResultadosBusquedaPage resultados = new InicioPage(driver).abrir().buscar("laptop");

        assertTrue(resultados.nombresProductos().contains("14.1-inch Laptop"));
    }

    @Test
    @DisplayName("CP-013 Búsqueda sin resultados")
    void busquedaSinResultados() {
        ResultadosBusquedaPage resultados = new InicioPage(driver).abrir().buscar("zzzproductox");

        assertEquals("No products were found that matched your criteria.", resultados.mensajeSinResultados());
        assertTrue(resultados.nombresProductos().isEmpty());
    }

    @Test
    @DisplayName("CP-014 Búsqueda con 2 caracteres")
    void busquedaCon2Caracteres() {
        ResultadosBusquedaPage resultados = new InicioPage(driver).abrir().buscar("la");

        assertEquals("Search term minimum length is 3 characters", resultados.aviso());
        assertTrue(resultados.nombresProductos().isEmpty());
    }
}
