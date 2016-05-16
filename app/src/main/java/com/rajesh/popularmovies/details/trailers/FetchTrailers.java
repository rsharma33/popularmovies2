package com.rajesh.popularmovies.details.trailers;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.rajesh.popularmovies.details.DataSetUpdateListener;
import com.rajesh.popularmovies.details.FetchMovieElements;
import com.rajesh.popularmovies.details.MovieServerConnector;

public class FetchTrailers extends FetchMovieElements {


    public FetchTrailers(Activity activity, DataSetUpdateListener listener) {
        super(activity, listener);
    }

    @Override
    @NonNull
    protected MovieServerConnector setServerConnector(Integer[] params) {
        return new MovieTrailersServerConnector(mActivity, params[0]);
    }

}
