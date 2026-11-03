
// Responsable: Katlyn Yiliana Fernández Ramos
package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.pages.CarritoPage;
import cr.ac.utn.proyecto.pages.ListadoProductosPage;
import cr.ac.utn.proyecto.pages.ProductoPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CarritoTest extends BaseTest {

    private static final String CATEGORIA_NOTEBOOKS = "notebooks";
    private static final String NOMBRE_LAPTOP = "14.1-inch Laptop";
    private static final String RUTA_LAPTOP = "141-inch-laptop";
    private static final String RUTA_COMPUTADORA = "build-your-own-computer";

    @Test
    @DisplayName("CP-021 Agregar un producto desde el catálogo")
    void agregarProductoDesdeCatalogo() {

        // Act (Acción)

        // Se abre la categoría Notebooks y se agrega la laptop desde el listado
        ListadoProductosPage listado = new ListadoProductosPage(driver)
                .abrirCategoria(CATEGORIA_NOTEBOOKS)
                .agregarAlCarrito(NOMBRE_LAPTOP);

        // Assert (Comprobación)

        // Se verifica el mensaje de confirmación y que el indicador del carrito pase a (1)
        assertTrue(listado.mensajeNotificacion().contains("The product has been added to your shopping cart"),
                "El mensaje de confirmación no coincide. Texto real: " + listado.mensajeNotificacion());
        assertEquals("(1)", listado.indicadorCarrito(), "El indicador del carrito no se actualizó");

        // Se verifica que el producto aparezca en el carrito
        CarritoPage carrito = listado.irAlCarrito();
        assertTrue(carrito.nombresProductos().contains(NOMBRE_LAPTOP),
                "El producto no aparece en el carrito. Productos: " + carrito.nombresProductos());
    }

    @Test
    @DisplayName("CP-024 Quitar un producto del carrito")
    void quitarProductoDelCarrito() {

        // Arrange (Preparación)

        // Se agrega la laptop desde su página y se abre el carrito
        CarritoPage carrito = new ProductoPage(driver)
                .abrir(RUTA_LAPTOP)
                .agregarAlCarrito()
                .irAlCarrito();

        // Act (Acción)

        // Se marca Remove y se pulsa Update shopping cart
        carrito.quitarPrimerProducto();

        // Assert (Comprobación)

        // Se verifica el mensaje de carrito vacío y que el indicador vuelva a (0)
        assertTrue(carrito.mensajeCarrito().contains("Your Shopping Cart is empty!"),
                "El mensaje de carrito vacío no coincide. Texto real: " + carrito.mensajeCarrito());
        assertEquals("(0)", carrito.indicadorCarrito(), "El indicador del carrito no volvió a 0");
    }

    @Test
    @DisplayName("CP-028 Producto configurable sin opciones obligatorias")
    void productoConfigurableSinOpciones() {

        // Act (Acción)

        // Se abre Build your own computer y se pulsa Add to cart sin elegir ninguna opción
        ProductoPage producto = new ProductoPage(driver)
                .abrir(RUTA_COMPUTADORA)
                .agregarAlCarrito();

        // Assert (Comprobación)

        // Se verifica el aviso de opción obligatoria y que el carrito siga en (0)
        assertTrue(producto.mensajeNotificacion().contains("Please select HDD"),
                "El aviso no coincide. Texto real: " + producto.mensajeNotificacion());
        assertEquals("(0)", producto.indicadorCarrito(), "El producto se agregó al carrito aunque faltaban opciones");
    }
}