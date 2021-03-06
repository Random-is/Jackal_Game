package model;

import java.util.ArrayList;
import java.util.Collections;

public class PlayerList extends ArrayList<Player> {
    private Player leader;

    public void createPlayers(int count) {
        clear();
        for (int i = 0; i < count; i++)
            add(new Player());
        leader = get(0);
    }

    public void roleToColor() {
        for (int i = 0; i < size(); i++) {
            switch (get(i).getRole()) {
                case 0:
                    get(i).setColor("white");
                    break;
                case 1:
                    get(i).setColor("yellow");
                    break;
                case 2:
                    get(i).setColor("red");
                    break;
                case 3:
                    get(i).setColor("black");
                    break;
            }
        }
    }

    public void sortByColor() {
        int ind = indexOfByColor("white");
        Player first = get(ind);
        remove(ind);
        Collections.shuffle(this);
        add(0, first);
    }

    public int readyCount() {
        int k = 0;
        for (Player player : this)
            if (player.isReady())
                k++;
        return k;
    }

    public void reset() {
        for (Player player : this)
            player.reset();
    }

    public void resetNicknames() {
        for (Player player : this)
            player.setNickname("NOT_CONNECTED");
    }

    public void resetReady() {
        for (Player player : this)
            player.setReady(false);
    }

    public Player get(String nickname) {
        return get(indexOf(nickname));
    }

    public int indexOfByColor(String color) {
        for (int i = 0; i < size(); i++)
            if (color.equals(get(i).getColor()))
                return i;
        return -1;
    }

    public int indexOf(String nickname) {
        for (int i = 0; i < size(); i++)
            if (nickname.equals(get(i).getNickname()))
                return i;
        return -1;
    }

    public Player getLeader() {
        return leader;
    }

    public void setLeader(Player leader) {
        this.leader = leader;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < size(); i++) {
            result.append("| ").append(i).append(": ")
                    .append(get(i).toString())
                    .append(i + 1 < size() ? "\n" : "");
        }
        return result.toString();
    }
}
