package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.base.DatosPrueba;
import cr.ac.utn.proyecto.pages.CarritoPage;
import cr.ac.utn.proyecto.pages.InicioPage;
import cr.ac.utn.proyecto.pages.PedidoCompletadoPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Pruebas de RF-09 Proceso de compra. */
class CompraTest extends BaseTest {

    private static final String CATEGORIA = "notebooks";
    private static final String PRODUCTO = "14.1-inch Laptop";

    @Test
    @DisplayName("CP-032 Compra completa como invitado")
    void compraCompletaComoInvitado() {
        new InicioPage(driver).abrir()
                .irACategoria(CATEGORIA)
                .agregarAlCarrito(PRODUCTO);

        PedidoCompletadoPage pedido = new InicioPage(driver).irAlCarrito()
                .aceptarTerminos()
                .irAlCheckout()
                .continuarComoInvitado()
                .llenarFacturacion("Ana", "Prueba", DatosPrueba.correoUnico(), "United States", "Alabama",
                        "Alajuela", "Calle 1", "20101", "88888888")
                .continuarFacturacion()
                .continuarDireccionEnvio()
                .continuarMetodoEnvio()
                .continuarMetodoPago()
                .continuarInformacionPago()
                .confirmar();

        assertEquals("Your order has been successfully processed!", pedido.mensajeConfirmacion());
        assertFalse(pedido.numeroOrden().isEmpty());
    }

    @Test
    @DisplayName("CP-034 Checkout sin aceptar los términos de servicio")
    void checkoutSinAceptarLosTerminosDeServicio() {
        new InicioPage(driver).abrir()
                .irACategoria(CATEGORIA)
                .agregarAlCarrito(PRODUCTO);
        CarritoPage carrito = new InicioPage(driver).irAlCarrito();

        carrito.irAlCheckout();

        assertEquals("Please accept the terms of service before the next step.", carrito.avisoTerminos());
        assertTrue(driver.getCurrentUrl().endsWith("/cart"));
    }
}
