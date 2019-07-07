package view;

import controller.GameFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.ControllerFXML;
import model.GameType;

import java.io.IOException;

public class MenuView extends ControllerFXML {
    private GameFactory gameFactory;

    @FXML private AnchorPane rootPane, connectCircle;

    @FXML private Button multiBtn;

    @FXML private TextField nickField;

    @FXML private ImageView search;

    public static String nickname;

    @FXML
    void selectGameType(ActionEvent event) throws IOException {
        switch (((Button) event.getSource()).getText()) {
            case "Single":
                gameFactory.getGame(GameType.SINGLE);
                break;
            case "Connect":
                gameFactory.getGame(GameType.CONNECT);
                connectCircle.setStyle("-fx-background-color: #a7d6a2; -fx-background-radius: 50");
                multiBtn.setDisable(false);
                break;
            case "Multiplayer":
                search.setVisible(true);
                nickname = nickField.getText();
                gameFactory.getGame(GameType.MULTI);
                break;
            case "Hotseat":
                gameFactory.getGame(GameType.HOT_SEAT);
                break;
        }
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
