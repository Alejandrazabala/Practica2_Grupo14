import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

/**
 * Cita de la clínica veterinaria: una Cita cuyo cliente es el identificador
 * de una mascota y que atiende un veterinario. Cada tipo de cita (consulta,
 * cirugía...) define su precio, su tipo y la especialidad que exige.
 *
 * Una cita pendiente tiene resultado null. Cuando termina, se llama a
 * terminar(resultado, notas).
 */
public abstract class CitaVeterinaria extends Cita {

    private static final long serialVersionUID = 1L;

    private final Mascota mascota;
    private final Veterinario veterinario;
    private Resultado resultado;
    private String notas;

    /**
     * @throws ArgumentoException si la mascota o el veterinario son nulos, o si
     *                            los datos de la cita no son válidos
     */
    protected CitaVeterinaria(Mascota mascota, Veterinario veterinario, LocalDate fecha,
                              LocalTime inicio, int duracion) throws ArgumentoException {
        super(idDe(mascota), fecha, inicio, duracion);
        if (veterinario == null) {
            throw new ArgumentoException("El veterinario no puede ser nulo");
        }
        this.mascota = mascota;
        this.veterinario = veterinario;
        this.resultado = null;
        this.notas = "";
    }

    /** Identificador de la mascota, que es el "cliente" de la cita. */
    private static String idDe(Mascota mascota) throws ArgumentoException {
        if (mascota == null) {
            throw new ArgumentoException("La mascota no puede ser nula");
        }
        return mascota.getId();
    }

    /**
     * Comprueba un texto y lo devuelve sin espacios sobrantes.
     *
     * @param s             el texto
     * @param que           cómo se llama el texto, para el mensaje de error
     * @param vacioPermitido si es false, el texto no puede estar vacío
     * @throws ArgumentoException si el texto es nulo, o vacío cuando no se permite
     */
    protected static String texto(String s, String que, boolean vacioPermitido)
            throws ArgumentoException {
        if (s == null) {
            throw new ArgumentoException(que + " no puede ser nulo");
        }
        String limpio = s.trim();
        if (!vacioPermitido && limpio.isEmpty()) {
            throw new ArgumentoException(que + " no puede estar vacío");
        }
        return limpio;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    /** Resultado de la cita, o null si todavía está pendiente. */
    public Resultado getResultado() {
        return resultado;
    }

    public String getNotas() {
        return notas;
    }

    /** Precio de la cita, en céntimos. */
    public abstract int precio();

    /** Nombre del tipo de cita: «Consulta» o «Cirugía». */
    public abstract String getTipo();

    /** Especialidad que debe tener el veterinario que la atienda. */
    public abstract Especialidad especialidadRequerida();

    /** Texto con los datos propios de este tipo de cita. */
    public abstract String descripcion();

    /**
     * Método plantilla: comprueba las normas comunes a todas las citas, en este
     * orden (domingo, horario, pasado, especialidad), y termina llamando a
     * comprobarNormasPropias, que añade las normas de cada tipo de cita.
     *
     * @param ahora fecha y hora actuales
     * @throws ClinicaException si la cita incumple alguna norma
     */
    public final void comprobarNormas(LocalDateTime ahora) throws ClinicaException {
        if (ahora == null) {
            throw new ClinicaException("Se necesita la fecha y hora actuales para comprobar las normas");
        }
        // 1. Domingo
        if (!Clinica.abre(getFecha())) {
            throw new ClinicaException("La clínica está cerrada los domingos");
        }
        // 2. Horario
        if (getInicio().isBefore(Clinica.APERTURA) || getFin().isAfter(Clinica.CIERRE)) {
            throw new ClinicaException("La cita debe empezar y terminar entre las "
                    + Clinica.APERTURA + " y las " + Clinica.CIERRE);
        }
        // 3. Pasado
        if (LocalDateTime.of(getFecha(), getInicio()).isBefore(ahora)) {
            throw new ClinicaException("No se puede programar una cita en el pasado");
        }
        // 4. Especialidad
        if (!veterinario.puedeAtender(especialidadRequerida())) {
            throw new ClinicaException("El veterinario " + veterinario.getNombre()
                    + " no tiene la especialidad " + especialidadRequerida());
        }
        // 5. Normas propias de cada tipo de cita
        comprobarNormasPropias(ahora);
    }

    /**
     * Normas propias de cada tipo de cita. Por defecto no hace nada; las
     * subclases la redefinen si tienen normas adicionales.
     */
    protected void comprobarNormasPropias(LocalDateTime ahora) throws ClinicaException {
    }

    /** Texto con los datos comunes a todas las citas veterinarias. */
    protected String camposComunes() {
        return getMascota().getNombre() + " (" + getMascota().getId() + ") con "
                + veterinario.getNombre() + " el " + getFecha() + " de " + getInicio()
                + " a " + getFin();
    }

    @Override
    public String toString() {
        String estado = "pendiente";
        if (resultado != null) {
            estado = resultado.toString();
        }
        return getTipo() + ": " + camposComunes() + " - " + descripcion() + " [" + estado + "]";
    }
}
