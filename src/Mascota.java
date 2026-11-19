import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

    /**
     * Mascota de un cliente. Sus datos no cambian una vez dada de alta.
     * El peso se guarda en gramos.
     */
    public class Mascota implements Serializable {

        private static final long serialVersionUID = 1L;

        private final String id;
        private final String nombre;
        private final String propietario;
        private final Especie especie;
        private final LocalDate nacimiento;
        private final int peso;

        /**
         * @param peso peso en gramos
         * @throws ArgumentoException si algún dato es nulo, está vacío o es imposible
         */
        public Mascota(String id, String nombre, String propietario, Especie especie,
                       LocalDate nacimiento, int peso) throws ArgumentoException {
            if (id == null || id.isBlank()) {
                throw new ArgumentoException("El identificador de la mascota no puede ser nulo ni vacío");
            }
            if (nombre == null || nombre.isBlank()) {
                throw new ArgumentoException("El nombre de la mascota no puede ser nulo ni vacío");
            }
            if (propietario == null || propietario.isBlank()) {
                throw new ArgumentoException("El propietario no puede ser nulo ni vacío");
            }
            if (especie == null) {
                throw new ArgumentoException("La especie no puede ser nula");
            }
            if (nacimiento == null) {
                throw new ArgumentoException("La fecha de nacimiento no puede ser nula");
            }
            if (peso <= 0) {
                throw new ArgumentoException("El peso debe ser mayor que 0 gramos");
            }
            this.id = id;
            this.nombre = nombre;
            this.propietario = propietario;
            this.especie = especie;
            this.nacimiento = nacimiento;
            this.peso = peso;
        }

        public String getId() {
            return id;
        }

        public String getNombre() {
            return nombre;
        }

        public String getPropietario() {
            return propietario;
        }

        public Especie getEspecie() {
            return especie;
        }

        public LocalDate getNacimiento() {
            return nacimiento;
        }

        /** Peso en gramos. */
        public int getPeso() {
            return peso;
        }

        /**
         * Edad en años cumplidos en la fecha indicada.
         *
         * @throws ArgumentoException si hoy es null o es anterior al nacimiento
         */
        public int getEdad(LocalDate hoy) throws ArgumentoException {
            if (hoy == null) {
                throw new ArgumentoException("La fecha actual no puede ser nula");
            }
            if (hoy.isBefore(nacimiento)) {
                throw new ArgumentoException("La fecha actual es anterior al nacimiento de la mascota");
            }
            return Period.between(nacimiento, hoy).getYears();
        }

        @Override
        public String toString() {
            return id + " " + nombre + " (" + especie + ") - " + propietario;
        }
    }
