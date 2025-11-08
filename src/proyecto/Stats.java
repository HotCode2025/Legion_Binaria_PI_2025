package proyecto;

public class Stats {
    private int ganadas = 0;
    private int perdidas = 0;

    public void win() {
        ganadas++;
    }

    public void lose() {
        perdidas++;
    }

    public int getGanadas() {
        return ganadas;
    }

    public int getPerdidas() {
        return perdidas;
    }
}
