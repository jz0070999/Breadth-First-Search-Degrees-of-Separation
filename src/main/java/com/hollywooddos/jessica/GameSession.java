package com.hollywooddos.jessica;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class GameSession {

    private int gameID;
    private Player player;
    private Actor startingActor;
    private Actor endingActor;
    private GameMode gameMode;

    // Path & connection counts
    private final List<Movie> connectingMovies = new ArrayList<>();
    private int userConnections; // number of movie hops user made
    private int bestPossibleScore; // true shortest‐path length

    // Final results
    private int scoreDifference; // userConnections – bestPossibleScore
    private String badgeRank; // Gold, Silver, Bronze, No badge
    private Date datePlayed;
    private boolean isGameWon;

    public GameSession(int gameID, Player player, Actor startingActor, Actor endingActor, GameMode gameMode,
            int bestPossibleScore) {
        this.gameID = gameID;
        this.player = player;
        this.startingActor = startingActor;
        this.endingActor = endingActor;
        this.gameMode = gameMode;
        this.bestPossibleScore = bestPossibleScore;
        this.datePlayed = new Date();
    }

    public void startGame() {
        userConnections = 0;
        connectingMovies.clear();
        isGameWon = false;
        System.out.println("Game started (" + gameMode.describeMode() + ")");
    }

    /** Called each time the player picks a connecting movie. */
    public void selectMovie(Movie movie) {
        if (movie != null && !connectingMovies.contains(movie)) {
            connectingMovies.add(movie);
            userConnections++;
        }
    }

    /** Called when the player reaches the target actor. */
    public void endGame() {
        // Compute difference
        scoreDifference = userConnections - bestPossibleScore;
        // I figured out the socring:
        // Scoring example:
        // Jennifer Aniston and Vince Vaughn co-starred in "The Break‑Up,"
        // so the shortest path between them is one movie.
        // bestPossibleScore = 1
        // If the user also picks exactly that one movie, userConnections = 1
        // scoreDifference = userConnections – bestPossibleScore = 1 – 1 = 0
        // A difference of 0 earns the Gold badge.

        isGameWon = (scoreDifference == 0);

        // Assign badge based on your new rules (1‐step = gold)
        if (scoreDifference == 0) {
            badgeRank = "Gold";
        } else if (scoreDifference == 1) {
            badgeRank = "Silver";
        } else if (scoreDifference == 2) {
            badgeRank = "Bronze";
        } else {
            badgeRank = "No badge";
        }

        // Tell the player
        System.out.println(
                "Game over! You used " + userConnections + " connections (best was " + bestPossibleScore + ").");
        System.out.println("Badge earned: " + badgeRank);
    }

    // Getters and setters.

    public int getGameID() {
        return gameID;
    }

    public int getPlayerID() {
        return player.getPlayerID();
    }

    public String getUsername() {
        return player.getUsername();
    }

    public Actor getStartingActor() {
        return startingActor;
    }

    public Actor getEndingActor() {
        return endingActor;
    }

    public int getBestPossibleScore() {
        return bestPossibleScore;
    }

    public int getUserConnections() {
        return userConnections;
    }

    public int getScoreDifference() {
        return scoreDifference;
    }

    public String getBadgeRank() {
        return badgeRank;
    }

    public boolean isGameWon() {
        return isGameWon;
    }

    public Date getDatePlayed() {
        return datePlayed;
    }

    public List<Movie> getConnectingPath() {
        return new ArrayList<>(connectingMovies);
    }
}