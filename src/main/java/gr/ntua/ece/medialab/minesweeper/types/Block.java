package gr.ntua.ece.medialab.minesweeper.types;

public class Block {

    private Mine mine;
    private Coordinates coords;
    private boolean isMarked;

    public Block(Mine mine , Coordinates coords, boolean isMarked) {
        this.mine = mine;
        this.coords = coords;
        this.isMarked = isMarked;
    }

    public Mine getMine() {
        return mine;
    }

    public Coordinates getCoordinates() {
        return coords;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public void mark() {
        isMarked = true;
    }

    public void unmark() {
        isMarked = false;
    }

}