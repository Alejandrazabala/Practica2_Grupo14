import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Cita abstracta de una agenda. Es inmutable: sus observadoras son final para
 * que ninguna subclase pueda romper el invariante.
 *
 * Invariante: cliente no nulo ni vacío, fecha e inicio no nulos, duración > 0
 * y la cita termina antes de medianoche.
 */
public abstract class Cita implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String cliente;
    private final LocalDate fecha;
    private final LocalTime inicio;
    private final int duracion;

    /**
     * @param cliente  identificador del cliente
     * @param fecha    día de la cita
     * @param inicio   hora de inicio
     * @param duracion duración en minutos
     * @throws ArgumentoException si algún argumento es null o imposible
     */
    protected Cita(String cliente, LocalDate fecha, LocalTime inicio, int duracion)
            throws ArgumentoException {
        if (cliente == null || cliente.isBlank()) {
            throw new ArgumentoException("El cliente no puede ser nulo ni vacío");
        }
        if (fecha == null) {
            throw new ArgumentoException("La fecha no puede ser nula");
        }
        if (inicio == null) {
            throw new ArgumentoException("La hora de inicio no puede ser nula");
        }
        if (duracion <= 0) {
            throw new ArgumentoException("La duración debe ser mayor que 0 minutos");
        }
        if (!cabeEnElDia(inicio, duracion)) {
            throw new ArgumentoException("La cita debe terminar antes de medianoche");
        }
        this.cliente = cliente;
        this.fecha = fecha;
        this.inicio = inicio;
        this.duracion = duracion;
    }

    /**
     * Indica si una actividad que empieza en {@code inicio} y dura {@code duracion}
     * minutos termina antes de medianoche (es decir, sin dar la vuelta al reloj).
     */
    static boolean cabeEnElDia(LocalTime inicio, int duracion) {
        return inicio.toSecondOfDay() + duracion * 60L < 24 * 3600L;
    }

    public final String getCliente() {
        return cliente;
    }

    public final LocalDate getFecha() {
        return fecha;
    }

    public final LocalTime getInicio() {
        return inicio;
    }

    public final int getDuracion() {
        return duracion;
    }

    public final LocalTime getFin() {
        return inicio.plusMinutes(duracion);
    }

    /**
     * Crea una copia de esta cita que empieza a otra hora. Cada tipo de cita
     * devuelve una copia de su propio tipo (polimorfismo).
     *
     * @throws ArgumentoException si la nueva hora es null o la copia no es válida
     */
    public abstract Cita conInicio(LocalTime nuevoInicio) throws ArgumentoException;

    @Override
    public String toString() {
        return cliente + " | " + fecha + " | " + inicio + "-" + getFin()
                + " (" + duracion + " min)";
    }
}
