// Responsable: Melanni Michel Díaz Ugalde
package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.base.GeneradorDatos;
import cr.ac.utn.proyecto.pages.PagoPage;
import cr.ac.utn.proyecto.pages.ProductoPage;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CompraTest extends BaseTest {

        private static final String RUTA_LAPTOP = "141-inch-laptop";
        private static final String NOMBRE = "Prueba";
        private static final String APELLIDO = "Equipo";
        private static final String PAIS = "Costa Rica";
        private static final String CIUDAD = "Alajuela";
        private static final String DIRECCION = "Calle de prueba 123";
        private static final String CODIGO_POSTAL = "20101";
        private static final String TELEFONO = "88888888";

        @Test
        @DisplayName("CP-031 Compra completa como invitado")
        void compraCompletaComoInvitado() {

                // Arrange (Preparación)

                // Se agrega la laptop al carrito y se abre el carrito
                new ProductoPage(driver)
                                .abrir(RUTA_LAPTOP)
                                .agregarAlCarrito()
                                .irAlCarrito();
                PagoPage pago = new PagoPage(driver);

                // Act (Acción)

                // Se aceptan los términos, se pulsa Checkout y se compra como invitado
                pago.aceptarTerminos().pulsarCheckout().elegirComprarComoInvitado();

                // Se llena la facturación con un correo único y se avanza por envío, pago y
                // confirmación
                pago.llenarFacturacion(NOMBRE, APELLIDO, GeneradorDatos.correoUnico(), PAIS, CIUDAD,
                DIRECCION, CODIGO_POSTAL, TELEFONO)
                .continuarFacturacion()
                .continuarDireccionEnvio()
                .continuarMetodoEnvio()
                .continuarMetodoPago()
                .continuarInformacionPago()
                .confirmarOrden();

                // Assert (Comprobación)

                // Se verifica el mensaje de orden procesada y que se muestre el número de orden
                assertTrue(pago.mensajeCompraExitosa().contains("Your order has been successfully processed!"),
                                "El mensaje de orden procesada no coincide. Texto real: "
                                                + pago.mensajeCompraExitosa());
                assertTrue(pago.numeroOrden().contains("Order number"),
                                "No aparece el número de orden. Texto real: " + pago.numeroOrden());
        }

        @Test
        @DisplayName("CP-032 Checkout sin aceptar términos de servicio")
        void checkoutSinAceptarTerminos() {

                // Arrange (Preparación)

                // Se agrega la laptop al carrito y se abre el carrito
                new ProductoPage(driver)
                                .abrir(RUTA_LAPTOP)
                                .agregarAlCarrito()
                                .irAlCarrito();
                PagoPage pago = new PagoPage(driver);

                // Act (Acción)

                // Se pulsa Checkout sin marcar la casilla de términos
                pago.pulsarCheckout();

                // Assert (Comprobación)

                // Se verifica el aviso de términos y que el usuario siga en el carrito
                String aviso = pago.avisoTerminos();
                assertTrue(aviso.contains("terms of service"),
                                "El aviso de términos no coincide. Texto real: " + aviso);
                assertTrue(pago.urlActual().contains("cart"),
                                "El sitio avanzó al checkout sin aceptar los términos. URL real: " + pago.urlActual());
        }

        @Test
        @DisplayName("CP-033 Facturación con campos obligatorios vacíos")
        void facturacionConCamposVacios() {

                // Arrange (Preparación)

                // Se llega al formulario de facturación como invitado
                new ProductoPage(driver)
                                .abrir(RUTA_LAPTOP)
                                .agregarAlCarrito()
                                .irAlCarrito();
                PagoPage pago = new PagoPage(driver);
                pago.aceptarTerminos().pulsarCheckout().elegirComprarComoInvitado();

                // Act (Acción)

                // Se pulsa Continue sin escribir nada
                pago.continuarFacturacion();

                // Assert (Comprobación)

                // Se verifican los avisos de campos obligatorios y que no se pase al siguiente
                // paso
                List<String> avisos = pago.avisosFacturacion();
                assertTrue(avisos.contains("First name is required."),
                                "Falta el aviso de nombre obligatorio. Avisos reales: " + avisos);
                assertTrue(avisos.contains("Email is required."),
                                "Falta el aviso de correo obligatorio. Avisos reales: " + avisos);
                assertFalse(avisos.isEmpty(), "No se mostró ningún aviso de campo obligatorio");
        }
}
