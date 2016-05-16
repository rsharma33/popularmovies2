package com.rajesh.popularmovies.details;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rajesh.popularmovies.FavoriteMoviesManager;
import com.rajesh.popularmovies.Movie;
import com.rajesh.popularmovies.R;
import com.squareup.picasso.Picasso;


/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsActivityFragment extends Fragment {

    public static final String ARG_MOVIE = "arg_movie";
    private FavoriteMoviesManager mManager;
    // Fixme: Use a callback interface instead
    private CallBacks mCallBacks;

    public DetailsActivityFragment() {
    }

    public static DetailsActivityFragment newInstance(Movie movie) {
        DetailsActivityFragment fragment = new DetailsActivityFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_MOVIE, movie);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallBacks = (CallBacks) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        mManager = FavoriteMoviesManager.create(getActivity());
        final Movie movie = getArguments().getParcelable(ARG_MOVIE);

        TextView moviePlot = (TextView) rootView.findViewById(R.id.movie_plot);
        moviePlot.setText("null".equals(movie.getPlotSynopsis()) ? "" : movie.getPlotSynopsis());

        TextView movieTitle = (TextView) rootView.findViewById(R.id.movie_title);
        movieTitle.setText(movie.getOriginalTitle());


        final FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        if (mManager.isFavorite(movie)) {
            fab.setImageResource(R.drawable.ic_favorite_black_24dp);
        } else {
            fab.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mManager.isFavorite(movie)) {
                    mManager.remove(movie);
                    fab.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    Toast.makeText(getActivity(), movie.getOriginalTitle() + " has been removed from your favorites", Toast.LENGTH_SHORT).show();
                } else {
                    mManager.add(movie);
                    fab.setImageResource(R.drawable.ic_favorite_black_24dp);
                    Toast.makeText(getActivity(), movie.getOriginalTitle() + " has been added to your favorites", Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView rating = (TextView) rootView.findViewById(R.id.movie_rating);
        rating.setText(String.valueOf("User Rating [ " + movie.getUserRating() + "/10 ]"));

        TextView releaseDate = (TextView) rootView.findViewById(R.id.movie_release_date);
        releaseDate.setText("Release Date [ " + ("null".equals(movie.getReleaseDate()) ? "N/A" : movie.getReleaseDate()) + " ]");

        ImageView imageView = (ImageView) rootView.findViewById(R.id.movie_poster);
        Picasso.with(getActivity()).load(movie.getPosterUrl()).error(R.drawable.no_poseter_found).into(imageView);

        rootView.findViewById(R.id.movie_trailers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallBacks.showTrailers(movie.getId());
            }
        });
        rootView.findViewById(R.id.movie_reviews).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallBacks.showReviews(movie.getId());
            }
        });

        return rootView;
    }

    public interface CallBacks {
        void showReviews(int id);

        void showTrailers(int id);
    }

}
