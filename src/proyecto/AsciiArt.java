package proyecto;

public final class AsciiArt {

    private AsciiArt() {
    }

    public static String[] ahorcadoTitulo() {
        return new String[] {
                "    /\\   | |                           | |      ",
                "   /  \\  | |__   ___  _ __ ___ __ _  __| | ___  ",
                "  / /\\ \\ | '_ \\ / _ \\| '__/ __/ _` |/ _` |/ _ \\ ",
                " / ____ \\| | | | (_) | | | (_| (_| | (_| | (_) |",
                "/_/    \\_\\_| |_|\\___/|_|  \\___\\__,_|\\__,_|\\___/ ",
                "",
                "Este es el juego de ahorcado.",
                "El juego cuenta con algunas palabras almacenadas, pero se pueden agregar más.",
                "Una vez que se selecciona ¡Jugar!, el juego elije una palabra al azar.",
                "Se ingresan letras, 1 en 1, buscando completar los espacios."
        };
    }

    public static String[] felicidades() {
        return new String[] {
                "  _   _____ _____ _     ___ ____ ___ ____    _    ____  _____ ____    _ ",
                " (_) |  ___| ____| |   |_ _/ ___|_ _|  _ \\  / \\  |  _ \\| ____/ ___|  | |",
                " | | | |_  |  _| | |    | | |    | || | | |/ _ \\ | | | |  _| \\___ \\  | |",
                " | | |  _| | |___| |___ | | |___ | || |_| / ___ \\| |_| | |___ ___) | |_|",
                " |_| |_|   |_____|_____|___\\____|___|____/_/   \\_\\____/|_____|____/  (_)"
        };
    }

    public static String[] gameOver() {
        return new String[] {
                "  ____    _    __  __ _____     ____  _   _ _____ ____  ",
                " / ___|  / \\  |  \\/  | ____|   / _ \\| | | | ____|  _ \\ ",
                "| |  _  / _ \\ | |\\/| |  _|    | | | | | | |  _| | |_) |",
                "| |_| |/ ___ \\| |  | | |___   | |_| |\\ V /| |___|  _ < ",
                " \\____/_/   \\_\\_|  |_|_____|   \\___/  \\_/ |_____|_| \\_\\"
        };
    }

    public static String[] caritaFelizJugador(String quien) {
        return new String[] {
                "   *****                                  *****   ",
                " *       * **************************** *       * ",
                "*  ^   ^  **         " + quien + "        **  ^   ^  *",
                "*    L    **         ¡GANASTE!        **    L    *",
                "*  \\___/  **       ¡Felicidades!      **  \\___/  *",
                " *       * **************************** *       * ",
                "   *****                                  *****   "
        };
    }

    public static String[] caritaTriste() {
        return new String[] {
                "                   *****   ",
                "*************    *       * ",
                "  LO SIENTO     * ´O   O` *",
                " HAS PERDIDO    *    L    *",
                "*************   *   ~~~~  *",
                "                 *       * ",
                "                   *****   "
        };
    }

    public static String[] ahorcadoEtapa(int errores) {
        String cabeza = (errores >= 1) ? " |     O  " : " |        ";
        String tronco;
        if (errores >= 4)
            tronco = " |    /|\\  ";
        else if (errores >= 3)
            tronco = " |    /|  ";
        else if (errores >= 2)
            tronco = " |     |  ";
        else
            tronco = " |        ";

        String piernas;
        if (errores >= 6)
            piernas = " |    / \\  ";
        else if (errores >= 5)
            piernas = " |    /   ";
        else
            piernas = " |        ";

        return new String[] {
                "  _____   ",
                " |     |  ",
                cabeza,
                tronco,
                piernas,
                " |         ",
                "==========="
        };
    }

    public static void loader(int loops, int frameDelayMs) {
        final char[] spin = new char[] { '|', '/', '-', '\\' };
        for (int j = 0; j < loops; j++) {
            for (int i = 0; i < spin.length; i++) {
                Util.cls();
                Util.blank(10);
                Util.center("Cargando" + ".".repeat(i));
                Util.center("[ " + spin[i] + " ]");
                Util.sleep(frameDelayMs);
            }
        }
        Util.cls();
    }
}
