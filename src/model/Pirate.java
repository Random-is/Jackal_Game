package model;

import javafx.scene.image.ImageView;

public class Pirate extends ImageView {
    private String color;
    private Card card;
    private boolean haveMoney = false;

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
        this.card = card;
    }
}
