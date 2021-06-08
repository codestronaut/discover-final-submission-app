package com.coder.knight.jetpack.discover.ui.movie;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.coder.knight.jetpack.discover.BuildConfig;
import com.coder.knight.jetpack.discover.data.source.ContentRepository;
import com.coder.knight.jetpack.discover.data.source.remote.response.MovieResponse;
import com.coder.knight.jetpack.discover.utils.DummyData;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MovieViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private MovieViewModel movieViewModel;
    private ContentRepository contentRepository = mock(ContentRepository.class);

    @Before
    public void setUp() {
        movieViewModel = new MovieViewModel(contentRepository);
    }

    @Test
    public void getMovieList() {
        MovieResponse dummyMovie = DummyData.generateDummyMovie();
        MutableLiveData<MovieResponse> movies = new MutableLiveData<>();
        movies.setValue(dummyMovie);

        when(contentRepository.getMovies(BuildConfig.API_KEY)).thenReturn(movies);
        // noinspection unchecked
        Observer<MovieResponse> observer = mock(Observer.class);
        movieViewModel.getMovies().observeForever(observer);
        verify(observer).onChanged(dummyMovie);
    }
}