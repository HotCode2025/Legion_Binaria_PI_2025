
package proyecto.games;

import proyecto.*;
import java.util.*;

public class PiedraPapelTijera implements Game {
    @Override public String titulo() { return "Piedra, Papel o Tijera"; }

    @Override
    public void play(Stats stats, Scanner in) {
        int ronda = 1, pJ=0, pC=0;
        Random rnd = new Random();
        while (ronda <= 3) {
            Util.cls();
            Util.center("---- Ronda Nº " + ronda + " ----");
            Util.center("Elige: 1=Piedra, 2=Papel, 3=Tijera");
            String s = in.nextLine().trim();
            if (!s.matches("[123]")) continue;
            int j = Integer.parseInt(s);
            int c = rnd.nextInt(3)+1;
            String sj = nombre(j), sc = nombre(c);
            Util.center("Tú: " + sj + " | CPU: " + sc);
            if (gana(j,c)) { Util.center("Ganaste la ronda"); pJ++; }
            else if (j==c) { Util.center("Empate"); }
            else { Util.center("Perdiste la ronda"); pC++; }
            Util.center("Enter para seguir"); in.nextLine();
            ronda++;
        }
        Util.cls();
        if (pJ>pC) { Util.blockCentered(AsciiArt.felicidades()); stats.win(); }
        else if (pJ<pC) { Util.blockCentered(AsciiArt.gameOver()); stats.lose(); }
        else {
            Util.center("Empate. Ronda extra...");
            // Desempate
            rnd = new Random();
            while (true) {
                Util.center("Elige: 1=Piedra, 2=Papel, 3=Tijera");
                String s = in.nextLine().trim();
                if (!s.matches("[123]")) continue;
                int j = Integer.parseInt(s);
                int c = rnd.nextInt(3)+1;
                if (j==c) { Util.center("Empate, de nuevo..."); continue; }
                if (gana(j,c)) { Util.blockCentered(AsciiArt.felicidades()); stats.win(); }
                else { Util.blockCentered(AsciiArt.gameOver()); stats.lose(); }
                break;
            }
        }
        Util.center("Enter para volver");
        in.nextLine();
    }

    private boolean gana(int a, int b){
        return (a==1 && b==3) || (a==2 && b==1) || (a==3 && b==2);
    }
    private String nombre(int x){
        return switch(x){ case 1->"Piedra"; case 2->"Papel"; default->"Tijera"; };
    }
}
