package com.rajesh.popularmovies.details.reviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rajesh.popularmovies.details.MovieElementsAdapter;

import java.util.Map;

public class ReviewsArrayAdapter extends MovieElementsAdapter {

    public ReviewsArrayAdapter(Context context, int resource, Map<String, String> elements) {
        super(elements, resource, context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(this.context).inflate(this.resource, parent, false);
        }

        ((TextView) view.findViewById(android.R.id.text1)).setText((String) elements.keySet().toArray()[position]);
        ((TextView) view.findViewById(android.R.id.text2)).setText((String) elements.values().toArray()[position]);

        return view;
    }


}
