package model;

import javafx.scene.image.ImageView;

public class Ship extends ImageView {
    private Card card;
    private String color;

    public Ship(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        if (card != null)
            card.setShip(null);
        this.card = card;
    }
}
