package gr.ntua.ece.medialab.minesweeper.types;

import java.util.List;

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

    private static final Image nb0Image = new Image("0.png");
    private static final Image nb1Image = new Image("1.png");
	private static final Image nb2Image = new Image("2.png");
	private static final Image nb3Image = new Image("3.png");
    private static final Image nb4Image = new Image("4.png");
	private static final Image nb5Image = new Image("5.png");
    private static final Image nb6Image = new Image("6.png");
    private static final Image nb7Image = new Image("7.png");
    private static final Image nb8Image = new Image("8.png");

    private static int leftClicksCount;

    private Mine mine;
    private Coordinates coords;
    
    private boolean isOpen;
    private boolean isMarked;
    
    public Block(Mine mine, Coordinates coords) {
        this.mine = mine;
        this.coords = coords;
        
        imageView.setImage(blockImage);
        imageView.setFitWidth(BLOCK_SIZE - 2);
        imageView.setFitHeight(BLOCK_SIZE - 2);
        imageView.setVisible(true);
        
        getChildren().setAll(imageView);
        
        setTranslateX(coords.getX() * BLOCK_SIZE);
        setTranslateY(coords.getY() * BLOCK_SIZE);
        
        setOnMouseClicked(e -> {
            if(!Minefield.isIntact()) return;

            if(!App.getCountdownTimer().isRunning()) App.getCountdownTimer().start();

            if(e.getButton() == MouseButton.PRIMARY && !isMarked) {
                open(true);
                leftClicksCount++;
            } else if(e.getButton() == MouseButton.SECONDARY && !isOpen) {
                if(isMarked) {
                    unmark();
                } else if(Minefield.getMarkedBlockCount() < Minefield.getScenario().getMineCount()){
                    mark();
                    
                    if(getMine() != null && getMine() instanceof Supermine && leftClicksCount >= 4) {
                        List<Block> xBlocks = Minefield.getAllBlocks().stream().filter(block -> block.getCoordinates().getX() == getCoordinates().getX()).toList();
                    	List<Block> yBlocks = Minefield.getAllBlocks().stream().filter(block -> block.getCoordinates().getY() == getCoordinates().getY()).toList();

                        for(Block block : xBlocks) {
                            if(block.hasMine()) block.mark();
                            else block.open(false);
                        }

                        for(Block block : yBlocks) {
                            if(block.hasMine()) block.mark();
                            else block.open(false);
                        }
                    }
                }
            }
        });

        leftClicksCount = 0;
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
        imageView.setImage(flagImage);
        App.setMarkedCounter(Minefield.getMarkedBlockCount());
    }
    
    public void unmark() {
        isMarked = false;
        imageView.setImage(blockImage);
        App.setMarkedCounter(Minefield.getMarkedBlockCount());
    }
    
    public long getNeighboringMineCount() {
        return Minefield.getNeighboringBlocks(this).stream().filter(block -> block.hasMine()).count();
    }
    
    public void open(boolean recursive) {
        if(isOpen) return;

        isOpen = true;

        if(hasMine()) {
            imageView.setImage(mineImage);
            getMine().explode();
            
            return;
        }

        long neighboringMineCount = getNeighboringMineCount();
        
        if(neighboringMineCount == 0) imageView.setImage(nb0Image);
        if(neighboringMineCount == 1) imageView.setImage(nb1Image);
        if(neighboringMineCount == 2) imageView.setImage(nb2Image);
        if(neighboringMineCount == 3) imageView.setImage(nb3Image);
        if(neighboringMineCount == 4) imageView.setImage(nb4Image);
        if(neighboringMineCount == 5) imageView.setImage(nb5Image);
        if(neighboringMineCount == 6) imageView.setImage(nb6Image);
        if(neighboringMineCount == 7) imageView.setImage(nb7Image);
        if(neighboringMineCount == 8) imageView.setImage(nb8Image);
        
        if(neighboringMineCount == 0 && recursive) {
            Minefield.getNeighboringBlocks(this).forEach(block -> { if(!block.isMarked() && !block.isOpen()) block.open(recursive); });
        }
    }
    
}