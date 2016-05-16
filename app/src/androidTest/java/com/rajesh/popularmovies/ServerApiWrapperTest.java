package com.rajesh.popularmovies;

import android.test.AndroidTestCase;

import com.rajesh.popularmovies.network.MovieDatabaseServerConnector;

import java.util.List;

/**
 * Project: Popular Movies
 * Created by muhammad on 03/07/15.
 */
public class ServerApiWrapperTest extends AndroidTestCase {

    private static final int DEFAULT_SERVER_PAGE_SIZE = 20;
    private MovieDatabaseServerConnector connector;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        connector = new MovieDatabaseServerConnector(getContext());

    }

    public void testGivenTotalNumberOfMoviesAndRequiredMoviesPerPageCaclculateNumberOfPages() {
        int totalNumberOfMovies = 1002;
        int requiredPageSize = 40;
        int expected = 26;
        int actual = totalNumberOfMovies / requiredPageSize;
        if (totalNumberOfMovies % requiredPageSize != 0) {
            actual += 1;
        }
        assertEquals("Number of pages calculated is not correct", expected, actual);
    }

    public void testGivenRequiredPageSizeOf40ReturnListOfMoviesOfTheSameSizeForFirstPage() throws Exception {
        int requiredPageSize = 40;
        int page = 1;
        List<Movie> movies = connector.getMovies(page, requiredPageSize,0);
        int numberOfResultsInCurrentPage = movies.size();

        assertEquals("Number of movies returned in list", requiredPageSize, numberOfResultsInCurrentPage);
    }

    public void testGivenRequiredPageSizeOf60ReturnListOfMoviesOfTheSameSizeForThirdPage() throws Exception {
        int requiredPageSize = 60;
        int page = 3;
        List<Movie> movies = connector.getMovies(page, requiredPageSize, 0);
        int numberOfResultsInCurrentPage = movies.size();

        assertEquals("Number of movies returned in list", requiredPageSize, numberOfResultsInCurrentPage);
    }

}
