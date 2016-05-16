package com.rajesh.popularmovies.details;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.Map;

public abstract class MovieElementsAdapter extends BaseAdapter {
    protected final Context context;
    protected final int resource;
    private final Object mLock = new Object();
    protected Map<String, String> elements;

    public MovieElementsAdapter(Map<String, String> elements, int resource, Context context) {
        this.elements = elements;
        this.resource = resource;
        this.context = context;
    }

    public Map<String, String> getElements() {
        return elements;
    }

    @Override
    public int getCount() {
        return elements.size();
    }

    @Override
    public String getItem(int position) {
        return (String) elements.values().toArray()[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    abstract public View getView(int position, View convertView, ViewGroup parent);

    public void updateValues(Map<String, String> elements) {
        synchronized (mLock) {
            this.elements = elements;
        }
        notifyDataSetChanged();
    }
}
