
package proyecto.games;

import proyecto.*;
import java.util.*;

public class MemoryGame implements Game {
    @Override
    public String titulo() {
        return "Juego de memoria";
    }

    @Override
    public void play(Stats stats, Scanner in) {
        List<String> palabras = List.of(
                "Eclipse", "Montaña", "Relámpago", "Almohada", "Laberinto",
                "Espejo", "Cactus", "Horizonte", "Guitarra", "Reloj",
                "Papel", "Murmullo", "Abismo", "Cometa", "Cosecha",
                "Tinta", "Susurro", "Escalera", "Nube", "Travesía");
        int nivel = 1;
        boolean salir = false;
        Util.cls();
        Util.center("Reglas: recuerda el orden. 4 niveles (5,10,15,20 palabras).");
        Util.center("Presiona Enter para comenzar.");
        in.nextLine();

        while (!salir && nivel <= 4) {
            int cant = 5 * nivel;
            List<Integer> idx = indicesSinRepetir(cant, 20);
            List<String> mostradas = new ArrayList<>();
            // conteo
            for (int k = 3; k >= 1; k--) {
                Util.cls();
                Util.center("Las palabras se mostraran en: " + k);
                Util.sleep(700);
            }
            // mostrar palabras
            for (int i = 0; i < cant; i++) {
                Util.cls();
                Util.center((i + 1) + ". " + palabras.get(idx.get(i)));
                mostradas.add(palabras.get(idx.get(i)));
                Util.sleep(850);
            }

            // desordenar
            List<String> desorden = new ArrayList<>(mostradas);
            Collections.shuffle(desorden, new Random());

            // pedir orden
            boolean correcto = true;
            Util.cls();
            Util.center("| Índice | Palabra [nivel: " + nivel + "/4]");
            for (int i = 0; i < desorden.size(); i++) {
                Util.center("[" + (i + 1) + "] " + desorden.get(i));
            }
            Util.blank(1);
            Util.center("Ingresá el ORDEN correcto como índices separados por espacios (ej: 2 1 3 ...)");
            String linea = in.nextLine().trim();
            String[] parts = linea.split("\\s+");
            if (parts.length != cant) {
                correcto = false;
            } else {
                for (int i = 0; i < cant; i++) {
                    int pos;
                    try {
                        pos = Integer.parseInt(parts[i]);
                    } catch (Exception e) {
                        correcto = false;
                        break;
                    }
                    if (pos < 1 || pos > cant) {
                        correcto = false;
                        break;
                    }
                    if (!desorden.get(pos - 1).equals(mostradas.get(i))) {
                        correcto = false;
                        break;
                    }
                }
            }

            Util.cls();
            if (correcto) {
                stats.win();
                Util.blockCentered(AsciiArt.felicidades());
                if (nivel < 4) {
                    Util.center("¿Continuar al siguiente nivel? (1:Si, otra tecla: Salir)");
                    String s = in.nextLine().trim();
                    if ("1".equals(s)) {
                        nivel++;
                    } else
                        salir = true;
                } else {
                    Util.center("¡Completaste todos los niveles!");
                    Util.blockCentered(AsciiArt.felicidades());

                    salir = true;
                    in.nextLine();
                }
            } else {
                stats.lose();
                Util.blockCentered(AsciiArt.gameOver());
                Util.center("Mejor suerte la próxima. ¿Reintentar nivel? (1:Si, otra tecla: Salir)");
                String s = in.nextLine().trim();
                if (!"1".equals(s))
                    salir = true;
            }
        }
    }

    private List<Integer> indicesSinRepetir(int cant, int max) {
        List<Integer> ids = new ArrayList<>();
        Random rnd = new Random();
        while (ids.size() < cant) {
            int x = rnd.nextInt(max);
            if (!ids.contains(x))
                ids.add(x);
        }
        return ids;
    }
}
