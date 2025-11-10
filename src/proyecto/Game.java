package proyecto;
import java.util.Scanner;

public interface Game {
    String titulo();
    void play(Stats stats, Scanner in);
}
