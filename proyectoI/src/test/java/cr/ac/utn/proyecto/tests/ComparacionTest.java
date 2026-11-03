// Responsable: Melanni Michel Díaz Ugalde
package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.pages.ComparacionPage;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ComparacionTest extends BaseTest {

        private static final String RUTA_LAPTOP = "141-inch-laptop";
        private static final String NOMBRE_LAPTOP = "14.1-inch Laptop";
        private static final String RUTA_ZAPATILLA = "blue-and-green-sneaker";
        private static final String NOMBRE_ZAPATILLA = "Blue and green Sneaker";

        @Test
        @DisplayName("CP-036 Comparar dos productos y ver la tabla")
        void compararDosProductos() {

                // Arrange (Preparación)

                ComparacionPage comparacion = new ComparacionPage(driver);

                // Act (Acción)

                // Se agregan dos productos a la lista de comparación y se abre la tabla
                comparacion.agregarProductoAComparar(RUTA_LAPTOP)
                                .agregarProductoAComparar(RUTA_ZAPATILLA)
                                .abrirComparacion();

                // Assert (Comprobación)

                // Se verifica que la tabla muestre ambos productos
                List<String> nombres = comparacion.nombresProductos();
                assertTrue(nombres.contains(NOMBRE_LAPTOP) && nombres.contains(NOMBRE_ZAPATILLA),
                                "La tabla no muestra ambos productos. Productos reales: " + nombres);
        }

        @Test
        @DisplayName("CP-037 Quitar un producto de la comparación")
        void quitarProductoDeLaComparacion() {

                // Arrange (Preparación)

                // Se agregan dos productos y se abre la tabla
                ComparacionPage comparacion = new ComparacionPage(driver)
                                .agregarProductoAComparar(RUTA_LAPTOP)
                                .agregarProductoAComparar(RUTA_ZAPATILLA)
                                .abrirComparacion();

                // Act (Acción)

                // Se pulsa Remove en el primer producto
                comparacion.quitarPrimerProducto();

                // Assert (Comprobación)

                // Se verifica que quede solo el otro producto
                List<String> nombres = comparacion.nombresProductos();
                assertEquals(1, nombres.size(), "Debía quedar un solo producto. Productos reales: " + nombres);
                assertTrue(nombres.contains(NOMBRE_ZAPATILLA),
                                "Quedó un producto distinto al esperado. Productos reales: " + nombres);
        }

        @Test
        @DisplayName("CP-038 Limpiar la lista de comparación")
        void limpiarListaDeComparacion() {

                // Arrange (Preparación)

                // Se agregan dos productos y se abre la tabla
                ComparacionPage comparacion = new ComparacionPage(driver)
                                .agregarProductoAComparar(RUTA_LAPTOP)
                                .agregarProductoAComparar(RUTA_ZAPATILLA)
                                .abrirComparacion();

                // Act (Acción)

                // Se pulsa Clear list
                comparacion.limpiarLista();

                // Assert (Comprobación)

                // Se verifica el mensaje de que no hay productos para comparar
                String mensaje = comparacion.mensajeListaVacia();
                assertTrue(mensaje.contains("no items to compare"),
                                "El mensaje de lista vacía no coincide. Texto real: " + mensaje);
        }
}