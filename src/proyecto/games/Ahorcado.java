
package proyecto.games;

import proyecto.*;
import java.util.*;

public class Ahorcado implements Game {
    @Override public String titulo() { return "Ahorcado"; }

    private static final String[] DIBUJO = new String[]{
        "  _____   \n |     |  \n |        \n |        \n |        \n |        \n |        \n===========",
        "  _____   \n |     |  \n |     O  \n |        \n |        \n |        \n |        \n===========",
        "  _____   \n |     |  \n |     O  \n |     |  \n |        \n |        \n |        \n===========",
        "  _____   \n |     |  \n |     O  \n |    /|  \n |        \n |        \n |        \n===========",
        "  _____   \n |     |  \n |     O  \n |    /|\\ \n |        \n |        \n |        \n===========",
        "  _____   \n |     |  \n |     O  \n |    /|\\ \n |    /   \n |        \n |        \n===========",
        "  _____   \n |     |  \n |     O  \n |    /|\\ \n |    / \\ \n |        \n |        \n==========="
    };

    @Override
    public void play(Stats stats, Scanner in) {
        String[] dic = {"perro","gato","libro","escuela","elefante","mariposa","dinosaurio","helicoptero","estrella","computadora","camioneta","esmeralda","catedral","enciclopedia","espejo"};
        Random rnd = new Random();
        String palabra = dic[rnd.nextInt(dic.length)];
        Set<Character> usadas = new LinkedHashSet<>();
        char[] guiones = new char[palabra.length()];
        Arrays.fill(guiones, '_');
        int errores = 0;

        while (errores < 6 && new String(guiones).contains("_")) {
            Util.cls();
            Util.blockCentered(DIBUJO[errores]);
            Util.center("Palabra: "+String.valueOf(guiones));
            Util.center("Letras usadas: "+usadas);
            System.out.print("Ingresa una letra: ");
            String s = in.nextLine().toLowerCase(Locale.ROOT);
            if (s.isEmpty()) continue;
            char c = s.charAt(0);
            if (usadas.contains(c)) continue;
            usadas.add(c);
            boolean match = false;
            for (int i=0;i<palabra.length();i++) {
                if (palabra.charAt(i)==c) { guiones[i] = c; match=true; }
            }
            if (!match) errores++;
        }
        Util.cls();
        if (!new String(guiones).contains("_")) {
            Util.center("¡Muy bien! La palabra era "+palabra);
            Util.blockCentered(AsciiArt.felicidades());
            stats.win();
        } else {
            Util.blockCentered(DIBUJO[6]);
            Util.center("Se terminaron los intentos. La palabra era "+palabra);
            stats.lose();
        }
        Util.center("Enter para volver");
        in.nextLine();
    }
}
