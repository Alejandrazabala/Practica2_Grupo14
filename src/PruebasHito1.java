import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

// Pruebas sencillas del hito 1: Cita, Agenda y ArgumentoException.
// Cada prueba muestra OK o FALLO. Al final se muestra un resumen.

void main() throws Exception {
    int total = 0;
    int fallos = 0;

    LocalDate lunes = LocalDate.of(2026, 10, 19);
    LocalDate martes = LocalDate.of(2026, 10, 20);
    LocalDate miercoles = LocalDate.of(2026, 10, 21);
    LocalTime apertura = LocalTime.of(9, 0);
    LocalTime cierre = LocalTime.of(20, 0);

    Agenda agenda = new Agenda(apertura, cierre);
    Cita c;

    // ==================================================================
    IO.println("===== PRUEBAS DE CITA =====");
    // ==================================================================

    // Prueba 1
    total++;
    IO.println("Prueba 1: el constructor guarda los datos y getFin suma la duración");
    c = new CitaDePrueba("M001", lunes, LocalTime.of(9, 0), 30);
    if (c.getCliente().equals("M001") && c.getFecha().equals(lunes)
            && c.getDuracion() == 30 && c.getFin().equals(LocalTime.of(9, 30))) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // Prueba 2
    total++;
    IO.println("Prueba 2: un cliente nulo lanza excepción");
    try {
        new CitaDePrueba(null, lunes, LocalTime.of(9, 0), 30);
        IO.println("  FALLO: debía lanzar ArgumentoException");
        fallos++;
    } catch (ArgumentoException e) {
        IO.println("  OK");
    }

    // Prueba 3
    total++;
    IO.println("Prueba 3: una duración de 0 minutos lanza excepción");
    try {
        new CitaDePrueba("M001", lunes, LocalTime.of(9, 0), 0);
        IO.println("  FALLO: debía lanzar ArgumentoException");
        fallos++;
    } catch (ArgumentoException e) {
        IO.println("  OK");
    }

    // Prueba 4
    total++;
    IO.println("Prueba 4: una cita que cruza la medianoche lanza excepción");
    try {
        new CitaDePrueba("M001", lunes, LocalTime.of(23, 30), 60);
        IO.println("  FALLO: debía lanzar ArgumentoException");
        fallos++;
    } catch (ArgumentoException e) {
        IO.println("  OK");
    }

    // Prueba 5
    total++;
    IO.println("Prueba 5: conInicio crea una copia a otra hora y del mismo tipo");
    c = new CitaDePrueba("M001", lunes, LocalTime.of(9, 0), 30);
    Cita copia = c.conInicio(LocalTime.of(11, 0));
    if (copia.getInicio().equals(LocalTime.of(11, 0))
            && c.getInicio().equals(LocalTime.of(9, 0))
            && copia instanceof CitaDePrueba) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // ==================================================================
    IO.println("");
    IO.println("===== PRUEBAS DE RESERVAR =====");
    // ==================================================================

    // Prueba 6
    total++;
    IO.println("Prueba 6: reservar añade la cita");
    agenda = new Agenda(apertura, cierre);
    agenda.reservar(new CitaDePrueba("M001", lunes, LocalTime.of(9, 0), 30));
    if (agenda.getNumCitas() == 1) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // Prueba 7
    total++;
    IO.println("Prueba 7: las citas quedan ordenadas por hora");
    agenda = new Agenda(apertura, cierre);
    agenda.reservar(new CitaDePrueba("M001", lunes, LocalTime.of(15, 0), 30));
    agenda.reservar(new CitaDePrueba("M002", lunes, LocalTime.of(10, 0), 30));
    if (agenda.getCitaEn(0).getCliente().equals("M002")
            && agenda.getCitaEn(1).getCliente().equals("M001")) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // Prueba 8
    total++;
    IO.println("Prueba 8: una cita que acaba después del cierre lanza excepción");
    agenda = new Agenda(apertura, cierre);
    try {
        agenda.reservar(new CitaDePrueba("M001", lunes, LocalTime.of(19, 45), 30));
        IO.println("  FALLO: debía lanzar ArgumentoException");
        fallos++;
    } catch (ArgumentoException e) {
        if (agenda.getNumCitas() == 0) {
            IO.println("  OK");
        } else {
            IO.println("  FALLO: la agenda cambió");
            fallos++;
        }
    }

    // Prueba 9
    total++;
    IO.println("Prueba 9: una cita que acaba justo al cierre es válida");
    agenda = new Agenda(apertura, cierre);
    agenda.reservar(new CitaDePrueba("M001", lunes, LocalTime.of(19, 30), 30));
    if (agenda.getNumCitas() == 1) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // Prueba 10
    total++;
    IO.println("Prueba 10: una cita que se solapa con otra lanza excepción");
    agenda = new Agenda(apertura, cierre);
    agenda.reservar(new CitaDePrueba("M001", lunes, LocalTime.of(10, 0), 60));
    try {
        agenda.reservar(new CitaDePrueba("M002", lunes, LocalTime.of(10, 30), 60));
        IO.println("  FALLO: debía lanzar ArgumentoException");
        fallos++;
    } catch (ArgumentoException e) {
        if (agenda.getNumCitas() == 1) {
            IO.println("  OK");
        } else {
            IO.println("  FALLO: la agenda cambió");
            fallos++;
        }
    }

    // Prueba 11
    total++;
    IO.println("Prueba 11: dos citas contiguas (una acaba cuando empieza la otra) son válidas");
    agenda = new Agenda(apertura, cierre);
    agenda.reservar(new CitaDePrueba("M001", lunes, LocalTime.of(9, 0), 30));
    agenda.reservar(new CitaDePrueba("M002", lunes, LocalTime.of(9, 30), 30));
    if (agenda.getNumCitas() == 2) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // Prueba 12
    total++;
    IO.println("Prueba 12: un cliente no puede tener dos citas el mismo día");
    agenda = new Agenda(apertura, cierre);
    agenda.reservar(new CitaDePrueba("M001", lunes, LocalTime.of(10, 0), 30));
    try {
        agenda.reservar(new CitaDePrueba("M001", lunes, LocalTime.of(15, 0), 30));
        IO.println("  FALLO: debía lanzar ArgumentoException");
        fallos++;
    } catch (ArgumentoException e) {
        IO.println("  OK");
    }

    // ==================================================================
    IO.println("");
    IO.println("===== PRUEBAS DE CANCELAR Y RETRASAR =====");
    // ==================================================================

    // Prueba 13
    total++;
    IO.println("Prueba 13: cancelar elimina la cita");
    agenda = new Agenda(apertura, cierre);
    agenda.reservar(new CitaDePrueba("M001", lunes, LocalTime.of(10, 0), 30));
    agenda.cancelar("M001", lunes);
    if (agenda.getNumCitas() == 0 && !agenda.tieneCita("M001", lunes)) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // Prueba 14
    total++;
    IO.println("Prueba 14: cancelar una cita que no existe lanza excepción");
    agenda = new Agenda(apertura, cierre);
    try {
        agenda.cancelar("M001", lunes);
        IO.println("  FALLO: debía lanzar ArgumentoException");
        fallos++;
    } catch (ArgumentoException e) {
        IO.println("  OK");
    }

    // Prueba 15
    total++;
    IO.println("Prueba 15: retrasar mueve la cita y conserva su tipo");
    agenda = new Agenda(apertura, cierre);
    agenda.reservar(new CitaDePrueba("M001", lunes, LocalTime.of(10, 0), 30));
    agenda.retrasar("M001", lunes, 60);
    c = agenda.getCitaDe("M001", lunes);
    if (c.getInicio().equals(LocalTime.of(11, 0)) && c instanceof CitaDePrueba
            && agenda.getNumCitas() == 1) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // Prueba 16
    total++;
    IO.println("Prueba 16: retrasar más allá del cierre lanza excepción y la cita no cambia");
    agenda = new Agenda(apertura, cierre);
    agenda.reservar(new CitaDePrueba("M001", lunes, LocalTime.of(10, 0), 30));
    try {
        agenda.retrasar("M001", lunes, 600);
        IO.println("  FALLO: debía lanzar ArgumentoException");
        fallos++;
    } catch (ArgumentoException e) {
        if (agenda.getCitaDe("M001", lunes).getInicio().equals(LocalTime.of(10, 0))) {
            IO.println("  OK");
        } else {
            IO.println("  FALLO: la cita cambió");
            fallos++;
        }
    }

    // Prueba 17
    total++;
    IO.println("Prueba 17: retrasar encima de otra cita lanza excepción");
    agenda = new Agenda(apertura, cierre);
    agenda.reservar(new CitaDePrueba("M001", lunes, LocalTime.of(10, 0), 30));
    agenda.reservar(new CitaDePrueba("M002", lunes, LocalTime.of(11, 0), 30));
    try {
        agenda.retrasar("M001", lunes, 45);
        IO.println("  FALLO: debía lanzar ArgumentoException");
        fallos++;
    } catch (ArgumentoException e) {
        IO.println("  OK");
    }

    // ==================================================================
    IO.println("");
    IO.println("===== PRUEBAS DE CONSULTAS =====");
    // ==================================================================

    // Prueba 18
    total++;
    IO.println("Prueba 18: getCitasDel devuelve solo las citas de esa fecha");
    agenda = new Agenda(apertura, cierre);
    agenda.reservar(new CitaDePrueba("M001", lunes, LocalTime.of(10, 0), 30));
    agenda.reservar(new CitaDePrueba("M002", lunes, LocalTime.of(11, 0), 30));
    agenda.reservar(new CitaDePrueba("M003", martes, LocalTime.of(10, 0), 30));
    ArrayList<Cita> citasLunes = agenda.getCitasDel(lunes);
    if (citasLunes.size() == 2) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // Prueba 19
    total++;
    IO.println("Prueba 19: getCitasEntre incluye las dos fechas extremas");
    agenda = new Agenda(apertura, cierre);
    agenda.reservar(new CitaDePrueba("M001", lunes, LocalTime.of(10, 0), 30));
    agenda.reservar(new CitaDePrueba("M002", martes, LocalTime.of(10, 0), 30));
    agenda.reservar(new CitaDePrueba("M003", miercoles, LocalTime.of(10, 0), 30));
    ArrayList<Cita> entre = agenda.getCitasEntre(lunes, martes);
    if (entre.size() == 2 && entre.get(0).getCliente().equals("M001")
            && entre.get(1).getCliente().equals("M002")) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // Prueba 20
    total++;
    IO.println("Prueba 20: tieneCita responde bien y getCitaDe sin cita lanza excepción");
    agenda = new Agenda(apertura, cierre);
    agenda.reservar(new CitaDePrueba("M001", lunes, LocalTime.of(10, 0), 30));
    boolean tiene = agenda.tieneCita("M001", lunes);
    boolean noTiene = agenda.tieneCita("M001", martes);
    boolean lanzo = false;
    try {
        agenda.getCitaDe("M001", martes);
    } catch (ArgumentoException e) {
        lanzo = true;
    }
    if (tiene && !noTiene && lanzo) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // ==================================================================
    IO.println("");
    IO.println("===== PRUEBAS DE HUECOS =====");
    // ==================================================================

    // Prueba 21
    total++;
    IO.println("Prueba 21: en una agenda vacía el primer hueco es la apertura");
    agenda = new Agenda(apertura, cierre);
    if (agenda.primerHueco(lunes, 30).equals(LocalTime.of(9, 0))) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // Prueba 22
    total++;
    IO.println("Prueba 22: el primer hueco se busca entre las citas");
    agenda = new Agenda(apertura, cierre);
    agenda.reservar(new CitaDePrueba("M001", lunes, LocalTime.of(9, 0), 30));
    agenda.reservar(new CitaDePrueba("M002", lunes, LocalTime.of(10, 0), 30));
    if (agenda.primerHueco(lunes, 30).equals(LocalTime.of(9, 30))
            && agenda.primerHueco(lunes, 45).equals(LocalTime.of(10, 30))) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // Prueba 23
    total++;
    IO.println("Prueba 23: si el día está lleno no hay hueco (null)");
    agenda = new Agenda(apertura, cierre);
    agenda.reservar(new CitaDePrueba("M001", lunes, LocalTime.of(9, 0), 660));
    if (agenda.primerHueco(lunes, 30) == null && !agenda.hayHueco(lunes, 30)) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // Prueba 24
    total++;
    IO.println("Prueba 24: estaLibre distingue tramos ocupados y libres");
    agenda = new Agenda(apertura, cierre);
    agenda.reservar(new CitaDePrueba("M001", lunes, LocalTime.of(10, 0), 60));
    if (!agenda.estaLibre(lunes, LocalTime.of(10, 30), 30)
            && agenda.estaLibre(lunes, LocalTime.of(11, 0), 30)
            && !agenda.estaLibre(lunes, LocalTime.of(19, 45), 30)) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // Prueba 25
    total++;
    IO.println("Prueba 25: primerDiaConHueco salta los días llenos");
    agenda = new Agenda(apertura, cierre);
    agenda.reservar(new CitaDePrueba("M001", lunes, LocalTime.of(9, 0), 660));
    if (agenda.primerDiaConHueco(lunes, 30).equals(martes)) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // Prueba 26
    total++;
    IO.println("Prueba 26: getTiempoLibre resta las duraciones de las citas del día");
    agenda = new Agenda(apertura, cierre);
    agenda.reservar(new CitaDePrueba("M001", lunes, LocalTime.of(9, 0), 30));
    agenda.reservar(new CitaDePrueba("M002", lunes, LocalTime.of(10, 0), 45));
    if (agenda.getTiempoLibre(lunes) == 660 - 75) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // ==================================================================
    IO.println("");
    IO.println("===== PRUEBA DE SERIALIZACIÓN =====");
    // ==================================================================

    // Prueba 27
    total++;
    IO.println("Prueba 27: una agenda con citas se guarda y se lee igual");
    agenda = new Agenda(apertura, cierre);
    agenda.reservar(new CitaDePrueba("M001", lunes, LocalTime.of(10, 0), 30));
    agenda.reservar(new CitaDePrueba("M002", martes, LocalTime.of(11, 0), 45));
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    ObjectOutputStream salida = new ObjectOutputStream(bytes);
    salida.writeObject(agenda);
    salida.close();
    ObjectInputStream entrada = new ObjectInputStream(
            new ByteArrayInputStream(bytes.toByteArray()));
    Agenda leida = (Agenda) entrada.readObject();
    entrada.close();
    if (leida.getNumCitas() == 2 && leida.getCitaEn(0).getCliente().equals("M001")
            && leida.getCitaEn(1).getInicio().equals(LocalTime.of(11, 0))
            && leida.getApertura().equals(apertura)) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // ==================================================================
    IO.println("");
    IO.println("RESULTADO: " + (total - fallos) + " de " + total + " pruebas correctas");
    if (fallos == 0) {
        IO.println("Todo correcto.");
    } else {
        IO.println("Hay " + fallos + " prueba(s) que fallan.");
    }
}