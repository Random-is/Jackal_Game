package model;

import java.util.ArrayList;
import java.util.Collections;

public class Game {
    private GameType gameType;
    private Player currentTurnPlayer;
    private Player mainPlayer;
    private PlayerList players = new PlayerList();
    private Card[][] matrix = new Card[13][13];
    private int money = 40, rom = 10;

    public Game(GameType gameType) {
        this.gameType = gameType;
        players.createPlayers(4);
    }

    public void generateField() {
        ArrayList<CardType> randomCards = new ArrayList<>();
        for (CardType cardType : CardType.values())
            for (int i = 0; i < cardType.getCount(); i++)
                randomCards.add(cardType);
        Collections.shuffle(randomCards);
        generateField(randomCards);
    }

    public void generateField(ArrayList<CardType> randomCards) {
        int k = 0;
        for (int i = 0; i < 13; i++)
            for (int j = 0; j < 13; j++)
                if ((i == 0 || i == 12) || (j == 0 || j == 12))
                    matrix[i][j] = new Card(CardType.SEA, i, j);
                else if ((i == 1 || i == 11) && (j == 1 || j == 11))
                    matrix[i][j] = new Card(CardType.SEA, i, j);
                else
                    matrix[i][j] = new Card(randomCards.get(k++), i, j);
    }

    public void placeCrews() {
        int k = players.indexOf(mainPlayer);
        int x = 6, y = 12;
        for (int i = 0; i < players.size(); i++) {
            Ship ship = players.get(k).getShip();
            matrix[x][y].setShip(ship);
            for (int j = 0; j < players.get(k).getPirates().size(); j++)
                matrix[x][y].addPirate(players.get(k).getPirates().get(j));
            x = i < 1 ? x - 6 : x + 6;
            y = i < 2 ? y - 6 : y + 6;
            k = k == players.size() - 1 ? 0 : (k + 1);
        }
    }

    public void endTurn() {
        int current = players.indexOf(currentTurnPlayer);
        currentTurnPlayer = players.get(current + 1 < players.size() ? current + 1 : 0);
        if (GameType.HOT_SEAT == gameType) mainPlayer = currentTurnPlayer;
    }

    public boolean isThisPlayerTurn(Player player) {
        return currentTurnPlayer == player;
    }

    public boolean isMainPlayerTurn() {
        return mainPlayer == currentTurnPlayer;
    }

    public Player getCurrentTurnPlayer() {
        return currentTurnPlayer;
    }

    public void setCurrentTurnPlayer(Player currentTurnPlayer) {
        this.currentTurnPlayer = currentTurnPlayer;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getRom() {
        return rom;
    }

    public void setRom(int rom) {
        this.rom = rom;
    }

    public PlayerList getPlayers() {
        return players;
    }

    public Card[][] getMatrix() {
        return matrix;
    }

    public Player getMainPlayer() {
        return mainPlayer;
    }

    public void setMainPlayer(Player mainPlayer) {
        this.mainPlayer = mainPlayer;
    }

    public GameType getGameType() {
        return gameType;
    }
}
