package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.pages.CategoriaPage;
import cr.ac.utn.proyecto.pages.InicioPage;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Pruebas de RF-04 Catalogo. */
class CatalogoTest extends BaseTest {

    @Test
    @DisplayName("CP-018 Ordenar por precio de menor a mayor")
    void ordenarPorPrecioDeMenorAMayor() {
        CategoriaPage categoria = new InicioPage(driver).abrir()
                .irACategoria("books")
                .ordenarPor("Price: Low to High");

        List<Double> precios = categoria.preciosProductos();
        List<Double> copiaOrdenada = precios.stream().sorted().toList();
        assertEquals(copiaOrdenada, precios);
        assertEquals(10.00, precios.get(0));
        assertEquals(51.00, precios.get(precios.size() - 1));
    }
}
