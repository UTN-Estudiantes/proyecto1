package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cr.ac.utn.proyecto.pages.ComparacionPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("RF-08 Comparacion de productos")
class ComparacionTest extends PruebaTienda {

    @Test
    @DisplayName("CP-032 Comparar dos productos muestra la tabla con ambos")
    void compararDosProductos() {
        ComparacionPage comparacion = abrirTienda().abrirProducto("141-inch-laptop")
                .agregarAComparacion()
                .abrirProducto("build-your-own-computer")
                .agregarAComparacion();

        String contenido = comparacion.obtenerContenido();
        assertAll(
                () -> assertTrue(comparacion.seMuestraTabla(), "Debe verse la tabla comparativa"),
                () -> assertTrue(contenido.contains("14.1-inch Laptop")),
                () -> assertTrue(contenido.contains("Build your own computer")));
    }

    @Test
    @DisplayName("CP-033 Limpiar la lista de comparacion")
    void limpiarLista() {
        ComparacionPage comparacion = abrirTienda().abrirProducto("141-inch-laptop")
                .agregarAComparacion()
                .limpiarLista();

        assertAll(
                () -> assertFalse(comparacion.seMuestraTabla()),
                () -> assertTrue(comparacion.obtenerContenido().contains("You have no items to compare")));
    }
}
