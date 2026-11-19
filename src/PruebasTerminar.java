import java.time.LocalDate;
import java.time.LocalTime;

// Pruebas de terminar e ingreso de CitaVeterinaria (la cita de prueba cuesta 50,00 euros).

void main() throws Exception {
    int total = 0;
    int fallos = 0;

    LocalDate lunes = LocalDate.of(2026, 10, 19);
    Mascota toby = new Mascota("M001", "Toby", "Ana García", Especie.PERRO,
            LocalDate.of(2020, 3, 15), 28000);
    Veterinario general = new Veterinario("V1", "Laura Martín", Especialidad.GENERAL);

    CitaVeterinaria cita;

    // Prueba 1
    total++;
    IO.println("Prueba 1: una cita nueva está pendiente (resultado null) y no ingresa nada");
    cita = new CitaVeterinariaDePrueba(toby, general, lunes, LocalTime.of(9, 0), 30, Especialidad.GENERAL);
    if (cita.getResultado() == null && cita.ingreso() == 0 && cita.getNotas().equals("")) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // Prueba 2
    total++;
    IO.println("Prueba 2: el cliente de la cita es el identificador de la mascota");
    if (cita.getCliente().equals("M001") && cita.getMascota() == toby && cita.getVeterinario() == general) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // Prueba 3
    total++;
    IO.println("Prueba 3: una cita atendida guarda resultado y notas, e ingresa su precio");
    cita = new CitaVeterinariaDePrueba(toby, general, lunes, LocalTime.of(9, 0), 30, Especialidad.GENERAL);
    cita.terminar(Resultado.ATENDIDA, "Sano");
    if (cita.getResultado() == Resultado.ATENDIDA && cita.getNotas().equals("Sano")
            && cita.ingreso() == 5000) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // Prueba 4
    total++;
    IO.println("Prueba 4: una cita no presentada no ingresa nada");
    cita = new CitaVeterinariaDePrueba(toby, general, lunes, LocalTime.of(9, 0), 30, Especialidad.GENERAL);
    cita.terminar(Resultado.NO_PRESENTADO, "");
    if (cita.getResultado() == Resultado.NO_PRESENTADO && cita.ingreso() == 0) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // Prueba 5
    total++;
    IO.println("Prueba 5: una cita cancelada no ingresa nada");
    cita = new CitaVeterinariaDePrueba(toby, general, lunes, LocalTime.of(9, 0), 30, Especialidad.GENERAL);
    cita.terminar(Resultado.CANCELADA, "");
    if (cita.getResultado() == Resultado.CANCELADA && cita.ingreso() == 0) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // Prueba 6
    total++;
    IO.println("Prueba 6: las notas pueden estar vacías");
    cita = new CitaVeterinariaDePrueba(toby, general, lunes, LocalTime.of(9, 0), 30, Especialidad.GENERAL);
    cita.terminar(Resultado.ATENDIDA, "");
    if (cita.getResultado() == Resultado.ATENDIDA && cita.getNotas().equals("")) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // Prueba 7
    total++;
    IO.println("Prueba 7: terminar con resultado nulo lanza excepción y la cita sigue pendiente");
    cita = new CitaVeterinariaDePrueba(toby, general, lunes, LocalTime.of(9, 0), 30, Especialidad.GENERAL);
    try {
        cita.terminar(null, "notas");
        IO.println("  FALLO: debía lanzar ArgumentoException");
        fallos++;
    } catch (ArgumentoException e) {
        if (cita.getResultado() == null) {
            IO.println("  OK");
        } else {
            IO.println("  FALLO: la cita cambió");
            fallos++;
        }
    }

    // Prueba 8
    total++;
    IO.println("Prueba 8: terminar con notas nulas lanza excepción y la cita sigue pendiente");
    cita = new CitaVeterinariaDePrueba(toby, general, lunes, LocalTime.of(9, 0), 30, Especialidad.GENERAL);
    try {
        cita.terminar(Resultado.ATENDIDA, null);
        IO.println("  FALLO: debía lanzar ArgumentoException");
        fallos++;
    } catch (ArgumentoException e) {
        if (cita.getResultado() == null) {
            IO.println("  OK");
        } else {
            IO.println("  FALLO: la cita cambió");
            fallos++;
        }
    }

    // Prueba 9
    total++;
    IO.println("Prueba 9: terminar una cita ya terminada lanza excepción y conserva el primer resultado");
    cita = new CitaVeterinariaDePrueba(toby, general, lunes, LocalTime.of(9, 0), 30, Especialidad.GENERAL);
    cita.terminar(Resultado.ATENDIDA, "Sano");
    try {
        cita.terminar(Resultado.CANCELADA, "otra vez");
        IO.println("  FALLO: debía lanzar ArgumentoException");
        fallos++;
    } catch (ArgumentoException e) {
        if (cita.getResultado() == Resultado.ATENDIDA && cita.getNotas().equals("Sano")) {
            IO.println("  OK");
        } else {
            IO.println("  FALLO: la cita cambió");
            fallos++;
        }
    }

    // Prueba 10
    total++;
    IO.println("Prueba 10: una mascota nula o un veterinario nulo lanzan excepción");
    boolean lanzaMascota = false;
    boolean lanzaVeterinario = false;
    try {
        new CitaVeterinariaDePrueba(null, general, lunes, LocalTime.of(9, 0), 30, Especialidad.GENERAL);
    } catch (ArgumentoException e) {
        lanzaMascota = true;
    }
    try {
        new CitaVeterinariaDePrueba(toby, null, lunes, LocalTime.of(9, 0), 30, Especialidad.GENERAL);
    } catch (ArgumentoException e) {
        lanzaVeterinario = true;
    }
    if (lanzaMascota && lanzaVeterinario) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // Prueba 11
    total++;
    IO.println("Prueba 11: toString indica si la cita está pendiente o cómo terminó");
    cita = new CitaVeterinariaDePrueba(toby, general, lunes, LocalTime.of(9, 0), 30, Especialidad.GENERAL);
    boolean pendiente = cita.toString().contains("pendiente") && cita.toString().contains("Toby");
    cita.terminar(Resultado.ATENDIDA, "Sano");
    boolean atendida = cita.toString().contains("ATENDIDA");
    if (pendiente && atendida) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO: " + cita);
        fallos++;
    }

    IO.println("");
    IO.println("RESULTADO: " + (total - fallos) + " de " + total + " pruebas correctas");
    if (fallos == 0) {
        IO.println("Todo correcto.");
    } else {
        IO.println("Hay " + fallos + " prueba(s) que fallan.");
    }
}
