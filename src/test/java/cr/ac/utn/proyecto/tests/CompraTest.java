package cr.ac.utn.proyecto.tests;

import static cr.ac.utn.proyecto.base.DatosDePrueba.APELLIDO;
import static cr.ac.utn.proyecto.base.DatosDePrueba.NOMBRE;
import static cr.ac.utn.proyecto.base.DatosDePrueba.correoUnico;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.pages.PaginaCarrito;
import cr.ac.utn.proyecto.pages.PaginaInicio;
import cr.ac.utn.proyecto.pages.PaginaPago;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** RF-09: proceso de compra (checkout). */
class CompraTest extends BaseTest {

    private static final String PRODUCTO = "14.1-inch Laptop";

    private PaginaCarrito carritoConElProducto() {
        return new PaginaInicio(driver).abrir()
                .buscar("laptop")
                .abrirProducto(PRODUCTO)
                .agregarAlCarrito()
                .irAlCarrito();
    }

    @Test
    @DisplayName("CP-036 Compra completa como invitado")
    void compraCompletaComoInvitado() {
        PaginaPago pago = carritoConElProducto()
                .aceptarTerminos()
                .irAPagoComoInvitado()
                .completarFacturacion(NOMBRE, APELLIDO, correoUnico(), "Costa Rica",
                        "San José", "Avenida Central 100", "10101", "22223333")
                .continuarDeFacturacion()
                .continuarDeDireccionDeEnvio()
                .elegirMetodoDeEnvio("Ground")
                .continuarDeMetodoDeEnvio()
                .elegirMetodoDePago("Cash On Delivery")
                .continuarDeMetodoDePago()
                .continuarDeInformacionDePago()
                .confirmarOrden();

        assertEquals("Your order has been successfully processed!", pago.obtenerMensajeDeOrdenProcesada());
    }

    @Test
    @DisplayName("CP-038 Checkout sin aceptar los términos de servicio")
    void checkoutSinAceptarTerminos() {
        PaginaCarrito carrito = carritoConElProducto().intentarIrAPago();

        assertTrue(carrito.obtenerAvisoDeTerminos().contains("Please accept the terms of service before the next step."));
        assertTrue(driver.getCurrentUrl().endsWith("/cart"), "No se debe avanzar al checkout");
    }

    @Test
    @DisplayName("CP-039 Facturación con campos obligatorios vacíos")
    void facturacionConCamposVacios() {
        PaginaPago pago = carritoConElProducto()
                .aceptarTerminos()
                .irAPagoComoInvitado()
                .continuarDeFacturacion();

        List<String> errores = pago.obtenerErroresDeFacturacion();

        assertFalse(errores.isEmpty(), "Deben indicarse los campos obligatorios sin completar");
        assertTrue(errores.contains("First name is required."));
        assertTrue(errores.contains("Last name is required."));
        assertTrue(errores.contains("Email is required."));
    }
}
