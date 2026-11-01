package cr.ac.utn.proyecto.base;

/**
 * Genera datos que cambian en cada ejecución (el registro no acepta correos
 * repetidos).
 */
public final class GeneradorDatos {

    private GeneradorDatos() {}

    public static String correoUnico() {
        long horaActual = System.currentTimeMillis();
        String correo = "cuenta" + horaActual + "@ejemplo.com";
        return correo;
    }
}