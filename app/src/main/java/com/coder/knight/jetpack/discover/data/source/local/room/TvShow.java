package com.coder.knight.jetpack.discover.data.source.local.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tv_show_table")
public class TvShow {
    @PrimaryKey
    @ColumnInfo(name = "tv_id")
    public int tvId;

    @ColumnInfo(name = "tv_title")
    private String tvTitle;

    @ColumnInfo(name = "tv_date")
    private String tvDate;

    @ColumnInfo(name = "tv_rating")
    private double tvRating;

    @ColumnInfo(name = "tv_popularity")
    private String tvPopularity;

    @ColumnInfo(name = "tv_poster")
    private String tvPoster;

    public int getTvId() {
        return tvId;
    }

    public void setTvId(int tvId) {
        this.tvId = tvId;
    }

    public String getTvTitle() {
        return tvTitle;
    }

    public void setTvTitle(String tvTitle) {
        this.tvTitle = tvTitle;
    }

    public String getTvDate() {
        return tvDate;
    }

    public void setTvDate(String tvDate) {
        this.tvDate = tvDate;
    }

    public double getTvRating() {
        return tvRating;
    }

    public void setTvRating(double tvRating) {
        this.tvRating = tvRating;
    }

    public String getTvPopularity() {
        return tvPopularity;
    }

    public void setTvPopularity(String tvPopularity) {
        this.tvPopularity = tvPopularity;
    }

    public String getTvPoster() {
        return tvPoster;
    }

    public void setTvPoster(String tvPoster) {
        this.tvPoster = tvPoster;
    }
}
