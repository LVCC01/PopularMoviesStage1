package com.example.android.cinema;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hjadhav on 4/21/2017.
 */

public class MovieDataParcel implements Parcelable {
    String moviePoster;
    String movieTitle;
    String moviePlot;
    String movieRating;
    String movieReleaseDate;

    public MovieDataParcel (String imageUrl,String title,String plot,String rating,String releaseDate){
        this.moviePoster = imageUrl;
        this.movieTitle = title;
        this.moviePlot = plot;
        this.movieRating = rating;
        this.movieReleaseDate = releaseDate;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(moviePoster);
        dest.writeString(movieTitle);
        dest.writeString(moviePlot);
        dest.writeString(movieRating);
        dest.writeString(movieReleaseDate);
    }

    protected MovieDataParcel(Parcel in) {
        moviePoster = in.readString();
        movieTitle = in.readString();
        moviePlot = in.readString();
        movieRating = in.readString();
        movieReleaseDate = in.readString();
    }

    public static final Creator<MovieDataParcel> CREATOR = new Creator<MovieDataParcel>() {
        @Override
        public MovieDataParcel createFromParcel(Parcel in) {
            return new MovieDataParcel(in);
        }

        @Override
        public MovieDataParcel[] newArray(int size) {
            return new MovieDataParcel[size];
        }
    };


}
