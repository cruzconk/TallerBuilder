package model;

/**
 * Cumple un rol equivalente al Director del patron: conoce "recetas" de
 * construccion (personajes predefinidos) y reutiliza el mismo Builder por
 * dentro, sin duplicar la logica de construccion en cada lugar del programa.
 */
public class PersonajePlantillas {

    private PersonajePlantillas() {
        // clase de utilidades, no se instancia
    }

    public static Personaje guerreroPorDefecto() {
        return Personaje.builder("Thoran", "Enano", "Guerrero")
                .conArma("Hacha de guerra")
                .conArmadura("Cota de malla")
                .agregarHabilidad("Golpe certero")
                .agregarHabilidad("Grito de guerra")
                .conNivel(1)
                .build();
    }

    public static Personaje magoPorDefecto() {
        return Personaje.builder("Elyndra", "Elfo", "Mago")
                .conArma("Baston arcano")
                .agregarHabilidad("Bola de fuego")
                .agregarHabilidad("Escudo mágico")
                .conMascota("Familiar buho")
                .build();
    }

    public static Personaje arqueroPorDefecto() {
        return Personaje.builder("Kaelen", "Humano", "Arquero")
                .conArma("Arco largo")
                .conArmadura("Cuero reforzado")
                .agregarHabilidad("Disparo múltiple")
                .build();
    }
}
