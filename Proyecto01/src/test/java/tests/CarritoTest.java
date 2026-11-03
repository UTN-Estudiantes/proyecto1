package tests;

import base.BaseTest;
import pages.CarritoPage;
import pages.ProductoPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
    @DisplayName("CP-030 Cambiar cantidad y recalcular subtotal con dos productos")
    public void cambiarCantidadDosProductos() {
        ProductoPage producto = new ProductoPage(driver);
        producto.abrirUrl(URL_LAPTOP);
        producto.agregarAlCarrito(ID_BTN_CARRITO);
        producto.abrirUrl(URL_SITIO + "/computing-and-internet");
        producto.agregarAlCarrito("add-to-cart-button-13");

        CarritoPage carrito = new CarritoPage(driver);
        carrito.abrir(URL_SITIO);
        carrito.cambiarCantidadProducto(0, "2");
        carrito.cambiarCantidadProducto(1, "3");
        carrito.actualizarCarrito();

        double precio1 = Double.parseDouble( carrito.obtenerPrecioUnitario(0).replace(",", ""));
        double precio2 = Double.parseDouble( carrito.obtenerPrecioUnitario(1).replace(",", ""));
        double subtotal1 = Double.parseDouble(carrito.obtenerSubtotalProducto(0).replace(",", ""));
        double subtotal2 = Double.parseDouble( carrito.obtenerSubtotalProducto(1).replace(",", ""));

        assertEquals(precio1 * 2, subtotal1, 0.01);
        assertEquals(precio2 * 3, subtotal2, 0.01);
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
