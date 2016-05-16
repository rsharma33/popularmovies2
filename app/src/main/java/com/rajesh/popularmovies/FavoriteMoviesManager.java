package com.rajesh.popularmovies;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;

public class FavoriteMoviesManager {
    private static final SQLiteQueryBuilder sQueryBuilder;
    private static FavoriteMoviesManager sMoviesManager;

    static {
        sQueryBuilder = new SQLiteQueryBuilder();
        sQueryBuilder.setTables(MoviesContract.MovieEntry.TABLE_NAME);
    }

    private final ContentResolver mContentResolver;


    private FavoriteMoviesManager(Context context) {
        mContentResolver = context.getContentResolver();
    }

    public static FavoriteMoviesManager create(Context context) {
        if (sMoviesManager == null) {
            sMoviesManager = new FavoriteMoviesManager(context);
        }
        return sMoviesManager;
    }

    public void add(Movie movie) {

        ContentValues values = new ContentValues();
        values.put(MoviesContract.MovieEntry.COLUMN_ID, movie.getId());
        values.put(MoviesContract.MovieEntry.COLUMN_TITLE, movie.getOriginalTitle());
        values.put(MoviesContract.MovieEntry.COLUMN_RATING, movie.getUserRating());
        values.put(MoviesContract.MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        values.put(MoviesContract.MovieEntry.COLUMN_PLOT, movie.getPlotSynopsis());
        values.put(MoviesContract.MovieEntry.COLUMN_POSTER_URL, movie.getPosterUrl());
        mContentResolver.insert(MoviesContract.MovieEntry.CONTENT_URI, values);
    }

    public void remove(Movie movie) {
        mContentResolver.delete(MoviesContract.MovieEntry.CONTENT_URI, MoviesContract.MovieEntry.COLUMN_ID + " = " + movie.getId(), null);
    }

    public boolean isFavorite(Movie movie) {
        Cursor cursor = mContentResolver.query(MoviesContract.MovieEntry.CONTENT_URI.buildUpon().appendPath(String.valueOf(movie.getId())).build(), null, null, null, null);
        if (cursor != null) {
            if (cursor.getCount() == 1) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

}
