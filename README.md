# Taller: Patrón de diseño Builder

## Parte 1 — Preguntas conceptuales

### 1. ¿Qué problema resuelve el patrón Builder?

El patrón Builder resuelve el problema de construir objetos complejos que tienen muchos atributos, especialmente cuando varios de esos atributos son opcionales. En lugar de forzar al cliente a pasar todos los valores en un único constructor (el "constructor telescópico", que va encadenando sobrecargas con cada vez más parámetros), el Builder permite ir configurando el objeto paso a paso, indicando solo los campos que realmente interesan, y dejando la creación final encapsulada en un método `build()`.

Al menos dos desventajas del constructor telescópico:

- Es difícil de leer y de usar: cuando hay varios parámetros del mismo tipo (por ejemplo varios `boolean` o `String` seguidos), es fácil invertir el orden de dos argumentos sin que el compilador lo detecte, generando errores silenciosos.
- Obliga a declarar una combinación de constructores sobrecargados que crece exponencialmente con el número de campos opcionales, y aun así el cliente debe pasar valores "de relleno" (`null`, `false`, `0`) para los campos que no le interesan, lo que hace el código repetitivo y poco expresivo.

### 2. Los 4 roles clásicos del patrón

- **Product**: el objeto final y complejo que se quiere construir (el resultado).
- **Builder**: interfaz o clase abstracta que declara los pasos necesarios para construir las partes del Product.
- **ConcreteBuilder**: implementación concreta del Builder; ejecuta los pasos de construcción y mantiene la representación específica del producto que va armando.
- **Director**: conoce el orden y la combinación de pasos necesarios para construir una configuración concreta del producto, delegando el trabajo real en un Builder que recibe como colaborador.

### 3. ¿Por qué se suele omitir el rol Director?

En la práctica moderna, sobre todo con builders de encadenamiento de métodos (*fluent builders*) como los que se usan en Java, el propio código cliente puede llamar directamente a los métodos del builder en el orden que necesite, sin que haga falta una clase intermedia que orqueste esos pasos. El Director solo aporta valor real cuando la misma secuencia de construcción se repite en varios lugares del programa: en ese caso conviene centralizarla para no duplicar lógica (como se hace en este taller con las "plantillas prediseñadas"). Si la construcción es simple o cada cliente arma el objeto de forma distinta, mantener un Director aparte agrega una capa de indirección innecesaria.

### 4. Diferencia entre construcción y representación final

La **construcción** es el proceso, es decir, la secuencia de pasos/llamadas que va definiendo poco a poco las distintas partes del objeto (por ejemplo, ir llamando a `conArma()`, `agregarHabilidad()`, `conNivel()`, etc.). La **representación final** es el resultado concreto e inmutable que se obtiene al llamar a `build()`: el objeto ya ensamblado, desacoplado de cómo fue construido. Esto permite que, usando el mismo proceso de construcción pero con ConcreteBuilders distintos, se obtengan representaciones finales diferentes.

---

## Parte 2 — Refactorización

Se refactoriza la clase `Pizza` para usar Builder con encadenamiento de métodos, permitiendo crear una pizza especificando solo los campos que se necesiten (masa y tamaño obligatorios; el resto opcional con valores por defecto):

```java
public class Pizza {
    private final String masa;
    private final String tamano;
    private final boolean queso;
    private final boolean pepperoni;
    private final boolean champinones;
    private final boolean pina;
    private final String salsa;

    private Pizza(Builder builder) {
        this.masa = builder.masa;
        this.tamano = builder.tamano;
        this.queso = builder.queso;
        this.pepperoni = builder.pepperoni;
        this.champinones = builder.champinones;
        this.pina = builder.pina;
        this.salsa = builder.salsa;
    }

    public static Builder builder(String masa, String tamano) {
        return new Builder(masa, tamano);
    }

    public static class Builder {
        // Obligatorios
        private final String masa;
        private final String tamano;

        // Opcionales, con valores por defecto
        private boolean queso = false;
        private boolean pepperoni = false;
        private boolean champinones = false;
        private boolean pina = false;
        private String salsa = "tradicional";

        public Builder(String masa, String tamano) {
            this.masa = masa;
            this.tamano = tamano;
        }

        public Builder conQueso() {
            this.queso = true;
            return this;
        }

        public Builder conPepperoni() {
            this.pepperoni = true;
            return this;
        }

        public Builder conChampinones() {
            this.champinones = true;
            return this;
        }

        public Builder conPina() {
            this.pina = true;
            return this;
        }

        public Builder conSalsa(String salsa) {
            this.salsa = salsa;
            return this;
        }

        public Pizza build() {
            return new Pizza(this);
        }
    }
}

// Uso (solo se especifican los campos que se necesiten):
Pizza p = Pizza.builder("delgada", "mediana")
        .conQueso()
        .conPepperoni()
        .conSalsa("picante")
        .build();
```

---

## Parte 3 — Identificar los roles

| Clase | Rol |
|---|---|
| `interface CarroBuilder { ... }` | Builder |
| `class CarroDeportivoBuilder implements CarroBuilder { ... }` | ConcreteBuilder |
| `class CarroDirector { ... }` | Director |
| `class Carro { ... }` | Product |

---

## Parte 4 — Encontrar el error

El problema es que el `ComputadorBuilder` crea una única instancia de `Computador` como campo de la clase (`private Computador computador = new Computador();`) y esa misma instancia es la que se va mutando y la que finalmente devuelve `build()`. Si se reutiliza el mismo builder para armar un segundo computador (llamando de nuevo a los métodos de configuración y a `build()`), en realidad se sigue modificando y devolviendo la referencia al mismo objeto `Computador` de la primera vez: no se crean dos computadores distintos, sino uno solo que va cambiando, y cualquier referencia guardada del "primer" computador termina reflejando también los valores del segundo.

La solución es que `build()` cree una instancia nueva de `Computador` cada vez que se invoca (o, equivalentemente, que el builder guarde los valores en sus propios campos temporales y arme el objeto `Computador` solo dentro de `build()`). Así cada llamada a `build()` produce un objeto independiente e inmutable, y el mismo builder puede reutilizarse de forma segura para construir varios computadores distintos:

```java
public class ComputadorBuilder {

    public ComputadorBuilder procesador(String p) {
        // computador ya no es un campo del builder:
        // se crea uno nuevo en cada build()
        this.procesador = p;
        return this;
    }

    // ... el resto de metodos guardan los valores en campos
    // del propio builder, NO en un objeto Computador compartido

    public Computador build() {
        // se crea una instancia NUEVA en cada llamada
        return new Computador(procesador, /* ...otros campos... */);
    }
}
```

---

## Parte 5 — Para argumentar

**Casos donde es favorable aplicar el patrón Builder**

1. Objetos con muchos parámetros opcionales del mismo tipo o similares, donde un constructor tradicional sería difícil de leer y de usar correctamente (por ejemplo, la clase `Pizza` de este taller o el `Personaje` del RPG de la Parte 6, con varios campos opcionales como arma, armadura, mascota, etc.).
2. Objetos que deben quedar inmutables una vez creados pero que requieren una construcción flexible o por etapas, como armar una configuración compleja (por ejemplo, una petición HTTP con múltiples cabeceras y parámetros opcionales, o un documento/reporte con secciones opcionales).

**Casos donde no es favorable aplicar el patrón Builder**

1. Objetos simples con pocos campos y todos obligatorios, donde un constructor normal ya es claro y directo (por ejemplo, una clase `Punto` con solo `x` e `y`): agregar un Builder aquí solo introduce código y clases adicionales sin beneficio real.
2. Objetos que se crean con mucha frecuencia en rutas críticas de rendimiento (por ejemplo, DTOs simples mapeados directamente desde filas de una base de datos), donde instanciar un objeto Builder adicional por cada creación agrega una sobrecarga innecesaria de memoria y de llamadas.

---

## Parte 6 — Práctica: Creador de personajes para RPG

Implementado en Java (proyecto Eclipse `PersonajeRPGBuilder`):

- `model/Personaje.java`: el **Product**, inmutable, con un `Builder` anidado. Nombre, raza y clase son obligatorios (se piden en el constructor del Builder); arma, armadura, mascota, nivel y habilidades son opcionales. `agregarHabilidad()` puede llamarse múltiples veces seguidas antes de construir. El método `ficha()` imprime solo los campos que fueron definidos.
- `model/PersonajePlantillas.java`: cumple un rol equivalente al **Director** — expone `guerreroPorDefecto()`, `magoPorDefecto()` y `arqueroPorDefecto()`, reutilizando el mismo Builder por dentro sin duplicar la lógica de construcción.
- `app/Main.java`: crea y muestra al menos tres personajes distintos — uno bien equipado, uno con los atributos mínimos y uno usando una plantilla predefinida.
