import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Subclase mínima de CitaVeterinaria solo para las pruebas: como
 * CitaVeterinaria es abstracta, necesitamos una clase concreta para crear
 * objetos. Cuesta 50,00 euros y la especialidad que exige se elige al crearla.
 */
class CitaVeterinariaDePrueba extends CitaVeterinaria {

    private static final long serialVersionUID = 1L;

    private final Especialidad requerida;

    CitaVeterinariaDePrueba(Mascota mascota, Veterinario veterinario, LocalDate fecha,
                            LocalTime inicio, int duracion, Especialidad requerida) throws ArgumentoException {
        super(mascota, veterinario, fecha, inicio, duracion);
        this.requerida = requerida;
    }

    @Override
    public int precio() {
        return 5000;
    }

    @Override
    public String getTipo() {
        return "Prueba";
    }

    @Override
    public Especialidad especialidadRequerida() {
        return requerida;
    }

    @Override
    public String descripcion() {
        return "cita de prueba";
    }

    @Override
    public Cita conInicio(LocalTime nuevoInicio) throws ArgumentoException {
        return new CitaVeterinariaDePrueba(getMascota(), getVeterinario(), getFecha(),
                nuevoInicio, getDuracion(), requerida);
    }
}
