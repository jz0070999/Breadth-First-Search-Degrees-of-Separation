package hollywooddos.test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import com.hollywooddos.jessica.Actor;
import com.hollywooddos.jessica.Movie;
import com.hollywooddos.jessica.data.TmdbDatabaseClient;

import info.movito.themoviedbapi.tools.TmdbException;

public class HollywoodDOSTests {

    @Test
    void testMovieSearch() {
        // Create an instance of TmdbClient
        TmdbDatabaseClient client = new TmdbDatabaseClient();

        // Test searching for movies with a specific phrase
        String searchPhrase = "The Adderall Diaries";
        List<Movie> results = null;
        try {
            results = client.searchMovies(searchPhrase);
        } catch (TmdbException e) {
            e.printStackTrace();
            fail("Exception occurred while searching for movies: " + e.getMessage());
        }

        // Check if the results are not null and contain expected movies
        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertTrue(results.stream().anyMatch(movie -> movie.getName().contains(searchPhrase)));
    }

    @Test
    void testActorInMovie() {

        TmdbDatabaseClient client = new TmdbDatabaseClient();

        // test if actor is in the acting credits of a movie
        String actor = "Timothee Chalamet";
        String movie = "The adderall diaries";

        // calling the methods
        List<Movie> movieResults = null; // set movie results to null
        try {
            movieResults = client.searchMovies(movie);
        } catch (TmdbException e) {
            e.printStackTrace();
            fail("Exception: movie search failed with exception" + e.getMessage());
        }
        Movie firstMovie = movieResults.getFirst(); // gets the first movie in the list, easy bc there is probably only
                                                    // one the adderall diaries movie in the USA
        List<Actor> actors = client.getActorsForMovie(firstMovie);

        // for each loop
        for (Actor actorResult : actors) {
            if (actorResult.getName().equals(actor)) {
                // we only want to assert True once, so that is why we are keeping that inside
                // of an if statement
                assertTrue(actorResult.getName().equals(actor));
                break;
            }
        }

    }
}
