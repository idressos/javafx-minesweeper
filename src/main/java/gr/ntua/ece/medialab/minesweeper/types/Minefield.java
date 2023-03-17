package gr.ntua.ece.medialab.minesweeper.types;

import java.util.List;
import java.util.ArrayList;

import java.security.SecureRandom;

import javafx.scene.layout.Pane;

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

        setPrefSize(Block.BLOCK_SIZE * scenario.getGridSize(), Block.BLOCK_SIZE * scenario.getGridSize());
        
        SecureRandom rand = new SecureRandom();
        
        List<Coordinates> bombCoords = new ArrayList<Coordinates>();
        while(bombCoords.size() < scenario.getMineCount()) {
            Coordinates coords = new Coordinates(rand.nextInt(getGridWidth()), rand.nextInt(getGridHeight()));
            
            if(!bombCoords.contains(coords)) bombCoords.add(coords);
        }
        
        for(int x = 0; x < getGridWidth(); x++) {
            for(int y = 0; y < getGridHeight(); y++) {
                Coordinates coords = new Coordinates(x, y);
                
                grid[x][y] = new Block(bombCoords.contains(coords) ? (bombCoords.indexOf(coords) == 4 ? new Supermine() : new Mine()) : null, coords);
            }
        }
        
        getChildren().setAll(getAllBlocks());
        
        isIntact = true;
        leftClicksCount = 0;
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
        
        for(int x = 0; x < getGridWidth(); x++) {
            for(int y = 0; y < getGridHeight(); y++) {
                blocks.add(grid[x][y]);
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
        if(isIntact) {
            isIntact = false;
            
            for(Block block : getAllBlocks()) {
                if(block.hasMine()) block.open();
            }
            
            App.getCountdownTimer().stop();
        }
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