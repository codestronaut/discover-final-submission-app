package com.coder.knight.jetpack.discover.data.source.remote;

import com.coder.knight.jetpack.discover.data.source.local.entity.MovieEntity;
import com.coder.knight.jetpack.discover.data.source.local.entity.TvShowEntity;
import com.coder.knight.jetpack.discover.data.source.remote.response.MovieResponse;
import com.coder.knight.jetpack.discover.data.source.remote.response.TrailerResponse;
import com.coder.knight.jetpack.discover.data.source.remote.response.TvShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    /*
     * Movies
     * */
    @GET("movie/upcoming")
    Call<MovieResponse> getMovies(
            @Query("api_key") String apiKey
    );

    @GET("movie/{movie_id}")
    Call<MovieEntity> getDetailMovie(
            @Path("movie_id") int id,
            @Query("api_key") String apiKey
    );

    @GET("movie/{movie_id}/videos")
    Call<TrailerResponse> getMovieTrailer(
            @Path("movie_id") int id,
            @Query("api_key") String apiKey
    );

    /*
     * Tv Shows
     * */
    @GET("tv/on_the_air")
    Call<TvShowResponse> getTvShows(
            @Query("api_key") String apiKey
    );

    @GET("tv/{id}")
    Call<TvShowEntity> getDetailTvShow(
            @Path("id") int id,
            @Query("api_key") String apiKey
    );

    @GET("tv/{id}/videos")
    Call<TrailerResponse> getTvShowTrailer(
            @Path("id") int id,
            @Query("api_key") String apiKey
    );
}
