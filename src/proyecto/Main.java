package proyecto;

import proyecto.games.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Stats stats = new Stats();
        Map<Integer, Game> juegos = new LinkedHashMap<>();
        juegos.put(1, new Ahorcado());
        juegos.put(2, new AutoObstaculos());
        juegos.put(3, new PiedraPapelTijera());
        juegos.put(4, new Penales());
        juegos.put(5, new Hanoi());
        juegos.put(6, new MemoryGame());

        boolean activo = true;
        boolean primer = true;
        while (activo) {
            Util.cls();
            Util.blank(3);
            if (primer) {
                Util.center("Bienvenido/a a nuestra aplicación");
                Util.center("Elegí un juego para empezar");
                primer = false;
            } else {
                Util.center("¿Otro juego?");
            }
            Util.blank(1);
            for (Map.Entry<Integer, Game> e : juegos.entrySet()) {
                Util.center(e.getKey()+". "+e.getValue().titulo());
            }
            Util.center("0. SALIR");
            Util.blank(1);
            System.out.print("".repeat(35)); // margen
            String opc = in.nextLine().trim();
            int k = -1;
            try { k = Integer.parseInt(opc); } catch (Exception ignored) {}
            if (k == 0) { activo = false; break; }
            if (!juegos.containsKey(k)) {
                Util.center("Opción inválida"); Util.sleep(700); continue;
            }
            juegos.get(k).play(stats, in);
        }
        Util.cls();
        Util.blank(4);
        Util.center("Las estadísticas finales son:");
        Util.center("Partidas ganadas: "+stats.getGanadas()+" - Partidas perdidas: "+stats.getPerdidas());
        Util.blank(2);
    }
}
