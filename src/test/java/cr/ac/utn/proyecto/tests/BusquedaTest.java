package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.pages.PaginaBusqueda;
import cr.ac.utn.proyecto.pages.PaginaInicio;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** RF-03: búsqueda de productos. */
class BusquedaTest extends BaseTest {

    @Test
    @DisplayName("CP-013 Búsqueda por texto con coincidencias")
    void busquedaConCoincidencias() {
        PaginaBusqueda resultados = new PaginaInicio(driver).abrir().buscar("laptop");

        assertTrue(resultados.obtenerCantidadDeResultados() >= 1, "Debe listarse al menos un producto");
        assertTrue(resultados.obtenerNombresDeProductos().contains("14.1-inch Laptop"));
    }

    @Test
    @DisplayName("CP-014 Búsqueda por texto sin coincidencias")
    void busquedaSinCoincidencias() {
        PaginaBusqueda resultados = new PaginaInicio(driver).abrir().buscar("zzzxqw");

        assertEquals("No products were found that matched your criteria.", resultados.obtenerMensajeSinResultados());
        assertEquals(0, resultados.obtenerCantidadDeResultados());
    }

    @Test
    @DisplayName("CP-015 Búsqueda con texto de 2 caracteres (bajo el mínimo)")
    void busquedaConDosCaracteres() {
        PaginaBusqueda resultados = new PaginaInicio(driver).abrir().buscar("ab");

        assertTrue(resultados.obtenerAvisoDeBusqueda().contains("Search term minimum length is 3 characters"));
        assertEquals(0, resultados.obtenerCantidadDeResultados());
    }

    @Test
    @DisplayName("CP-017 Búsqueda avanzada por categoría y rango de precio")
    void busquedaAvanzadaPorCategoriaYPrecio() {
        PaginaBusqueda resultados = new PaginaInicio(driver).abrir()
                .irABusquedaAvanzada()
                .buscarConFiltros("laptop", "Computers", true, "1000", "2000");

        assertTrue(resultados.obtenerNombresDeProductos().contains("14.1-inch Laptop"));
        assertFalse(resultados.obtenerPreciosDeProductos().isEmpty());
        for (BigDecimal precio : resultados.obtenerPreciosDeProductos()) {
            assertTrue(precio.compareTo(new BigDecimal("1000")) >= 0 && precio.compareTo(new BigDecimal("2000")) <= 0,
                    "Precio fuera del rango: " + precio);
        }
    }
}
