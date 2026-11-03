
// Responsable: Katlyn Yiliana Fernández Ramos
package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.pages.CarritoPage;
import cr.ac.utn.proyecto.pages.ListaDeseosPage;
import cr.ac.utn.proyecto.pages.ProductoPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ListaDeseosTest extends BaseTest {

    private static final String NOMBRE_PRODUCTO = "Blue and green Sneaker";
    private static final String RUTA_PRODUCTO = "blue-and-green-sneaker";

    @Test
    @DisplayName("CP-029 Agregar a la lista de deseos y moverlo al carrito")
    void agregarADeseosYMoverAlCarrito() {

        // Arrange (Preparación)

        // Se abre la página de la laptop y se agrega a la lista de deseos
        ProductoPage producto = new ProductoPage(driver)
                .abrir(RUTA_PRODUCTO)
                .agregarALaListaDeDeseos();

        // Assert (Comprobación)

        // Se verifica el mensaje de confirmación de la lista de deseos
        assertTrue(producto.mensajeNotificacion().contains("The product has been added to your wishlist"),
                "El mensaje no coincide. Texto real: " + producto.mensajeNotificacion());

        // Act (Acción)

        // Se abre la lista de deseos
        ListaDeseosPage lista = producto.irAListaDeseos();

        // Assert (Comprobación)

        // Se verifica que el producto aparezca en la lista
        assertTrue(lista.nombresProductos().contains(NOMBRE_PRODUCTO),
                "El producto no aparece en la lista. Productos: " + lista.nombresProductos());

        // Act (Acción)

        // Se mueve el producto al carrito
        CarritoPage carrito = lista.moverPrimerProductoAlCarrito();

        // Assert (Comprobación)

        // Se verifica que el producto aparezca en el carrito
        assertTrue(carrito.nombresProductos().contains(NOMBRE_PRODUCTO),
                "El producto no aparece en el carrito. Productos: " + carrito.nombresProductos());

        // Se verifica que la lista de deseos haya quedado vacía
        ListaDeseosPage listaFinal = new ListaDeseosPage(driver).abrir();
        assertTrue(listaFinal.contenidoLista().contains("The wishlist is empty!"),
                "La lista no quedó vacía. Texto real: " + listaFinal.contenidoLista());
    }
}