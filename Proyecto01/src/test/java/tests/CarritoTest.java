package tests;

import base.BaseTest;
import pages.CarritoPage;
import pages.ProductoPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CarritoTest extends BaseTest {

    private static final String URL_LAPTOP     = URL_SITIO + "/141-inch-laptop";
    private static final String ID_BTN_CARRITO = "add-to-cart-button-31";
    private static final String ID_CAMPO_QTY   = "addtocart_31_EnteredQuantity";
    private static final String TEXTO_AGREGADO = "The product has been added to your shopping cart";

    @Test
    @DisplayName("CP-029 Agregar producto simple al carrito")
    public void agregarProductoSimple() {
        ProductoPage producto = new ProductoPage(driver);
        producto.abrirUrl(URL_LAPTOP);
        producto.agregarAlCarrito(ID_BTN_CARRITO);

        String notificacion = producto.obtenerTextoNotificacion();
        assertTrue(notificacion.contains(TEXTO_AGREGADO),
                "Se esperaba el texto de confirmacion pero se obtuvo: " + notificacion);
    }

    @Test
    @DisplayName("CP-030 Contador del carrito en encabezado se incrementa al agregar producto")
    public void contadorCarritoIncrementa() {
        ProductoPage producto = new ProductoPage(driver);
        String antes = producto.obtenerCantidadCarritoEncabezado();

        producto.abrirUrl(URL_LAPTOP);
        producto.agregarAlCarrito(ID_BTN_CARRITO);

        String despues = producto.obtenerCantidadCarritoEncabezado();
        assertFalse(antes.equals(despues),
                "El contador del carrito en el encabezado debe cambiar al agregar un producto");
    }

    @Test
    @DisplayName("CP-031 Cambiar cantidad de un producto en el carrito")
    public void cambiarCantidadEnCarrito() {
        ProductoPage producto = new ProductoPage(driver);
        producto.abrirUrl(URL_LAPTOP);
        producto.agregarAlCarrito(ID_BTN_CARRITO);

        CarritoPage carrito = new CarritoPage(driver);
        carrito.abrir(URL_SITIO);
        carrito.cambiarCantidad("3");
        carrito.actualizarCarrito();

        String subtotal = carrito.obtenerSubtotalProducto();
        assertTrue(subtotal.contains("4770.00"),
                "Con cantidad 3 el subtotal esperado es 4770.00, se obtuvo: " + subtotal);
    }

    @Test
    @DisplayName("CP-032 Eliminar producto del carrito deja el carrito vacio")
    public void eliminarProductoDejaCarritoVacio() {
        ProductoPage producto = new ProductoPage(driver);
        producto.abrirUrl(URL_LAPTOP);
        producto.agregarAlCarrito(ID_BTN_CARRITO);

        CarritoPage carrito = new CarritoPage(driver);
        carrito.abrir(URL_SITIO);
        carrito.eliminarPrimerProducto();

        assertTrue(carrito.estaVacio(),
                "Se espera el mensaje de carrito vacio despues de eliminar el unico producto");
    }

    @Test
    @DisplayName("CP-033 Cantidad cero o negativa no agrega el producto al carrito")
    public void cantidadCeroNoAgregaProducto() {
        ProductoPage producto = new ProductoPage(driver);
        producto.abrirUrl(URL_LAPTOP);
        producto.ingresarCantidad(ID_CAMPO_QTY, "0");
        producto.agregarAlCarrito(ID_BTN_CARRITO);

        String notificacion = producto.obtenerTextoNotificacion();
        assertFalse(notificacion.contains(TEXTO_AGREGADO),
                "Con cantidad 0 no debe aparecer la confirmacion de producto agregado");
    }
}
