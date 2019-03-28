package model;

public enum CardType {
    SEA(0),
    GRACE(34),
    ARROW(21),
    HORSE(5),
    JUNGLE(5),
    DESERT(4),
    BOG(2),
    MOUNTAINS(1),
    ACE(6),
    HOLE(3),
    CROCODILE(4),
    KILLER(1),
    FORTRESS(2),
    GIRL(1),
    CHEST(16),
    BALLOON(2),
    GUN(2),
    ROM(6),
    BARREL(4),
    TREASURE(1);

    private int count;

    CardType(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }
}
