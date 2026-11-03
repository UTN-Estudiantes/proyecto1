package cr.ac.utn.proyecto.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cr.ac.utn.proyecto.pages.CambiarClavePage;
import cr.ac.utn.proyecto.pages.CuentaPage;
import cr.ac.utn.proyecto.pages.InicioPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("RF-10 Cuenta del usuario")
class CuentaTest extends PruebaTienda {

    @Test
    @DisplayName("CP-038 Editar el nombre en la informacion de la cuenta")
    void editarInformacion() {
        registrarUsuarioNuevo();
        CuentaPage cuenta = new InicioPage(driver).irAMiCuenta()
                .cambiarNombre("Mariana", "Solis");

        assertEquals("Mariana", cuenta.irAMiCuenta().obtenerNombre());
    }

    @Test
    @DisplayName("CP-039 Cambiar la contrasena y entrar con la nueva")
    void cambiarContrasena() {
        Usuario usuario = registrarUsuarioNuevo();
        String claveNueva = "Nueva12345";

        CambiarClavePage cambio = new InicioPage(driver).irAMiCuenta()
                .irACambiarClave()
                .cambiarClave(usuario.clave(), claveNueva);
        assertTrue(cambio.obtenerMensajeResultado().contains("Password was changed"));

        InicioPage inicio = cambio.cerrarSesion()
                .irALogin()
                .iniciarSesion(usuario.correo(), claveNueva);
        assertTrue(inicio.haySesionIniciada(), "Debe entrar con la contrasena nueva");
    }
}
