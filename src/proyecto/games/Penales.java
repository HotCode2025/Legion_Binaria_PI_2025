package proyecto.games;

import proyecto.*;
import java.util.*;

public class Penales implements Game {

    @Override public String titulo() { return "Penales"; }

    @Override
    public void play(Stats stats, Scanner in) {
        Random rnd = new Random();
        int goles = 0, atajadas = 0;

        Util.cls();
        Util.center("Bienvenido al Juego de Penales");
        Util.center("Objetivo: anotar 7 goles antes de que el arquero ataje 3.");
        Util.center("Elegí 1(Izq) / 2(Centro) / 3(Der). Enter para comenzar.");
        in.nextLine();

        boolean fin = false;
        while (!fin) {
            Util.cls();
            marcador(goles, atajadas);
            Util.center("Elige tu tiro [1=Izq, 2=Centro, 3=Der]:");
            String s = in.nextLine().trim();
            if (!s.matches("[123]")) continue;

            int tiro = Integer.parseInt(s);     // 1..3
            int arquero = rnd.nextInt(3) + 1;   // 1..3

            boolean atajo = (tiro == arquero);

            // Mostrar jugada con ASCII
            dibujarJugada(arquero, tiro, atajo);

            if (atajo) {
                atajadas++;
                Util.center("¡ATAJADA!");
            } else {
                goles++;
                Util.center("¡GOOOOL!");
            }
            Util.sleep(900);

            if (goles >= 7) {
                stats.win();
                Util.cls();
                Util.blockCentered(AsciiArt.felicidades());
                Util.center("¡Ganaste la tanda!");
                fin = true;
            } else if (atajadas >= 3) {
                stats.lose();
                Util.cls();
                Util.center("Perdiste, el arquero atajó 3.");
                fin = true;
            }
        }

        Util.center("Enter para volver");
        in.nextLine();
    }

    /* =================== ASCII =================== */

    private void marcador(int goles, int atajadas) {
        Util.center("Marcador: Tú " + goles + " | Arquero " + atajadas);
        Util.blank(1);
    }

    /**
     * Dibuja un arco con 3 carriles:
     *  - arquero parado en 'arqueroLane' (1..3)
     *  - pelota en 'shotLane' (1..3)
     *  - si atajo=true, la pelota se muestra frente al arquero
     *    si atajo=false, la pelota aparece "dentro" de la red en ese carril
     */
    private void dibujarJugada(int arqueroLane, int shotLane, boolean atajo) {
        Util.cls();
        Util.center("Disparo...");
        Util.blank(1);
        Util.center("==============  A R C O  ==============");

        // Cada carril es un bloque de ancho fijo
        final int CELL_W = 11;

        // Buffer de 10 filas de cancha
        int rows = 10;
        String[][] c = new String[3][rows];
        for (int ln = 0; ln < 3; ln++) Arrays.fill(c[ln], " ".repeat(CELL_W));

        // Red (líneas verticales) y travesaños
        for (int r = 1; r <= 5; r++) {
            for (int ln = 0; ln < 3; ln++) {
                c[ln][r] = setChar(c[ln][r], 0,  '|');
                c[ln][r] = setChar(c[ln][r], CELL_W - 1, '|');
            }
        }
        // Techo de red
        for (int ln = 0; ln < 3; ln++) {
            char[] row0 = c[ln][0].toCharArray();
            Arrays.fill(row0, '=');
            c[ln][0] = new String(row0);
        }
        // Piso del área
        for (int ln = 0; ln < 3; ln++) {
            char[] row = c[ln][8].toCharArray();
            Arrays.fill(row, '_');
            c[ln][8] = new String(row);
        }

        // Arquero (stick figure) en su carril, centrado
        int a = arqueroLane - 1;
        putCentered(c[a], 2, "   O    ");
        putCentered(c[a], 3, "  /|\\   ");
        putCentered(c[a], 4, "   |    ");
        putCentered(c[a], 5, "  / \\   ");

        // Pelota
        if (atajo) {
            // Frente al arquero (un poco por delante del torso)
            putCentered(c[a], 3, "  (o)   ");
        } else {
            // Dentro de la red en el carril pateado
            int s = shotLane - 1;
            putCentered(c[s], 1, "   o    ");
        }

        // Render
        for (int r = 0; r < rows; r++) {
            String line = " " + c[0][r] + " " + c[1][r] + " " + c[2][r];
            Util.center(line);
        }
        Util.center("=======================================");
        Util.blank(1);
    }

    /* ===== helpers para centrar strings dentro de celdas ===== */

    private static void putCentered(String[] column, int row, String sprite) {
        if (row < 0 || row >= column.length) return;
        int w = column[row].length();
        String s = sprite;
        if (s.length() > w) s = s.substring(0, w);
        int pad = (w - s.length()) / 2;
        String out = " ".repeat(pad) + s + " ".repeat(w - pad - s.length());
        column[row] = out;
    }

    private static String setChar(String base, int idx, char ch) {
        char[] a = base.toCharArray();
        if (idx >= 0 && idx < a.length) a[idx] = ch;
        return new String(a);
    }
}
