package com.coder.knight.jetpack.discover.ui.tvshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.coder.knight.jetpack.discover.BuildConfig;
import com.coder.knight.jetpack.discover.data.source.ContentRepository;
import com.coder.knight.jetpack.discover.data.source.remote.response.TvShowResponse;

public class TvShowViewModel extends ViewModel {
    private ContentRepository contentRepository;

    public TvShowViewModel(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    LiveData<TvShowResponse> getTvShow() {
        return contentRepository.getTvShows(BuildConfig.API_KEY);
    }
}
