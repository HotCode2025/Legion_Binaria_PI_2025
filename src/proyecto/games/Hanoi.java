package proyecto.games;

import proyecto.*;
import java.util.*;

public class Hanoi implements Game {

    // Ancho fijo de cada columna y separador entre columnas
    private static final int COL = 11;
    private static final String SEP = "   ";

    @Override
    public String titulo() {
        return "Torres de Hanoi";
    }

    @Override
    public void play(Stats stats, Scanner in) {
        Util.cls();
        mostrarReglas(in);

        int n = 0;
        while (n < 2 || n > 8) {
            Util.center("Cantidad de discos (2-8): ");
            String s = in.nextLine().trim();
            try {
                n = Integer.parseInt(s);
            } catch (Exception ignored) {
            }
        }

        // Torretas como pilas (tope = arriba)
        Deque<Integer>[] t = new Deque[3];
        for (int i = 0; i < 3; i++)
            t[i] = new ArrayDeque<>();
        for (int d = n; d >= 1; d--)
            t[0].push(d);

        int movs = 0;
        while (t[2].size() != n) { // ganar = todos los discos en la torre 3
            dibujar(t, n);
            Util.center("Mover desde torre (1-3), hacia (1-3). Vacío para salir.");
            System.out.print(">> desde: ");
            String ds = in.nextLine().trim();
            if (ds.isEmpty())
                break;
            System.out.print(">> hacia: ");
            String hs = in.nextLine().trim();
            if (hs.isEmpty())
                break;

            int d, h;
            try {
                d = Integer.parseInt(ds);
                h = Integer.parseInt(hs);
            } catch (Exception e) {
                continue;
            }

            if (d < 1 || d > 3 || h < 1 || h > 3)
                continue;
            if (t[d - 1].isEmpty())
                continue;

            int disco = t[d - 1].peek();
            // Regla: no poner uno grande sobre uno chico
            if (!t[h - 1].isEmpty() && t[h - 1].peek() < disco) {
                Util.center("Movimiento inválido (no puedes poner un disco grande sobre uno más chico).");
                Util.sleep(900);
                continue;
            }

            t[d - 1].pop();
            t[h - 1].push(disco);
            movs++;
        }

        Util.cls();
        if (t[2].size() == n) {
            int minimo = (int) Math.pow(2, n) - 1;
            if (movs <= minimo) {
                Util.blockCentered(AsciiArt.felicidades());
                Util.center("¡Ganaste! Movimientos: " + movs + " (mínimo: " + minimo + ")");
                stats.win();
            } else {
                Util.center("Completaste, pero superaste el mínimo (" + movs + " vs " + minimo + ").");
                stats.lose();
            }
        } else {
            Util.center("Juego cancelado.");
        }
        Util.center("Enter para volver");
        in.nextLine();
    }

    // ---------- Reglas ----------
    private void mostrarReglas(Scanner in) {
        Util.center("Torres de Hanoi");
        Util.center("Mueve todos los discos de la Torre 1 a la Torre 3.");
        Util.blank(1);
        Util.center("REGLAS:");
        Util.center("1) Solo puedes mover UN disco a la vez.");
        Util.center("2) Solo puedes mover el disco SUPERIOR de una torre.");
        Util.center("3) Nunca coloques un disco GRANDE sobre uno más CHICO.");
        Util.blank(1);
        Util.center("Objetivo: dejar los discos en la TORRE 3 en el mismo orden (grande abajo).");
        Util.blank(1);
        Util.center("Presiona Enter para continuar…");
        in.nextLine();
        Util.cls();
    }
    // ----------------------------

    private void dibujar(Deque<Integer>[] t, int n) {
        Util.cls();
        Util.center("Estado actual");

        // Dibujo de arriba hacia abajo, asegurando el MISMO largo por fila
        for (int level = n; level >= 1; level--) {
            String c1 = renderCol(t[0], level);
            String c2 = renderCol(t[1], level);
            String c3 = renderCol(t[2], level);
            String line = c1 + SEP + c2 + SEP + c3; // todas las filas mismo ancho
            Util.center(line);
        }

        // Línea de base / etiquetas, centradas por columna con igual ancho
        String l1 = labelCol("TORRE 1");
        String l2 = labelCol("TORRE 2");
        String l3 = labelCol("TORRE 3");
        Util.center(l1 + SEP + l2 + SEP + l3);
    }

    private String renderCol(Deque<Integer> torre, int level) {
        int d = discoEnNivel(torre, level);
        if (d == 0)
            return "     |     "; // 5 + 1 + 5 = 11 ✅
        int side = 5 - d;
        return " ".repeat(side) + "o".repeat(2 * d + 1) + " ".repeat(side); // 11
    }

    // Etiqueta centrada en la columna con guiones a los lados
    private String labelCol(String text) {
        int inner = Math.max(0, COL - text.length());
        int left = inner / 2;
        int right = inner - left;
        return "-".repeat(left) + text + "-".repeat(right);
    }

    // Devuelve el tamaño del disco que se “ve” en el nivel indicado (o 0 si vacío)
    private int discoEnNivel(Deque<Integer> torre, int level) {
        List<Integer> list = new ArrayList<>(torre); // tope = índice 0
        int visualIndex = list.size() - level;
        if (visualIndex < 0 || visualIndex >= list.size())
            return 0;
        return list.get(visualIndex);
    }
}
