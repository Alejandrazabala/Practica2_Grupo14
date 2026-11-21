import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Consulta veterinaria. Cuesta 35,00 euros. Su duración y la especialidad que
 * exige dependen de la especie de la mascota.
 */
public class Consulta extends CitaVeterinaria {

    private static final long serialVersionUID = 1L;

    /** Precio de una consulta, en céntimos. */
    public static final int PRECIO = 3500;

    private final String motivo;

    /**
     * @throws ArgumentoException si algún dato es nulo o el motivo está vacío
     */
    public Consulta(Mascota mascota, Veterinario veterinario, LocalDate fecha,
                    LocalTime inicio, String motivo) throws ArgumentoException {
        super(mascota, veterinario, fecha, inicio, duracionDe(mascota));
        this.motivo = texto(motivo, "El motivo", false);
    }

    /** La duración de la consulta depende de la especie de la mascota. */
    private static int duracionDe(Mascota mascota) throws ArgumentoException {
        if (mascota == null) {
            throw new ArgumentoException("La mascota no puede ser nula");
        }
        return mascota.getEspecie().getDuracionConsulta();
    }

    public String getMotivo() {
        return motivo;
    }

    @Override
    public int precio() {
        return PRECIO;
    }

    @Override
    public String getTipo() {
        return "Consulta";
    }

    @Override
    public Especialidad especialidadRequerida() {
        return getMascota().getEspecie().getEspecialidad();
    }

    @Override
    public String descripcion() {
        return "Motivo: " + motivo;
    }

    /** Devuelve una Consulta (subtipo de Cita) que empieza a otra hora. */
    @Override
    public Consulta conInicio(LocalTime nuevoInicio) throws ArgumentoException {
        return new Consulta(getMascota(), getVeterinario(), getFecha(), nuevoInicio, motivo);
    }
}
