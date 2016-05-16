package com.rajesh.popularmovies.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.rajesh.popularmovies.Movie;
import com.rajesh.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Project: Popular Movies
 * Created by muhammad on 01/07/15.
 */
public class CustomArrayAdapter extends BaseAdapter {

    private final Context context;
    private final int resource;
    private final Object mLock = new Object();
    private List<Movie> elements;

    public CustomArrayAdapter(Context context, int resource, List<Movie> elements) {
        this.context = context;
        this.resource = resource;
        this.elements = elements;
    }

    public List<Movie> getElements() {
        return elements;
    }

    @Override
    public int getCount() {
        return elements.size();
    }

    @Override
    public Movie getItem(int position) {
        return elements.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView view = (ImageView) convertView;
        if (view == null) {
            view = (ImageView) LayoutInflater.from(this.context).inflate(this.resource, parent, false);
        }

        String url = getItem(position).getPosterUrl();
        Picasso.with(context).load(url).error(R.drawable.no_poseter_found).into(view);
        return view;
    }


    public void updateValues(List<Movie> elements) {
        synchronized (mLock) {
            this.elements = elements;
        }
        notifyDataSetChanged();
    }
}
