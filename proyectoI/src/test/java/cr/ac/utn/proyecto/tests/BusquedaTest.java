package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.pages.InicioPage;
import cr.ac.utn.proyecto.pages.ResultadosBusquedaPage;

public class BusquedaTest extends BaseTest {

    @Test
    @DisplayName("CP-011 Búsqueda por texto con resultados")
    void busquedaConResultados() {

        // Arrange (Preparación)
        String textoBusqueda = "computer";

        // Act (Acción)
        // Se realiza la búsqueda con el texto que se espera que tenga resultados
        ResultadosBusquedaPage resultados = new InicioPage(driver).abrir().buscar(textoBusqueda);

        // Assert (Comprobación)
        // Se verifica que los resultados de la búsqueda sean los esperados
        assertFalse(resultados.titulosProductos().isEmpty(), "No se encontraron resultados para la búsqueda");
    }

    @Test
    @DisplayName("CP-013 Búsqueda con texto demasiado corto")
    void busquedaConTextoCorto() {
        // Arrange (Preparación)
        String textoBusqueda = "sk";

        // Act (Acción)
        // Se realiza la búsqueda con un texto demasiado corto
        ResultadosBusquedaPage resultados = new InicioPage(driver).abrir().buscar(textoBusqueda);

        // Assert (Comprobación)
        // Se verifican los mensajes de error para la búsqueda con texto demcorto
        assertEquals("Search term minimum length is 3 characters", resultados.avisoBusqueda(), 
        "El aviso de texto demasiado corto no coincide.");
    }
}