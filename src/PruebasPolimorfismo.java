import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

// Pruebas de polimorfismo: citas de distinto tipo guardadas en variables de tipo
// CitaVeterinaria, y retrasar en la agenda de un veterinario conserva el tipo.

void main() throws Exception {
    int total = 0;
    int fallos = 0;

    LocalDate lunes = LocalDate.of(2026, 10, 19);
    LocalDate martes = LocalDate.of(2026, 10, 20);

    Mascota toby = new Mascota("M001", "Toby", "Ana García", Especie.PERRO,
            LocalDate.of(2020, 3, 15), 28000);
    Mascota misi = new Mascota("M002", "Misi", "Bruno López", Especie.GATO,
            LocalDate.of(2022, 6, 1), 4000);
    Mascota kiwi = new Mascota("M003", "Kiwi", "Carla Ruiz", Especie.EXOTICO,
            LocalDate.of(2024, 1, 10), 1800);
    Veterinario general = new Veterinario("V1", "Laura Martín", Especialidad.GENERAL);
    Veterinario cirujano = new Veterinario("V2", "Pablo Ortega", Especialidad.CIRUGIA);
    Veterinario exoticos = new Veterinario("V3", "Elena Sanz", Especialidad.EXOTICOS);

    // Una lista con cuatro citas de tipos y especies distintos
    ArrayList<CitaVeterinaria> citas = new ArrayList<>();
    citas.add(new Consulta(toby, general, lunes, LocalTime.of(9, 0), "cojera"));
    citas.add(new Consulta(misi, general, lunes, LocalTime.of(12, 0), "pérdida de apetito"));
    citas.add(new Consulta(kiwi, exoticos, lunes, LocalTime.of(10, 0), "revisión anual"));
    citas.add(new Cirugia(toby, cirujano, martes, LocalTime.of(9, 0), 120, "extirpación de un quiste"));

    String[] tipos = {"Consulta", "Consulta", "Consulta", "Cirugía"};
    int[] precios = {3500, 3500, 3500, 22200};
    int[] duraciones = {30, 30, 45, 120};
    Especialidad[] especialidades = {Especialidad.GENERAL, Especialidad.GENERAL,
            Especialidad.EXOTICOS, Especialidad.CIRUGIA};

    // Prueba 1
    total++;
    IO.println("Prueba 1: el tipo de cada cita se calcula según su clase");
    boolean bien = true;
    for (int i = 0; i < citas.size(); i++) {
        if (!citas.get(i).getTipo().equals(tipos[i])) {
            bien = false;
        }
    }
    if (bien) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // Prueba 2
    total++;
    IO.println("Prueba 2: el precio de cada cita se calcula según su clase");
    bien = true;
    for (int i = 0; i < citas.size(); i++) {
        if (citas.get(i).precio() != precios[i]) {
            bien = false;
        }
    }
    if (bien) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // Prueba 3
    total++;
    IO.println("Prueba 3: la duración de cada cita se calcula según su clase y la especie");
    bien = true;
    for (int i = 0; i < citas.size(); i++) {
        if (citas.get(i).getDuracion() != duraciones[i]) {
            bien = false;
        }
    }
    if (bien) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // Prueba 4
    total++;
    IO.println("Prueba 4: la especialidad exigida de cada cita se calcula según su clase");
    bien = true;
    for (int i = 0; i < citas.size(); i++) {
        if (citas.get(i).especialidadRequerida() != especialidades[i]) {
            bien = false;
        }
    }
    if (bien) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // Prueba 5
    total++;
    IO.println("Prueba 5: si todas se atienden, el ingreso total es la suma de los precios");
    int suma = 0;
    for (CitaVeterinaria c : citas) {
        c.terminar(Resultado.ATENDIDA, "");
        suma += c.ingreso();
    }
    if (suma == 3500 + 3500 + 3500 + 22200) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO: " + suma);
        fallos++;
    }

    // Prueba 6
    total++;
    IO.println("Prueba 6: retrasar una consulta en la agenda conserva el tipo Consulta");
    Agenda agenda = general.getAgenda();
    agenda.reservar(new Consulta(toby, general, lunes, LocalTime.of(9, 0), "cojera"));
    agenda.retrasar("M001", lunes, 60);
    Cita retrasada = agenda.getCitaDe("M001", lunes);
    if (retrasada instanceof Consulta && retrasada.getInicio().equals(LocalTime.of(10, 0))
            && ((Consulta) retrasada).getMotivo().equals("cojera")
            && ((Consulta) retrasada).precio() == 3500) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // Prueba 7
    total++;
    IO.println("Prueba 7: retrasar una cirugía en la agenda conserva el tipo Cirugia y su precio");
    Agenda agendaCirujano = cirujano.getAgenda();
    agendaCirujano.reservar(new Cirugia(toby, cirujano, martes, LocalTime.of(9, 0), 120, "quiste"));
    agendaCirujano.retrasar("M001", martes, 30);
    Cita cirugiaRetrasada = agendaCirujano.getCitaDe("M001", martes);
    if (cirugiaRetrasada instanceof Cirugia && cirugiaRetrasada.getInicio().equals(LocalTime.of(9, 30))
            && cirugiaRetrasada.getDuracion() == 120
            && ((Cirugia) cirugiaRetrasada).precio() == 22200
            && ((Cirugia) cirugiaRetrasada).getIntervencion().equals("quiste")) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // Prueba 8
    total++;
    IO.println("Prueba 8: la consulta de un exótico ocupa 45 minutos en la agenda");
    Agenda agendaExoticos = exoticos.getAgenda();
    agendaExoticos.reservar(new Consulta(kiwi, exoticos, lunes, LocalTime.of(10, 0), "revisión anual"));
    if (agendaExoticos.getTiempoLibre(lunes) == 660 - 45
            && agendaExoticos.getCitaDe("M003", lunes).getFin().equals(LocalTime.of(10, 45))) {
        IO.println("  OK");
    } else {
        IO.println("  FALLO");
        fallos++;
    }

    // Prueba 9
    total++;
    IO.println("Prueba 9: una cirugía que se solapa con otra en la agenda lanza excepción");
    try {
        agendaCirujano.reservar(new Cirugia(misi, cirujano, martes, LocalTime.of(10, 0), 60, "esterilización"));
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
