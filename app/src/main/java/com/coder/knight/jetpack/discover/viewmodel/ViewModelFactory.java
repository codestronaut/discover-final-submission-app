package com.coder.knight.jetpack.discover.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.coder.knight.jetpack.discover.data.source.ContentRepository;
import com.coder.knight.jetpack.discover.di.Injection;
import com.coder.knight.jetpack.discover.ui.detail.DetailViewModel;
import com.coder.knight.jetpack.discover.ui.favorite.FavoriteViewModel;
import com.coder.knight.jetpack.discover.ui.movie.MovieViewModel;
import com.coder.knight.jetpack.discover.ui.tvshow.TvShowViewModel;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private static volatile ViewModelFactory INSTANCE;

    private final ContentRepository mContentRepository;

    private ViewModelFactory(ContentRepository contentRepository) {
        this.mContentRepository = contentRepository;
    }

    public static ViewModelFactory getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewModelFactory(Injection.providerRepository(application));
                }
            }
        }

        return INSTANCE;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MovieViewModel.class)) {
            // noinspection unchecked
            return (T) new MovieViewModel(mContentRepository);
        } else if (modelClass.isAssignableFrom(TvShowViewModel.class)) {
            // noinspection unchecked
            return (T) new TvShowViewModel(mContentRepository);
        } else if (modelClass.isAssignableFrom(DetailViewModel.class)) {
            // noinspection unchecked
            return (T) new DetailViewModel(mContentRepository);
        } else if (modelClass.isAssignableFrom(FavoriteViewModel.class)) {
            // noinspection unchecked
            return (T) new FavoriteViewModel(mContentRepository);
        }

        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
