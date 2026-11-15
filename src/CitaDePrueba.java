import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Subclase mínima de Cita solo para las pruebas: como Cita es abstracta,
 * necesitamos una clase concreta para poder crear objetos.
 */
class CitaDePrueba extends Cita {

    private static final long serialVersionUID = 1L;

    CitaDePrueba(String cliente, LocalDate fecha, LocalTime inicio, int duracion)
            throws ArgumentoException {
        super(cliente, fecha, inicio, duracion);
    }

    @Override
    public Cita conInicio(LocalTime nuevoInicio) throws ArgumentoException {
        return new CitaDePrueba(getCliente(), getFecha(), nuevoInicio, getDuracion());
    }
}
