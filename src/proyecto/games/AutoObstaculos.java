package proyecto.games;

import proyecto.*;
import java.util.*;

/**
 * Auto con obstáculos — modo por turno:
 * - En pantalla siempre hay un único obstáculo.
 * - Cada entrada del usuario (A/D/W) resuelve ese obstáculo.
 * - Si el carril del auto coincide con el del obstáculo -> choque y termina.
 * - Si no coincide -> se cuenta como esquivado y se genera un obstáculo nuevo.
 */
public class AutoObstaculos implements Game {

    @Override
    public String titulo() {
        return "Auto con obstaculos";
    }

    // Configuración de pista
    private static final int LANES = 3;
    private static final int TRACK_ROWS = 10; // alto visible de la pista

    // Sprites
    private static final String[] CAR = {
            "  ###  ",
            " #@@@# ",
            "#######",
            "#     #"
    };
    private static final int CAR_H = CAR.length;

    private static final String[] OBS = {
            " XXX ",
            "XXXXX",
            " XXX "
    };
    private static final int OBS_H = OBS.length;

    @Override
    public void play(Stats stats, Scanner in) {
        int carLane = 1; // 0..2 (arranca centro)
        int esquivados = 0; // gana con 5
        boolean choque = false;

        // Un obstáculo activo por turno
        int obsLane = rngLane();

        while (!choque && esquivados < 5) {
            // Dibujar estado actual
            dibujar(carLane, obsLane, esquivados);

            // Input (un solo movimiento por turno)
            System.out.print("Mover (A=Izq, D=Der, W=Seguir, Q=Salir): ");
            String s = in.nextLine().trim();
            if (s.equalsIgnoreCase("q"))
                break;
            if (s.equalsIgnoreCase("a") && carLane > 0)
                carLane--;
            else if (s.equalsIgnoreCase("d") && carLane < LANES - 1)
                carLane++;
            // con W o tecla distinta mantiene carril

            // Resolver el obstáculo del turno
            if (carLane == obsLane) {
                choque = true; // terminó
            } else {
                // Esquivado: sumar y crear obstáculo nuevo
                esquivados++;
                // SIN pantallas intermedias: se vuelve al while y se redibuja
                obsLane = rngLane();
            }
        }

        Util.cls();
        if (choque) {
            Util.center("¡PERDISTE! Te chocaste.");
            stats.lose();
        } else if (esquivados >= 5) {
            Util.blockCentered(AsciiArt.felicidades());
            Util.center("¡GANASTE! Esquivaste 5 obstáculos.");
            stats.win();
        } else {
            Util.center("Juego cancelado.");
        }
        Util.center("Enter para volver.");
        in.nextLine();
    }

    private void dibujar(int carLane, int obsLane, int esquivados) {
        Util.cls();
        Util.center("Evita chocar.  A=Izquierda, D=Derecha, W=Seguir");
        Util.blank(1);
        Util.center("================  PISTA  ================");

        // buffer de pista: 3 carriles × TRACK_ROWS
        String[][] laneCells = new String[LANES][TRACK_ROWS];
        for (int ln = 0; ln < LANES; ln++) {
            Arrays.fill(laneCells[ln], "       ");
        }

        // Ubicaciones fijas: obstáculo arriba, auto abajo
        int obsRowTop = 1;
        int carRowTop = TRACK_ROWS - CAR_H;

        // Pintar obstáculo
        for (int r = 0; r < OBS_H; r++) {
            laneCells[obsLane][obsRowTop + r] = center7(OBS[r]);
        }
        // Pintar auto
        for (int r = 0; r < CAR_H; r++) {
            laneCells[carLane][carRowTop + r] = center7(CAR[r]);
        }

        // Render vertical
        for (int rr = 0; rr < TRACK_ROWS; rr++) {
            StringBuilder line = new StringBuilder();
            line.append("   |");
            for (int ln = 0; ln < LANES; ln++) {
                line.append(laneCells[ln][rr]).append("|");
            }
            Util.center(line.toString());
        }

        Util.center("=========================================");
        Util.blank(1);
        Util.center("Esquivados: " + esquivados + " / 5");
        Util.blank(1);
    }

    private static int rngLane() {
        return new Random().nextInt(LANES); // 0..2
    }

    private static String center7(String s) {
        if (s.length() > 7)
            s = s.substring(0, 7);
        int total = 7 - s.length();
        int left = total / 2;
        int right = total - left;
        return " ".repeat(left) + s + " ".repeat(right);
    }
}
