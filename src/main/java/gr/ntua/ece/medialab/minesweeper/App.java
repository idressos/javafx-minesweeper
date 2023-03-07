package gr.ntua.ece.medialab.minesweeper;

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
import javafx.scene.layout.TilePane;
import javafx.scene.layout.BorderPane;

import javafx.application.Platform;
import javafx.application.Application;

import java.io.IOException;
import java.util.Optional;

import gr.ntua.ece.medialab.minesweeper.dialogs.ExceptionDialog;
import gr.ntua.ece.medialab.minesweeper.dialogs.ScenarioCreationDialog;
import gr.ntua.ece.medialab.minesweeper.exceptions.InvalidDescriptionException;
import gr.ntua.ece.medialab.minesweeper.exceptions.InvalidValueException;
import gr.ntua.ece.medialab.minesweeper.game.CountdownTimer;
import gr.ntua.ece.medialab.minesweeper.types.Scenario;

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
                ScenarioCreationDialog scenarioCreationDialog = new ScenarioCreationDialog();
                
                Optional<Scenario> result = scenarioCreationDialog.showAndWait();
                result.ifPresent(scenario -> {
                	// TODO: Handle scenario creation event
                });
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
            			
            			// TODO: Handle scenario load event
            		} catch(IOException | InvalidValueException | InvalidDescriptionException ex) {
            			new ExceptionDialog(ex).showAndWait();
            		}
            	});
            }
        });
        
        MenuItem startItem = new MenuItem("Start");
        startItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                // TODO: Handle game start event
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
        
        Label minesLabel = new Label("Mines:");
        minesCounter = new Label("-");
        Label markedLabel = new Label("Marked:");
        markedCounter = new Label("-");
        
        statsHBox.getChildren().addAll(minesLabel, minesCounter, markedLabel, markedCounter, countdownTimer);
        
        // Top VBox
        VBox topVBox = new VBox();
        topVBox.getChildren().addAll(menuBar, statsHBox);
        
        rootPane.setTop(topVBox);
        
        // Game pane
        TilePane gamePane = new TilePane();
        rootPane.setCenter(gamePane);
        
        // Initialize scene
        Scene scene = new Scene(rootPane, 600, 600);
        
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
    
    public static void setMinesCounter(int count) {
    	minesCounter.setText(count >= 0 ? String.valueOf(count) : "-");
    }
    
    public static void setMarkedCounter(int count) {
    	markedCounter.setText(count >= 0 ? String.valueOf(count) : "-");
    }
    
}