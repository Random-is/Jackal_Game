package view;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.*;

public class GameView extends ControllerFXML {
    private int cardSize = 50;
    private ScalePane gamePane;
    private Label[] names, moneys, roms;
    private ImageView[] piratesImg, playersImg;

    @FXML private AnchorPane rootPane;

    @FXML private ImageView pirateOne, pirateTwo, pirateThree;

    @FXML private ImageView playerOne, playerTwo, playerThree, playerFour;

    @FXML private Label pOneName, pTwoName, pThreeName, pFourName;

    @FXML private Label pOneMoney, pTwoMoney, pThreeMoney, pFourMoney;

    @FXML private Label pOneRom, pTwoRom, pThreeRom, pFourRom;

    @FXML private Label mapMoney, mapRom;

    @FXML private Label playerMoney, playerRom;

    @FXML
    void initialize() {
        names = new Label[]{pOneName, pTwoName, pThreeName, pFourName};
        moneys = new Label[]{pOneMoney, pTwoMoney, pThreeMoney, pFourMoney};
        roms = new Label[]{pOneRom, pTwoRom, pThreeRom, pFourRom};
        playersImg = new ImageView[]{playerOne, playerTwo, playerThree, playerFour};
        piratesImg = new ImageView[]{pirateOne, pirateTwo, pirateThree};
    }

    public void createImages(Game game) {
        PlayerList players = game.getPlayers();
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            Ship ship = player.getShip();
            ship.setImage(new Image("images/players/" + ship.getColor() + ".png"));
            player.setAvatar(new Image("images/players/" + player.getColor() + ".png"));
            for (int j = 0; j < player.getPirates().size(); j++) {
                Pirate pirate = player.getPirates().get(j);
                pirate.setImage(new Image("images/pirates/" + pirate.getColor() + ".png"));
            }
        }
    }

    public void setCrews(Game game) {
        PlayerList players = game.getPlayers();
        for (int i = 0; i < players.size(); i++) {
            Ship ship = players.get(i).getShip();
            ship.setFitHeight(cardSize);
            ship.setFitWidth(cardSize);
            ship.setLayoutX(ship.getCard().getLayoutX());
            ship.setLayoutY(ship.getCard().getLayoutY());
            gamePane.getItems().add(ship);
            for (int j = 0; j < players.get(i).getPirates().size(); j++) {
                Pirate pirate = players.get(i).getPirates().get(j);
                pirate.setFitWidth(cardSize / 3f);
                pirate.setFitHeight(cardSize / 3f);
                gamePane.getItems().add(pirate);
            }
        }
    }

    public void setMap(Card[][] matrix) {
        gamePane = new ScalePane(cardSize);
        for (int i = 0; i < 13; i++)
            for (int j = 0; j < 13; j++) {
                Card card = matrix[i][j];
                card.setLayoutX(cardSize * i);
                card.setLayoutY(cardSize * j);
                card.setPrefSize(cardSize, cardSize);
                card.setAlignment(Pos.CENTER);
                if (card.getCardType() == CardType.SEA) {
                    card.setStyle("-fx-border-color: black; -fx-background-color: #8bb9ff");
                    card.open();
                } else
                    card.setStyle("-fx-border-color: black; -fx-background-color: #638e56");
                gamePane.getItems().add(card);
            }
        gamePane.centerItems(getStage().getScene().getWidth(), getStage().getScene().getHeight());
        rootPane.getChildren().add(gamePane);
        gamePane.toBack();
    }

    public void setPlayers(Game game) {
        PlayerList players = game.getPlayers();
        Player player = game.getMainPlayer();
        for (int i = 0; i < player.getPirates().size(); i++)
            piratesImg[i].setImage(player.getPirates().get(i).getImage());
        for (int i = 0; i < players.size(); i++) {
            names[i].setText(players.get(i).getNickname());
            playersImg[i].setImage(players.get(i).getAvatar());
        }
    }

    public void placePirate(Card card, Pirate pirate) {
        switch (card.getPirates().size()) {
            case 1:
                pirate.setLayoutX(card.getLayoutX() + cardSize / 2f - pirate.getFitWidth() / 2);
                pirate.setLayoutY(card.getLayoutY() + cardSize / 2f - pirate.getFitHeight() / 2);
                break;
            case 2:
                if (card.getPirates().indexOf(pirate) == 0) {
                    pirate.setLayoutX(card.getLayoutX() + cardSize / 2f - pirate.getFitWidth() / 2 - cardSize / 5f);
                    pirate.setLayoutY(card.getLayoutY() + cardSize / 2f - pirate.getFitHeight() / 2);
                } else {
                    pirate.setLayoutX(card.getLayoutX() + cardSize / 2f - pirate.getFitWidth() / 2 + cardSize / 5f);
                    pirate.setLayoutY(card.getLayoutY() + cardSize / 2f - pirate.getFitHeight() / 2);
                }
                break;
            case 3:
                if (card.getPirates().indexOf(pirate) == 0) {
                    pirate.setLayoutX(card.getLayoutX() + cardSize / 2f - pirate.getFitWidth() / 2 - cardSize / 5f);
                    pirate.setLayoutY(card.getLayoutY() + cardSize / 2f - pirate.getFitHeight() / 2 - cardSize / 6.25);
                } else if (card.getPirates().indexOf(pirate) == 1) {
                    pirate.setLayoutX(card.getLayoutX() + cardSize / 2f - pirate.getFitWidth() / 2 + cardSize / 5f);
                    pirate.setLayoutY(card.getLayoutY() + cardSize / 2f - pirate.getFitHeight() / 2 - cardSize / 6.25);
                } else {
                    pirate.setLayoutX(card.getLayoutX() + cardSize / 2f - pirate.getFitWidth() / 2);
                    pirate.setLayoutY(card.getLayoutY() + cardSize / 2f - pirate.getFitHeight() / 2 + cardSize / 6.25);
                }
                break;
        }
    }

    public void updateData(Game game) {
        PlayerList players = game.getPlayers();
        Player player = game.getMainPlayer();
        playerMoney.setText(String.valueOf(player.getMoney()));
        playerRom.setText(String.valueOf(player.getRom()));
        if (game.getGameType() == GameType.HOT_SEAT)
            for (int i = 0; i < player.getPirates().size(); i++)
                piratesImg[i].setImage(player.getPirates().get(i).getImage());
        for (int i = 0; i < players.size(); i++) {
            moneys[i].setText(String.valueOf(players.get(i).getMoney()));
            roms[i].setText(String.valueOf(players.get(i).getRom()));
        }
        mapMoney.setText(String.valueOf(game.getMoney()));
        mapRom.setText(String.valueOf(game.getRom()));
        for (int i = 0; i < 13; i++)
            for (int j = 0; j < 13; j++)
                for (Pirate pirate : game.getMatrix()[i][j].getPirates()) {
                    placePirate(game.getMatrix()[i][j], pirate);
                }
    }

    public void reset() {
        rootPane.getChildren().remove(gamePane);
    }
}
