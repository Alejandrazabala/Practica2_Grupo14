import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * TAD Agenda: citas de un único recurso (aquí, un veterinario) ordenadas por
 * fecha y hora de inicio. La agenda abre todos los días entre {@code apertura}
 * y {@code cierre}. No sabe qué tipos de cita existen: solo trabaja con {@link Cita}.
 *
 * Normas:
 * - Una cita debe empezar y terminar dentro del horario de la agenda.
 * - Dos citas del mismo día no pueden solaparse.
 * - Un cliente tiene como mucho una cita por día.
 */
public class Agenda implements Serializable {

    private static final long serialVersionUID = 1L;

    private final LocalTime apertura;
    private final LocalTime cierre;
    private final ArrayList<Cita> citas;

    /**
     * @throws ArgumentoException si algún argumento es null o apertura no es anterior a cierre
     */
    public Agenda(LocalTime apertura, LocalTime cierre) throws ArgumentoException {
        if (apertura == null || cierre == null) {
            throw new ArgumentoException("La apertura y el cierre no pueden ser nulos");
        }
        if (!apertura.isBefore(cierre)) {
            throw new ArgumentoException("La apertura debe ser anterior al cierre");
        }
        this.apertura = apertura;
        this.cierre = cierre;
        this.citas = new ArrayList<>();
    }

    public LocalTime getApertura() {
        return apertura;
    }

    public LocalTime getCierre() {
        return cierre;
    }

    /** Minutos que dura la jornada. */
    public int getJornada() {
        return minutosEntre(apertura, cierre);
    }

    /**
     * Reserva una cita ya creada.
     *
     * @throws ArgumentoException si c es null, no cabe en el horario, se solapa con
     *                            otra cita o el cliente ya tiene cita ese día
     */
    public void reservar(Cita c) throws ArgumentoException {
        if (c == null) {
            throw new ArgumentoException("La cita no puede ser nula");
        }
        comprobarHorario(c);
        if (buscar(c.getCliente(), c.getFecha()) != null) {
            throw new ArgumentoException("El cliente " + c.getCliente()
                    + " ya tiene una cita el " + c.getFecha());
        }
        if (haySolape(c.getFecha(), c.getInicio(), c.getFin(), null)) {
            throw new ArgumentoException("La cita se solapa con otra de la agenda");
        }
        insertar(c);
    }

    /**
     * Cancela la cita de un cliente en una fecha.
     *
     * @throws ArgumentoException si algún argumento es inválido o el cliente no tiene cita ese día
     */
    public void cancelar(String cliente, LocalDate fecha) throws ArgumentoException {
        Cita c = getCitaDe(cliente, fecha);
        citas.remove(c);
    }

    /**
     * Retrasa la cita de un cliente. La cita nueva se crea con
     * {@link Cita#conInicio(LocalTime)}, así que conserva su tipo.
     * Si falla, la agenda no cambia.
     *
     * @throws ArgumentoException si minutos &lt;= 0, no existe la cita, o la cita retrasada
     *                            no cabe en el horario o se solapa con otra
     */
    public void retrasar(String cliente, LocalDate fecha, int minutos) throws ArgumentoException {
        if (minutos <= 0) {
            throw new ArgumentoException("Los minutos de retraso deben ser mayores que 0");
        }
        Cita actual = getCitaDe(cliente, fecha);
        if (!Cita.cabeEnElDia(actual.getInicio(), minutos)) {
            throw new ArgumentoException("La cita retrasada pasaría de medianoche");
        }
        Cita nueva = actual.conInicio(actual.getInicio().plusMinutes(minutos));
        comprobarHorario(nueva);
        if (haySolape(nueva.getFecha(), nueva.getInicio(), nueva.getFin(), actual)) {
            throw new ArgumentoException("La cita retrasada se solapa con otra de la agenda");
        }
        citas.remove(actual);
        insertar(nueva);
    }

    public int getNumCitas() {
        return citas.size();
    }

    /**
     * Cita en la posición i (empezando en 0) de la agenda ordenada por fecha y hora.
     *
     * @throws ArgumentoException si i está fuera de rango
     */
    public Cita getCitaEn(int i) throws ArgumentoException {
        if (i < 0 || i >= citas.size()) {
            throw new ArgumentoException("Posición de cita fuera de rango: " + i);
        }
        return citas.get(i);
    }

    /** Lista nueva con las citas de una fecha, ordenadas por hora. */
    public ArrayList<Cita> getCitasDel(LocalDate fecha) throws ArgumentoException {
        if (fecha == null) {
            throw new ArgumentoException("La fecha no puede ser nula");
        }
        ArrayList<Cita> resultado = new ArrayList<>();
        for (Cita c : citas) {
            if (c.getFecha().equals(fecha)) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    /**
     * Lista nueva con las citas entre dos fechas, ambas incluidas, en orden.
     *
     * @throws ArgumentoException si alguna fecha es null o desde es posterior a hasta
     */
    public ArrayList<Cita> getCitasEntre(LocalDate desde, LocalDate hasta)
            throws ArgumentoException {
        if (desde == null || hasta == null) {
            throw new ArgumentoException("Las fechas no pueden ser nulas");
        }
        if (desde.isAfter(hasta)) {
            throw new ArgumentoException("La fecha inicial no puede ser posterior a la final");
        }
        ArrayList<Cita> resultado = new ArrayList<>();
        for (Cita c : citas) {
            if (!c.getFecha().isBefore(desde) && !c.getFecha().isAfter(hasta)) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    public boolean tieneCita(String cliente, LocalDate fecha) throws ArgumentoException {
        return buscar(cliente, fecha) != null;
    }

    /**
     * @throws ArgumentoException si algún argumento es inválido o el cliente no tiene cita ese día
     */
    public Cita getCitaDe(String cliente, LocalDate fecha) throws ArgumentoException {
        Cita c = buscar(cliente, fecha);
        if (c == null) {
            throw new ArgumentoException("El cliente " + cliente + " no tiene cita el " + fecha);
        }
        return c;
    }

    /**
     * ¿Está libre el tramo de d minutos que empieza a la hora h? Un tramo que
     * se sale del horario de la agenda no está libre.
     */
    public boolean estaLibre(LocalDate fecha, LocalTime h, int d) throws ArgumentoException {
        if (fecha == null || h == null) {
            throw new ArgumentoException("La fecha y la hora no pueden ser nulas");
        }
        comprobarDuracion(d);
        if (!Cita.cabeEnElDia(h, d)) {
            return false;
        }
        LocalTime fin = h.plusMinutes(d);
        return !h.isBefore(apertura) && !fin.isAfter(cierre)
                && !haySolape(fecha, h, fin, null);
    }

    /** ¿Hay algún hueco de d minutos seguidos ese día? */
    public boolean hayHueco(LocalDate fecha, int d) throws ArgumentoException {
        return primerHueco(fecha, d) != null;
    }

    /**
     * Hora más temprana a la que empieza un hueco de d minutos seguidos ese día,
     * o {@code null} si no hay ninguno.
     */
    public LocalTime primerHueco(LocalDate fecha, int d) throws ArgumentoException {
        if (fecha == null) {
            throw new ArgumentoException("La fecha no puede ser nula");
        }
        comprobarDuracion(d);
        LocalTime cursor = apertura;
        for (Cita c : getCitasDel(fecha)) {
            if (minutosEntre(cursor, c.getInicio()) >= d) {
                return cursor;
            }
            if (c.getFin().isAfter(cursor)) {
                cursor = c.getFin();
            }
        }
        if (minutosEntre(cursor, cierre) >= d) {
            return cursor;
        }
        return null;
    }

    /**
     * Primer día, a partir de {@code desde} (incluido), con un hueco de d minutos.
     *
     * @throws ArgumentoException si desde es null, d &lt;= 0 o d es mayor que la jornada
     *                            (en ese caso nunca habría hueco)
     */
    public LocalDate primerDiaConHueco(LocalDate desde, int d) throws ArgumentoException {
        if (desde == null) {
            throw new ArgumentoException("La fecha no puede ser nula");
        }
        comprobarDuracion(d);
        if (d > getJornada()) {
            throw new ArgumentoException("La duración no cabe en una jornada");
        }
        LocalDate dia = desde;
        while (!hayHueco(dia, d)) {
            dia = dia.plusDays(1);
        }
        return dia;
    }

    /** Minutos de la jornada que no están ocupados por citas ese día. */
    public int getTiempoLibre(LocalDate fecha) throws ArgumentoException {
        int ocupado = 0;
        for (Cita c : getCitasDel(fecha)) {
            ocupado += c.getDuracion();
        }
        return getJornada() - ocupado;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Agenda ").append(apertura).append("-").append(cierre)
                .append(" (").append(citas.size()).append(" citas)");
        for (Cita c : citas) {
            sb.append("\n  ").append(c);
        }
        return sb.toString();
    }

    // ------------------------------------------------------------------
    // Métodos privados auxiliares
    // ------------------------------------------------------------------

    private static int minutosEntre(LocalTime a, LocalTime b) {
        return (b.toSecondOfDay() - a.toSecondOfDay()) / 60;
    }

    private static void comprobarDuracion(int d) throws ArgumentoException {
        if (d <= 0) {
            throw new ArgumentoException("La duración debe ser mayor que 0 minutos");
        }
    }

    /** Lanza excepción si la cita no empieza y termina dentro del horario. */
    private void comprobarHorario(Cita c) throws ArgumentoException {
        if (c.getInicio().isBefore(apertura) || c.getFin().isAfter(cierre)) {
            throw new ArgumentoException("La cita debe estar dentro del horario "
                    + apertura + "-" + cierre);
        }
    }

    /** Busca la cita del cliente en la fecha; null si no existe. */
    private Cita buscar(String cliente, LocalDate fecha) throws ArgumentoException {
        if (cliente == null || cliente.isBlank()) {
            throw new ArgumentoException("El cliente no puede ser nulo ni vacío");
        }
        if (fecha == null) {
            throw new ArgumentoException("La fecha no puede ser nula");
        }
        for (Cita c : citas) {
            if (c.getFecha().equals(fecha) && c.getCliente().equals(cliente)) {
                return c;
            }
        }
        return null;
    }

    /** ¿Algún tramo [inicio, fin) de ese día choca con una cita (distinta de ignorada)? */
    private boolean haySolape(LocalDate fecha, LocalTime inicio, LocalTime fin, Cita ignorada) {
        for (Cita x : citas) {
            if (x != ignorada && x.getFecha().equals(fecha)
                    && inicio.isBefore(x.getFin()) && x.getInicio().isBefore(fin)) {
                return true;
            }
        }
        return false;
    }

    /** Inserta manteniendo el orden por fecha y hora de inicio. */
    private void insertar(Cita nueva) {
        int i = 0;
        while (i < citas.size() && !vaAntes(nueva, citas.get(i))) {
            i++;
        }
        citas.add(i, nueva);
    }

    private static boolean vaAntes(Cita a, Cita b) {
        int cmp = a.getFecha().compareTo(b.getFecha());
        if (cmp != 0) {
            return cmp < 0;
        }
        return a.getInicio().isBefore(b.getInicio());
    }
}

