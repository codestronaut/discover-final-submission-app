package com.coder.knight.jetpack.discover.ui.tvshow;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.coder.knight.jetpack.discover.BuildConfig;
import com.coder.knight.jetpack.discover.data.source.ContentRepository;
import com.coder.knight.jetpack.discover.data.source.remote.response.TvShowResponse;
import com.coder.knight.jetpack.discover.utils.DummyData;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TvShowViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private TvShowViewModel tvShowViewModel;
    private ContentRepository contentRepository = mock(ContentRepository.class);

    @Before
    public void setUp() {
        tvShowViewModel = new TvShowViewModel(contentRepository);
    }

    @Test
    public void getTvShowList() {
        TvShowResponse dummyTvShow = DummyData.generateDummyTvShow();
        MutableLiveData<TvShowResponse> tvShows = new MutableLiveData<>();
        tvShows.setValue(dummyTvShow);

        when(contentRepository.getTvShows(BuildConfig.API_KEY)).thenReturn(tvShows);
        // noinspection unchecked
        Observer<TvShowResponse> observer = mock(Observer.class);
        tvShowViewModel.getTvShow().observeForever(observer);
        verify(observer).onChanged(dummyTvShow);
    }
}