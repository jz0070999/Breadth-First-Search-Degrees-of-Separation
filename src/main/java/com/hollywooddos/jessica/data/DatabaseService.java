package com.hollywooddos.jessica.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hollywooddos.jessica.Actor;
import com.hollywooddos.jessica.Movie;

public class DatabaseService {
    private static final String DB_URL = "jdbc:sqlite:/Users/jessicazumbach/Developer/repos/SE-2025Spring-TechTitans/db/StagingDB.db";

    public void testConnection() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            System.out.println("Connected to SQLite database!");
            // You can now run queries using conn.createStatement() or PreparedStatement
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Deprecated
    public ResultSet executeQueryForResult(String query) {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                return conn.createStatement().executeQuery(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Movie> executeQueryForMovies(String query) {
        List<Movie> movies = new ArrayList<>();
        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet resultSet = stmt.executeQuery(query)) {
            while (resultSet.next()) {
                Movie movie = null;
                try {
                    movie = new Movie();
                    movie.setId(resultSet.getString("movie_id"));
                    movie.setName(resultSet.getString("movie_title"));
                } catch (SQLException e) {
                    String id = resultSet.getString("movie_id");
                    movie = getMovieById(id);
                }
                movies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public List<Actor> executeQueryForActors(String query) {
        List<Actor> actors = new ArrayList<>();
        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet resultSet = stmt.executeQuery(query)) {
            while (resultSet.next()) {
                Actor actor = null;
                try {
                    actor = new Actor();
                    actor.setId(resultSet.getString("actor_id"));
                    actor.setName(resultSet.getString("actor_name"));
                } catch (SQLException e) {
                    String id = resultSet.getString("actor_id");
                    actor = getActorById(id);
                }

                actors.add(actor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return actors;
    }

    public Actor getActorById(String actorId) {
        List<Actor> actors = this.executeQueryForActors("SELECT * FROM Actors WHERE actor_id = '" + actorId + "'");
        Actor actor = actors.getFirst();
        if (actor != null) {
            return actor;
        }
        // if the actor is null, return null
        return null;
    }

    public Movie getMovieById(String movieId) {
        List<Movie> movies = this.executeQueryForMovies("SELECT * FROM Movies WHERE movie_id = '" + movieId + "'");
        Movie movie = movies.getFirst();
        if (movie != null) {
            return movie;
        }
        // if the movie is null, return null
        return null;
    }

    public boolean executeQuery(String query) {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                return conn.createStatement().execute(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}