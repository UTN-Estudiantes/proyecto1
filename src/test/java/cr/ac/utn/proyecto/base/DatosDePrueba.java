package cr.ac.utn.proyecto.base;

/**
 * Datos compartidos por las pruebas. El sitio es público y compartido, por eso cada prueba usa un correo nuevo.
 */
public final class DatosDePrueba {

    public static final String NOMBRE = "Prueba";
    public static final String APELLIDO = "Qa";
    public static final String CLAVE_VALIDA = "Clave123";

    private DatosDePrueba() {
    }

    public static String correoUnico() {
        return "qa." + System.nanoTime() + "@example.com";
    }
}
