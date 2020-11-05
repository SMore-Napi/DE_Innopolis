package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Roman Soldatov BS19-02
 * Computational Practicum Assignment
 * <p>
 * Extend the Main class from Application to run the javafx application
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // There will be shown the charts.fxml scene
        // which was generated by the Scene Builder
        Parent root = FXMLLoader.load(getClass().getResource("charts.fxml"));
        primaryStage.setTitle("Charts plotting");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
