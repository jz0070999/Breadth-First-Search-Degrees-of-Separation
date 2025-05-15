package com.hollywooddos.jessica;

import java.util.List;
import java.util.Objects;

import com.hollywooddos.jessica.data.TmdbClient;
import com.hollywooddos.jessica.views.SeparationListEntry;

import info.movito.themoviedbapi.tools.TmdbException;

public class Movie implements Entry, SeparationListEntry {
    private String name;
    private String id;
    public boolean isLast = false;

    public Movie() {
    }

    public Movie(String name, String id) {
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

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        Movie other = (Movie) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
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
