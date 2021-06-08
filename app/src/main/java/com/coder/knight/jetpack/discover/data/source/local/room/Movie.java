package com.coder.knight.jetpack.discover.data.source.local.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movie_table")
public class Movie {
    @PrimaryKey
    @ColumnInfo(name = "movie_id")
    public int movieId;

    @ColumnInfo(name = "movie_title")
    private String movieTitle;

    @ColumnInfo(name = "movie_date")
    private String movieDate;

    @ColumnInfo(name = "movie_rating")
    private double movieRating;

    @ColumnInfo(name = "movie_popularity")
    private String moviePopularity;

    @ColumnInfo(name = "movie_poster")
    private String moviePoster;

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieDate() {
        return movieDate;
    }

    public void setMovieDate(String movieDate) {
        this.movieDate = movieDate;
    }

    public double getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(double movieRating) {
        this.movieRating = movieRating;
    }

    public String getMoviePopularity() {
        return moviePopularity;
    }

    public void setMoviePopularity(String moviePopularity) {
        this.moviePopularity = moviePopularity;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }
}
