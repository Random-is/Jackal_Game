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
        Game game = new Game(gameType);
        GameView gameView = main.getGameView();
        gameView.reset();
        if (gameType == GameType.SINGLE) {
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
            game.setCurrentTurnPlayer(players.get(0));

            Move move = new Move(game, gameView);
            move.createPiratesListenerForSingle();
            move.createCardListener();

            gameView.createImages(game);
            gameView.setPlayers(game);
            gameView.setMap(game.getMatrix());
            gameView.setCrews(game);
            gameView.updateData(game);
        } else if (gameType == GameType.MULTI) {
        } else if (gameType == GameType.HOST) {
        } else if (gameType == GameType.CONNECT) {
        } else if (gameType == GameType.HOT_SEAT) {
            ArrayList<String> colors = new ArrayList<>(Arrays.asList("black", "white", "yellow", "red"));
            PlayerList players = game.getPlayers();
            for (int i = 0; i < 4; i++) {
                players.get(i).setNickname(colors.get(0).toUpperCase());
                players.get(i).createCrew(colors.get(0));
                colors.remove(0);
            }
            players.sort();
            game.setMainPlayer(players.get(0));
            game.setCurrentTurnPlayer(players.get(0));
            game.generateField();
            game.placeCrews();

            Move move = new Move(game, gameView);
            move.createPiratesListenerForHotSeat();
            move.createCardListener();

            gameView.createImages(game);
            gameView.setPlayers(game);
            gameView.setMap(game.getMatrix());
            gameView.setCrews(game);
            gameView.updateData(game);
        }
        gameView.getStage().show();
    }
}
