package com.hollywooddos.jessica.data;

import java.util.ArrayList;
import java.util.List;

import com.hollywooddos.jessica.Actor;
import com.hollywooddos.jessica.Movie;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.core.popularperson.PopularPersonResultsPage;
import info.movito.themoviedbapi.model.movielists.MovieResultsPageWithDates;
import info.movito.themoviedbapi.model.movies.Cast;
import info.movito.themoviedbapi.model.movies.Credits;
import info.movito.themoviedbapi.model.people.credits.MovieCast;
import info.movito.themoviedbapi.model.people.credits.MovieCredits;
import info.movito.themoviedbapi.tools.TmdbException;

@Deprecated
public class TmdbClient {
    private String API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2NDc3MDk2YWVkOWJjOGI0NWM0YmQzNDNiYzMzY2M4NCIsIm5iZiI6MTc0MTM3Nzk0NS43Nywic3ViIjoiNjdjYjUxOTk3Yzk2N2UwNGQ1NWI4OTgwIiwic2NvcGVzIjpbImFwaV9yZWFkIl0sInZlcnNpb24iOjF9.3fERS3wF8KGtsbRSqYbM_EtXDItZvJTidqkJbcgAyY0";
    private TmdbApi api;
    private static List<Actor> nowPlayingActors;
    private static List<Movie> nowPlayingMovies;

    public TmdbClient() {
        api = new TmdbApi(API_KEY);
        if (nowPlayingMovies == null) {
            nowPlayingMovies = getNowPlayingMovies();
        }
        if (nowPlayingActors == null) {
            nowPlayingActors = getNowPlayingActors();
        }
    }

    public List<Movie> searchMovies(String searchPhrase) throws TmdbException {
        // now we are going to search the nowplayingmovies for the search phrase
        // without calling the API

        // if the search phrase is empty, return all movies
        if (searchPhrase == null || searchPhrase.isEmpty()) {
            return nowPlayingMovies;
        }
        // if the search phrase is not empty, filter the movies to a new collection copy
        // and return the new collection
        // this is a lambda expression
        List<Movie> movieResults = nowPlayingMovies.stream()
                .filter(x -> x.getName().toLowerCase().contains(searchPhrase.toLowerCase())).toList();
        return movieResults;
    }

    public List<Actor> getActorsForMovie(Movie movie) throws TmdbException {
        return getActorsForMovie(movie.getId());
    }

    public List<Actor> getActorsForMovie(String movieId) throws TmdbException {
        List<Actor> actorList = nowPlayingActors;

        // remove all actors that are not in the movie
        actorList.removeIf(x -> x.getMovieList().stream().noneMatch(y -> y.getId() == movieId));
        return actorList;
    }

    public List<Actor> searchActors(String searchPhrase) throws TmdbException {
        // now we are going to search the nowplayingactors for the search phrase
        // without calling the API

        // if the search phrase is empty, return all actors
        if (searchPhrase == null || searchPhrase.isEmpty()) {
            return nowPlayingActors;
        }
        // if the search phrase is not empty, filter the actors to a new collection copy
        // and return the new collection
        // this is a lambda expression
        List<Actor> actorResults = nowPlayingActors.stream()
                .filter(x -> x.getName().toLowerCase().contains(searchPhrase.toLowerCase())).toList();

        return actorResults;
    }

    private List<Movie> getNowPlayingMovies() {
        List<Movie> movieList = new ArrayList<>();
        MovieResultsPageWithDates nowPlayingList = null;
        try {
            nowPlayingList = api.getMovieLists().getNowPlaying("en-US", 1, "US");
        } catch (TmdbException e) {
            e.printStackTrace();
        }
        int totalPages = nowPlayingList.getTotalPages();
        for (int i = 1; i <= totalPages; i++) {
            MovieResultsPageWithDates nowPlaying = null;
            try {
                nowPlaying = api.getMovieLists().getNowPlaying("en-US", i, "US");
            } catch (TmdbException e) {
                e.printStackTrace();
            }
            for (info.movito.themoviedbapi.model.core.Movie movieResult : nowPlaying.getResults()) {
                Movie movie = new Movie();
                movie.setName(movieResult.getTitle());
                movie.setId(Integer.toString(movieResult.getId()));
                List<Actor> actors = null;
                try {
                    actors = fetchActorsForMovieId(movieResult.getId());
                } catch (TmdbException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                for (Actor actor : actors) {
                    actors.add(actor);
                }
                movieList.add(movie);
            }
        }

        return movieList;
    }

    private List<Actor> getNowPlayingActors() {
        List<Actor> actorList = new ArrayList<>();

        for (Movie movie : nowPlayingMovies.subList(0, 50)) {
            try {
                List<Actor> credits = getActorsForMovie(movie);
                if (credits != null) {
                    actorList.addAll(credits);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                break;
            }
        }
        return actorList;
    }

    public List<Actor> fetchActorsForMovieId(int movieId) throws TmdbException {
        List<Actor> actorList = new ArrayList<>();
        Credits credits = api.getMovies().getCredits(movieId, "en-US");
        for (Cast personCast : credits.getCast()) {
            Actor actor = new Actor();
            actor.setName(personCast.getName());
            actor.setId(Integer.toString(personCast.getId()));
            actorList.add(actor);
        }
        return actorList;
    }

    public List<Movie> getMoviesForActor(Actor actor) throws TmdbException {
        return getMoviesForActor(actor.getId());
    }

    public List<Movie> getMoviesForActor(String actorId) throws TmdbException {
        List<Movie> movieList = this.nowPlayingMovies;
        movieList.removeIf(x -> {
            try {
                return getActorsForMovie(x).stream().noneMatch(y -> y.getId() == actorId);
            } catch (TmdbException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return false;
        });
        return movieList;
    }
}
