package com.rajesh.popularmovies.details.reviews;

import android.content.Context;
import android.support.annotation.NonNull;

import com.rajesh.popularmovies.details.MovieServerConnector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MovieReviewsServerConnector extends MovieServerConnector {


    public MovieReviewsServerConnector(Context context, int id) {
        super(context, id);
    }


    @Override
    @NonNull
    protected Map<String, String> parseElements(JSONArray results) throws JSONException {
        Map<String, String> reviews = new HashMap<>();
        for (int i = 0; i < results.length(); i++) {
            JSONObject movieJsonObject = results.getJSONObject(i);
            reviews.put(movieJsonObject.getString("author"), movieJsonObject.getString("content"));
        }
        return reviews;
    }


    @Override
    protected String setPath() {
        return "reviews";
    }
}
