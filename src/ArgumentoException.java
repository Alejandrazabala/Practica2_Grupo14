/**
 * Checked exception que salta con un argumento null o un valor sin sentido.
 * La lanzan el constructor de Cita y los métodos de Agenda.
 */
public class ArgumentoException extends Exception {

    private static final long serialVersionUID = 1L;

    public ArgumentoException(String mensaje) {
        super(mensaje);
    }
}
