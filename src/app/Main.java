package app;

import model.Personaje;
import model.PersonajePlantillas;

public class Main {

    public static void main(String[] args) {

        // 1) Personaje bien equipado: define todos los campos posibles
        Personaje personajeCompleto = Personaje.builder("Aria", "Elfo", "Arquero")
                .conArma("Arco élfico")
                .conArmadura("Armadura de cuero tallado")
                .agregarHabilidad("Disparo certero")
                .agregarHabilidad("Ojo de halcón")
                .agregarHabilidad("Paso silencioso")
                .conMascota("Lince de nieve")
                .conNivel(5)
                .build();

        // 2) Personaje con los atributos mínimos: solo los obligatorios
        Personaje personajeMinimo = Personaje.builder("Borin", "Enano", "Guerrero")
                .build();

        // 3) Personaje usando una plantilla predefinida
        Personaje personajePlantilla = PersonajePlantillas.magoPorDefecto();

        System.out.println("Personaje 1 (bien equipado):");
        System.out.println(personajeCompleto.ficha());
        System.out.println();

        System.out.println("Personaje 2 (atributos mínimos):");
        System.out.println(personajeMinimo.ficha());
        System.out.println();

        System.out.println("Personaje 3 (plantilla predefinida - mago):");
        System.out.println(personajePlantilla.ficha());
        System.out.println();

        // Ejemplos adicionales de plantillas disponibles
        System.out.println("Plantilla guerrero por defecto:");
        System.out.println(PersonajePlantillas.guerreroPorDefecto().ficha());
        System.out.println();

        System.out.println("Plantilla arquero por defecto:");
        System.out.println(PersonajePlantillas.arqueroPorDefecto().ficha());
    }
}
