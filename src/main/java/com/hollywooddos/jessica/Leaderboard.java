package com.hollywooddos.jessica;

import java.util.ArrayList;
import java.util.Collections;

public class Leaderboard {

    // data fields
    public ArrayList<Player> players;
    public int maxEntries;

    public Leaderboard(int maxEntries) {
        this.players = new ArrayList<Player>();
        this.maxEntries = maxEntries;
    }

    public String addPlayer(String name, int playerID, int score, int badgeID) {
        Player player = new Player(name, playerID, score, badgeID);
        players.add(player);
        for (int i = players.size() - 1; i > 0; i--) {
            if (players.get(i).getUserScore() > players.get(i - 1).getUserScore()) {
                Collections.swap(players, i, i - 1); // swap the players in ascending order
            } else {
                break;
            }
        }
        if (players.size() > maxEntries) {
            players.remove(players.size() - 1);
        }
        return player.getUsername() + " added to the leaderboard.";
    }

    public void removePlayer(int playerID) {
        players.removeIf(player -> player.getPlayerID() == playerID); // use playerID instead of name due to uniqueness
    }

    public ArrayList<Player> getTopPlayers(ArrayList<Player> player) {
        return new ArrayList<>(players);
    }
    // testing needed

}
