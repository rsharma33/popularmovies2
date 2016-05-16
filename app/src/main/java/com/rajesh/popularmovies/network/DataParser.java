package com.rajesh.popularmovies.network;

import com.rajesh.popularmovies.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: Popular Movies
 * Created by muhammad on 27/06/15.
 */
public class DataParser {
    public static final String TOTAL_PAGES_KEY = "total_pages";
    public static final String TOTAL_RESULTS_KEY = "total_results";
    public static final String RESULTS_KEY = "results";
    public static final String ORIGINAL_TITLE_KEY = "original_title";
    public static final String VOTE_AVERAGE_KEY = "vote_average";
    public static final String RELEASE_DATE_KEY = "release_date";
    public static final String OVERVIEW_KEY = "overview";
    public static final String POSTER_PATH_KEY = "poster_path";
    public static final String PAGE_NUMBER_KEY = "page";
    public static final String RESULTS_kEY = "results";
    public static final String ID = "id";
    private final JSONObject jsonObject;

    public DataParser(String data) throws JSONException {
        this.jsonObject = new JSONObject(data);
    }


    public int getCurrentPageNumber() throws JSONException {
        return jsonObject.getInt(PAGE_NUMBER_KEY);
    }

    public int getTotalNumberOfPages() throws JSONException {
        return jsonObject.getInt(TOTAL_PAGES_KEY);
    }

    public int getTotalNumberOfResults() throws JSONException {
        return jsonObject.getInt(TOTAL_RESULTS_KEY);
    }

    public int getNumberOfResultsInCurrentPage() throws JSONException {
        JSONArray results = jsonObject.getJSONArray(RESULTS_KEY);
        return results.length();
    }

    public Movie getMovie(int id) throws JSONException {
        JSONArray results = jsonObject.getJSONArray(RESULTS_kEY);
        JSONObject movieJsonObject = results.getJSONObject(id);
        return new Movie(movieJsonObject.getString(ORIGINAL_TITLE_KEY),
                movieJsonObject.getDouble(VOTE_AVERAGE_KEY),
                movieJsonObject.getString(RELEASE_DATE_KEY),
                movieJsonObject.getString(OVERVIEW_KEY),
                movieJsonObject.getString(POSTER_PATH_KEY), movieJsonObject.getInt(ID));
    }

    public List<Movie> getMovies() throws JSONException {
        List<Movie> movies = new ArrayList<>();
        JSONArray results = jsonObject.getJSONArray(RESULTS_kEY);
        for (int i = 0; i < results.length(); i++) {
            JSONObject movieJsonObject = results.getJSONObject(i);
            Movie movie = new Movie(movieJsonObject.getString(ORIGINAL_TITLE_KEY),
                    movieJsonObject.getDouble(VOTE_AVERAGE_KEY),
                    movieJsonObject.getString(RELEASE_DATE_KEY),
                    movieJsonObject.getString(OVERVIEW_KEY),
                    movieJsonObject.getString(POSTER_PATH_KEY), movieJsonObject.getInt(ID));
            movies.add(movie);
        }
        return movies;
    }
}
