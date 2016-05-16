package com.rajesh.popularmovies;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class MoviesDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movie.db";
    private static final int DATABASE_VERSION = 1;

    public MoviesDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableQuery = generateCreateTableQuery(MoviesContract.MovieEntry.TABLE_NAME, new String[]{
                MoviesContract.MovieEntry._ID,
                MoviesContract.MovieEntry.COLUMN_ID,
                MoviesContract.MovieEntry.COLUMN_TITLE,
                MoviesContract.MovieEntry.COLUMN_RATING,
                MoviesContract.MovieEntry.COLUMN_RELEASE_DATE,
                MoviesContract.MovieEntry.COLUMN_PLOT,
                MoviesContract.MovieEntry.COLUMN_POSTER_URL});
        sqLiteDatabase.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // upgrade is not supported yet
    }

    private String generateCreateTableQuery(String tableName, String[] columnNames) {
        StringBuilder builder = new StringBuilder("CREATE TABLE ");
        builder.append(tableName).append("(");
        builder.append(BaseColumns._ID);
        builder.append(" INT PRIMARY KEY, ");
        for (int i = 1; i < columnNames.length; i++) {
            builder.append(columnNames[i]);
            if (i != columnNames.length - 1) {
                builder.append(", ");
            }
        }
        builder.append(");");
        return builder.toString();
    }
}
