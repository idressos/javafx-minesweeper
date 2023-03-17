package gr.ntua.ece.medialab.minesweeper.dialogs;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.io.IOException;

import javafx.geometry.Insets;

import javafx.application.Platform;

import javafx.scene.layout.GridPane;

import javafx.scene.control.Label;
import javafx.scene.control.Dialog;
import javafx.scene.control.Spinner;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonType;

import javafx.collections.FXCollections;

import javafx.scene.control.ButtonBar.ButtonData;

import gr.ntua.ece.medialab.minesweeper.types.Scenario;
import gr.ntua.ece.medialab.minesweeper.exceptions.InvalidValueException;

public class ScenarioCreationDialog extends Dialog<Scenario> {
	
	public ScenarioCreationDialog() {
		super();
		
		setTitle("New Scenario");
		setHeaderText("Create your custom scenario configuration");
		
		ButtonType okButtonType = new ButtonType("OK", ButtonData.OK_DONE);
		getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));
		
		TextField scenarioIdField = new TextField();
		ChoiceBox<Integer> difficultyLevelChoiceBox = new ChoiceBox<Integer>(FXCollections.observableArrayList(1, 2));
		Spinner<Integer> mineCountSpinner = new Spinner<Integer>(9 ,45, 9, 1);
		CheckBox supermineCheckBox = new CheckBox();
		Spinner<Integer> timeLimitSpinner = new Spinner<Integer>(120, 360, 120, 1);
		
		grid.add(new Label("Scenario ID:"), 0, 0);
		grid.add(scenarioIdField, 1, 0);
		grid.add(new Label("Difficulty Level:"), 0, 1);
		grid.add(difficultyLevelChoiceBox, 1, 1);
		grid.add(new Label("Mine Count:"), 0, 2);
		grid.add(mineCountSpinner, 1, 2);
		grid.add(new Label("Supermine:"), 0, 3);
		grid.add(supermineCheckBox, 1, 3);
		grid.add(new Label("Time Limit:"), 0, 4);
		grid.add(timeLimitSpinner, 1, 4);
		
		getDialogPane().setContent(grid);
		
		Platform.runLater(() -> scenarioIdField.requestFocus());
		
		setResultConverter(dialogButton -> {
		    if(dialogButton == okButtonType) {
		    	try {
		    		Scenario scenario = new Scenario(
		    			difficultyLevelChoiceBox.getValue(),
		        		mineCountSpinner.getValue(),
		        		timeLimitSpinner.getValue(),
		        		supermineCheckBox.isSelected());
		    		
		    		Files.createDirectories(Paths.get("medialab"));
		    		scenario.toFile("medialab/" + scenarioIdField.getText() + ".txt");
		    		
		    		return scenario;
		    	} catch(InvalidValueException | IOException ex) {
		    		new ExceptionDialog(ex).showAndWait();
		    	}
		    }
		    
		    return null;
		});
	}
	
}