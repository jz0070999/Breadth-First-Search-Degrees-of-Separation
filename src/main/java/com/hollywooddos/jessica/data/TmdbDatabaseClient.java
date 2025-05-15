package com.hollywooddos.jessica.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hollywooddos.jessica.Actor;
import com.hollywooddos.jessica.GameSession;
import com.hollywooddos.jessica.Movie;
import com.hollywooddos.jessica.Player;
import com.hollywooddos.jessica.CurrentPlayerInfo;

import info.movito.themoviedbapi.tools.TmdbException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.hollywooddos.jessica.Score;

public class TmdbDatabaseClient {
    private DatabaseService dbService;

    public TmdbDatabaseClient() {
        this.dbService = new DatabaseService();
    }

    // adding a new player to the database
    public void addNewPlayer(String username, String password) {
        String sql = "INSERT INTO Player (username, password) VALUES (?, ?)";
        try (PreparedStatement stmt = dbService.getConnection().prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean validateExistingPlayerInfo(String username, String password) {
        String sql = "SELECT COUNT(*) AS cnt FROM Player WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = dbService.getConnection().prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt("cnt") > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getCurrentPlayerID(String username) throws SQLException {
        String sql = "SELECT player_ID FROM Player WHERE username = ?";
        try (PreparedStatement statement = dbService.getConnection().prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getInt("player_ID");
                }
            }

            throw new SQLException("Player not found");
        }
    }

    // public Player getPlayerInfo(String username) {
    // }

    public void addActor(Actor actor) {
        String query = "INSERT INTO Actors (actor_id, actor_name) VALUES (" + actor.getId() + ", '" + actor.getName()
                + "')";
        dbService.executeQuery(query);
    }

    public void addMovie(Movie movie) {
        String query = "INSERT INTO Movies (movie_id, movie_title) VALUES (" + movie.getId() + ", '" + movie.getName()
                + "')";
        dbService.executeQuery(query);
    }

    public List<Movie> getAllMovies() {
        List<Movie> movies = dbService.executeQueryForMovies("SELECT * FROM Movies");
        if (movies != null) {
            return movies;
        }
        // if the movies are null, return an empty list
        return new ArrayList<>();
    }

    public List<Actor> getActorsForMovie(Movie movie) {
        List<Actor> actors = dbService
                .executeQueryForActors("SELECT actor_id FROM ActingCredits WHERE movie_id = '" + movie.getId() + "'");

        if (actors != null) {
            return actors;
        }
        // if the actors are null, return an empty list
        return new ArrayList<>();
    }

    public List<Movie> getMoviesForActor(Actor actor) {
        List<Movie> movies = dbService
                .executeQueryForMovies("SELECT movie_id FROM ActingCredits WHERE actor_id = '" + actor.getId() + "'");

        if (movies != null) {
            return movies;
        }
        // if the movies are null, return an empty list
        return new ArrayList<>();
    }

    public List<Movie> searchMovies(String searchPhrase) throws TmdbException {
        // now we are going to search the nowplayingmovies for the search phrase
        // without calling the API

        // if the search phrase is empty, return all movies
        if (searchPhrase == null || searchPhrase.isEmpty()) {
            return getAllMovies();
        }
        // if the search phrase is not empty, filter the movies to a new collection copy
        // and return the new collection
        // this is a lambda expression
        List<Movie> movieResults = getAllMovies().stream()
                .filter(x -> x.getName().toLowerCase().contains(searchPhrase.toLowerCase())).toList();
        return movieResults;
    }

    public List<Actor> searchActors(String searchPhrase) throws TmdbException {
        // now we are going to search the nowplayingactors for the search phrase
        // without calling the API

        // if the search phrase is empty, return all actors
        if (searchPhrase == null || searchPhrase.isEmpty()) {
            return getAllActors();
        }
        // if the search phrase is not empty, filter the actors to a new collection copy
        // and return the new collection
        // this is a lambda expression
        List<Actor> actorResults = getAllActors().stream()
                .filter(x -> x.getName().toLowerCase().contains(searchPhrase.toLowerCase())).toList();

        return actorResults;
    }

    public List<Actor> getAllActors() {
        List<Actor> actors = dbService.executeQueryForActors("SELECT * FROM Actors");
        if (actors != null) {
            return actors;
        }
        return new ArrayList<>();
    }

    public List<Score> fetchTopScores(int limit) throws SQLException {
        // using double quotes because I like writing multiple lines
        // without using the concatenation operator
        String sql = """
                SELECT Player.username, GameSession.scoreDifference AS score FROM GameSession
                JOIN Player ON GameSession.player_ID = Player.player_ID
                ORDER BY GameSession.scoreDifference DESC LIMIT ? """;

        // What I learned: PreparedStatement is a standard interface in the java.sql
        // package
        try (PreparedStatement statement = dbService.getConnection().prepareStatement(sql)) {
            // bind the value of the local variable limit parameter to the ? placerholder
            // Parameter indexing in the Java Database Connectivity starts at 1
            statement.setInt(1, limit);
            try (ResultSet topScores = statement.executeQuery()) {
                List<Score> scores = new ArrayList<>();
                int rank = 1;
                while (topScores.next()) {
                    String username = topScores.getString("username");
                    int score = topScores.getInt("score");
                    scores.add(new Score(rank, username, score));
                    rank++;
                }
                return scores;
            }

        }

    }

}