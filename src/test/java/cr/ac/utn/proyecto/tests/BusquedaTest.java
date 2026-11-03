package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cr.ac.utn.proyecto.pages.ResultadosBusquedaPage;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("RF-03 Busqueda de productos")
class BusquedaTest extends PruebaTienda {

    @Test
    @DisplayName("CP-013 Busqueda con coincidencias lista los productos")
    void busquedaConCoincidencias() {
        ResultadosBusquedaPage resultados = abrirTienda().buscar("laptop");

        List<String> nombres = resultados.obtenerNombresResultados();
        assertAll(
                () -> assertTrue(resultados.contarResultados() > 0, "Debe haber resultados"),
                () -> assertTrue(nombres.contains("14.1-inch Laptop"), "Resultados: " + nombres),
                () -> assertTrue(nombres.stream().allMatch(n -> n.toLowerCase().contains("laptop")),
                        "Todos los resultados deben coincidir con el texto: " + nombres));
    }

    @Test
    @DisplayName("CP-014 Busqueda sin coincidencias informa que no hay productos")
    void busquedaSinCoincidencias() {
        ResultadosBusquedaPage resultados = abrirTienda().buscar("zzproductoinexistente");

        assertAll(
                () -> assertEquals(0, resultados.contarResultados()),
                () -> assertTrue(resultados.obtenerMensajeSinResultados()
                        .contains("No products were found that matched your criteria")));
    }

    @Test
    @DisplayName("CP-015 Busqueda con texto de 2 caracteres (limite) muestra aviso")
    void busquedaConTextoCorto() {
        ResultadosBusquedaPage resultados = abrirTienda().buscar("ab");

        assertAll(
                () -> assertTrue(resultados.obtenerAdvertencia()
                        .contains("Search term minimum length is 3 characters")),
                () -> assertEquals(0, resultados.contarResultados()));
    }

    @Test
    @DisplayName("CP-016 Busqueda avanzada por categoria y rango de precio")
    void busquedaAvanzadaPorCategoriaYPrecio() {
        ResultadosBusquedaPage resultados = abrirTienda().buscar("computer")
                .buscarAvanzado("computer", "Computers", "1000", "1300");

        List<Double> precios = resultados.obtenerPreciosResultados();
        assertAll(
                () -> assertTrue(resultados.contarResultados() > 0, "Debe haber al menos un resultado"),
                () -> assertTrue(precios.stream().allMatch(p -> p >= 1000 && p <= 1300),
                        "Todos los precios deben estar entre 1000 y 1300: " + precios));
    }
}
