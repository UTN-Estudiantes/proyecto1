package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.pages.PaginaInicio;
import cr.ac.utn.proyecto.pages.PaginaListaDeseos;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** RF-07: lista de deseos. */
class ListaDeseosTest extends BaseTest {

    private static final String PRODUCTO = "Smartphone";

    @Test
    @DisplayName("CP-031 Agregar un producto a la lista de deseos y consultarla")
    void agregarALaListaDeDeseosYConsultarla() {
        PaginaListaDeseos lista = new PaginaInicio(driver).abrir()
                .buscar(PRODUCTO)
                .abrirProducto(PRODUCTO)
                .agregarALaListaDeDeseos()
                .irALaListaDeDeseos();

        assertEquals("(1)", lista.obtenerIndicadorListaDeseos());
        assertTrue(lista.obtenerNombresDeProductos().contains(PRODUCTO));
    }
}
