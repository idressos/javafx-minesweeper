package gr.ntua.ece.medialab.minesweeper;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Parent;

import javafx.application.Application;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) {
        Group group = new Group();
        Scene scene = new Scene(group, 600, 300);
        
        stage.setTitle("MediaLab Minesweeper");
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}