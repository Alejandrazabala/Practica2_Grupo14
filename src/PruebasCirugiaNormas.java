import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

// Pruebas de las normas propias de Cirugia: programarla al menos el día anterior,
// terminar como muy tarde a las 14:00 y durar de 30 a 240 minutos en tramos de 30.
// "Ahora" es el viernes 16/10/2026 a las 10:00.

void main() throws Exception {
    int total = 0;
    int fallos = 0;

    LocalDateTime ahora = LocalDateTime.of(2026, 10, 16, 10, 0);
    LocalDate hoy = LocalDate.of(2026, 10, 16);
    LocalDate manana = LocalDate.of(2026, 10, 17);
    LocalDate martes = LocalDate.of(2026, 10, 20);
    LocalDate domingo = LocalDate.of(2026, 10, 25);

    Mascota toby = new Mascota("M001", "Toby", "Ana García", Especie.PERRO,
            LocalDate.of(2020, 3, 15), 28000);
    Veterinario general = new Veterinario("V1", "Laura Martín", Especialidad.GENERAL);
    Veterinario cirujano = new Veterinario("V2", "Pablo Ortega", Especialidad.CIRUGIA);

    CitaVeterinaria cita;

    // ---------------- Se cumplen todas las normas ----------------

    // Prueba 1
    total++;
    IO.println("Prueba 1: una cirugía correcta (martes 9:00, 120 min, cirujano) cumple las normas");
    cita = new Cirugia(toby, cirujano, martes, LocalTime.of(9, 0), 120, "quiste");
    try {
        cita.comprobarNormas(ahora);
        IO.println("  OK");
    } catch (ClinicaException e) {
        IO.println("  FALLO: " + e.getMessage());
        fallos++;
    }

    // ---------------- Al menos el día anterior ----------------

    // Prueba 2
    total++;
    IO.println("Prueba 2: una cirugía para hoy (aunque sea más tarde) lanza excepción");
    cita = new Cirugia(toby, cirujano, hoy, LocalTime.of(12, 0), 60, "quiste");
    try {
        cita.comprobarNormas(ahora);
        IO.println("  FALLO: debía lanzar ClinicaException");
        fallos++;
    } catch (ClinicaException e) {
        IO.println("  OK");
    }

    // Prueba 3
    total++;
    IO.println("Prueba 3: una cirugía para mañana es válida (límite: justo el día anterior)");
    cita = new Cirugia(toby, cirujano, manana, LocalTime.of(9, 0), 60, "quiste");
    try {
        cita.comprobarNormas(ahora);
        IO.println("  OK");
    } catch (ClinicaException e) {
        IO.println("  FALLO: " + e.getMessage());
        fallos++;
    }

    // ---------------- Termina como muy tarde a las 14:00 ----------------

    // Prueba 4
    total++;
    IO.println("Prueba 4: una cirugía que termina justo a las 14:00 es válida (límite)");
    cita = new Cirugia(toby, cirujano, martes, LocalTime.of(12, 0), 120, "quiste");
    try {
        cita.comprobarNormas(ahora);
        IO.println("  OK");
    } catch (ClinicaException e) {
        IO.println("  FALLO: " + e.getMessage());
        fallos++;
    }

    // Prueba 5
    total++;
    IO.println("Prueba 5: una cirugía que termina a las 14:30 lanza excepción");
    cita = new Cirugia(toby, cirujano, martes, LocalTime.of(13, 0), 90, "quiste");
    try {
        cita.comprobarNormas(ahora);
        IO.println("  FALLO: debía lanzar ClinicaException");
        fallos++;
    } catch (ClinicaException e) {
        IO.println("  OK");
    }

    // Prueba 6
    total++;
    IO.println("Prueba 6: una cirugía por la tarde (16:00) lanza excepción");
    cita = new Cirugia(toby, cirujano, martes, LocalTime.of(16, 0), 60, "quiste");
    try {
        cita.comprobarNormas(ahora);
        IO.println("  FALLO: debía lanzar ClinicaException");
        fallos++;
    } catch (ClinicaException e) {
        IO.println("  OK");
    }

    // ---------------- Duración de 30 a 240 en tramos de 30 ----------------

    // Prueba 7
    total++;
    IO.println("Prueba 7: una cirugía de 30 minutos es válida (límite inferior)");
    cita = new Cirugia(toby, cirujano, martes, LocalTime.of(9, 0), 30, "quiste");
    try {
        cita.comprobarNormas(ahora);
        IO.println("  OK");
    } catch (ClinicaException e) {
        IO.println("  FALLO: " + e.getMessage());
        fallos++;
    }

    // Prueba 8
    total++;
    IO.println("Prueba 8: una cirugía de 240 minutos es válida (límite superior)");
    cita = new Cirugia(toby, cirujano, martes, LocalTime.of(9, 0), 240, "quiste");
    try {
        cita.comprobarNormas(ahora);
        IO.println("  OK");
    } catch (ClinicaException e) {
        IO.println("  FALLO: " + e.getMessage());
        fallos++;
    }

    // Prueba 9
    total++;
    IO.println("Prueba 9: una cirugía de 15 minutos (menos de 30) lanza excepción");
    cita = new Cirugia(toby, cirujano, martes, LocalTime.of(9, 0), 15, "quiste");
    try {
        cita.comprobarNormas(ahora);
        IO.println("  FALLO: debía lanzar ClinicaException");
        fallos++;
    } catch (ClinicaException e) {
        IO.println("  OK");
    }

    // Prueba 10
    total++;
    IO.println("Prueba 10: una cirugía de 270 minutos (más de 240) lanza excepción");
    cita = new Cirugia(toby, cirujano, martes, LocalTime.of(9, 0), 270, "quiste");
    try {
        cita.comprobarNormas(ahora);
        IO.println("  FALLO: debía lanzar ClinicaException");
        fallos++;
    } catch (ClinicaException e) {
        IO.println("  OK");
    }

    // Prueba 11
    total++;
    IO.println("Prueba 11: una cirugía de 45 minutos (no es tramo de 30) lanza excepción");
    cita = new Cirugia(toby, cirujano, martes, LocalTime.of(9, 0), 45, "quiste");
    try {
        cita.comprobarNormas(ahora);
        IO.println("  FALLO: debía lanzar ClinicaException");
        fallos++;
    } catch (ClinicaException e) {
        IO.println("  OK");
    }

    // ---------------- Las normas comunes también se aplican ----------------

    // Prueba 12
    total++;
    IO.println("Prueba 12: una cirugía con un veterinario generalista lanza excepción");
    cita = new Cirugia(toby, general, martes, LocalTime.of(9, 0), 60, "quiste");
    try {
        cita.comprobarNormas(ahora);
        IO.println("  FALLO: debía lanzar ClinicaException");
        fallos++;
    } catch (ClinicaException e) {
        IO.println("  OK");
    }

    // Prueba 13
    total++;
    IO.println("Prueba 13: una cirugía en domingo lanza excepción");
    cita = new Cirugia(toby, cirujano, domingo, LocalTime.of(9, 0), 60, "quiste");
    try {
        cita.comprobarNormas(ahora);
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
