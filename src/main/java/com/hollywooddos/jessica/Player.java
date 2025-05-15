package com.hollywooddos.jessica;

// Note to self: I think I can have a simplified player class 
// We are computing badges in the game session so we don't need to store them in the player class
// The player plays the game via the game session class
// at the end of the same the game computes the badgeRank inside the GameSession class
public class Player {
    private final String username;
    private final int playerID;

    private int bestPossibleScore;
    private int userScore;
    private int scoreDifference;

    private String badgeRank;

    public Player(String username, int playerID, int bestPossibleScore, int userScore) {
        this.username = username;
        this.playerID = playerID;
        this.bestPossibleScore = bestPossibleScore;
        this.userScore = userScore;
        this.scoreDifference = userScore - bestPossibleScore;
        assignBadge();
    }

    public void recordGameResult(int bestPossibleScore, int userScore) {
        this.bestPossibleScore = bestPossibleScore;
        this.userScore = userScore;
        this.scoreDifference = userScore - bestPossibleScore;
        assignBadge();
    }

    private void assignBadge() {
        switch (scoreDifference) {
        case 0:
            badgeRank = "Gold";
            break;
        case 1:
            badgeRank = "Silver";
            break;
        case 2:
            badgeRank = "Bronze";
            break;
        default:
            badgeRank = "No badge";
            break;
        }
    }

    // getters and setters

    public String getUsername() {
        return username;
    }

    public int getPlayerID() {
        return playerID;
    }

    public int getBestPossibleScore() {
        return bestPossibleScore;
    }

    public int getUserScore() {
        return userScore;
    }

    public int getScoreDifference() {
        return scoreDifference;
    }

    public String getBadgeRank() {
        return badgeRank;
    }
}