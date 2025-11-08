package proyecto;

public class Util {
    public static void cls() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void blank(int n) {
        for (int i = 0; i < n; i++)
            System.out.println();
    }

    public static void center(String s) {
        if (s == null)
            s = "";
        int width = 90;
        if (s.length() >= width) {
            System.out.println(s);
            return;
        }
        int pad = Math.max(0, (width - s.length()) / 2);
        System.out.println(" ".repeat(pad) + s);
    }

    public static void blockCentered(String[] block) {
        for (String l : block)
            center(l);
    }

    public static void blockCentered(String block) {
        String[] lines = block.split("\\R");
        for (String l : lines)
            center(l);
    }

    public static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
        }
    }
}
