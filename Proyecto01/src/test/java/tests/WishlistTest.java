package tests;

import base.BaseTest;
import pages.ProductoPage;
import pages.WishlistPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WishlistTest extends BaseTest {

    private static final String URL_PRODUCTO    = URL_SITIO + "/black-white-diamond-heart";
    private static final String ID_BTN_WISHLIST = "add-to-wishlist-button-14";
    private static final String TEXTO_WISHLIST  = "The product has been added to your wishlist";

    @Test
    @DisplayName("CP-039 Agregar producto a la lista de deseos")
    public void agregarProductoAWishlist() {
        ProductoPage producto = new ProductoPage(driver);
        producto.abrirUrl(URL_PRODUCTO);
        producto.agregarAWishlist(ID_BTN_WISHLIST);

        String notificacion = producto.obtenerTextoNotificacion();
        assertTrue(notificacion.contains(TEXTO_WISHLIST),
                "Se esperaba la confirmacion de wishlist, se obtuvo: " + notificacion);
    }

    @Test
    @DisplayName("CP-040 Contador de wishlist en encabezado se incrementa al agregar producto")
    public void contadorWishlistIncrementa() {
        ProductoPage producto = new ProductoPage(driver);
        String cantidadAntes = producto.obtenerCantidadWishlistEncabezado();

        producto.abrirUrl(URL_PRODUCTO);
        producto.agregarAWishlist(ID_BTN_WISHLIST);

        String cantidadDespues = producto.obtenerCantidadWishlistEncabezado();
        assertFalse(cantidadAntes.equals(cantidadDespues),
                "El contador de wishlist en el encabezado debe incrementar al agregar un producto");
    }

    @Test
    @DisplayName("CP-041 Eliminar producto de la lista de deseos deja la wishlist vacia")
    public void eliminarProductoDejaWishlistVacia() {
        ProductoPage producto = new ProductoPage(driver);
        producto.abrirUrl(URL_PRODUCTO);
        producto.agregarAWishlist(ID_BTN_WISHLIST);

        WishlistPage wishlist = new WishlistPage(driver);
        wishlist.abrir(URL_SITIO);
        wishlist.eliminarPrimerProducto();

        assertTrue(wishlist.estaVacia(),
                "Se espera la wishlist vacia despues de eliminar el unico producto");
    }

    @Test
    @DisplayName("CP-039b La wishlist esta vacia al iniciar una sesion de navegador nueva")
    public void wishlistVaciaAlInicio() {
        WishlistPage wishlist = new WishlistPage(driver);
        wishlist.abrir(URL_SITIO);

        assertTrue(wishlist.estaVacia(),
                "La wishlist debe estar vacia en un navegador nuevo sin productos agregados");
    }

    @Test
    @DisplayName("CP-039c El producto agregado a wishlist aparece en la pagina de wishlist")
    public void productoApareceEnWishlist() {
        ProductoPage producto = new ProductoPage(driver);
        producto.abrirUrl(URL_PRODUCTO);
        producto.agregarAWishlist(ID_BTN_WISHLIST);

        WishlistPage wishlist = new WishlistPage(driver);
        wishlist.abrir(URL_SITIO);

        String nombreEnWishlist = wishlist.obtenerNombreProducto();
        assertTrue(nombreEnWishlist.contains("Black & White Diamond Heart"),
                "El producto 'Black & White Diamond Heart' debe aparecer en la wishlist, se obtuvo: " + nombreEnWishlist);
    }
}
