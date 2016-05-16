package com.rajesh.popularmovies.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

import com.rajesh.popularmovies.Movie;
import com.rajesh.popularmovies.R;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Project: Popular Movies
 * Created by muhammad on 26/06/15.
 */
public class MovieDatabaseServerConnector {

    private static final String TAG = "ServerConnector";
    private static final int DEFAULT_SERVER_PAGE_SIZE = 20;
    private final String apikey;
    private Context context;


    public MovieDatabaseServerConnector(Context context) {
        this.context = context;
//      this.apikey = context.getString(R.string.server_api_key);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.apikey = sharedPreferences.getString("api-key", context.getString(R.string.server_api_key));
    }

    public String getData() throws IOException, UnauthorizedException {
        String baseUrl = this.context.getString(R.string.server_base_url);
        // TODO: store string constants in resource file(s)
        Uri uri = Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter("sort_by", "popularity.desc")
                .appendQueryParameter("api_key", this.apikey).build();

        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(uri.toString()).openConnection();
        httpURLConnection.connect();

        int responseCode;
        try {
            responseCode = httpURLConnection.getResponseCode();
        } catch (IOException e) {
            responseCode = httpURLConnection.getResponseCode();
        }

        switch (responseCode) {
            case HttpURLConnection.HTTP_OK:
                InputStream inputStream = httpURLConnection.getInputStream();
                StringBuilder stringBuilder = new StringBuilder();

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                    stringBuilder.append("\n");
                }
                return stringBuilder.toString();
            case HttpURLConnection.HTTP_UNAUTHORIZED:
                throw new UnauthorizedException();
            default:
                throw new IllegalStateException("Connection method is not equipped to handle this case");
        }
    }

    public String getPage(int page, int sortCriteria) throws IOException, UnauthorizedException {
        String baseUrl = this.context.getString(R.string.server_base_url);
        // TODO: store string constants in resource file(s)
        Uri uri = Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter("sort_by", sortCriteria == 0 ? "popularity.desc" : "vote_average.desc")
                .appendQueryParameter("api_key", this.apikey)
                .appendQueryParameter("page", String.valueOf(page))
                .build();
        Log.d(TAG, "Loading uri: " + uri.toString());
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(uri.toString()).openConnection();
        httpURLConnection.connect();

        int responseCode;
        try {
            responseCode = httpURLConnection.getResponseCode();
        } catch (IOException e) {
            responseCode = httpURLConnection.getResponseCode();
        }

        switch (responseCode) {
            case HttpURLConnection.HTTP_OK:
                InputStream inputStream = httpURLConnection.getInputStream();
                StringBuilder stringBuilder = new StringBuilder();

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                    stringBuilder.append("\n");
                }
                return stringBuilder.toString();
            case HttpURLConnection.HTTP_UNAUTHORIZED:
                throw new UnauthorizedException();
            default:
                Log.e(TAG, "Unknown response code: " + responseCode);
                throw new IllegalStateException("Connection method is not equipped to handle this case");
        }
    }

    public List<Movie> getMovies(int page, int pageSize, int sortCriteria) throws IOException, UnauthorizedException, JSONException {
        Log.d(TAG, "PageSize: " + pageSize);
        int numberOfServerPagesPerResult = pageSize / DEFAULT_SERVER_PAGE_SIZE;
        Log.d(TAG, "Number of server pages per result: " + numberOfServerPagesPerResult);
        int firstRequiredPage = (page - 1) * numberOfServerPagesPerResult + 1;
        int lastRequiredPage = page * numberOfServerPagesPerResult;
        Log.d(TAG, "requesting pages from [" + firstRequiredPage + "] to [" + lastRequiredPage + "]");
        List<Movie> movies = new ArrayList<>();
        for (int i = firstRequiredPage; i <= lastRequiredPage; i++) {
            String pageData = getPage(i, sortCriteria);
            movies.addAll(new DataParser(pageData).getMovies());
        }
        return movies;
    }
}
