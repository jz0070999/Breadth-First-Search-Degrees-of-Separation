package com.hollywooddos.jessica;

import java.util.List;
import java.util.Objects;

import com.hollywooddos.jessica.views.SeparationListEntry;

public class Actor implements Entry, SeparationListEntry {
    private String name;
    private String id;
    @Deprecated
    private List<Movie> movieList;
    @Deprecated
    private Entry next;
    @Deprecated
    private Entry previous;

    public boolean isLast = false;

    public Actor() {

    }

    public Actor(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object obj) {
        // Quick checks for reference equality or null
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        // Compare IDs
        Actor other = (Actor) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        // Hash primarily by the 'id' field
        return Objects.hash(this.id);
    }

    public void setId(String id) {
        this.id = id;
    }

    @Deprecated
    public Entry getNext() {
        return this.next;
    }

    @Deprecated
    public void setNext(Entry entry) {
        this.next = entry;
    }

    @Deprecated
    public Entry getPrevious() {
        return this.previous;
    }

    @Deprecated
    public void setPrevious(Entry entry) {
        this.previous = entry;
    }

    @Deprecated
    public List<Movie> getMovieList() {
        return this.movieList;
    }

    @Deprecated
    public boolean addMovie(Movie movie) {

        if (movie == null) {
            return false;
        }
        // method verifies that actor is not already in the actorlist
        for (int i = 0; i < this.movieList.size(); i++) {
            if (this.movieList.get(i).equals(movie)) {
                return false;
            }
        }

        this.movieList.add(movie);

        return true;
    }

    @Deprecated
    public boolean removeMovie(Movie movie) {
        if (movie == null) {
            return false;
        }
        // method iterates through the list to verify the actor is present
        for (int i = 0; i < this.movieList.size(); i++) {
            if (this.movieList.get(i).equals(movie)) {
                this.movieList.remove(i);
                return true;
            }
        }

        return false;
    }

    @Deprecated
    public boolean hasMovie(Movie movie) {
        if (movie == null) {
            return false;
        }
        for (int i = 0; i < this.movieList.size(); i++) {
            if (movie.equals(movieList.get(i))) {
                return true;
            }
        }
        return false;
    }

    // public boolean equals(Entry entry) {
    // if (entry == null) {
    // return false;
    // }
    // if (this.id.equals(entry.getId())) {
    // return true;
    // }
    // return false;
    // }

    @Override
    public String getText() {
        return getName();
    }

    @Override
    public void setText(String text) {
        setName(text);
    }

    @Override
    public void setIsLast(boolean isLast) {
        this.isLast = isLast;
    }
}
