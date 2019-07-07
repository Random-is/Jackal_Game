package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Card extends ImageView {
    private int mX, mY;
    private Ship ship;
    private ArrayList<Pirate> pirates = new ArrayList<>();
    private CardType cardType;
    private boolean open;
    private int money = 0;
    private boolean used = false;

    public Card(CardType cardType, int mX, int mY) {
        this.mX = mX;
        this.mY = mY;
        this.cardType = cardType;
        setImage(new Image("images/cards/original/close2.png"));
        if (cardType == CardType.SEA)
            open();

    }

    public boolean isOpen() {
        return open;
    }

    public void open() {
        open = true;
        setImage(new Image(cardType.getUrl()));
//        setText(cardType.name());
    }

    public int getmX() {
        return mX;
    }

    public int getmY() {
        return mY;
    }

    public void addPirate(Pirate pirate) {
        open();
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

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
