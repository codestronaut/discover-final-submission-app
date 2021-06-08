package com.coder.knight.jetpack.discover.ui.favorite;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;

import com.coder.knight.jetpack.discover.data.source.ContentRepository;
import com.coder.knight.jetpack.discover.data.source.local.room.Movie;
import com.coder.knight.jetpack.discover.data.source.local.room.TvShow;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FavoriteViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private ContentRepository contentRepository = mock(ContentRepository.class);
    private FavoriteViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new FavoriteViewModel(contentRepository);
    }

    @Test
    public void getFavoriteMovie() {
        MutableLiveData<PagedList<Movie>> dummyFavoriteMovie = new MutableLiveData<>();
        // noinspection unchecked
        PagedList<Movie> pagedList = mock(PagedList.class);
        dummyFavoriteMovie.setValue(pagedList);
        when(contentRepository.getAllMovieFavorite()).thenReturn(dummyFavoriteMovie);
        // noinspection unchecked
        Observer<PagedList<Movie>> observer = mock(Observer.class);
        viewModel.getAllFavoriteMovie().observeForever(observer);
        verify(observer).onChanged(pagedList);
    }

    @Test
    public void getFavoriteTvShow() {
        MutableLiveData<PagedList<TvShow>> dummyFavoriteTvShow = new MutableLiveData<>();
        // noinspection unchecked
        PagedList<TvShow> pagedList = mock(PagedList.class);
        dummyFavoriteTvShow.setValue(pagedList);
        when(contentRepository.getAllTvShowFavorite()).thenReturn(dummyFavoriteTvShow);
        // noinspection unchecked
        Observer<PagedList<TvShow>> observer = mock(Observer.class);
        viewModel.getAllFavoriteTvShow().observeForever(observer);
        verify(observer).onChanged(pagedList);
    }
}