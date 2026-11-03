package cr.ac.utn.proyecto.base;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Genera datos de prueba unicos para cada ejecucion.
 * El sitio rechaza correos repetidos, por eso cada registro usa la hora en milisegundos
 * mas un contador (dos pruebas en el mismo milisegundo no chocan).
 */
public final class DatosPrueba {

    private static final AtomicInteger CONTADOR = new AtomicInteger();

    /** Contrasena valida para el sitio (minimo 6 caracteres). */
    public static final String CLAVE_VALIDA = "Prueba123";

    private DatosPrueba() {}

    public static String correoUnico() {
        return "qa.utn." + System.currentTimeMillis() + "." + CONTADOR.incrementAndGet() + "@prueba.com";
    }
}
