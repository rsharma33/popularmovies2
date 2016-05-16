package com.rajesh.popularmovies;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.rajesh.popularmovies.MoviesContract.MovieEntry;

public class MoviesContentProvider extends ContentProvider {
    public static final int CODE_MOVIES = 100;
    public static final int CODE_MOVIE = 101;
    private static final SQLiteQueryBuilder sQueryBuilder;
    public static final String TAG = "MovieContentProvider";

    static {
        sQueryBuilder = new SQLiteQueryBuilder();
        sQueryBuilder.setTables(MovieEntry.TABLE_NAME);
    }

    private UriMatcher mUriMatcher = buildUriMatcher();
    private MoviesDatabaseHelper mMoviesDatabaseHelper;

    public static UriMatcher buildUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(MoviesContract.CONTENT_AUTHORITY, MoviesContract.PATH_MOVIE, CODE_MOVIES);
        matcher.addURI(MoviesContract.CONTENT_AUTHORITY, MoviesContract.PATH_MOVIE + "/#", CODE_MOVIE);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mMoviesDatabaseHelper = new MoviesDatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.v(TAG, "Attempting to query for Uri: " + uri);
        // FIXME: fix the selection parameter
        final String singleMovieQuerySelection = MovieEntry.TABLE_NAME + "." + MovieEntry.COLUMN_ID + " = " + MovieEntry.extractMovieId(uri);
        int match = mUriMatcher.match(uri);
        switch (match) {
            case CODE_MOVIE:
                Log.v(TAG, "matched for a single movie with id: " + extractMovieId(uri));
                return sQueryBuilder.query(mMoviesDatabaseHelper.getReadableDatabase(), null, singleMovieQuerySelection, new String[]{}, null, null, null);
            case CODE_MOVIES:
                Log.v(TAG, "matched for a all movies");
                return sQueryBuilder.query(mMoviesDatabaseHelper.getReadableDatabase(), null, null, null, null, null, null);
            default:
                Log.w(TAG, "No match found for uri: " + uri);
                return null;
        }
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        int match = mUriMatcher.match(uri);
        switch (match) {
            case CODE_MOVIE:
                return MovieEntry.CONTENT_ITEM_TYPE;
            case CODE_MOVIES:
                return MovieEntry.CONTENT_TYPE;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        mMoviesDatabaseHelper.getWritableDatabase().insert(MovieEntry.TABLE_NAME, null, values);
        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return mMoviesDatabaseHelper.getWritableDatabase().delete(MovieEntry.TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    private String extractMovieId(Uri uri) {
        return uri.getLastPathSegment();
    }
}
