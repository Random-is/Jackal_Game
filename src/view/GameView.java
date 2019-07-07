package view;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.*;

import java.io.IOException;
import java.util.ArrayList;

public class GameView extends ControllerFXML {
    private int cardSize = 50;
    private ScalePane gamePane;
    private Label[] names, moneys, roms;
    private ImageView[] piratesImg, playersImg;
    private AnchorPane[] turns;
    private ArrayList<ImageView> money = new ArrayList<>();
    private ArrayList<Label> moneyText = new ArrayList<>();
    private ArrayList<Label> sleepText = new ArrayList<>();

    @FXML private AnchorPane rootPane, turnOne, turnTwo, turnThree, turnFour;

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
        turns = new AnchorPane[]{turnOne, turnTwo, turnThree, turnFour};
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
                card.setFitHeight(cardSize);
                card.setFitWidth(cardSize);
//                card.setPrefSize(cardSize, cardSize);
//                card.setAlignment(Pos.CENTER);
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
        if (card.getCardType() == CardType.JUNGLE) {
            switch (pirate.getSleep()) {
                case 0:
                    pirate.setLayoutX(card.getLayoutX() + cardSize - pirate.getFitWidth() - 8);
                    pirate.setLayoutY(card.getLayoutY());
                    break;
                case 1:
                    pirate.setLayoutX(card.getLayoutX());
                    pirate.setLayoutY(card.getLayoutY() + cardSize - pirate.getFitHeight());
                    break;
            }
        } else if (card.getCardType() == CardType.DESERT) {
            switch (pirate.getSleep()) {
                case 0:
                    pirate.setLayoutX(card.getLayoutX() + cardSize - pirate.getFitWidth());
                    pirate.setLayoutY(card.getLayoutY());
                    break;
                case 1:
                    pirate.setLayoutX(card.getLayoutX() + cardSize / 2f - pirate.getFitWidth() / 2 - 8);
                    pirate.setLayoutY(card.getLayoutY() + cardSize / 2f - pirate.getFitHeight() / 2);
                    break;
                case 2:
                    pirate.setLayoutX(card.getLayoutX() + cardSize - pirate.getFitWidth());
                    pirate.setLayoutY(card.getLayoutY() + cardSize - pirate.getFitHeight());
                    break;
            }
        } else if (card.getCardType() == CardType.BOG) {
            switch (pirate.getSleep()) {
                case 0:
                    pirate.setLayoutX(card.getLayoutX() + 6);
                    pirate.setLayoutY(card.getLayoutY());
                    break;
                case 1:
                    pirate.setLayoutX(card.getLayoutX() + cardSize - pirate.getFitHeight() - 6);
                    pirate.setLayoutY(card.getLayoutY() + 6);
                    break;
                case 2:
                    pirate.setLayoutX(card.getLayoutX() + 3);
                    pirate.setLayoutY(card.getLayoutY() + cardSize / 2f - pirate.getFitHeight() / 2 + 6);
                    break;
                case 3:
                    pirate.setLayoutX(card.getLayoutX() + cardSize - pirate.getFitWidth() - 1);
                    pirate.setLayoutY(card.getLayoutY() + cardSize - pirate.getFitHeight());
                    break;
            }
        } else if (card.getCardType() == CardType.MOUNTAINS) {
            switch (pirate.getSleep()) {
                case 0:
                    pirate.setLayoutX(card.getLayoutX() + cardSize - pirate.getFitWidth() - 1);
                    pirate.setLayoutY(card.getLayoutY() + cardSize - pirate.getFitHeight() - 1);
                    break;
                case 1:
                    pirate.setLayoutX(card.getLayoutX() + 7);
                    pirate.setLayoutY(card.getLayoutY() + cardSize - pirate.getFitHeight() - 3);
                    break;
                case 2:
                    pirate.setLayoutX(card.getLayoutX() + 2);
                    pirate.setLayoutY(card.getLayoutY() + cardSize / 2f - pirate.getFitHeight() / 2 - 4);
                    break;
                case 3:
                    pirate.setLayoutX(card.getLayoutX() + cardSize / 2f - pirate.getFitWidth() / 2);
                    pirate.setLayoutY(card.getLayoutY());
                    break;
                case 4:
                    pirate.setLayoutX(card.getLayoutX() + cardSize - pirate.getFitWidth());
                    pirate.setLayoutY(card.getLayoutY() + 2);
                    break;
            }
        } else {
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
    }

    public void updateData(Game game) {
        PlayerList players = game.getPlayers();
        Player player = game.getMainPlayer();
        playerMoney.setText(String.valueOf(player.getMoney()));
        playerRom.setText(String.valueOf(player.getRom()));

        gamePane.getItems().removeAll(sleepText);
        sleepText = new ArrayList<>();

        for (int i = 0; i < turns.length; i++) {
            turns[i].setStyle("-fx-background-color: white");
        }
        turns[game.getPlayers().indexOf(game.getCurrentTurnPlayer())].setStyle("-fx-background-color: #f4f4f4");

        for (int i = 0; i < player.getPirates().size(); i++) {
            Pirate pirate = player.getPirates().get(i);
            piratesImg[i].setOnMouseClicked(event -> {
                if (pirate.isHaveMoney()) {
                    pirate.setHaveMoney(false);
                    pirate.getCard().setMoney(pirate.getCard().getMoney() + 1);
                    if (game.getGameType() == GameType.MULTI) {
                        try {
                            game.getClient().commandHandler("send GAME TAKE "
                                    + game.getPlayers().indexOf(player) + " "
                                    + player.getPirates().indexOf(pirate));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    updateData(game);
                } else {
                    if (pirate.getCard().getMoney() > 0) {
                        pirate.setHaveMoney(true);
                        pirate.getCard().setMoney(pirate.getCard().getMoney() - 1);
                        if (game.getGameType() == GameType.MULTI) {
                            try {
                                game.getClient().commandHandler("send GAME TAKE "
                                        + game.getPlayers().indexOf(player) + " "
                                        + player.getPirates().indexOf(pirate));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        updateData(game);
                    }
                }
            });
        }

        if (game.getGameType() == GameType.HOT_SEAT)
            for (int i = 0; i < player.getPirates().size(); i++) {
                Pirate pirate = player.getPirates().get(i);
                piratesImg[i].setImage(pirate.getImage());

//                if (pirate.getSleep() > 0) {
//                    Label mS = new Label(String.valueOf(pirate.getSleep()));
//                    mS.setLayoutX(pirate.getCard().getLayoutX() + cardSize / 2f - pirate.getFitWidth() / 2);
//                    mS.setLayoutY(pirate.getCard().getLayoutY() + cardSize / 2f - pirate.getFitHeight() / 2);
//                    mS.setPrefWidth(cardSize / 3f);
//                    mS.setAlignment(Pos.CENTER);
//                    sleepText.add(mS);
//                    gamePane.getItems().add(mS);
//                }
                if (pirate.isKilled()) {
                    pirate.setVisible(false);
                } else {
                    pirate.setVisible(true);
                }
            }

        for (int i = 0; i < players.size(); i++) {
            moneys[i].setText(String.valueOf(players.get(i).getMoney()));
            roms[i].setText(String.valueOf(players.get(i).getRom()));
        }
        mapMoney.setText(String.valueOf(game.getMoney()));
        mapRom.setText(String.valueOf(game.getRom()));

        gamePane.getItems().removeAll(money);
        gamePane.getItems().removeAll(moneyText);
        money = new ArrayList<>();
        moneyText = new ArrayList<>();
        for (int i = 0; i < 13; i++)
            for (int j = 0; j < 13; j++) {
                for (Pirate pirate : game.getMatrix()[i][j].getPirates()) {
                    placePirate(game.getMatrix()[i][j], pirate);
                }
                addCardMoney(game.getMatrix()[i][j]);
            }
        for (int i = 0; i < players.size(); i++) {
            Ship ship = players.get(i).getShip();
            ship.setLayoutX(ship.getCard().getLayoutX());
            ship.setLayoutY(ship.getCard().getLayoutY());
        }

    }

    private void addCardMoney(Card card) {
        if (card.getMoney() != 0) {
            ImageView m = new ImageView("images/money.png");
            m.setFitHeight(cardSize / 3f);
            m.setFitWidth(cardSize / 3f);
            m.setLayoutX(card.getLayoutX());
            m.setLayoutY(card.getLayoutY() + cardSize - cardSize / 3f);
            money.add(m);
            gamePane.getItems().add(m);
            Label mL = new Label(String.valueOf(card.getMoney()));
            mL.setLayoutX(card.getLayoutX());
            mL.setPrefWidth(cardSize / 3f);
            mL.setAlignment(Pos.CENTER);
            mL.setLayoutY(card.getLayoutY() + cardSize - cardSize / 3f);
            moneyText.add(mL);
            gamePane.getItems().add(mL);
        }
    }

    public void reset() {
        rootPane.getChildren().remove(gamePane);
    }
}
