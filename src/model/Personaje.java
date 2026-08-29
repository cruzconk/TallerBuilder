package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Product of the Builder pattern.
 *
 * Represents an RPG character that is fully immutable once created.
 * It exposes no public constructors or setters: the only way to get
 * an instance is through Personaje.builder(...).
 */
public class Personaje {

    // Required fields
    private final String nombre;
    private final String raza;
    private final String clase;

    // Optional fields
    private final String arma;
    private final String armadura;
    private final List<String> habilidades;
    private final String mascota;
    private final int nivel;

    // Private constructor: only the Builder can create instances
    private Personaje(Builder builder) {
        this.nombre = builder.nombre;
        this.raza = builder.raza;
        this.clase = builder.clase;
        this.arma = builder.arma;
        this.armadura = builder.armadura;
        // defensive copy + unmodifiable list to preserve immutability
        this.habilidades = Collections.unmodifiableList(new ArrayList<>(builder.habilidades));
        this.mascota = builder.mascota;
        this.nivel = builder.nivel;
    }

    // Getters (no setters: the object is read-only)
    public String getNombre() { return nombre; }
    public String getRaza() { return raza; }
    public String getClaseP() { return clase; }
    public String getArma() { return arma; }
    public String getArmadura() { return armadura; }
    public List<String> getHabilidades() { return habilidades; }
    public String getMascota() { return mascota; }
    public int getNivel() { return nivel; }

    /**
     * Prints the "character sheet", showing only the fields that were
     * actually set (no null values or empty text).
     */
    public String ficha() {
        StringBuilder sb = new StringBuilder();
        sb.append("===== Character sheet =====\n");
        sb.append("Name: ").append(nombre).append("\n");
        sb.append("Race: ").append(raza).append("\n");
        sb.append("Class: ").append(clase).append("\n");
        sb.append("Level: ").append(nivel).append("\n");

        if (arma != null && !arma.isBlank()) {
            sb.append("Weapon: ").append(arma).append("\n");
        }
        if (armadura != null && !armadura.isBlank()) {
            sb.append("Armor: ").append(armadura).append("\n");
        }
        if (!habilidades.isEmpty()) {
            sb.append("Skills: ").append(String.join(", ", habilidades)).append("\n");
        }
        if (mascota != null && !mascota.isBlank()) {
            sb.append("Companion/pet: ").append(mascota).append("\n");
        }
        sb.append("============================");
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
     * Builder (a single ConcreteBuilder, since there is only one product type).
     * Supports method chaining and lets the caller set only the fields that
     * matter. The three required fields are requested in the constructor
     * so they can never be missing.
     */
    public static class Builder {
        // required
        private final String nombre;
        private final String raza;
        private final String clase;

        // optional, with default values
        private String arma;
        private String armadura;
        private final List<String> habilidades = new ArrayList<>();
        private String mascota;
        private int nivel = 1; // defaults to 1 if not specified

        public Builder(String nombre, String raza, String clase) {
            if (nombre == null || nombre.isBlank()) {
                throw new IllegalArgumentException("Name is required");
            }
            if (raza == null || raza.isBlank()) {
                throw new IllegalArgumentException("Race is required");
            }
            if (clase == null || clase.isBlank()) {
                throw new IllegalArgumentException("Class is required");
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

        // Can be called multiple times in a row before building
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
