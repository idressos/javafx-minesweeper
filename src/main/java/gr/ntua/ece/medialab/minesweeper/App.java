package gr.ntua.ece.medialab.minesweeper;

import java.util.Optional;

import java.io.IOException;

import javafx.stage.Stage;

import javafx.scene.Scene;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.control.Menu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import javafx.scene.layout.BorderPane;

import javafx.application.Platform;
import javafx.application.Application;

import gr.ntua.ece.medialab.minesweeper.types.Scenario;
import gr.ntua.ece.medialab.minesweeper.types.Minefield;

import gr.ntua.ece.medialab.minesweeper.game.CountdownTimer;

import gr.ntua.ece.medialab.minesweeper.dialogs.ExceptionDialog;
import gr.ntua.ece.medialab.minesweeper.dialogs.ScenarioCreationDialog;

import gr.ntua.ece.medialab.minesweeper.exceptions.InvalidValueException;
import gr.ntua.ece.medialab.minesweeper.exceptions.InvalidDescriptionException;

public class App extends Application {

    private static Label minesCounter = new Label("-");
    private static Label markedCounter = new Label("-");
    private static CountdownTimer countdownTimer = new CountdownTimer();
    
    @Override
    public void start(Stage stage) {
    	// Root application pane
        BorderPane rootPane = new BorderPane();
        
        // Application Menu
        Menu appMenu = new Menu("Application");
        
        MenuItem createItem = new MenuItem("Create");
        createItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                new ScenarioCreationDialog().showAndWait();
            }
        });
        
        MenuItem loadItem = new MenuItem("Load");
        loadItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            	TextInputDialog textInputDialog = new TextInputDialog();
            	textInputDialog.setTitle("Load Scenario");
            	textInputDialog.setHeaderText("Input desired scenario ID");
            	textInputDialog.setContentText("Scenario ID:");
            	
            	Optional<String> input = textInputDialog.showAndWait();
            	input.ifPresent(scenarioId -> {
            		try {
            			Scenario scenario = Scenario.fromFile("medialab/" + scenarioId + ".txt");
                        
            			rootPane.setCenter(new Minefield(scenario));
                        countdownTimer.set(Minefield.getScenario().getTimeLimit());

                        stage.sizeToScene();
            		} catch(IOException | InvalidValueException | InvalidDescriptionException ex) {
            			new ExceptionDialog(ex).showAndWait();
            		}
            	});
            }
        });
        
        MenuItem startItem = new MenuItem("Start");
        startItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if(rootPane.getCenter() != null && rootPane.getCenter() instanceof Minefield) {
                    countdownTimer.start();
                }
            }
        });
        
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            	Platform.exit();
                System.exit(0);
            }
        });
        
        appMenu.getItems().addAll(createItem, loadItem, startItem, exitItem);
        
        // Details Menu
        Menu detailsMenu = new Menu("Details");
        
        MenuItem roundsItem = new MenuItem("Rounds");
        roundsItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                // TODO: Handle ROUNDS event
            }
        });
        
        MenuItem solutionItem = new MenuItem("Solution");
        solutionItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                // TODO: Handle SOLUTION event
            }
        });
        
        detailsMenu.getItems().addAll(roundsItem, solutionItem);
        
        // Menu Bar
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(appMenu, detailsMenu);
        
        // Stats HBox
        HBox statsHBox = new HBox();
        statsHBox.setSpacing(6.0);
        
        Label minesLabel = new Label("Mines:");
        minesLabel.setStyle("-fx-font-size: 18px;");
        minesCounter.setStyle("-fx-font-size: 18px;");
        Label markedLabel = new Label("Marked:");
        markedLabel.setStyle("-fx-font-size: 18px;");
        markedCounter.setStyle("-fx-font-size: 18px;");
        
        Region leftFiller = new Region();
        HBox.setHgrow(leftFiller, Priority.ALWAYS);
        Region rightFiller = new Region();
        HBox.setHgrow(rightFiller, Priority.ALWAYS);
        
        statsHBox.getChildren().addAll(minesLabel, minesCounter, leftFiller, countdownTimer, rightFiller, markedLabel, markedCounter);
        
        // Top VBox
        VBox topVBox = new VBox();
        topVBox.getChildren().addAll(menuBar, statsHBox);
        
        rootPane.setTop(topVBox);
        
        // Initialize scene
        Scene scene = new Scene(rootPane);
        
        // Stage options
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("MediaLab Minesweeper");
        
        // Show stage
        stage.show();
    }
    
    public static void main(String[] args) {
        launch();
    }
    
    public static void setMinesCounter(long count) {
    	minesCounter.setText(count >= 0 ? String.valueOf(count) : "-");
    }
    
    public static void setMarkedCounter(long count) {
    	markedCounter.setText(count >= 0 ? String.valueOf(count) : "-");
    }
    
    public static CountdownTimer getCountdownTimer() {
        return countdownTimer;
    }
    
}