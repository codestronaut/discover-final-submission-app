package com.coder.knight.jetpack.discover.data.source.remote;

import androidx.annotation.NonNull;

import com.coder.knight.jetpack.discover.data.source.local.entity.MovieEntity;
import com.coder.knight.jetpack.discover.data.source.local.entity.TvShowEntity;
import com.coder.knight.jetpack.discover.data.source.remote.response.MovieResponse;
import com.coder.knight.jetpack.discover.data.source.remote.response.TrailerResponse;
import com.coder.knight.jetpack.discover.data.source.remote.response.TvShowResponse;
import com.coder.knight.jetpack.discover.utils.EspressoIdlingResource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteRepository {
    private static RemoteRepository INSTANCE;
    private ApiInterface mApiInterface;

    private RemoteRepository(ApiInterface apiInterface) {
        this.mApiInterface = apiInterface;
    }

    public static RemoteRepository getInstance(ApiInterface apiInterface) {
        if (INSTANCE == null) {
            INSTANCE = new RemoteRepository(apiInterface);
        }

        return INSTANCE;
    }

    public void getMovieList(String apiKey, LoadMovieCallback callback) {
        EspressoIdlingResource.increment();
        mApiInterface.getMovies(apiKey).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    callback.onAllMoviesReceived(response.body());
                    EspressoIdlingResource.decrement();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                callback.onDataNotLoaded();
            }
        });
    }

    public void getTvShowList(String apiKey, LoadTvShowCallback callback) {
        EspressoIdlingResource.increment();
        mApiInterface.getTvShows(apiKey).enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvShowResponse> call, @NonNull Response<TvShowResponse> response) {
                if (response.isSuccessful()) {
                    callback.onAllTvShowReceived(response.body());
                    EspressoIdlingResource.decrement();
                }
            }

            @Override
            public void onFailure(@NonNull Call<TvShowResponse> call, @NonNull Throwable t) {
                callback.onDataNotLoaded();
            }
        });
    }

    public void getMovieDetail(int id, String apiKey, LoadMovieDetailCallback callback) {
        mApiInterface.getDetailMovie(id, apiKey).enqueue(new Callback<MovieEntity>() {
            @Override
            public void onResponse(@NonNull Call<MovieEntity> call, @NonNull Response<MovieEntity> response) {
                if (response.isSuccessful()) {
                    callback.onMovieLoaded(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieEntity> call, @NonNull Throwable t) {
                callback.onDataNotLoaded();
            }
        });
    }

    public void getTvShowDetail(int id, String apiKey, LoadTvShowDetailCallback callback) {
        mApiInterface.getDetailTvShow(id, apiKey).enqueue(new Callback<TvShowEntity>() {
            @Override
            public void onResponse(@NonNull Call<TvShowEntity> call, @NonNull Response<TvShowEntity> response) {
                if (response.isSuccessful()) {
                    callback.onTvShowLoaded(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TvShowEntity> call, @NonNull Throwable t) {
                callback.onDataNotLoaded();
            }
        });
    }

    public void getMovieTrailer(int movieId, String apiKey, LoadMovieTrailerCallback callback) {
        mApiInterface.getMovieTrailer(movieId, apiKey).enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(@NonNull Call<TrailerResponse> call, @NonNull Response<TrailerResponse> response) {
                if (response.isSuccessful()) {
                    callback.onTrailerLoaded(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TrailerResponse> call, @NonNull Throwable t) {
                callback.onDataNotLoaded();
            }
        });
    }

    public void getTvShowTrailer(int tvId, String apiKey, LoadTvShowTrailerCallback callback) {
        mApiInterface.getTvShowTrailer(tvId, apiKey).enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(@NonNull Call<TrailerResponse> call, @NonNull Response<TrailerResponse> response) {
                if (response.isSuccessful()) {
                    callback.onTrailerLoaded(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TrailerResponse> call, @NonNull Throwable t) {
                callback.onDataNotLoaded();
            }
        });
    }

    // The Callbacks
    public interface LoadMovieCallback {
        void onAllMoviesReceived(MovieResponse movieResponse);

        void onDataNotLoaded();
    }

    public interface LoadMovieDetailCallback {
        void onMovieLoaded(MovieEntity movieEntity);

        void onDataNotLoaded();
    }

    public interface LoadTvShowCallback {
        void onAllTvShowReceived(TvShowResponse tvShowResponse);

        void onDataNotLoaded();
    }

    public interface LoadTvShowDetailCallback {
        void onTvShowLoaded(TvShowEntity tvShowEntity);

        void onDataNotLoaded();
    }

    public interface LoadMovieTrailerCallback {
        void onTrailerLoaded(TrailerResponse trailerResponse);

        void onDataNotLoaded();
    }

    public interface LoadTvShowTrailerCallback {
        void onTrailerLoaded(TrailerResponse trailerResponse);

        void onDataNotLoaded();
    }
}
