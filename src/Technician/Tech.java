package Technician;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Tech extends Application {

    public void start(Stage primaryStage) {
        try {
            HBox root = FXMLLoader.load(getClass().getResource("TechView.fxml"));
            Scene scene = new Scene(root,1300,800);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

