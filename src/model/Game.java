package model;

import java.util.ArrayList;

public class Game {
    ArrayList<Card> field = new ArrayList<>();
    ArrayList<Player> players = new ArrayList<>();
    int money = 37, rom = 10;
    int[][] mas = new int[11][11];

    public void generateField() {
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (!((i == 0 || i == 10) && (j == 0 || j == 10))) {
                    mas[i][j] = 1;
                }
            }
        }
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                System.out.print(mas[i][j] + " ");
            }
            System.out.println();
        }
    }
}
