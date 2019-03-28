package controller;

import model.Game;
import model.GameType;
import model.Player;
import model.PlayerList;
import view.GameView;

import java.util.ArrayList;
import java.util.Arrays;

public class GameFactory {
    private Main main;

    public GameFactory(Main main) {
        this.main = main;
    }

    public void getGame(GameType gameType) {
        Game game = new Game();
        GameView gameView = main.getGameView();
        gameView.reset();
        switch (gameType) {
            case SINGLE:
                ArrayList<String> colors = new ArrayList<>(Arrays.asList("black", "white", "yellow", "red"));
                PlayerList players = game.getPlayers();
                Player mainPlayer = players.get(0);
                game.setMainPlayer(mainPlayer);
                mainPlayer.createCrew(colors.get(0));
                colors.remove(0);
                for (int i = 0; i < 3; i++) {
                    players.get(i + 1).playerToBot(colors.get(0));
                    colors.remove(0);
                }
                players.sort();
                game.generateField();
                game.placeCrews();

                gameView.createImages(game);
                gameView.setPlayers(game);
                gameView.setMap(game.getMatrix());
                gameView.setCrews(game);
                gameView.updateData(game);
                break;
            case MULTI:
                break;
            case HOST:
                break;
            case CONNECT:
                break;
        }
        gameView.getStage().show();
    }
}
