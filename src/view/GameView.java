package view;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import model.ControllerFXML;
import model.Game;

public class GameView extends ControllerFXML {

    @FXML private AnchorPane rootPane;

    @FXML
    void initialize() {
        new Game().generateField();
    }
}
