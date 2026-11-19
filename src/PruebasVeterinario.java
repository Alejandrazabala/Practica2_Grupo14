import java.time.LocalTime;

// Pruebas de Veterinario: datos, agenda y la regla de especialidades.

void main() throws Exception {
    int total = 0;
    int fallos = 0;

    Veterinario general = new Veterinario("V1", "Alejandra Zabala", Especialidad.GENERAL);
    Veterinario cirujano = new Veterinario("V2", "Celia Lobato", Especialidad.CIRUGIA);
    Veterinario exoticos = new Veterinario("V3", "Maria Esteban", Especialidad.EXOTICOS);

    // Prueba 1
    total++;
    IO.println("Prueba 1: el constructor guarda los datos");
    if (general.getCodigo().equals("V1") && general.getNombre().equals("Laura Martín")
            && general.getEspecialidad() == Especialidad.GENERAL) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // Prueba 2
    total++;
    IO.println("Prueba 2: la agenda tiene el horario de la clínica y empieza vacía");
    if (general.getAgenda().getApertura().equals(LocalTime.of(9, 0))
            && general.getAgenda().getCierre().equals(LocalTime.of(20, 0))
            && general.getAgenda().getNumCitas() == 0) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // Prueba 3
    total++;
    IO.println("Prueba 3: cada veterinario tiene su propia agenda");
    if (general.getAgenda() != cirujano.getAgenda()) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // Prueba 4
    total++;
    IO.println("Prueba 4: un generalista solo atiende GENERAL");
    if (general.puedeAtender(Especialidad.GENERAL)
            && !general.puedeAtender(Especialidad.CIRUGIA)
            && !general.puedeAtender(Especialidad.EXOTICOS)) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // Prueba 5
    total++;
    IO.println("Prueba 5: un cirujano atiende GENERAL y CIRUGIA, pero no EXOTICOS");
    if (cirujano.puedeAtender(Especialidad.GENERAL)
            && cirujano.puedeAtender(Especialidad.CIRUGIA)
            && !cirujano.puedeAtender(Especialidad.EXOTICOS)) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // Prueba 6
    total++;
    IO.println("Prueba 6: un veterinario de exóticos atiende GENERAL y EXOTICOS, pero no CIRUGIA");
    if (exoticos.puedeAtender(Especialidad.GENERAL)
            && exoticos.puedeAtender(Especialidad.EXOTICOS)
            && !exoticos.puedeAtender(Especialidad.CIRUGIA)) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // Prueba 7
    total++;
    IO.println("Prueba 7: puedeAtender con una especialidad nula devuelve falso");
    if (!general.puedeAtender(null)) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // Prueba 8
    total++;
    IO.println("Prueba 8: un código nulo lanza excepción");
    try {
        new Veterinario(null, "Laura Martín", Especialidad.GENERAL);
        IO.println("  FALLO: debía lanzar ArgumentoException");
        fallos++;
    } catch (ArgumentoException e) {
        IO.println("  OK");
    }

    // Prueba 9
    total++;
    IO.println("Prueba 9: un nombre vacío lanza excepción");
    try {
        new Veterinario("V1", "", Especialidad.GENERAL);
        IO.println("  FALLO: debía lanzar ArgumentoException");
        fallos++;
    } catch (ArgumentoException e) {
        IO.println("  OK");
    }

    // Prueba 10
    total++;
    IO.println("Prueba 10: una especialidad nula lanza excepción");
    try {
        new Veterinario("V1", "Laura Martín", null);
        IO.println("  FALLO: debía lanzar ArgumentoException");
        fallos++;
    } catch (ArgumentoException e) {
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