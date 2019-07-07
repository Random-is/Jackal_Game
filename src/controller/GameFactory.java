package controller;

import model.Game;
import model.GameType;
import model.Player;
import model.PlayerList;
import view.GameView;
import view.MenuView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class GameFactory {
    private Main main;
    public static Client client;

    public GameFactory(Main main) {
        this.main = main;
    }

    public void getGame(GameType gameType) throws IOException {
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
            players.sortByColor();
            game.generateField();
            game.placeCrews();
            game.setCurrentTurnPlayer(players.get(0));

            Move move = new Move(game, gameView);
            move.createPiratesListenerForSingle();
            move.createShipListenerForSingle();
            move.createCardListener();

            gameView.createImages(game);
            gameView.setPlayers(game);
            gameView.setMap(game.getMatrix());
            gameView.setCrews(game);
            gameView.updateData(game);
            gameView.getStage().show();
        } else if (gameType == GameType.MULTI) {
            client.commandHandler("send CONNECT " + MenuView.nickname + " QUEUE");
        } else if (gameType == GameType.HOST) {
        } else if (gameType == GameType.CONNECT) {
            client = new Client(game, gameView);
            client.start();
            game.setClient(client);
            game.setGameType(GameType.MULTI);
            client.commandHandler("con");
        } else if (gameType == GameType.HOT_SEAT) {
            ArrayList<String> colors = new ArrayList<>(Arrays.asList("black", "white", "yellow", "red"));
            PlayerList players = game.getPlayers();
            for (int i = 0; i < 4; i++) {
                players.get(i).setNickname(colors.get(0).toUpperCase());
                players.get(i).createCrew(colors.get(0));
                colors.remove(0);
            }
            players.sortByColor();
            game.setMainPlayer(players.get(0));
            game.setCurrentTurnPlayer(players.get(0));
            game.generateField();
            game.placeCrews();

            Move move = new Move(game, gameView);
            move.createPiratesListenerForHotSeat();
            move.createShipListenerForHotSeat();
            move.createCardListener();

            gameView.createImages(game);
            gameView.setPlayers(game);
            gameView.setMap(game.getMatrix());
            gameView.setCrews(game);
            gameView.updateData(game);
            gameView.getStage().show();
        }
    }
}
