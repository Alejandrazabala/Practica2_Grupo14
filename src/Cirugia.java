import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

/**
 * Cirugía. Dura de 30 a 240 minutos, en tramos de 30. Su precio depende de la
 * duración y del peso de la mascota (anestesia). La atiende un veterinario con
 * especialidad CIRUGIA.
 */
public class Cirugia extends CitaVeterinaria {

    private static final long serialVersionUID = 1L;

    /** Precio por hora de cirugía, en céntimos. */
    public static final int PRECIO_HORA = 9000;
    /** Precio de la anestesia por kilo de peso, en céntimos. */
    public static final int ANESTESIA_POR_KG = 150;
    /** Hora límite: la cirugía debe terminar como muy tarde a esta hora. */
    public static final LocalTime FIN_MANANA = LocalTime.of(14, 0);
    public static final int DURACION_MINIMA = 30;
    public static final int DURACION_MAXIMA = 240;

    private final String intervencion;

    /**
     * @param duracion duración en minutos
     * @throws ArgumentoException si algún dato es nulo o la intervención está vacía
     */
    public Cirugia(Mascota mascota, Veterinario veterinario, LocalDate fecha,
                   LocalTime inicio, int duracion, String intervencion) throws ArgumentoException {
        super(mascota, veterinario, fecha, inicio, duracion);
        this.intervencion = texto(intervencion, "La intervención", false);
    }

    public String getIntervencion() {
        return intervencion;
    }

    /**
     * PRECIO_HORA * duracion / 60 + ANESTESIA_POR_KG * peso / 1000
     * (el peso está en gramos).
     */
    @Override
    public int precio() {
        return PRECIO_HORA * getDuracion() / 60
                + ANESTESIA_POR_KG * getMascota().getPeso() / 1000;
    }

    @Override
    public String getTipo() {
        return "Cirugía";
    }

    @Override
    public Especialidad especialidadRequerida() {
        return Especialidad.CIRUGIA;
    }

    @Override
    public String descripcion() {
        return "Intervención: " + intervencion;
    }

    /**
     * Normas propias de la cirugía: dura de 30 a 240 minutos en tramos de 30,
     * termina como muy tarde a las 14:00 y se programa al menos el día anterior.
     */
    @Override
    protected void comprobarNormasPropias(LocalDateTime ahora) throws ClinicaException {
        int d = getDuracion();
        if (d < DURACION_MINIMA || d > DURACION_MAXIMA || d % 30 != 0) {
            throw new ClinicaException("La cirugía debe durar entre " + DURACION_MINIMA
                    + " y " + DURACION_MAXIMA + " minutos, en tramos de 30");
        }
        if (getFin().isAfter(FIN_MANANA)) {
            throw new ClinicaException("La cirugía debe terminar como muy tarde a las "
                    + FIN_MANANA);
        }
        if (!getFecha().isAfter(ahora.toLocalDate())) {
            throw new ClinicaException("La cirugía debe programarse al menos el día anterior");
        }
    }

    /** Devuelve una Cirugia (subtipo de Cita) que empieza a otra hora. */
    @Override
    public Cirugia conInicio(LocalTime nuevoInicio) throws ArgumentoException {
        return new Cirugia(getMascota(), getVeterinario(), getFecha(), nuevoInicio,
                getDuracion(), intervencion);
    }
}

