package gr.ntua.ece.medialab.minesweeper.types;

import java.util.List;
import java.util.ArrayList;

public class Grid {

    private int width;
    private int height;

    private Block[][] blockGrid;

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;

        blockGrid = new Block[width][height];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Block getBlock(Coordinates coords) {
        return blockGrid[coords.getX()][coords.getY()];
    }

    public  List<Block> getNeighboringBlocks(Coordinates coords) {
        List<Block> neighboringBlocks = new ArrayList<Block>();

        for(int x = coords.getX() - 1; x <= coords.getX() + 1; x++) {
            for(int y = coords.getY() - 1; y <= coords.getY() + 1; y++) {
                Coordinates neighboringCoordinates = new Coordinates(x, y);

                if(!(x == coords.getX() && y == coords.getY())) {
                    if(withinGrid(neighboringCoordinates)) neighboringBlocks.add(getBlock(neighboringCoordinates));
                }
            }
        }

        return neighboringBlocks;
    }

    public List<Block> getNeighboringBlocks(Block block) {
        return getNeighboringBlocks(block.getCoordinates());
    }

    public boolean withinGrid(Coordinates coords) {
        if(coords.getX() < 0 || coords.getY() < 0) return false;
        if(coords.getX() >= width || coords.getY() >= height) return false;

        return true;
    }

}