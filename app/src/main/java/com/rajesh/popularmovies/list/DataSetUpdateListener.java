package com.rajesh.popularmovies.list;

import com.rajesh.popularmovies.Movie;

import java.util.List;

/**
 * Project: Popular Movies
 * Created by muhammad on 14/07/15.
 */
public interface DataSetUpdateListener {
    void onDataSetUpdated(List<Movie> movies);
}
