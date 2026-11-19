import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Clínica veterinaria.
 *
 * NOTA SOBRE EL REPARTO POR HITOS:
 * Según el enunciado, Clinica pertenece al hito 3. En el hito 2 solo se
 * incluye la parte mínima que las clases de ese hito necesitan para compilar:
 *
 *  - APERTURA y CIERRE: Veterinario crea su Agenda con el horario de la
 *    clínica, y CitaVeterinaria.comprobarNormas comprueba que la cita empieza
 *    y termina dentro de ese horario.
 *  - abre(fecha): CitaVeterinaria.comprobarNormas lo usa para la norma de que
 *    la clínica cierra los domingos.
 *
 * Estas constantes y este metodo se definen aquí, y no se duplican en otras
 * clases, porque el diseño del enunciado los sitúa en Clinica.
 *
 * Por la misma razón, ClinicaException se ha creado en el hito 2: la lanza
 * comprobarNormas.
 *
 * El resto de la clase (listas de veterinarios, mascotas e historial,
 * programar, atender, cancelar, consultas e informe) NO se ha adelantado y se
 * implementará en el hito 3, manteniendo estos tres miembros tal cual.
 */
public class Clinica {

    public static final LocalTime APERTURA = LocalTime.of(9, 0);
    public static final LocalTime CIERRE = LocalTime.of(20, 0);

    /** La clínica abre de lunes a sábado y cierra los domingos. */
    public static boolean abre(LocalDate fecha) {
        return fecha != null && fecha.getDayOfWeek() != DayOfWeek.SUNDAY;
    }
}