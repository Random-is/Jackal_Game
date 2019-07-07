package model;

import javafx.scene.image.ImageView;

public class Pirate extends ImageView {
    private String color;
    private Card card;
    private boolean haveMoney = false;
    private int sleep = 0;
    private Card previousCard;
    private boolean killed = false;
    private boolean inHole = false;

    public Pirate(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        if (this.card != null)
            this.card.getPirates().remove(this);
        this.previousCard = this.card;
        this.card = card;
    }

    public int getSleep() {
        return sleep;
    }

    public void setSleep(int sleep) {
        this.sleep = sleep;
    }

    public Card getPreviousCard() {
        return previousCard;
    }

    public void setPreviousCard(Card previousCard) {
        this.previousCard = previousCard;
    }

    public boolean isKilled() {
        return killed;
    }

    public void kill() {
        killed = true;
        haveMoney = false;
        sleep = 0;
        previousCard = null;
        inHole = false;
        setCard(null);
    }

    public void respawn() {
        this.killed = false;
    }

    public boolean isInHole() {
        return inHole;
    }

    public void setInHole(boolean inHole) {
        this.inHole = inHole;
    }

    public boolean isHaveMoney() {
        return haveMoney;
    }

    public void setHaveMoney(boolean haveMoney) {
        this.haveMoney = haveMoney;
    }
}
