package com.coder.knight.jetpack.discover.data.source.remote.response;

import com.coder.knight.jetpack.discover.data.source.local.entity.MovieEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieResponse {
    @SerializedName("results")
    @Expose
    private ArrayList<MovieEntity> movieList;

    public MovieResponse(ArrayList<MovieEntity> movieList) {
        this.movieList = movieList;
    }

    public ArrayList<MovieEntity> getMovieList() {
        return movieList;
    }
}
