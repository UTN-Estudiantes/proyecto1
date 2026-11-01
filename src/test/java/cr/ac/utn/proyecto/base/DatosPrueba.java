package cr.ac.utn.proyecto.base;

/** Datos que deben ser distintos en cada ejecucion. */
public final class DatosPrueba {
    private DatosPrueba() {}

    public static String correoUnico() {
        return System.currentTimeMillis() + "@prueba.com";
    }
}
