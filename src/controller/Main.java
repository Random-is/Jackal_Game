package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.ControllerFXML;
import view.GameView;
import view.MenuView;

import java.io.IOException;

public class Main extends Application {
    private ControllerFXML gameView, menuView;

    @Override
    public void start(Stage primaryStage) throws Exception {
        menuView = loadFXML(primaryStage, "../fxml/menu.fxml", "Create game", 1280, 720, true);
        gameView = loadFXML(new Stage(), "../fxml/game.fxml", "Jackal", 1280, 720, false);
    }

    public static void main(String[] args) {
        launch(args);
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
        controller.createControllers();
        if (show) stage.show();
        return controller;
    }

    public GameView getGameView() {
        return (GameView) gameView;
    }

    public MenuView getMenuView() {
        return (MenuView) menuView;
    }
}