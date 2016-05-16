package com.rajesh.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Project: Popular Movies
 * Created by muhammad on 27/06/15.
 */
public class Movie implements Parcelable {
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }

    };
    private String originalTitle;
    private double userRating;
    private String releaseDate;
    private String plotSynopsis;
    private String posterUrl;
    private int id;

    public Movie(String originalTitle, double userRating, String releaseDate, String plotSynopsis, String posterPath, int id) {
        this.originalTitle = originalTitle;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
        this.plotSynopsis = plotSynopsis;
        this.id = id;
        this.posterUrl = "http://image.tmdb.org/t/p/w185" + posterPath;
    }

    public Movie(Parcel parcel) {
        this.originalTitle = parcel.readString();
        this.userRating = parcel.readDouble();
        this.releaseDate = parcel.readString();
        this.plotSynopsis = parcel.readString();
        this.posterUrl = parcel.readString();
        this.id = parcel.readInt();
    }

    public int getId() {
        return id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public double getUserRating() {
        return userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "originalTitle='" + originalTitle + '\'' +
                ", userRating=" + userRating +
                ", releaseDate='" + releaseDate + '\'' +
                ", plotSynopsis='" + plotSynopsis + '\'' +
                ", posterUrl='" + posterUrl + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(originalTitle);
        parcel.writeDouble(userRating);
        parcel.writeString(releaseDate);
        parcel.writeString(plotSynopsis);
        parcel.writeString(posterUrl);
        parcel.writeInt(id);
    }
}
