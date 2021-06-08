package com.coder.knight.jetpack.discover.data.source;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.coder.knight.jetpack.discover.data.source.local.LocalRepository;
import com.coder.knight.jetpack.discover.data.source.local.entity.MovieEntity;
import com.coder.knight.jetpack.discover.data.source.local.entity.TvShowEntity;
import com.coder.knight.jetpack.discover.data.source.local.room.Movie;
import com.coder.knight.jetpack.discover.data.source.local.room.TvShow;
import com.coder.knight.jetpack.discover.data.source.remote.RemoteRepository;
import com.coder.knight.jetpack.discover.data.source.remote.response.MovieResponse;
import com.coder.knight.jetpack.discover.data.source.remote.response.TrailerResponse;
import com.coder.knight.jetpack.discover.data.source.remote.response.TvShowResponse;

public class ContentRepository {
    private volatile static ContentRepository INSTANCE = null;

    private final RemoteRepository remoteRepository;
    private final LocalRepository localRepository;

    private ContentRepository(@NonNull RemoteRepository remoteRepository, @NonNull LocalRepository localRepository) {
        this.remoteRepository = remoteRepository;
        this.localRepository = localRepository;
    }

    public static ContentRepository getInstance(RemoteRepository remoteRepository, LocalRepository localRepository) {
        if (INSTANCE == null) {
            synchronized (ContentRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ContentRepository(remoteRepository, localRepository);
                }
            }
        }

        return INSTANCE;
    }

    /*
     *
     * FROM API (REMOTE)
     *
     * */

    /*
     * Get Movie List and Detail
     * */
    public LiveData<MovieResponse> getMovies(String apiKey) {
        final MutableLiveData<MovieResponse> resultMovie = new MutableLiveData<>();

        remoteRepository.getMovieList(apiKey, new RemoteRepository.LoadMovieCallback() {
            @Override
            public void onAllMoviesReceived(MovieResponse movieResponse) {
                if (movieResponse != null) {
                    resultMovie.setValue(movieResponse);
                }
            }

            @Override
            public void onDataNotLoaded() {
                // Do Nothing . . .
            }
        });

        return resultMovie;
    }

    public LiveData<MovieEntity> getMovieDetail(int id, String apiKey) {
        final MutableLiveData<MovieEntity> resultMovieDetail = new MutableLiveData<>();

        remoteRepository.getMovieDetail(id, apiKey, new RemoteRepository.LoadMovieDetailCallback() {
            @Override
            public void onMovieLoaded(MovieEntity movieEntity) {
                if (movieEntity != null) {
                    resultMovieDetail.setValue(movieEntity);
                }
            }

            @Override
            public void onDataNotLoaded() {
                // Do nothing . . .
            }
        });

        return resultMovieDetail;
    }

    /*
     * Get Tv Show List and Detail
     * */
    public LiveData<TvShowResponse> getTvShows(String apiKey) {
        final MutableLiveData<TvShowResponse> resultTvShow = new MutableLiveData<>();

        remoteRepository.getTvShowList(apiKey, new RemoteRepository.LoadTvShowCallback() {
            @Override
            public void onAllTvShowReceived(TvShowResponse tvShowResponse) {
                if (tvShowResponse != null) {
                    resultTvShow.setValue(tvShowResponse);
                }
            }

            @Override
            public void onDataNotLoaded() {
                // Do nothing . . .
            }
        });

        return resultTvShow;
    }

    public LiveData<TvShowEntity> getTvShowDetail(int id, String apiKey) {
        final MutableLiveData<TvShowEntity> resultTvDetail = new MutableLiveData<>();

        remoteRepository.getTvShowDetail(id, apiKey, new RemoteRepository.LoadTvShowDetailCallback() {
            @Override
            public void onTvShowLoaded(TvShowEntity tvShowEntity) {
                if (tvShowEntity != null) {
                    resultTvDetail.setValue(tvShowEntity);
                }
            }

            @Override
            public void onDataNotLoaded() {
                // Do nothing . . .
            }
        });

        return resultTvDetail;
    }

    /*
     * Get Movie dan Tv Show Trailers
     * */
    public LiveData<TrailerResponse> getMovieTrailer(int movieId, String apiKey) {
        final MutableLiveData<TrailerResponse> movieTrailer = new MutableLiveData<>();

        remoteRepository.getMovieTrailer(movieId, apiKey, new RemoteRepository.LoadMovieTrailerCallback() {
            @Override
            public void onTrailerLoaded(TrailerResponse trailerResponse) {
                if (trailerResponse != null) {
                    movieTrailer.setValue(trailerResponse);
                }
            }

            @Override
            public void onDataNotLoaded() {
                // Do nothing . . .
            }
        });

        return movieTrailer;
    }

    public LiveData<TrailerResponse> getTvShowTrailer(int tvId, String apiKey) {
        final MutableLiveData<TrailerResponse> tvShowTrailer = new MutableLiveData<>();

        remoteRepository.getTvShowTrailer(tvId, apiKey, new RemoteRepository.LoadTvShowTrailerCallback() {
            @Override
            public void onTrailerLoaded(TrailerResponse trailerResponse) {
                if (trailerResponse != null) {
                    tvShowTrailer.setValue(trailerResponse);
                }
            }

            @Override
            public void onDataNotLoaded() {
                // Do nothing . . .
            }
        });

        return tvShowTrailer;
    }

    /*
     *
     * FROM DATABASE (LOCAL)
     *
     * */

    // Inserting movie data to database
    public void insertMovie(Movie movie) {
        localRepository.insertMovie(movie);
    }

    // Inserting tv show data to database
    public void insertTvShow(TvShow tvShow) {
        localRepository.insertTvShow(tvShow);
    }

    // Deleting movie data from database
    public void deleteMovie(Movie movie) {
        localRepository.deleteMovie(movie);
    }

    // Deleting tv show data from database
    public void deleteTvShow(TvShow tvShow) {
        localRepository.deleteTvShow(tvShow);
    }

    // Get favorite movie list from database
    public LiveData<PagedList<Movie>> getAllMovieFavorite() {
        return new LivePagedListBuilder<>(localRepository.getMovieFavorite(), 20).build();
    }

    // Get favorite tv show list from database
    public LiveData<PagedList<TvShow>> getAllTvShowFavorite() {
        return new LivePagedListBuilder<>(localRepository.getTvShowFavorite(), 20).build();
    }
}
