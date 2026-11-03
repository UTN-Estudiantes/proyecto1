package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.pages.CategoryPage;
import cr.ac.utn.proyecto.pages.HomePage;
import cr.ac.utn.proyecto.pages.SearchPage;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Pruebas automatizadas con POM para RF-03 (Busqueda de productos) y RF-04 (Catalogo).
 */
class BusquedaYCatalogoTest extends BaseTest {

    @Test
    @DisplayName("CP-010 Busqueda de producto por texto con coincidencias exactas")
    void busquedaProductoConCoincidencias() {
        // Arrange
        HomePage home = new HomePage(driver).abrir();

        // Act
        SearchPage busqueda = home.buscarProducto("Laptop");

        // Assert
        List<String> productos = busqueda.nombresProductos();
        assertFalse(productos.isEmpty());
        assertTrue(productos.contains("14.1-inch Laptop"));
    }

    @Test
    @DisplayName("CP-011 Busqueda de producto sin coincidencias muestra aviso informativo")
    void busquedaProductoSinCoincidenciasMuestraAviso() {
        // Arrange
        HomePage home = new HomePage(driver).abrir();

        // Act
        SearchPage busqueda = home.buscarProducto("ProductoInexistenteXYZ999");

        // Assert
        assertTrue(busqueda.nombresProductos().isEmpty());
        assertTrue(busqueda.mensajeAviso()
                .contains("No products were found that matched your criteria."));
    }

    @Test
    @DisplayName("CP-012 Busqueda rechazada con texto de 2 caracteres (bajo el limite minimo de 3)")
    void busquedaConTextoCortoMuestraAviso() {
        // Arrange
        HomePage home = new HomePage(driver).abrir();

        // Act
        SearchPage busqueda = home.buscarProducto("PC");

        // Assert
        assertTrue(busqueda.mensajeAviso()
                .contains("Search term minimum length is 3 characters"));
    }

    @Test
    @DisplayName("CP-016 Ordenamiento de productos de categoria por precio de menor a mayor")
    void ordenarPorPrecioMenorAMayor() {
        // Arrange
        HomePage home = new HomePage(driver).abrir();

        // Act
        CategoryPage categoria = home.irACategoria("Books")
                .ordenarPor("Price: Low to High");

        // Assert
        List<Double> precios = categoria.preciosProductos();
        assertTrue(precios.size() >= 2);
        for (int i = 0; i < precios.size() - 1; i++) {
            assertTrue(precios.get(i) <= precios.get(i + 1));
        }
    }
}
