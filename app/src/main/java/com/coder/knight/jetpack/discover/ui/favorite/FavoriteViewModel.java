package com.coder.knight.jetpack.discover.ui.favorite;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.coder.knight.jetpack.discover.data.source.ContentRepository;
import com.coder.knight.jetpack.discover.data.source.local.room.Movie;
import com.coder.knight.jetpack.discover.data.source.local.room.TvShow;

public class FavoriteViewModel extends ViewModel {
    private ContentRepository contentRepository;

    public FavoriteViewModel(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    public void insertMovie(Movie movie) {
        contentRepository.insertMovie(movie);
    }

    public void deleteMovie(Movie movie) {
        contentRepository.deleteMovie(movie);
    }

    public LiveData<PagedList<Movie>> getAllFavoriteMovie() {
        return contentRepository.getAllMovieFavorite();
    }

    public void insertTvShow(TvShow tvShow) {
        contentRepository.insertTvShow(tvShow);
    }

    public void deleteTvShow(TvShow tvShow) {
        contentRepository.deleteTvShow(tvShow);
    }

    public LiveData<PagedList<TvShow>> getAllFavoriteTvShow() {
        return contentRepository.getAllTvShowFavorite();
    }
}
