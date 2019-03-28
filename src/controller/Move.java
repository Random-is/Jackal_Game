package controller;

import model.Card;
import model.Game;
import model.Pirate;
import model.Player;
import view.GameView;

import java.util.ArrayList;

public class Move {
    private Game game;
    private GameView gameView;
    private Pirate selectedPirate;
    private Rules rules;

    public Move(Game game, GameView gameView) {
        this.game = game;
        this.gameView = gameView;
        rules = new Rules(game.getGameType());
    }

    public void createPirateListenerForPlayer(Player player) {
        ArrayList<Pirate> pirates = player.getPirates();
        for (Pirate pirate : pirates) {
            pirate.setOnMouseClicked(event -> {
                if (game.isThisPlayerTurn(player))
                    selectedPirate = pirate;
                else
                    System.out.println("Now turn of player " + game.getCurrentTurnPlayer().getNickname());
            });
        }
    }

    public void createPiratesListenerForSingle() {
        createPirateListenerForPlayer(game.getMainPlayer());
    }

    public void createPiratesListenerForHotSeat() {
        for (int i = 0; i < game.getPlayers().size(); i++) {
            createPirateListenerForPlayer(game.getPlayers().get(i));
        }
    }

    public void createCardListener() {
        Card[][] matrix = game.getMatrix();
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 13; j++) {
                Card card = matrix[i][j];
                card.setOnMouseClicked(event -> {
                    if (selectedPirate != null) {
                        card.addPirate(selectedPirate);
                        selectedPirate = null;
                        if (rules.isValidTurn()) {
                            game.endTurn();
                            gameView.updateData(game);
                        }
                    } else
                        System.out.println("YOU DON'T SELECT ANYONE");
                });
            }
        }
    }
}
