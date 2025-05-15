package com.hollywooddos.jessica;

import java.util.Date;

public class Score {
    private final int rank;
    private final String username;
    private final int score;

    public Score(int rank, String username, int score) {
        this.rank = rank;
        this.username = username;
        this.score = score;
    }

    public int getRank() {
        return rank;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }
}
