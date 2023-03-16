package gr.ntua.ece.medialab.minesweeper.types;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.shape.Rectangle;

import javafx.scene.layout.StackPane;

import javafx.scene.input.MouseButton;

import gr.ntua.ece.medialab.minesweeper.App;

public class Block extends StackPane {

    private Mine mine;
    private Coordinates coords;

    private boolean isOpen;
    private boolean isMarked;

    private static Rectangle border = new Rectangle(App.getBlockSize() - 2, App.getBlockSize() - 2);
    private Text text = new Text();

    public Block(Coordinates coords) {
        this.coords = coords;

        border.setStroke(Color.LIGHTGRAY);

        Image flagImage = new Image("");

        ImageView flagImageView = new ImageView(flagImage);
        flagImageView.setFitWidth(App.getBlockSize() - 2);
        flagImageView.setFitHeight(App.getBlockSize() - 2);
        flagImageView.setVisible(false);

        getChildren().addAll(border, text);

        setTranslateX(coords.getX() * App.getBlockSize());
        setTranslateY(coords.getY() * App.getBlockSize());

        setOnMouseClicked(e -> {
            if(!Minefield.isIntact()) return;
            if(e.getButton() == MouseButton.PRIMARY && !isMarked) {
                Minefield.incrementLeftClicksCount();
                open();
            } else if(e.getButton() == MouseButton.SECONDARY && !isOpen && Minefield.getLeftClicksCount() > 0) {
                if(isMarked) {
                    long neighboringMineCount = getNeighboringMineCount();
                    if(neighboringMineCount > 0) text.setText(String.valueOf(neighboringMineCount));

                    isMarked = false;

                    flagImageView.setVisible(false);
                    border.setFill(Color.BLACK);

                    App.setMarkedCounter(Minefield.getMarkedBlockCount());

                }
                else if(Minefield.getMarkedBlockCount() < Minefield.getScenario().getMineCount()){
                    flagImageView.setVisible(true);
                    isMarked = true;

                    border.setFill(null);

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
            getMine().explode();
            return;
        }
        
        isOpen = true;
        text.setVisible(true);
        border.setFill(null);

        if(getNeighboringMineCount() == 0) {
            Minefield.getNeighboringBlocks(this).forEach(block -> { if(!block.isMarked()) block.open(); });
        }
    }

}