
/**
 * Excepción comprobada que se utiliza cuando
 * una operación incumple las normas de la clínica.
 */
public class ClinicaException extends Exception {

    /**
     * Constructor que crea una excepción con un mensaje
     * que explica el motivo del error.
     *
     * @param mensaje descripción del error
     */
    public ClinicaException(String mensaje) {
        super(mensaje);
    }
}
