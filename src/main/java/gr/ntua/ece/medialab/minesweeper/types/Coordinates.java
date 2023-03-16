package gr.ntua.ece.medialab.minesweeper.types;

public class Coordinates {

    private int x;
    private int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Coordinates) {
            Coordinates coords = (Coordinates) o;
            if(coords.getX() == this.getX() && coords.getY() == this.getY()) return true;
        }

        return false;
    }

}