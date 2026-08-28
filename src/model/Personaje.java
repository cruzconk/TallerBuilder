package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Product del patron Builder.
 *
 * Representa un personaje de RPG completamente inmutable una vez creado.
 * No expone constructores publicos ni setters: la unica forma de obtener
 * una instancia es a traves de Personaje.builder(...).
 */
public class Personaje {

    // Campos obligatorios
    private final String nombre;
    private final String raza;
    private final String clase;

    // Campos opcionales
    private final String arma;
    private final String armadura;
    private final List<String> habilidades;
    private final String mascota;
    private final int nivel;

    // Constructor privado: solo el Builder puede crear instancias
    private Personaje(Builder builder) {
        this.nombre = builder.nombre;
        this.raza = builder.raza;
        this.clase = builder.clase;
        this.arma = builder.arma;
        this.armadura = builder.armadura;
        // copia defensiva + lista inmutable para no romper la inmutabilidad
        this.habilidades = Collections.unmodifiableList(new ArrayList<>(builder.habilidades));
        this.mascota = builder.mascota;
        this.nivel = builder.nivel;
    }

    // Getters (sin setters: el objeto es de solo lectura)
    public String getNombre() { return nombre; }
    public String getRaza() { return raza; }
    public String getClaseP() { return clase; }
    public String getArma() { return arma; }
    public String getArmadura() { return armadura; }
    public List<String> getHabilidades() { return habilidades; }
    public String getMascota() { return mascota; }
    public int getNivel() { return nivel; }

    /**
     * Imprime la "hoja de personaje", mostrando solo los campos que
     * realmente fueron definidos (sin null ni valores vacios).
     */
    public String ficha() {
        StringBuilder sb = new StringBuilder();
        sb.append("===== Hoja de personaje =====\n");
        sb.append("Nombre: ").append(nombre).append("\n");
        sb.append("Raza: ").append(raza).append("\n");
        sb.append("Clase: ").append(clase).append("\n");
        sb.append("Nivel: ").append(nivel).append("\n");

        if (arma != null && !arma.isBlank()) {
            sb.append("Arma equipada: ").append(arma).append("\n");
        }
        if (armadura != null && !armadura.isBlank()) {
            sb.append("Armadura: ").append(armadura).append("\n");
        }
        if (!habilidades.isEmpty()) {
            sb.append("Habilidades: ").append(String.join(", ", habilidades)).append("\n");
        }
        if (mascota != null && !mascota.isBlank()) {
            sb.append("Mascota/compañero: ").append(mascota).append("\n");
        }
        sb.append("==============================");
        return sb.toString();
    }

    @Override
    public String toString() {
        return ficha();
    }

    public static Builder builder(String nombre, String raza, String clase) {
        return new Builder(nombre, raza, clase);
    }

    /**
     * Builder (ConcreteBuilder unico, ya que solo hay un tipo de producto).
     * Permite encadenamiento de metodos y definir solo los campos que
     * interesen. Los tres campos obligatorios se piden en el constructor
     * para garantizar que nunca falten.
     */
    public static class Builder {
        // obligatorios
        private final String nombre;
        private final String raza;
        private final String clase;

        // opcionales, con valores por defecto
        private String arma;
        private String armadura;
        private final List<String> habilidades = new ArrayList<>();
        private String mascota;
        private int nivel = 1; // por defecto 1 si no se especifica

        public Builder(String nombre, String raza, String clase) {
            if (nombre == null || nombre.isBlank()) {
                throw new IllegalArgumentException("El nombre es obligatorio");
            }
            if (raza == null || raza.isBlank()) {
                throw new IllegalArgumentException("La raza es obligatoria");
            }
            if (clase == null || clase.isBlank()) {
                throw new IllegalArgumentException("La clase es obligatoria");
            }
            this.nombre = nombre;
            this.raza = raza;
            this.clase = clase;
        }

        public Builder conArma(String arma) {
            this.arma = arma;
            return this;
        }

        public Builder conArmadura(String armadura) {
            this.armadura = armadura;
            return this;
        }

        // Puede llamarse multiples veces seguidas antes de construir
        public Builder agregarHabilidad(String habilidad) {
            if (habilidad != null && !habilidad.isBlank()) {
                this.habilidades.add(habilidad);
            }
            return this;
        }

        public Builder conMascota(String mascota) {
            this.mascota = mascota;
            return this;
        }

        public Builder conNivel(int nivel) {
            this.nivel = nivel;
            return this;
        }

        public Personaje build() {
            return new Personaje(this);
        }
    }
}
