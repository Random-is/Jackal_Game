package view;

import controller.GameFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import model.ControllerFXML;
import model.GameType;

public class MenuView extends ControllerFXML {
    private GameFactory gameFactory;

    @FXML private AnchorPane rootPane;

    @FXML
    void selectGameType(ActionEvent event) {
        gameFactory.getGame(GameType.valueOf(((Button) event.getSource()).getText()));
    }

    @FXML
    void initialize() {
    }

    @Override
    public void createControllers() {
        gameFactory = new GameFactory(getMain());
        getStage().setX(0);
        getStage().setY(0);
    }
}
