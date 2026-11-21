import java.time.LocalDate;
import java.time.LocalTime;

// Pruebas de Cirugia (precio, tipo y datos). Precio = PRECIO_HORA * duracion / 60
// + ANESTESIA_POR_KG * peso / 1000, con el peso en gramos y toodo en céntimos.

void main() throws Exception {
    int total = 0;
    int fallos = 0;

    LocalDate martes = LocalDate.of(2026, 10, 20);
    Mascota toby = new Mascota("M001", "Toby", "Ana García", Especie.PERRO,
            LocalDate.of(2020, 3, 15), 28000);
    Mascota luna = new Mascota("M004", "Luna", "Diego Martín", Especie.PERRO,
            LocalDate.of(2025, 2, 20), 8000);
    Mascota kiwi = new Mascota("M003", "Kiwi", "Carla Ruiz", Especie.EXOTICO,
            LocalDate.of(2024, 1, 10), 1800);
    Veterinario cirujano = new Veterinario("V2", "Pablo Ortega", Especialidad.CIRUGIA);

    CitaVeterinaria cita;

    // Prueba 1
    total++;
    IO.println("Prueba 1: ejemplo del enunciado: 120 min a Toby (28 kg) = 222,00 euros");
    cita = new Cirugia(toby, cirujano, martes, LocalTime.of(9, 0), 120, "extirpación de un quiste");
    if (cita.precio() == 22200) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO: " + cita.precio());
        fallos++;
    }

    // Prueba 2
    total++;
    IO.println("Prueba 2: 60 min a Toby = 90,00 + 42,00 = 132,00 euros");
    cita = new Cirugia(toby, cirujano, martes, LocalTime.of(9, 0), 60, "extirpación de un quiste");
    if (cita.precio() == 13200) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO: " + cita.precio());
        fallos++;
    }

    // Prueba 3
    total++;
    IO.println("Prueba 3: 30 min a Luna (8 kg) = 45,00 + 12,00 = 57,00 euros");
    cita = new Cirugia(luna, cirujano, martes, LocalTime.of(9, 0), 30, "esterilización");
    if (cita.precio() == 5700) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO: " + cita.precio());
        fallos++;
    }

    // Prueba 4
    total++;
    IO.println("Prueba 4: 30 min a Kiwi (1,8 kg) = 45,00 + 2,70 = 47,70 euros");
    cita = new Cirugia(kiwi, cirujano, martes, LocalTime.of(9, 0), 30, "revisión dental");
    if (cita.precio() == 4770) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO: " + cita.precio());
        fallos++;
    }

    // Prueba 5
    total++;
    IO.println("Prueba 5: tipo Cirugía, especialidad CIRUGIA y duración guardada");
    cita = new Cirugia(toby, cirujano, martes, LocalTime.of(9, 0), 120, "extirpación de un quiste");
    if (cita.getTipo().equals("Cirugía") && cita.especialidadRequerida() == Especialidad.CIRUGIA
            && cita.getDuracion() == 120 && cita.getFin().equals(LocalTime.of(11, 0))) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // Prueba 6
    total++;
    IO.println("Prueba 6: la cirugía guarda la intervención y la descripción la incluye");
    Cirugia cirugia = new Cirugia(toby, cirujano, martes, LocalTime.of(9, 0), 120, "extirpación de un quiste");
    if (cirugia.getIntervencion().equals("extirpación de un quiste")
            && cirugia.descripcion().contains("extirpación de un quiste")) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // Prueba 7
    total++;
    IO.println("Prueba 7: una intervención vacía lanza excepción");
    try {
        new Cirugia(toby, cirujano, martes, LocalTime.of(9, 0), 120, "  ");
        IO.println("  FALLO: debía lanzar ArgumentoException");
        fallos++;
    } catch (ArgumentoException e) {
        IO.println("  OK");
    }

    // Prueba 8
    total++;
    IO.println("Prueba 8: una intervención nula lanza excepción");
    try {
        new Cirugia(toby, cirujano, martes, LocalTime.of(9, 0), 120, null);
        IO.println("  FALLO: debía lanzar ArgumentoException");
        fallos++;
    } catch (ArgumentoException e) {
        IO.println("  OK");
    }

    // Prueba 9
    total++;
    IO.println("Prueba 9: una duración de 0 minutos lanza excepción");
    try {
        new Cirugia(toby, cirujano, martes, LocalTime.of(9, 0), 0, "extirpación de un quiste");
        IO.println("  FALLO: debía lanzar ArgumentoException");
        fallos++;
    } catch (ArgumentoException e) {
        IO.println("  OK");
    }

    // Prueba 10
    total++;
    IO.println("Prueba 10: conInicio devuelve una Cirugia con el mismo precio y duración");
    Cirugia copia = cirugia.conInicio(LocalTime.of(11, 0));
    if (copia.getInicio().equals(LocalTime.of(11, 0)) && copia.getDuracion() == 120
            && copia.precio() == 22200 && copia.getIntervencion().equals("extirpación de un quiste")
            && copia != cirugia && cirugia.getInicio().equals(LocalTime.of(9, 0))) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // Prueba 11
    total++;
    IO.println("Prueba 11: una cirugía atendida ingresa su precio; si no se presenta, 0");
    CitaVeterinaria atendida = new Cirugia(toby, cirujano, martes, LocalTime.of(9, 0), 120, "quiste");
    atendida.terminar(Resultado.ATENDIDA, "Sin complicaciones");
    CitaVeterinaria noPresentada = new Cirugia(luna, cirujano, martes, LocalTime.of(12, 0), 30, "esterilización");
    noPresentada.terminar(Resultado.NO_PRESENTADO, "");
    if (atendida.ingreso() == 22200 && noPresentada.ingreso() == 0) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
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
