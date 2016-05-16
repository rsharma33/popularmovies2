package com.rajesh.popularmovies.details.trailers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rajesh.popularmovies.details.MovieElementsAdapter;

import java.util.Map;

public class TrailersArrayAdapter extends MovieElementsAdapter {

    public TrailersArrayAdapter(Context context, int resource, Map<String, String> elements) {
        super(elements, resource, context);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) convertView;
        if (view == null) {
            view = (TextView) LayoutInflater.from(this.context).inflate(this.resource, parent, false);
        }

        view.setText((String) elements.keySet().toArray()[position]);
        return view;
    }


}
