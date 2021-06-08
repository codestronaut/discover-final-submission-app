package com.coder.knight.jetpack.discover.data.source.local.room;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ContentDao {
    /*
     * FOR MOVIE
     * */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);

    @Query("SELECT * FROM movie_table WHERE movie_id = :movieId")
    List<Movie> loadAllMovie(int movieId);

    @Query("SELECT * FROM movie_table ORDER BY movie_title ASC")
    DataSource.Factory<Integer, Movie> getAllMovies();

    /*
     * FOR TV SHOW
     * */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertTvShow(TvShow tvShow);

    @Delete
    void deleteTvShow(TvShow tvShow);

    @Query("SELECT * FROM tv_show_table WHERE tv_id = :tvId")
    List<TvShow> loadAllTv(int tvId);

    @Query("SELECT * FROM tv_show_table ORDER BY tv_title ASC")
    DataSource.Factory<Integer, TvShow> getAllTvShows();
}
