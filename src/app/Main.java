package app;

import model.Personaje;
import model.PersonajePlantillas;

public class Main {

    public static void main(String[] args) {

        // 1) Fully equipped character: sets every possible field
        Personaje personajeCompleto = Personaje.builder("Aria", "Elf", "Archer")
                .conArma("Elven bow")
                .conArmadura("Carved leather armor")
                .agregarHabilidad("Precise shot")
                .agregarHabilidad("Hawk eye")
                .agregarHabilidad("Silent step")
                .conMascota("Snow lynx")
                .conNivel(5)
                .build();

        // 2) Minimal character: only the required fields
        Personaje personajeMinimo = Personaje.builder("Borin", "Dwarf", "Warrior")
                .build();

        // 3) Character built from a predefined template
        Personaje personajePlantilla = PersonajePlantillas.magoPorDefecto();

        System.out.println("Character 1 (fully equipped):");
        System.out.println(personajeCompleto.ficha());
        System.out.println();

        System.out.println("Character 2 (minimal attributes):");
        System.out.println(personajeMinimo.ficha());
        System.out.println();

        System.out.println("Character 3 (predefined template - mage):");
        System.out.println(personajePlantilla.ficha());
        System.out.println();

        // Extra examples using the available templates
        System.out.println("Default warrior template:");
        System.out.println(PersonajePlantillas.guerreroPorDefecto().ficha());
        System.out.println();

        System.out.println("Default archer template:");
        System.out.println(PersonajePlantillas.arqueroPorDefecto().ficha());
    }
}
