package model;

public enum CardType {
    SEA(0, "images/cards/original/sea.png"),
    GRACE(34, "images/cards/original/grass.png"),
    ARROW(21, "images/cards/original/arrow.png"),
    HORSE(5, "images/cards/original/horse.png"),
    JUNGLE(5, "images/cards/original/jungle.png"),
    DESERT(4, "images/cards/original/desert.png"),
    BOG(2, "images/cards/original/bog.png"),
    MOUNTAINS(1, "images/cards/original/mountains.png"),
    ICE(6, "images/cards/original/ice.png"),
    HOLE(3, "images/cards/original/hole.png"),
    CROCODILE(4, "images/cards/original/crocodile.png"),
    KILLER(1, "images/cards/original/killer.png"),
    FORT(2, "images/cards/original/fort.png"),
    GIRL(1, "images/cards/original/girl.png"),
    CHEST(17, "images/cards/original/chest.png"),
    BALLOON(2, "images/cards/original/balloon.png"),
    GUN(2, "images/cards/original/gun.png"),
    ROM(6, "images/cards/original/rom.png"),
    BARREL(4, "images/cards/original/barrel.png");

    private int count;
    private String url;

    CardType(int count, String url) {
        this.count = count;
        this.url = url;
    }

    public int getCount() {
        return count;
    }


    public String getUrl() {
        return url;
    }
}
