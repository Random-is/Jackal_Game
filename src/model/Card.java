package model;

import javafx.scene.control.Label;

import java.util.ArrayList;

public class Card extends Label {
    private int mX, mY;
    private Ship ship;
    private ArrayList<Pirate> pirates = new ArrayList<>();
    private CardType cardType;
    private boolean open;
    private int money = 0;

    public Card(CardType cardType, int mX, int mY) {
        this.mX = mX;
        this.mY = mY;
        this.cardType = cardType;
    }

    public boolean isOpen() {
        return open;
    }

    public void open() {
        open = true;
        setText(cardType.name());
    }

    public int getmX() {
        return mX;
    }

    public int getmY() {
        return mY;
    }

    public void addPirate(Pirate pirate) {
        pirate.setCard(this);
        pirates.add(pirate);
    }

    public ArrayList<Pirate> getPirates() {
        return pirates;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setShip(Ship ship) {
        if (ship != null)
            ship.setCard(this);
        this.ship = ship;
    }

    public Ship getShip() {
        return ship;
    }

    public boolean hasShip() {
        return ship != null;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
