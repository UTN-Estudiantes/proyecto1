package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.pages.PaginaComparacion;
import cr.ac.utn.proyecto.pages.PaginaInicio;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** RF-08: comparación de productos. */
class ComparacionTest extends BaseTest {

    private static final String LAPTOP = "14.1-inch Laptop";
    private static final String CELULAR = "Smartphone";

    @Test
    @DisplayName("CP-034 Comparar dos productos y ver la tabla comparativa")
    void compararDosProductos() {
        PaginaInicio inicio = new PaginaInicio(driver).abrir();
        inicio.buscar("laptop").abrirProducto(LAPTOP).agregarALaComparacion();

        PaginaComparacion comparacion = inicio.abrir()
                .buscar("smartphone")
                .abrirProducto(CELULAR)
                .agregarALaComparacion();

        assertEquals(2, comparacion.obtenerCantidadDeProductos());
        assertTrue(comparacion.obtenerNombresDeProductos().containsAll(List.of(LAPTOP, CELULAR)));
        assertTrue(comparacion.obtenerNombresDeCaracteristicas().contains("Screensize"));
    }
}
