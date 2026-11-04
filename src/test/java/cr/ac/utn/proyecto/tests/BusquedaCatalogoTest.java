package cr.ac.utn.proyecto.tests;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.base.Visual;
import cr.ac.utn.proyecto.pages.CategoryPage;
import cr.ac.utn.proyecto.pages.HomePage;
import cr.ac.utn.proyecto.pages.SearchResultsPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BusquedaCatalogoTest extends BaseTest {

    @Test
    @DisplayName("CP-009 Búsqueda exitosa")
    void busquedaExitosa() {
        // Arrange
        driver.get(URL_BASE);
        HomePage inicio = new HomePage(driver);

        // Act
        SearchResultsPage resultados = inicio.buscar("Computing and Internet");
        Visual.pausa();

        // Assert
        assertTrue(resultados.obtenerCantidadResultados() > 0,
                "La búsqueda debe devolver al menos un producto");
        assertTrue(resultados.existeProductoConTexto("Computing and Internet"),
                "El producto buscado debe aparecer entre los resultados");
    }

    @Test
    @DisplayName("CP-010 Búsqueda sin coincidencias")
    void busquedaSinCoincidencias() {
        // Arrange
        driver.get(URL_BASE);
        HomePage inicio = new HomePage(driver);

        // Act
        SearchResultsPage resultados = inicio.buscar("xyzproductoinexistente");
        Visual.pausa();

        // Assert
        String mensaje = resultados.obtenerMensajeSinResultados();
        assertTrue(mensaje.contains("No products were found that matched your criteria"),
                "Mensaje real: " + mensaje);
        assertEquals(0, resultados.obtenerCantidadResultados());
    }

    @Test
    @DisplayName("CP-011 Texto de búsqueda demasiado corto")
    void busquedaTextoCorto() {
        // Arrange
        driver.get(URL_BASE);
        HomePage inicio = new HomePage(driver);

        // Act
        SearchResultsPage resultados = inicio.buscar("ab");
        Visual.pausa();

        // Assert
        String advertencia = resultados.obtenerMensajeAdvertencia();
        assertTrue(advertencia.contains("Search term minimum length is 3 characters"),
                "Mensaje real: " + advertencia);
    }

    @Test
    @DisplayName("CP-012 Ordenar categoría por precio")
    void ordenarPorPrecio() {
        // Arrange
        driver.get(URL_BASE + "books");
        Visual.pausa();
        CategoryPage categoria = new CategoryPage(driver);

        // Act
        categoria.ordenarPor("Price: Low to High");
        Visual.pausa();

        // Assert
        List<Double> precios = categoria.obtenerPreciosProductos();
        List<Double> esperados = new ArrayList<>(precios);
        Collections.sort(esperados);

        assertTrue(precios.size() > 1, "La categoría debe tener varios productos");
        assertEquals(esperados, precios, "Los precios deben estar de menor a mayor");
    }

    @Test
    @DisplayName("CP-016 Suscripción al boletín con email válido")
    void suscripcionBoletinEmailValido() {
        // Arrange
        driver.get(URL_BASE);
        HomePage inicio = new HomePage(driver);
        String email = "cp016" + System.currentTimeMillis() + "@example.com";

        // Act
        inicio.suscribirBoletin(email);
        Visual.pausa();

        // Assert
        String mensaje = inicio.obtenerMensajeBoletin();
        assertTrue(mensaje.contains("Thank you for signing up!"),
                "Mensaje real: " + mensaje);
    }
}
