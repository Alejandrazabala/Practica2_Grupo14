
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Clase que representa la clínica veterinaria.
 * Define su horario de apertura y los días
 * en los que permanece abierta.
 */
public class Clinica {

    // Horario de apertura y cierre de la clínica
    public static final LocalTime APERTURA = LocalTime.of(9, 0);
    public static final LocalTime CIERRE = LocalTime.of(20, 0);

    /**
     * Comprueba si la clínica abre en una fecha determinada.
     * La clínica abre de lunes a sábado y cierra los domingos.
     *
     * @param fecha fecha que se desea comprobar
     * @return true si abre y false si es domingo
     */
    public static boolean abre(LocalDate fecha) {
        return fecha.getDayOfWeek() != DayOfWeek.SUNDAY;
    }
}
