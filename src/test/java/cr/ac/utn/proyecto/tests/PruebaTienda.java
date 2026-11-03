package cr.ac.utn.proyecto.tests;

import cr.ac.utn.proyecto.base.BaseTest;
import cr.ac.utn.proyecto.base.DatosPrueba;
import cr.ac.utn.proyecto.pages.InicioPage;

/**
 * Pasos comunes de las pruebas de la tienda. Cada prueba arranca con un navegador limpio
 * (BaseTest) y, si necesita un usuario, registra uno nuevo con correo unico:
 * asi ninguna prueba depende del orden ni de datos de otra ejecucion.
 */
abstract class PruebaTienda extends BaseTest {

    /** Datos del usuario creado para una prueba. */
    protected record Usuario(String nombre, String apellido, String correo, String clave) {}

    protected InicioPage abrirTienda() {
        return new InicioPage(driver).abrir();
    }

    /** Registra un usuario nuevo y deja la sesion iniciada. */
    protected Usuario registrarUsuarioNuevo() {
        Usuario usuario = new Usuario("Ana", "Prueba", DatosPrueba.correoUnico(), DatosPrueba.CLAVE_VALIDA);
        abrirTienda().irARegistro()
                .registrarUsuario(usuario.nombre(), usuario.apellido(), usuario.correo(), usuario.clave());
        return usuario;
    }
}
