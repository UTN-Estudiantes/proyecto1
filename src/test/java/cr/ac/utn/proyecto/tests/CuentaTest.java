package cr.ac.utn.proyecto.tests;

import static cr.ac.utn.proyecto.base.DatosDePrueba.APELLIDO;
import static cr.ac.utn.proyecto.base.DatosDePrueba.CLAVE_VALIDA;
import static cr.ac.utn.proyecto.base.DatosDePrueba.NOMBRE;
import static cr.ac.utn.proyecto.base.DatosDePrueba.correoUnico;
import static org.junit.jupiter.api.Assertions.assertEquals;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.pages.PaginaCuenta;
import cr.ac.utn.proyecto.pages.PaginaInicio;
import cr.ac.utn.proyecto.pages.PaginaRegistro.Genero;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** RF-10: gestión de la cuenta del usuario. */
class CuentaTest extends BaseTest {

    @Test
    @DisplayName("CP-040 Ver y editar la información de la cuenta")
    void editarLaInformacionDeLaCuenta() {
        PaginaCuenta cuenta = new PaginaInicio(driver).abrir()
                .irARegistro()
                .registrarUsuario(Genero.MASCULINO, NOMBRE, APELLIDO, correoUnico(), CLAVE_VALIDA, CLAVE_VALIDA)
                .continuar()
                .irAMiCuenta();
        assertEquals(NOMBRE, cuenta.obtenerNombre(), "Precondición: se muestra el nombre registrado");

        cuenta.cambiarNombre("Editado").guardarInformacion();

        assertEquals("Editado", cuenta.abrir().obtenerNombre());
    }
}
