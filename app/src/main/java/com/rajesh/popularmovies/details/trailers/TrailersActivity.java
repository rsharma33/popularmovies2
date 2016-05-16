package com.rajesh.popularmovies.details.trailers;

import android.support.v4.app.Fragment;

import com.rajesh.popularmovies.BaseActivity;

public class TrailersActivity extends BaseActivity {
    @Override
    protected Fragment createFragment() {
        return TrailersFragment.newInstance(getIntent().getIntExtra("extra_movie_id", 0));
    }
}
