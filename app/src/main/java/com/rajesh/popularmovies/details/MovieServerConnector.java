package com.rajesh.popularmovies.details;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

import com.rajesh.popularmovies.R;
import com.rajesh.popularmovies.network.UnauthorizedException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public abstract class MovieServerConnector {
    public static final String RESULTS_kEY = "results";
    protected final String apikey;
    protected Context context;
    protected int mId;
    protected String mPath;


    public MovieServerConnector(Context context, int id) {
        this.context = context;
        mId = id;
//        this.apikey = context.getString(R.string.server_api_key);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.apikey = sharedPreferences.getString("api-key", context.getString(R.string.server_api_key));
        mPath = setPath();
    }

    public String getData() throws IOException, UnauthorizedException {
        String baseUrl = this.context.getString(R.string.movie_url);
        // TODO: store string constants in resource file(s)
        Uri uri = Uri.parse(baseUrl).buildUpon()
                .appendPath(String.valueOf(mId))
                .appendPath(mPath)
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

    public Map<String, String> getElements() throws JSONException, IOException, UnauthorizedException {
        final JSONObject jsonObject = new JSONObject(getData());
        JSONArray results = jsonObject.getJSONArray(RESULTS_kEY);
        Map<String, String> elements = parseElements(results);
        return elements;
    }

    protected abstract String setPath();

    protected abstract Map<String, String> parseElements(JSONArray results) throws JSONException;
}
