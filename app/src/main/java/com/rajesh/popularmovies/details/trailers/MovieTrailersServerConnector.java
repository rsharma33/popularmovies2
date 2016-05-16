package com.rajesh.popularmovies.details.trailers;

import android.content.Context;
import android.util.Log;

import com.rajesh.popularmovies.details.MovieServerConnector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MovieTrailersServerConnector extends MovieServerConnector {
    protected static final String TAG = "TrailersServerConnector";


    public MovieTrailersServerConnector(Context context, int id) {
        super(context, id);
    }


    @Override
    protected String setPath() {
        return "videos";
    }

    @Override
    protected Map<String, String> parseElements(JSONArray results) throws JSONException {
        Map<String, String> trailers = new HashMap<>();
        for (int i = 0; i < results.length(); i++) {
            JSONObject movieJsonObject = results.getJSONObject(i);
            if (movieJsonObject.getString("site").equals("YouTube")) {
                trailers.put(movieJsonObject.getString("name"), movieJsonObject.getString("key"));
            } else {
                Log.w(TAG, "Ignored trailer for unknown website: " + movieJsonObject.getString("site"));
            }
        }
        return trailers;

    }


}
