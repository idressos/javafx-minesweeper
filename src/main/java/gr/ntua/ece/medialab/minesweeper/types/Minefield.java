package gr.ntua.ece.medialab.minesweeper.types;

import java.util.List;
import java.util.ArrayList;

import javafx.scene.layout.Pane;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import gr.ntua.ece.medialab.minesweeper.App;

public class Minefield extends Pane {

    private static Scenario scenario;
    private static Block[][] grid;

    private static boolean isIntact = true;

    private static int leftClicksCount = 0;

    public Minefield(Scenario scenario) {
        super();

        Minefield.scenario = scenario;

        grid = new Block[scenario.getGridSize()][scenario.getGridSize()];
    }

    public static Scenario getScenario() {
        return scenario;
    }

    public static int getGridWidth() {
        return scenario.getGridSize();
    }

    public static int getGridHeight() {
        return scenario.getGridSize();
    }

    public static Block getBlock(Coordinates coords) {
        return grid[coords.getX()][coords.getY()];
    }

    public static List<Block> getAllBlocks() {
        List<Block> blocks = new ArrayList<Block>();

        for(int i = 0; i < getGridWidth(); i++) {
            for(int j = 0; j < getGridHeight(); j++) {
                blocks.add(grid[i][j]);
            }
        }

        return blocks;
    }

    public static List<Block> getNeighboringBlocks(Coordinates coords) {
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

    public static List<Block> getNeighboringBlocks(Block block) {
        return getNeighboringBlocks(block.getCoordinates());
    }

    public static boolean withinGrid(Coordinates coords) {
        if(coords.getX() < 0 || coords.getY() < 0) return false;
        if(coords.getX() >= getGridWidth() || coords.getY() >= getGridHeight()) return false;

        return true;
    }

    public static boolean isIntact() {
        return isIntact;
    }

    public static void blowUp() {
        isIntact = false;

        for(Block block : getAllBlocks()) {
            if(block.hasMine()) {
                Image mineImage = new Image("");
                ImageView mineImageView = new ImageView(mineImage);

                mineImageView.setFitWidth(App.getBlockSize() - 2);
                mineImageView.setFitHeight(App.getBlockSize() - 2);

                block.getChildren().setAll(mineImageView);
                mineImageView.setVisible(true);
            }
        }
        
        App.stopTimer();
        // TODO: More game over methods
    }

    public static long getMarkedBlockCount() {
        return getAllBlocks().stream().filter(block -> block.isMarked()).count();
    }

    public static int getLeftClicksCount() {
        return leftClicksCount;
    }

    public static void incrementLeftClicksCount() {
        leftClicksCount++;
    }

}