import java.io.Serializable;

/**
 * Veterinario de la clínica. Tiene su propia agenda, con el horario de la
 * clínica, y una especialidad que decide qué citas puede atender.
 */
public class Veterinario implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String codigo;
    private final String nombre;
    private final Especialidad especialidad;
    private final Agenda agenda;

    /**
     * @throws ArgumentoException si algún dato es nulo o está vacío
     */
    public Veterinario(String codigo, String nombre, Especialidad especialidad)
            throws ArgumentoException {
        if (codigo == null || codigo.isBlank()) {
            throw new ArgumentoException("El código del veterinario no puede ser nulo ni vacío");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new ArgumentoException("El nombre del veterinario no puede ser nulo ni vacío");
        }
        if (especialidad == null) {
            throw new ArgumentoException("La especialidad no puede ser nula");
        }
        this.codigo = codigo;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.agenda = new Agenda(Clinica.APERTURA, Clinica.CIERRE);
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public Agenda getAgenda() {
        return agenda;
    }

    /**
     * Un veterinario puede atender una cita si la especialidad exigida es
     * GENERAL o es la suya.
     */
    public boolean puedeAtender(Especialidad requerida) {
        return requerida == Especialidad.GENERAL || requerida == especialidad;
    }

    @Override
    public String toString() {
        return codigo + " " + nombre + " (" + especialidad + ")";
    }
}
