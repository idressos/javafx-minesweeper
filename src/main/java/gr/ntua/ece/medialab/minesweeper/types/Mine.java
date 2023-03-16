package gr.ntua.ece.medialab.minesweeper.types;

public class Mine {

    public Mine() {}

    public void explode() {
        Minefield.blowUp();
    }

}