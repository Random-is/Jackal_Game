package model;

import java.util.ArrayList;
import java.util.Collections;

public class Game {
    private Player mainPlayer;
    private PlayerList players = new PlayerList();
    private Card[][] matrix = new Card[13][13];
    private int money = 40, rom = 10;

    public Game() {
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
            matrix[x][y].getPirates().addAll(players.get(k).getPirates());
            x = i < 1 ? x - 6 : x + 6;
            y = i < 2 ? y - 6 : y + 6;
            k = k == players.size() - 1 ? 0 : (k + 1);
        }
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
}
