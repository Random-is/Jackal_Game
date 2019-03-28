package controller;

import model.GameType;

public class Rules {
    private GameType gameType;

    public Rules(GameType gameType) {
        this.gameType = gameType;
    }

    public boolean isValidTurn() {
        return true;
    }
}
