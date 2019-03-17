package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.ControllerFXML;
import view.GameView;

import java.io.IOException;

public class Main extends Application {
    private ControllerFXML gameView;

    @Override
    public void start(Stage primaryStage) throws Exception {
        gameView = loadFXML(primaryStage, "../fxml/game.fxml", "Jackal", 1280, 720, true);
    }

    private ControllerFXML loadFXML(Stage stage, String file, String title, double width, double height, boolean show) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(file));
        Parent root = loader.load();
        ControllerFXML controller = loader.getController();
        controller.setMain(this);
        controller.setStage(stage);
        stage.setTitle(title);
        stage.setScene(new Scene(root, width, height));
        stage.getScene().setCursor(Cursor.DEFAULT);
        if (show) stage.show();
        return controller;
    }

    public GameView getGameView() {
        return (GameView) gameView;
    }
}