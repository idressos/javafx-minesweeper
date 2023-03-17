package gr.ntua.ece.medialab.minesweeper.types;

import javafx.scene.text.Text;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.StackPane;

import javafx.scene.input.MouseButton;

import gr.ntua.ece.medialab.minesweeper.App;

public class Block extends StackPane {
	
	public static final int BLOCK_SIZE = 40;

	private ImageView imageView = new ImageView();
	private static final Image blockImage = new Image("block.png");
	private static final Image flagImage = new Image("flag.png");
	private static final Image mineImage = new Image("mine.png");
	
    private Mine mine;
    private Coordinates coords;
    
    private boolean isOpen;
    private boolean isMarked;
    
    private Text text = new Text();
    
    public Block(Mine mine, Coordinates coords) {
        this.mine = mine;
        this.coords = coords;
        
        text.setVisible(false);
        
        imageView.setImage(blockImage);
        imageView.setFitWidth(BLOCK_SIZE - 2);
        imageView.setFitHeight(BLOCK_SIZE - 2);
        imageView.setVisible(true);
        
        getChildren().setAll(text, imageView);
        
        setTranslateX(coords.getX() * BLOCK_SIZE);
        setTranslateY(coords.getY() * BLOCK_SIZE);
        
        setOnMouseClicked(e -> {
            if(!Minefield.isIntact()) return;

            if(!App.getCountdownTimer().isRunning()) App.getCountdownTimer().start();

            if(e.getButton() == MouseButton.PRIMARY && !isMarked) {
                Minefield.incrementLeftClicksCount();
                open();
            } else if(e.getButton() == MouseButton.SECONDARY && !isOpen) {
                if(isMarked) {
                	isMarked = false;
                    
                    imageView.setImage(blockImage);
                    
                    App.setMarkedCounter(Minefield.getMarkedBlockCount());
                } else if(Minefield.getMarkedBlockCount() < Minefield.getScenario().getMineCount()){
                	isMarked = true;
                	
                	imageView.setImage(flagImage);
                    
                    if(Minefield.getLeftClicksCount() >= 4) {
                    	// TODO: Supermine reveal code
                    }
                    
                    App.setMarkedCounter(Minefield.getMarkedBlockCount());
                }
            }
        });
    }
    
    public Mine getMine() {
        return mine;
    }
    
    public boolean hasMine() {
        return mine != null;
    }
    
    public Coordinates getCoordinates() {
        return coords;
    }
    
    public boolean isOpen() {
        return isOpen;
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
    
    public long getNeighboringMineCount() {
        return Minefield.getNeighboringBlocks(this).stream().filter(block -> block.hasMine()).count();
    }
    
    public void open() {
        if(isOpen) return;
        if(hasMine()) {
            imageView.setImage(mineImage);
            getMine().explode();
            
            return;
        }
        
        long neighboringMineCount = getNeighboringMineCount();
        
        isOpen = true;
        
        text.setText(String.valueOf(neighboringMineCount));
        text.setVisible(true);
        imageView.setVisible(false);
        
        if(neighboringMineCount == 0) {
            Minefield.getNeighboringBlocks(this).forEach(block -> { if(!block.isMarked()) block.open(); });
        }
    }
    
}