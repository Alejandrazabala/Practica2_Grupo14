import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

// Pruebas de comprobarNormas: una prueba que se cumple y una que falla por cada norma
// común (domingo, horario, pasado, especialidad), con los casos límite.
// "Ahora" es el viernes 16/10/2026 a las 10:00.

void main() throws Exception {
    int total = 0;
    int fallos = 0;

    LocalDateTime ahora = LocalDateTime.of(2026, 10, 16, 10, 0);
    LocalDate lunes = LocalDate.of(2026, 10, 19);
    LocalDate domingo = LocalDate.of(2026, 10, 18);
    LocalDate hoy = LocalDate.of(2026, 10, 16);
    LocalDate ayer = LocalDate.of(2026, 10, 15);

    Mascota toby = new Mascota("M001", "Toby", "Ana García", Especie.PERRO,
            LocalDate.of(2020, 3, 15), 28000);
    Veterinario general = new Veterinario("V1", "Laura Martín", Especialidad.GENERAL);
    Veterinario cirujano = new Veterinario("V2", "Pablo Ortega", Especialidad.CIRUGIA);
    Veterinario exoticos = new Veterinario("V3", "Elena Sanz", Especialidad.EXOTICOS);

    CitaVeterinaria cita;

    // ---------------- Todas las normas se cumplen ----------------

    // Prueba 1
    total++;
    IO.println("Prueba 1: una cita correcta cumple todas las normas");
    cita = new CitaVeterinariaDePrueba(toby, general, lunes, LocalTime.of(10, 0), 30, Especialidad.GENERAL);
    try {
        cita.comprobarNormas(ahora);
        IO.println("  OK");
    } catch (ClinicaException e) {
        IO.println("  FALLO: " + e.getMessage());
        fallos++;
    }

    // ---------------- Norma 1: domingo ----------------

    // Prueba 2
    total++;
    IO.println("Prueba 2: una cita en domingo lanza excepción");
    cita = new CitaVeterinariaDePrueba(toby, general, domingo, LocalTime.of(10, 0), 30, Especialidad.GENERAL);
    try {
        cita.comprobarNormas(ahora);
        IO.println("  FALLO: debía lanzar ClinicaException");
        fallos++;
    } catch (ClinicaException e) {
        IO.println("  OK");
    }

    // ---------------- Norma 2: horario ----------------

    // Prueba 3
    total++;
    IO.println("Prueba 3: una cita que empieza antes de las 9:00 lanza excepción");
    cita = new CitaVeterinariaDePrueba(toby, general, lunes, LocalTime.of(8, 30), 30, Especialidad.GENERAL);
    try {
        cita.comprobarNormas(ahora);
        IO.println("  FALLO: debía lanzar ClinicaException");
        fallos++;
    } catch (ClinicaException e) {
        IO.println("  OK");
    }

    // Prueba 4
    total++;
    IO.println("Prueba 4: una cita que termina después de las 20:00 lanza excepción");
    cita = new CitaVeterinariaDePrueba(toby, general, lunes, LocalTime.of(19, 45), 30, Especialidad.GENERAL);
    try {
        cita.comprobarNormas(ahora);
        IO.println("  FALLO: debía lanzar ClinicaException");
        fallos++;
    } catch (ClinicaException e) {
        IO.println("  OK");
    }

    // Prueba 5
    total++;
    IO.println("Prueba 5: una cita que empieza justo a las 9:00 es válida (límite)");
    cita = new CitaVeterinariaDePrueba(toby, general, lunes, LocalTime.of(9, 0), 30, Especialidad.GENERAL);
    try {
        cita.comprobarNormas(ahora);
        IO.println("  OK");
    } catch (ClinicaException e) {
        IO.println("  FALLO: " + e.getMessage());
        fallos++;
    }

    // Prueba 6
    total++;
    IO.println("Prueba 6: una cita que termina justo a las 20:00 es válida (límite)");
    cita = new CitaVeterinariaDePrueba(toby, general, lunes, LocalTime.of(19, 30), 30, Especialidad.GENERAL);
    try {
        cita.comprobarNormas(ahora);
        IO.println("  OK");
    } catch (ClinicaException e) {
        IO.println("  FALLO: " + e.getMessage());
        fallos++;
    }

    // ---------------- Norma 3: pasado ----------------

    // Prueba 7
    total++;
    IO.println("Prueba 7: una cita de ayer lanza excepción");
    cita = new CitaVeterinariaDePrueba(toby, general, ayer, LocalTime.of(10, 0), 30, Especialidad.GENERAL);
    try {
        cita.comprobarNormas(ahora);
        IO.println("  FALLO: debía lanzar ClinicaException");
        fallos++;
    } catch (ClinicaException e) {
        IO.println("  OK");
    }

    // Prueba 8
    total++;
    IO.println("Prueba 8: una cita de hoy a una hora que ya pasó (9:00) lanza excepción");
    cita = new CitaVeterinariaDePrueba(toby, general, hoy, LocalTime.of(9, 0), 30, Especialidad.GENERAL);
    try {
        cita.comprobarNormas(ahora);
        IO.println("  FALLO: debía lanzar ClinicaException");
        fallos++;
    } catch (ClinicaException e) {
        IO.println("  OK");
    }

    // Prueba 9
    total++;
    IO.println("Prueba 9: una cita que empieza justo ahora (10:00) es válida (límite)");
    cita = new CitaVeterinariaDePrueba(toby, general, hoy, LocalTime.of(10, 0), 30, Especialidad.GENERAL);
    try {
        cita.comprobarNormas(ahora);
        IO.println("  OK");
    } catch (ClinicaException e) {
        IO.println("  FALLO: " + e.getMessage());
        fallos++;
    }

    // Prueba 10
    total++;
    IO.println("Prueba 10: una cita de hoy a una hora futura (11:00) es válida");
    cita = new CitaVeterinariaDePrueba(toby, general, hoy, LocalTime.of(11, 0), 30, Especialidad.GENERAL);
    try {
        cita.comprobarNormas(ahora);
        IO.println("  OK");
    } catch (ClinicaException e) {
        IO.println("  FALLO: " + e.getMessage());
        fallos++;
    }

    // ---------------- Norma 4: especialidad ----------------

    // Prueba 11
    total++;
    IO.println("Prueba 11: un generalista no puede atender una cita que exige CIRUGIA");
    cita = new CitaVeterinariaDePrueba(toby, general, lunes, LocalTime.of(10, 0), 30, Especialidad.CIRUGIA);
    try {
        cita.comprobarNormas(ahora);
        IO.println("  FALLO: debía lanzar ClinicaException");
        fallos++;
    } catch (ClinicaException e) {
        IO.println("  OK");
    }

    // Prueba 12
    total++;
    IO.println("Prueba 12: un veterinario de exóticos no puede atender una cita que exige CIRUGIA");
    cita = new CitaVeterinariaDePrueba(toby, exoticos, lunes, LocalTime.of(10, 0), 30, Especialidad.CIRUGIA);
    try {
        cita.comprobarNormas(ahora);
        IO.println("  FALLO: debía lanzar ClinicaException");
        fallos++;
    } catch (ClinicaException e) {
        IO.println("  OK");
    }

    // Prueba 13
    total++;
    IO.println("Prueba 13: cada veterinario puede atender la especialidad que le corresponde");
    try {
        new CitaVeterinariaDePrueba(toby, general, lunes, LocalTime.of(10, 0), 30,
                Especialidad.GENERAL).comprobarNormas(ahora);
        new CitaVeterinariaDePrueba(toby, cirujano, lunes, LocalTime.of(10, 0), 30,
                Especialidad.CIRUGIA).comprobarNormas(ahora);
        new CitaVeterinariaDePrueba(toby, cirujano, lunes, LocalTime.of(10, 0), 30,
                Especialidad.GENERAL).comprobarNormas(ahora);
        new CitaVeterinariaDePrueba(toby, exoticos, lunes, LocalTime.of(10, 0), 30,
                Especialidad.EXOTICOS).comprobarNormas(ahora);
        IO.println("  OK");
    } catch (ClinicaException e) {
        IO.println("  FALLO: " + e.getMessage());
        fallos++;
    }

    // ---------------- Orden de las comprobaciones y otros ----------------

    // Prueba 14
    total++;
    IO.println("Prueba 14: el domingo se comprueba antes que el pasado (domingo 11/10, ya pasado)");
    cita = new CitaVeterinariaDePrueba(toby, general, LocalDate.of(2026, 10, 11),
            LocalTime.of(10, 0), 30, Especialidad.GENERAL);
    try {
        cita.comprobarNormas(ahora);
        IO.println("  FALLO: debía lanzar ClinicaException");
        fallos++;
    } catch (ClinicaException e) {
        if (e.getMessage().contains("domingo")) {
            IO.println("  OK");
        } else {
            IO.println("  FALLO: el mensaje no habla del domingo: " + e.getMessage());
            fallos++;
        }
    }

    // Prueba 15
    total++;
    IO.println("Prueba 15: comprobarNormas con la fecha y hora actuales nulas lanza excepción");
    cita = new CitaVeterinariaDePrueba(toby, general, lunes, LocalTime.of(10, 0), 30, Especialidad.GENERAL);
    try {
        cita.comprobarNormas(null);
        IO.println("  FALLO: debía lanzar ClinicaException");
        fallos++;
    } catch (ClinicaException e) {
        IO.println("  OK");
    }

    IO.println("");
    IO.println("RESULTADO: " + (total - fallos) + " de " + total + " pruebas correctas");
    if (fallos == 0) {
        IO.println("Todo correcto.");
    } else {
        IO.println("Hay " + fallos + " prueba(s) que fallan.");
    }
}
