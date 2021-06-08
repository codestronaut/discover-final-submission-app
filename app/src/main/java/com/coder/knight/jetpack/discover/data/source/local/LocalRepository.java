package com.coder.knight.jetpack.discover.data.source.local;

import android.app.Application;
import android.os.AsyncTask;

import androidx.paging.DataSource;

import com.coder.knight.jetpack.discover.data.source.local.room.ContentDao;
import com.coder.knight.jetpack.discover.data.source.local.room.ContentDatabase;
import com.coder.knight.jetpack.discover.data.source.local.room.Movie;
import com.coder.knight.jetpack.discover.data.source.local.room.TvShow;

public class LocalRepository {
    private static LocalRepository INSTANCE;
    private final ContentDao contentDao;

    private LocalRepository(Application application) {
        ContentDatabase contentDatabase = ContentDatabase.getInstance(application);
        contentDao = contentDatabase.contentDao();
    }

    public static LocalRepository getInstance(Application application) {
        if (INSTANCE == null) {
            INSTANCE = new LocalRepository(application);
        }

        return INSTANCE;
    }

    public void insertMovie(Movie movie) {
        new InsertMoviesAsyncTask(contentDao).execute(movie);
    }

    public void deleteMovie(Movie movie) {
        new DeleteMovieAsyncTask(contentDao).execute(movie);
    }

    public void insertTvShow(TvShow tvShow) {
        new InsertTvShowsAsyncTask(contentDao).execute(tvShow);
    }

    public void deleteTvShow(TvShow tvShow) {
        new DeleteTvShowAsyncTask(contentDao).execute(tvShow);
    }

    public DataSource.Factory<Integer, Movie> getMovieFavorite() {
        return contentDao.getAllMovies();
    }

    public DataSource.Factory<Integer, TvShow> getTvShowFavorite() {
        return contentDao.getAllTvShows();
    }

    /*
     * Inserting and Deleting AsyncTask (Background worker)
     * */
    private static class InsertMoviesAsyncTask extends AsyncTask<Movie, Void, Void> {
        private ContentDao contentDao;

        private InsertMoviesAsyncTask(ContentDao contentDao) {
            this.contentDao = contentDao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            contentDao.insertMovie(movies[0]);
            return null;
        }
    }

    private static class DeleteMovieAsyncTask extends AsyncTask<Movie, Void, Void> {
        private ContentDao contentDao;

        private DeleteMovieAsyncTask(ContentDao contentDao) {
            this.contentDao = contentDao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            contentDao.deleteMovie(movies[0]);
            return null;
        }
    }

    private static class InsertTvShowsAsyncTask extends AsyncTask<TvShow, Void, Void> {
        private ContentDao contentDao;

        private InsertTvShowsAsyncTask(ContentDao contentDao) {
            this.contentDao = contentDao;
        }

        @Override
        protected Void doInBackground(TvShow... tvShows) {
            contentDao.insertTvShow(tvShows[0]);
            return null;
        }
    }

    private static class DeleteTvShowAsyncTask extends AsyncTask<TvShow, Void, Void> {
        private ContentDao contentDao;

        private DeleteTvShowAsyncTask(ContentDao contentDao) {
            this.contentDao = contentDao;
        }

        @Override
        protected Void doInBackground(TvShow... tvShows) {
            contentDao.deleteTvShow(tvShows[0]);
            return null;
        }
    }
}

