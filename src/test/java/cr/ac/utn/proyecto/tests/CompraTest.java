package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cr.ac.utn.proyecto.pages.CarritoPage;
import cr.ac.utn.proyecto.pages.CheckoutPage;
import cr.ac.utn.proyecto.pages.InicioPage;
import cr.ac.utn.proyecto.pages.OrdenCompletadaPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("RF-09 Proceso de compra")
class CompraTest extends PruebaTienda {

    private static final String LAPTOP_SLUG = "141-inch-laptop";

    /** Usuario nuevo con sesion y la laptop en el carrito: punto de partida de cada compra. */
    private CarritoPage carritoConProductoDeUsuarioNuevo() {
        registrarUsuarioNuevo();
        return new InicioPage(driver).abrirProducto(LAPTOP_SLUG)
                .agregarAlCarrito()
                .irAlCarrito();
    }

    @Test
    @DisplayName("CP-034 Compra completa de un usuario registrado")
    void compraCompleta() {
        OrdenCompletadaPage orden = carritoConProductoDeUsuarioNuevo()
                .aceptarTerminos()
                .pagar()
                .llenarFacturacion("United States", "Ciudad Quesada", "Calle 1, Avenida 2", "21001", "88887777")
                .continuarDesdeFacturacion()
                .completarPasosRestantes();

        assertAll(
                () -> assertTrue(orden.obtenerMensaje().contains("Your order has been successfully processed!")),
                () -> assertTrue(orden.obtenerDetalles().contains("Order number")),
                () -> assertTrue(orden.obtenerUrl().contains("/checkout/completed")));
    }

    @Test
    @DisplayName("CP-035 Pagar sin aceptar los terminos de servicio")
    void pagarSinAceptarTerminos() {
        CarritoPage carrito = carritoConProductoDeUsuarioNuevo()
                .pagarSinAceptarTerminos();

        assertAll(
                () -> assertTrue(carrito.obtenerAvisoTerminos().contains("terms of service")),
                () -> assertTrue(carrito.obtenerUrl().contains("/cart"), "Debe quedarse en el carrito"));
    }

    @Test
    @DisplayName("CP-036 Direccion de facturacion sin ciudad no avanza al siguiente paso")
    void facturacionSinCiudad() {
        CheckoutPage pago = carritoConProductoDeUsuarioNuevo()
                .aceptarTerminos()
                .pagar()
                .llenarFacturacion("United States", "", "Calle 1, Avenida 2", "21001", "88887777")
                .continuarDesdeFacturacion();

        String error = pago.obtenerErrorFacturacion();
        assertAll(
                () -> assertTrue(error.toLowerCase().contains("city"), "Mensaje real: " + error),
                () -> assertTrue(pago.sigueEnFacturacion(), "No debe avanzar al paso de envio"));
    }
}
