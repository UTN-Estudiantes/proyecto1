package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.pages.PaginaCarrito;
import cr.ac.utn.proyecto.pages.PaginaInicio;
import cr.ac.utn.proyecto.pages.PaginaProducto;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** RF-06: opciones de producto configurable (Build your own computer). */
class OpcionesProductoTest extends BaseTest {

    private static final String PRODUCTO = "Build your own computer";

    private PaginaProducto abrirProductoConfigurable() {
        return new PaginaInicio(driver).abrir().buscar(PRODUCTO).abrirProducto(PRODUCTO);
    }

    @Test
    @DisplayName("CP-028 Agregar Build your own computer sin elegir el disco duro (HDD)")
    void agregarSinElegirElDisco() {
        PaginaProducto producto = abrirProductoConfigurable().agregarAlCarrito();

        assertTrue(producto.obtenerMensajeDeNotificacion().contains("Please select HDD"));
        assertEquals("(0)", producto.obtenerIndicadorCarrito());
    }

    @Test
    @DisplayName("CP-029 Agregar Build your own computer con las opciones obligatorias")
    void agregarConOpcionesObligatorias() {
        PaginaProducto producto = abrirProductoConfigurable()
                .elegirOpcion("Processor", "2.2 GHz Intel Pentium Dual-Core E2200")
                .elegirOpcion("RAM", "2 GB")
                .elegirOpcion("HDD", "320 GB")
                .elegirOpcion("OS", "Ubuntu")
                .agregarAlCarrito();

        assertTrue(producto.obtenerMensajeDeNotificacion().contains("The product has been added to your shopping cart"));
        assertEquals("(1)", producto.obtenerIndicadorCarrito());
    }

    @Test
    @DisplayName("CP-030 El precio cambia según el disco duro elegido")
    void elPrecioCambiaConElDisco() {
        PaginaCarrito carrito = abrirProductoConfigurable()
                .elegirOpcion("HDD", "320 GB")
                .agregarAlCarrito()
                .irAlCarrito();
        BigDecimal precioCon320 = carrito.obtenerPrecioUnitarioDelProducto(PRODUCTO);
        carrito.quitarProducto(PRODUCTO);

        PaginaCarrito carritoNuevo = abrirProductoConfigurable()
                .elegirOpcion("HDD", "400 GB")
                .agregarAlCarrito()
                .irAlCarrito();
        BigDecimal precioCon400 = carritoNuevo.obtenerPrecioUnitarioDelProducto(PRODUCTO);

        assertEquals(0, precioCon320.add(new BigDecimal("100.00")).compareTo(precioCon400),
                "El HDD de 400 GB suma 100.00 al precio unitario del carrito");
    }
}
