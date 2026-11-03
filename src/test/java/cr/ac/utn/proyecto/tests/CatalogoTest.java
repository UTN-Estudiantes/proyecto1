package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.pages.PaginaCatalogo;
import cr.ac.utn.proyecto.pages.PaginaCatalogo.Orden;
import cr.ac.utn.proyecto.pages.PaginaInicio;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** RF-04: navegación y ordenamiento del catálogo. */
class CatalogoTest extends BaseTest {

    @Test
    @DisplayName("CP-018 Ordenar el listado por nombre de A a Z")
    void ordenarPorNombreAscendente() {
        PaginaCatalogo libros = new PaginaInicio(driver).abrir()
                .irACategoria("Books")
                .ordenarPor(Orden.NOMBRE_A_Z);

        List<String> nombres = libros.obtenerNombresDeProductos();
        List<String> esperados = new ArrayList<>(nombres);
        esperados.sort(String.CASE_INSENSITIVE_ORDER);

        assertTrue(nombres.size() > 1, "Se necesitan varios productos para comprobar el orden");
        assertEquals(esperados, nombres);
    }

    @Test
    @DisplayName("CP-019 Ordenar el listado por precio de menor a mayor")
    void ordenarPorPrecioAscendente() {
        PaginaCatalogo libros = new PaginaInicio(driver).abrir()
                .irACategoria("Books")
                .ordenarPor(Orden.PRECIO_MENOR_A_MAYOR);

        List<BigDecimal> precios = libros.obtenerPreciosDeProductos();

        assertTrue(precios.size() > 1, "Se necesitan varios productos para comprobar el orden");
        for (int i = 1; i < precios.size(); i++) {
            assertFalse(precios.get(i - 1).compareTo(precios.get(i)) > 0,
                    "Orden incorrecto: " + precios.get(i - 1) + " antes de " + precios.get(i));
        }
    }
}
