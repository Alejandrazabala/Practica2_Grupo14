import java.time.LocalDate;
import java.time.LocalTime;

// Pruebas de Consulta. Las citas se guardan en variables de tipo CitaVeterinaria
// para comprobar el polimorfismo: el precio, el tipo, la duración y la especialidad
// los calcula cada clase.

void main() throws Exception {
    int total = 0;
    int fallos = 0;

    LocalDate lunes = LocalDate.of(2026, 10, 19);
    Mascota toby = new Mascota("M001", "Toby", "Ana García", Especie.PERRO,
            LocalDate.of(2020, 3, 15), 28000);
    Mascota misi = new Mascota("M002", "Misi", "Bruno López", Especie.GATO,
            LocalDate.of(2022, 6, 1), 4000);
    Mascota kiwi = new Mascota("M003", "Kiwi", "Carla Ruiz", Especie.EXOTICO,
            LocalDate.of(2024, 1, 10), 1800);
    Veterinario general = new Veterinario("V1", "Laura Martín", Especialidad.GENERAL);
    Veterinario exoticos = new Veterinario("V3", "Elena Sanz", Especialidad.EXOTICOS);

    CitaVeterinaria cita;

    // Prueba 1
    total++;
    IO.println("Prueba 1: consulta de un perro: 30 min, 35,00 euros, GENERAL");
    cita = new Consulta(toby, general, lunes, LocalTime.of(9, 0), "cojera");
    if (cita.getDuracion() == 30 && cita.precio() == 3500 && cita.getTipo().equals("Consulta")
            && cita.especialidadRequerida() == Especialidad.GENERAL
            && cita.getFin().equals(LocalTime.of(9, 30))) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // Prueba 2
    total++;
    IO.println("Prueba 2: consulta de un gato: 30 min, 35,00 euros, GENERAL");
    cita = new Consulta(misi, general, lunes, LocalTime.of(12, 0), "pérdida de apetito");
    if (cita.getDuracion() == 30 && cita.precio() == 3500 && cita.getTipo().equals("Consulta")
            && cita.especialidadRequerida() == Especialidad.GENERAL) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // Prueba 3
    total++;
    IO.println("Prueba 3: consulta de un exótico: 45 min, 35,00 euros, EXOTICOS");
    cita = new Consulta(kiwi, exoticos, lunes, LocalTime.of(10, 0), "revisión anual");
    if (cita.getDuracion() == 45 && cita.precio() == 3500 && cita.getTipo().equals("Consulta")
            && cita.especialidadRequerida() == Especialidad.EXOTICOS
            && cita.getFin().equals(LocalTime.of(10, 45))) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // Prueba 4
    total++;
    IO.println("Prueba 4: el cliente de la consulta es el identificador de la mascota");
    cita = new Consulta(kiwi, exoticos, lunes, LocalTime.of(10, 0), "revisión anual");
    if (cita.getCliente().equals("M003") && cita.getMascota() == kiwi
            && cita.getVeterinario() == exoticos) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // Prueba 5
    total++;
    IO.println("Prueba 5: la consulta guarda su motivo y la descripción lo incluye");
    Consulta consulta = new Consulta(toby, general, lunes, LocalTime.of(9, 0), "cojera");
    if (consulta.getMotivo().equals("cojera") && consulta.descripcion().contains("cojera")) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // Prueba 6
    total++;
    IO.println("Prueba 6: un motivo nulo lanza excepción");
    try {
        new Consulta(toby, general, lunes, LocalTime.of(9, 0), null);
        IO.println("  FALLO: debía lanzar ArgumentoException");
        fallos++;
    } catch (ArgumentoException e) {
        IO.println("  OK");
    }

    // Prueba 7
    total++;
    IO.println("Prueba 7: un motivo vacío lanza excepción");
    try {
        new Consulta(toby, general, lunes, LocalTime.of(9, 0), "   ");
        IO.println("  FALLO: debía lanzar ArgumentoException");
        fallos++;
    } catch (ArgumentoException e) {
        IO.println("  OK");
    }

    // Prueba 8
    total++;
    IO.println("Prueba 8: una mascota nula lanza excepción");
    try {
        new Consulta(null, general, lunes, LocalTime.of(9, 0), "cojera");
        IO.println("  FALLO: debía lanzar ArgumentoException");
        fallos++;
    } catch (ArgumentoException e) {
        IO.println("  OK");
    }

    // Prueba 9
    total++;
    IO.println("Prueba 9: un veterinario nulo lanza excepción");
    try {
        new Consulta(toby, null, lunes, LocalTime.of(9, 0), "cojera");
        IO.println("  FALLO: debía lanzar ArgumentoException");
        fallos++;
    } catch (ArgumentoException e) {
        IO.println("  OK");
    }

    // Prueba 10
    total++;
    IO.println("Prueba 10: conInicio devuelve una Consulta que empieza a otra hora");
    Consulta copia = consulta.conInicio(LocalTime.of(11, 0));
    if (copia.getInicio().equals(LocalTime.of(11, 0)) && copia.getFin().equals(LocalTime.of(11, 30))
            && copia.getMotivo().equals("cojera") && copia.getCliente().equals("M001")
            && copia.getFecha().equals(lunes) && copia != consulta) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // Prueba 11
    total++;
    IO.println("Prueba 11: conInicio no modifica la consulta original");
    if (consulta.getInicio().equals(LocalTime.of(9, 0))) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // Prueba 12
    total++;
    IO.println("Prueba 12: conInicio nulo lanza excepción");
    try {
        consulta.conInicio(null);
        IO.println("  FALLO: debía lanzar ArgumentoException");
        fallos++;
    } catch (ArgumentoException e) {
        IO.println("  OK");
    }

    // Prueba 13
    total++;
    IO.println("Prueba 13: una consulta nueva está pendiente y toString la describe");
    String texto = consulta.toString();
    if (consulta.getResultado() == null && consulta.ingreso() == 0
            && texto.contains("Consulta") && texto.contains("Toby") && texto.contains("cojera")) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO: " + texto);
        fallos++;
    }

    // Prueba 14
    total++;
    IO.println("Prueba 14: una consulta atendida ingresa 35,00 euros");
    cita = new Consulta(toby, general, lunes, LocalTime.of(9, 0), "cojera");
    cita.terminar(Resultado.ATENDIDA, "Esguince leve, reposo");
    if (cita.ingreso() == 3500) {
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
