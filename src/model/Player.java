package model;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class Player {
    private String nickname = "NOT_CONNECTED";
    private boolean ready = false;
    private Image avatar;
    private String color;
    private Ship ship;
    private ArrayList<Pirate> pirates = new ArrayList<>();
    private int money = 0, rom = 0;

    public Player() {
    }

    public void createCrew(String color) {
        this.color = color;
        ship = new Ship(color);
        createPirates(color);
    }

    public void createPirates(String color) {
        for (int i = 0; i < 3; i++) {
            pirates.add(new Pirate(color));
        }
    }

    public void playerToBot(String color) {
        createCrew(color);
        nickname = color.toUpperCase() + "_BOT";
    }

    public void set(String nickname, boolean ready) {
        this.nickname = nickname;
        this.ready = ready;
    }

    public void reset() {
        set("NOT_CONNECTED", false);
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
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

    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }

    public ArrayList<Pirate> getPirates() {
        return pirates;
    }

    public String getColor() {
        return color;
    }

    public Ship getShip() {
        return ship;
    }

    @Override
    public String toString() {
        return "[" + nickname + "] " +
                (ready ? "READY" : "NOT_READY");
    }
}