package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cr.ac.utn.proyecto.pages.CategoriaPage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("RF-04 Catalogo: categorias, orden y paginacion")
class CatalogoTest extends PruebaTienda {

    @Test
    @DisplayName("CP-017 Ordenar Books por precio de menor a mayor")
    void ordenarPorPrecioAscendente() {
        CategoriaPage libros = abrirTienda().irACategoria("books")
                .ordenarPor("Price: Low to High");

        List<Double> precios = libros.obtenerPrecios();
        List<Double> ordenados = new ArrayList<>(precios);
        Collections.sort(ordenados);
        assertAll(
                () -> assertTrue(precios.size() > 1, "Debe haber varios productos"),
                () -> assertEquals(ordenados, precios, "Los precios deben quedar de menor a mayor"));
    }

    @Test
    @DisplayName("CP-018 Ordenar Books por nombre de la A a la Z")
    void ordenarPorNombre() {
        CategoriaPage libros = abrirTienda().irACategoria("books")
                .ordenarPor("Name: A to Z");

        List<String> nombres = libros.obtenerNombres();
        List<String> ordenados = new ArrayList<>(nombres);
        ordenados.sort(String.CASE_INSENSITIVE_ORDER);
        assertEquals(ordenados, nombres);
    }

    @Test
    @DisplayName("CP-019 Mostrar 4 productos por pagina (limite de la opcion menor)")
    void mostrarCuatroPorPagina() {
        CategoriaPage libros = abrirTienda().irACategoria("books")
                .mostrarPorPagina("4");

        int cantidad = libros.contarProductos();
        assertTrue(cantidad > 0 && cantidad <= 4, "Debe mostrar entre 1 y 4 productos, mostro " + cantidad);
    }

    @Test
    @DisplayName("CP-020 Cambiar la vista del listado a lista")
    void cambiarVistaALista() {
        CategoriaPage libros = abrirTienda().irACategoria("books")
                .cambiarVista("List");

        assertTrue(libros.seMuestraEnLista(), "El listado debe mostrarse en modo lista");
    }
}
