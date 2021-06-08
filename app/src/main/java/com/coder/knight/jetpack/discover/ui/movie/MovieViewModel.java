package com.coder.knight.jetpack.discover.ui.movie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.coder.knight.jetpack.discover.BuildConfig;
import com.coder.knight.jetpack.discover.data.source.ContentRepository;
import com.coder.knight.jetpack.discover.data.source.remote.response.MovieResponse;

public class MovieViewModel extends ViewModel {
    private ContentRepository contentRepository;

    public MovieViewModel(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    LiveData<MovieResponse> getMovies() {
        return contentRepository.getMovies(BuildConfig.API_KEY);
    }
}
