package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.pages.InicioPage;
import cr.ac.utn.proyecto.pages.ProductoPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Pruebas de RF-06 Productos con opciones. */
class ProductoConfigurableTest extends BaseTest {

    private static final String PRODUCTO = "build-your-own-computer";

    @Test
    @DisplayName("CP-029 Build your own computer sin elegir el HDD")
    void buildYourOwnComputerSinElegirElHdd() {
        ProductoPage producto = new ProductoPage(driver).abrir(PRODUCTO).agregarAlCarrito();

        assertEquals("Please select HDD", producto.mensajeNotificacion());
        assertEquals(0, new InicioPage(driver).cantidadEnCarrito());
    }

    @Test
    @DisplayName("CP-030 Build your own computer con las opciones obligatorias")
    void buildYourOwnComputerConLasOpcionesObligatorias() {
        ProductoPage producto = new ProductoPage(driver).abrir(PRODUCTO)
                .elegirOpcion("320 GB")
                .agregarAlCarrito();

        assertEquals("The product has been added to your shopping cart", producto.mensajeNotificacion());
        assertEquals(1, new InicioPage(driver).cantidadEnCarrito());
    }
}
