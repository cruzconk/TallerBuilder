package model;

/**
 * Plays a role equivalent to the Director: it knows "recipes" for
 * building predefined characters and reuses the same Builder internally,
 * without duplicating construction logic anywhere else in the program.
 */
public class PersonajePlantillas {

    private PersonajePlantillas() {
        // utility class, not meant to be instantiated
    }

    public static Personaje guerreroPorDefecto() {
        return Personaje.builder("Thoran", "Dwarf", "Warrior")
                .conArma("Battle axe")
                .conArmadura("Chainmail")
                .agregarHabilidad("Precise strike")
                .agregarHabilidad("War cry")
                .conNivel(1)
                .build();
    }

    public static Personaje magoPorDefecto() {
        return Personaje.builder("Elyndra", "Elf", "Mage")
                .conArma("Arcane staff")
                .agregarHabilidad("Fireball")
                .agregarHabilidad("Magic shield")
                .conMascota("Owl familiar")
                .build();
    }

    public static Personaje arqueroPorDefecto() {
        return Personaje.builder("Kaelen", "Human", "Archer")
                .conArma("Longbow")
                .conArmadura("Reinforced leather")
                .agregarHabilidad("Multi-shot")
                .build();
    }
}
