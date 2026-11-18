
/**
 * Enumerado que representa las especies de animales
 * que pueden atenderse en la clínica veterinaria.
 * Cada especie tiene una duración de consulta y
 * una especialidad veterinaria requerida.
 */
public enum Especie {

    // Especies, duración de consulta y especialidad necesaria
    PERRO("Perro", 30, Especialidad.GENERAL),
    GATO("Gato", 30, Especialidad.GENERAL),
    EXOTICO("Exótico", 45, Especialidad.EXOTICOS);

    // Atributos de cada especie
    private final String nombre;
    private final int duracionConsulta;
    private final Especialidad especialidad;

    /**
     * Constructor que establece los datos de cada especie.
     */
    Especie(String nombre, int duracionConsulta,
            Especialidad especialidad) {
        this.nombre = nombre;
        this.duracionConsulta = duracionConsulta;
        this.especialidad = especialidad;
    }

    /**
     * Devuelve el nombre de la especie.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve la duración de la consulta en minutos.
     */
    public int getDuracionConsulta() {
        return duracionConsulta;
    }

    /**
     * Devuelve la especialidad necesaria para atender
     * a un animal de esta especie.
     */
    public Especialidad getEspecialidad() {
        return especialidad;
    }

    /**
     * Devuelve el nombre de la especie como texto.
     */
    @Override
    public String toString() {
        return nombre;
    }
}

