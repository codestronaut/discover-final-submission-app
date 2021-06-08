package com.coder.knight.jetpack.discover.data.source;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.paging.DataSource;
import androidx.paging.PagedList;

import com.coder.knight.jetpack.discover.BuildConfig;
import com.coder.knight.jetpack.discover.data.FakeContentRepository;
import com.coder.knight.jetpack.discover.data.source.local.LocalRepository;
import com.coder.knight.jetpack.discover.data.source.local.entity.MovieEntity;
import com.coder.knight.jetpack.discover.data.source.local.entity.TvShowEntity;
import com.coder.knight.jetpack.discover.data.source.local.room.Movie;
import com.coder.knight.jetpack.discover.data.source.local.room.TvShow;
import com.coder.knight.jetpack.discover.data.source.remote.RemoteRepository;
import com.coder.knight.jetpack.discover.data.source.remote.response.MovieResponse;
import com.coder.knight.jetpack.discover.data.source.remote.response.TrailerResponse;
import com.coder.knight.jetpack.discover.data.source.remote.response.TvShowResponse;
import com.coder.knight.jetpack.discover.utils.DummyData;
import com.coder.knight.jetpack.discover.utils.LiveDataTestUtils;
import com.coder.knight.jetpack.discover.utils.PagedListUtil;

import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ContentRepositoryTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private RemoteRepository remoteRepository = mock(RemoteRepository.class);
    private LocalRepository localRepository = mock(LocalRepository.class);
    private FakeContentRepository contentRepository = new FakeContentRepository(remoteRepository, localRepository);
    private MovieResponse movieResponse = DummyData.generateDummyMovie();
    private int movieId = movieResponse.getMovieList().get(0).getMovieId();
    private MovieEntity movieEntity = DummyData.generateMovieById(movieId);
    private TvShowResponse tvShowResponse = DummyData.generateDummyTvShow();
    private int tvShowId = tvShowResponse.getTvShowList().get(0).getTvId();
    private TvShowEntity tvShowEntity = DummyData.generateTvShowById(tvShowId);
    private TrailerResponse trailerResponse = DummyData.generateTrailer();
    private List<Movie> movieList = DummyData.generateFavoriteMovie();
    private List<TvShow> tvShowList = DummyData.generateFavoriteTvShow();

    /*
     * Get Movie List
     * */
    @Test
    public void getMovieList() {
        doAnswer(invocation -> {
            ((RemoteRepository.LoadMovieCallback) invocation.getArguments()[1])
                    .onAllMoviesReceived(movieResponse);
            return null;
        }).when(remoteRepository).getMovieList(eq(BuildConfig.API_KEY), any(RemoteRepository.LoadMovieCallback.class));

        MovieResponse response = LiveDataTestUtils.getValue(contentRepository.getMovies(BuildConfig.API_KEY));
        verify(remoteRepository, times(1)).getMovieList(eq(BuildConfig.API_KEY),
                any(RemoteRepository.LoadMovieCallback.class));
        assertNotNull(response);
        assertEquals(movieResponse.getMovieList().size(), response.getMovieList().size());
    }

    /*
     * Get Movie Detail
     * */
    @Test
    public void getMovieDetail() {
        doAnswer(invocation -> {
            ((RemoteRepository.LoadMovieDetailCallback) invocation.getArguments()[2])
                    .onMovieLoaded(movieEntity);
            return null;
        }).when(remoteRepository).getMovieDetail(eq(movieId), eq(BuildConfig.API_KEY), any(RemoteRepository.LoadMovieDetailCallback.class));

        MovieEntity resultMovie = LiveDataTestUtils.getValue(contentRepository.getMovieDetail(movieId, BuildConfig.API_KEY));
        verify(remoteRepository, times(1))
                .getMovieDetail(eq(movieId), eq(BuildConfig.API_KEY), any(RemoteRepository.LoadMovieDetailCallback.class));
        assertNotNull(resultMovie);
        assertEquals(movieEntity.getMovieTitle(), resultMovie.getMovieTitle());
    }

    /*
     * Get Tv Show List
     * */
    @Test
    public void getTvShowList() {
        doAnswer(invocation -> {
            ((RemoteRepository.LoadTvShowCallback) invocation.getArguments()[1])
                    .onAllTvShowReceived(tvShowResponse);
            return null;
        }).when(remoteRepository).getTvShowList(eq(BuildConfig.API_KEY), any(RemoteRepository.LoadTvShowCallback.class));

        TvShowResponse response = LiveDataTestUtils.getValue(contentRepository.getTvShows(BuildConfig.API_KEY));
        verify(remoteRepository, times(1))
                .getTvShowList(eq(BuildConfig.API_KEY), any(RemoteRepository.LoadTvShowCallback.class));
        assertNotNull(response);
        assertEquals(movieResponse.getMovieList().size(), response.getTvShowList().size());
    }

    /*
     * Get Tv Show Detail
     * */
    @Test
    public void getTvShowDetail() {
        doAnswer(invocation -> {
            ((RemoteRepository.LoadTvShowDetailCallback) invocation.getArguments()[2])
                    .onTvShowLoaded(tvShowEntity);
            return null;
        }).when(remoteRepository).getTvShowDetail(eq(tvShowId), eq(BuildConfig.API_KEY), any(RemoteRepository.LoadTvShowDetailCallback.class));

        TvShowEntity resultTvShow = LiveDataTestUtils.getValue(contentRepository.getTvShowDetail(tvShowId, BuildConfig.API_KEY));
        verify(remoteRepository, times(1))
                .getTvShowDetail(eq(tvShowId), eq(BuildConfig.API_KEY), any(RemoteRepository.LoadTvShowDetailCallback.class));
        assertNotNull(resultTvShow);
        assertEquals(tvShowEntity.getTvTitle(), resultTvShow.getTvTitle());
    }

    /*
     * Get Content Trailer
     * */
    @Test
    public void getMovieTrailers() {
        doAnswer(invocation -> {
            ((RemoteRepository.LoadMovieTrailerCallback) invocation.getArguments()[2])
                    .onTrailerLoaded(trailerResponse);
            return null;
        }).when(remoteRepository).getMovieTrailer(eq(movieId), eq(BuildConfig.API_KEY), any(RemoteRepository.LoadMovieTrailerCallback.class));

        TrailerResponse response = LiveDataTestUtils.getValue(contentRepository.getMovieTrailer(movieId, BuildConfig.API_KEY));
        verify(remoteRepository, times(1))
                .getMovieTrailer(eq(movieId), eq(BuildConfig.API_KEY), any(RemoteRepository.LoadMovieTrailerCallback.class));
        assertNotNull(response);
        assertEquals(trailerResponse.getTrailers().get(0).getKey(), response.getTrailers().get(0).getKey());
    }

    @Test
    public void getTvShowTrailers() {
        doAnswer(invocation -> {
            ((RemoteRepository.LoadTvShowTrailerCallback) invocation.getArguments()[2])
                    .onTrailerLoaded(trailerResponse);
            return null;
        }).when(remoteRepository).getTvShowTrailer(eq(tvShowId), eq(BuildConfig.API_KEY), any(RemoteRepository.LoadTvShowTrailerCallback.class));

        TrailerResponse tvTrailer = LiveDataTestUtils.getValue(contentRepository.getTvShowTrailer(tvShowId, BuildConfig.API_KEY));
        verify(remoteRepository, times(1))
                .getTvShowTrailer(eq(tvShowId), eq(BuildConfig.API_KEY), any(RemoteRepository.LoadTvShowTrailerCallback.class));
        assertNotNull(tvTrailer);
        assertEquals(trailerResponse.getTrailers().get(0).getKey(), tvTrailer.getTrailers().get(0).getKey());
    }

    /*
     * Database read write test
     * To verify that our database is actually working
     * */

    @Test
    public void insertMovieToDb() {
        // adding some dummy data to Movie Entity
        Movie movie = movieList.get(0);
        contentRepository.insertMovie(movie);

        // verify that movie has been added to database
        verify(localRepository).insertMovie(movie);

        // check if movie not a null value
        // it mean that movie has successfully added to db
        assertNotNull(movie);
        // check movie id
        assertEquals(movie.getMovieId(), movieId);
    }

    @Test
    public void deleteMovieFromDb() {
        // Init movie, take from dummy data
        Movie movie = new Movie();
        movie.setMovieId(movieId);
        // deleted by id
        contentRepository.deleteMovie(movie);

        // verify that movie has been deleted
        verify(localRepository).deleteMovie(movie);

        // check if movie title is null
        // it mean that movie success deleted
        assertNull(movie.getMovieTitle());
    }

    @Test
    public void insertTvShowToDb() {
        // adding some dummy data to Tv Entity
        TvShow tvShow = tvShowList.get(0);
        contentRepository.insertTvShow(tvShow);

        // verify that tv show has been added to database
        verify(localRepository).insertTvShow(tvShow);

        // check if tv show not a null value
        // it mean that tv show has successfully added to db
        assertNotNull(tvShow);

        // check tv show id
        assertEquals(tvShow.getTvId(), tvShowId);
    }

    @Test
    public void deleteTvShowFromDb() {
        // Init tv show
        TvShow tvShow = new TvShow();
        tvShow.setTvId(tvShowId);
        // deleted by id
        contentRepository.deleteTvShow(tvShow);

        // verify that tv show has been deleted
        verify(localRepository).deleteTvShow(tvShow);

        // check if tv show title is null
        // it mean that tv show success deleted
        assertNull(tvShow.getTvTitle());
    }

    @Test
    public void getFavoriteMovieList() {
        // noinspection unchecked
        DataSource.Factory<Integer, Movie> dataSourceFactory = mock(DataSource.Factory.class);
        when(localRepository.getMovieFavorite()).thenReturn(dataSourceFactory);
        contentRepository.getAllMovieFavorite();
        // noinspection unchecked
        PagedList<Movie> movies = PagedListUtil.mockPagedList(movieList);

        verify(localRepository).getMovieFavorite();
        assertNotNull(movies);
        assertEquals(movieList.size(), movies.size());
    }

    @Test
    public void getFavoriteTvShowList() {
        // noinspection unchecked
        DataSource.Factory<Integer, TvShow> dataSourceFactory = mock(DataSource.Factory.class);
        when(localRepository.getTvShowFavorite()).thenReturn(dataSourceFactory);
        contentRepository.getAllTvShowFavorite();
        // noinspection unchecked
        PagedList<TvShow> tvShows = PagedListUtil.mockPagedList(tvShowList);

        verify(localRepository).getTvShowFavorite();
        assertNotNull(tvShows);
        assertEquals(tvShowList.size(), tvShows.size());
    }
}