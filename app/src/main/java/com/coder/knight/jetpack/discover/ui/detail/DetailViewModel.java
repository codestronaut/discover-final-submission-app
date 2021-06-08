package com.coder.knight.jetpack.discover.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.coder.knight.jetpack.discover.BuildConfig;
import com.coder.knight.jetpack.discover.data.source.ContentRepository;
import com.coder.knight.jetpack.discover.data.source.local.entity.MovieEntity;
import com.coder.knight.jetpack.discover.data.source.local.entity.TvShowEntity;
import com.coder.knight.jetpack.discover.data.source.remote.response.TrailerResponse;

public class DetailViewModel extends ViewModel {
    private ContentRepository contentRepository;
    private int contentId;

    public DetailViewModel(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    void setContentId(int id) {
        this.contentId = id;
    }

    LiveData<MovieEntity> getMovieDetail() {
        return contentRepository.getMovieDetail(contentId, BuildConfig.API_KEY);
    }

    LiveData<TvShowEntity> getTvShowDetail() {
        return contentRepository.getTvShowDetail(contentId, BuildConfig.API_KEY);
    }

    LiveData<TrailerResponse> getMovieTrailer() {
        return contentRepository.getMovieTrailer(contentId, BuildConfig.API_KEY);
    }

    LiveData<TrailerResponse> getTvShowTrailer() {
        return contentRepository.getTvShowTrailer(contentId, BuildConfig.API_KEY);
    }


}
