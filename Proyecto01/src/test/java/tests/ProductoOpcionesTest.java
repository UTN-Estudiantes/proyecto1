package tests;

import base.BaseTest;
import pages.ProductoPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductoOpcionesTest extends BaseTest {

    private static final String URL_PC    = URL_SITIO + "/build-your-own-computer";
    private static final String ID_BTN_PC = "add-to-cart-button-16";

    private static final String VAL_PROC_FAST = "14";
    private static final String VAL_PROC_SLOW = "13";
    private static final String VAL_RAM_4GB   = "16";
    private static final String VAL_RAM_8GB   = "17";

    private static final String ID_HDD_320   = "product_attribute_16_3_6_18";
    private static final String ID_HDD_400   = "product_attribute_16_3_6_19";
    private static final String ID_OS_UBUNTU = "product_attribute_16_4_7_44";
    private static final String ID_OS_WIN7   = "product_attribute_16_4_7_20";
    private static final String ID_OS_WIN10  = "product_attribute_16_4_7_21";
    private static final String ID_MS_OFFICE = "product_attribute_16_8_8_22";

    private static final String TEXTO_AGREGADO = "The product has been added to your shopping cart";

    @Test
    @DisplayName("CP-035 Las opciones tienen valores marcados por defecto al cargar la pagina")
    public void opcionesMarcadasPorDefecto() {
        ProductoPage producto = new ProductoPage(driver);
        producto.abrirUrl(URL_PC);
        producto.seleccionarHdd(ID_HDD_320);
        producto.agregarAlCarrito(ID_BTN_PC);

        String notificacion = producto.obtenerTextoNotificacion();
        assertTrue(notificacion.contains(TEXTO_AGREGADO),
                "Se espera que las opciones por defecto permitan agregar al carrito. Notificacion: " + notificacion);
    }

    @Test
    @DisplayName("CP-036 Agregar producto con opciones personalizadas al carrito")
    public void agregarConOpcionesPersonalizadas() {
        ProductoPage producto = new ProductoPage(driver);
        producto.abrirUrl(URL_PC);

        producto.seleccionarProcesador(VAL_PROC_FAST);
        producto.seleccionarRam(VAL_RAM_4GB);
        producto.seleccionarHdd(ID_HDD_400);
        producto.seleccionarSistemaOperativo(ID_OS_WIN7);
        producto.configurarSoftware(ID_MS_OFFICE, false);
        producto.agregarAlCarrito(ID_BTN_PC);

        String notificacion = producto.obtenerTextoNotificacion();
        assertTrue(notificacion.contains(TEXTO_AGREGADO),
                "Con opciones personalizadas se esperaba confirmacion, se obtuvo: " + notificacion);
    }

    @Test
    @DisplayName("CP-037 Agregar producto configurable con otra combinacion de opciones")
    public void agregarConOtraCombinacion() {
        ProductoPage producto = new ProductoPage(driver);
        producto.abrirUrl(URL_PC);

        producto.seleccionarProcesador(VAL_PROC_SLOW);
        producto.seleccionarRam(VAL_RAM_8GB);
        producto.seleccionarHdd(ID_HDD_320);
        producto.seleccionarSistemaOperativo(ID_OS_WIN10);
        producto.configurarSoftware(ID_MS_OFFICE, true);
        producto.agregarAlCarrito(ID_BTN_PC);

        String notificacion = producto.obtenerTextoNotificacion();
        assertTrue(notificacion.contains(TEXTO_AGREGADO),
                "Con opciones alternativas se esperaba confirmacion, se obtuvo: " + notificacion);
    }

    @Test
    @DisplayName("CP-038 El encabezado refleja el incremento al agregar producto configurable")
    public void encabezadoIncrementaConProductoConfigurable() {
        ProductoPage producto = new ProductoPage(driver);
        producto.abrirUrl(URL_PC);
        String cantidadAntes = producto.obtenerCantidadCarritoEncabezado();

        producto.seleccionarHdd(ID_HDD_320);
        producto.agregarAlCarrito(ID_BTN_PC);

        String cantidadDespues = producto.obtenerCantidadCarritoEncabezado();
        assertFalse(cantidadAntes.equals(cantidadDespues),
                "El contador del carrito debe incrementar despues de agregar el producto configurable");
    }
}
