package com.coder.knight.jetpack.discover.ui.detail;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.coder.knight.jetpack.discover.BuildConfig;
import com.coder.knight.jetpack.discover.data.source.ContentRepository;
import com.coder.knight.jetpack.discover.data.source.local.entity.MovieEntity;
import com.coder.knight.jetpack.discover.data.source.remote.response.MovieResponse;
import com.coder.knight.jetpack.discover.data.source.remote.response.TrailerResponse;
import com.coder.knight.jetpack.discover.utils.DummyData;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DetailViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private DetailViewModel detailViewModel;
    private ContentRepository contentRepository = mock(ContentRepository.class);
    private MovieResponse movieResponse = DummyData.generateDummyMovie();
    private MovieEntity movieEntity = movieResponse.getMovieList().get(0);
    private int movieId = movieEntity.getMovieId();
    private TrailerResponse trailerResponse = DummyData.generateTrailer();

    @Before
    public void setUp() {
        detailViewModel = new DetailViewModel(contentRepository);
        detailViewModel.setContentId(movieId);
    }

    @Test
    public void getDetailMovie() {
        MutableLiveData<MovieEntity> movieList = new MutableLiveData<>();
        movieList.setValue(movieEntity);

        when(contentRepository.getMovieDetail(movieId, BuildConfig.API_KEY)).thenReturn(movieList);
        // noinspection unchecked
        Observer<MovieEntity> observer = mock(Observer.class);
        detailViewModel.getMovieDetail().observeForever(observer);
        verify(observer).onChanged(movieEntity);
    }

    @Test
    public void getMovieTrailer() {
        MutableLiveData<TrailerResponse> trailerList = new MutableLiveData<>();
        trailerList.setValue(trailerResponse);

        when(contentRepository.getMovieTrailer(movieId, BuildConfig.API_KEY)).thenReturn(trailerList);
        // noinspection unchecked
        Observer<TrailerResponse> observer = mock(Observer.class);
        detailViewModel.getMovieTrailer().observeForever(observer);
        verify(observer).onChanged(trailerResponse);
    }
}