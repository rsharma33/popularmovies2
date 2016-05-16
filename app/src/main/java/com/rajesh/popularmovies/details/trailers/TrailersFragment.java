package com.rajesh.popularmovies.details.trailers;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;

import com.rajesh.popularmovies.details.MovieElementsFragment;

import java.util.HashMap;

public class TrailersFragment extends MovieElementsFragment {

    public static TrailersFragment newInstance(int id) {
        TrailersFragment fragment = new TrailersFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(KEY_ID, id);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    @NonNull
    protected FetchTrailers setMovieElementsFetcher() {
        return new FetchTrailers(getActivity(), this);
    }

    @Override
    @NonNull
    protected TrailersArrayAdapter setArrayAdapter() {
        return new TrailersArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, new HashMap<String, String>());
    }

    @Override
    @NonNull
    protected AdapterView.OnItemClickListener setonItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + arrayAdapter.getItem(position))));
            }
        };
    }

}
