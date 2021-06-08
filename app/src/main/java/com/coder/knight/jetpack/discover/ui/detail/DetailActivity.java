package com.coder.knight.jetpack.discover.ui.detail;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.coder.knight.jetpack.discover.R;
import com.coder.knight.jetpack.discover.data.source.local.entity.GenreEntity;
import com.coder.knight.jetpack.discover.data.source.local.entity.MovieEntity;
import com.coder.knight.jetpack.discover.data.source.local.entity.TrailerEntity;
import com.coder.knight.jetpack.discover.data.source.local.entity.TvShowEntity;
import com.coder.knight.jetpack.discover.data.source.local.room.ContentDatabase;
import com.coder.knight.jetpack.discover.data.source.local.room.Movie;
import com.coder.knight.jetpack.discover.data.source.local.room.TvShow;
import com.coder.knight.jetpack.discover.ui.favorite.FavoriteViewModel;
import com.coder.knight.jetpack.discover.utils.EspressoIdlingResource;
import com.coder.knight.jetpack.discover.utils.GlideApp;
import com.coder.knight.jetpack.discover.viewmodel.ViewModelFactory;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private static final String IMG_BASE_URL = "https://image.tmdb.org/t/p/w500";
    private ImageView imgBackdrop;
    private RoundedImageView imgPoster;
    private TextView tvTitle, tvDate, tvRating, tvLang, tvPopularity, tvVoted, tvOverview, tvGenre;
    private RatingBar detailRatingBar;
    private RecyclerView rvTrailer;
    private TrailerAdapter trailerAdapter;
    private MaterialFavoriteButton favoriteButton;
    private CoordinatorLayout container;
    private ProgressBar progressBar;

    private int movieId;
    private int tvShowId; // will be added later
    public static final String EXTRA_MOVIE = "extra_movie";
    public static final String EXTRA_TV = "extra_tv";

    /* FOR DATABASE */
    private FavoriteViewModel favoriteViewModel;
    List<Movie> movies = new ArrayList<>();
    private String movieTitle, movieDate, moviePopularity, moviePoster;
    private double movieRating;

    List<TvShow> tvShows = new ArrayList<>();
    private String tvShowTitle, tvShowDate, tvShowPopularity, tvShowPoster;
    private double tvShowRating;

    // tv show will be add later

    private DetailViewModel detailViewModel;
    private Toolbar detailToolbar;
    private CollapsingToolbarLayout detailCollapseToolbar;

    private static DetailViewModel obtainViewModel(AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(DetailViewModel.class);
    }

    private static FavoriteViewModel obtainFavoriteViewModel(AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(FavoriteViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        /*
         * Set Up
         * */
        initViews();
        setSupportActionBar(detailToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow);
        }

        detailCollapseToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.colorText));
        detailCollapseToolbar.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        /*
         * Observe data from ViewModel
         * */
        detailViewModel = obtainViewModel(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            movieId = bundle.getInt(EXTRA_MOVIE);
            if (movieId != 0) {
                detailViewModel.setContentId(movieId);
            }

            tvShowId = bundle.getInt(EXTRA_TV);
            if (tvShowId != 0) {
                detailViewModel.setContentId(tvShowId);
            }
        }

        // Load
        setUpLoadingBar();
        favoriteViewModel = obtainFavoriteViewModel(this);
        progressBar.setVisibility(View.VISIBLE);
        container.setVisibility(View.GONE);
        loadMovieDetail();
        loadTvShowDetail();
    }

    private void initViews() {
        container = findViewById(R.id.detail_container);
        progressBar = findViewById(R.id.detail_loading_bar);
        detailToolbar = findViewById(R.id.detail_toolbar);
        detailCollapseToolbar = findViewById(R.id.detail_collapse_toolbar);
        tvTitle = findViewById(R.id.detail_title_text);
        tvDate = findViewById(R.id.detail_date_text);
        tvRating = findViewById(R.id.detail_rating_text);
        tvLang = findViewById(R.id.detail_lang_text);
        tvPopularity = findViewById(R.id.detail_popularity_text);
        tvVoted = findViewById(R.id.detail_voted_text);
        tvOverview = findViewById(R.id.detail_overview_text);
        tvGenre = findViewById(R.id.detail_genre_text);
        detailRatingBar = findViewById(R.id.detail_rating_bar);
        imgPoster = findViewById(R.id.detail_item_poster);
        imgBackdrop = findViewById(R.id.detail_item_backdrop);
        rvTrailer = findViewById(R.id.rv_trailer);
        favoriteButton = findViewById(R.id.favorite_button);
    }

    private void loadMovieDetail() {
        EspressoIdlingResource.increment();
        detailViewModel.getMovieDetail().observe(this, movieEntity -> {
            if (movieEntity != null) {
                progressBar.setVisibility(View.GONE);
                container.setVisibility(View.VISIBLE);
                EspressoIdlingResource.decrement();
                populateMovie(movieEntity);
                getMovieTrailer();
                checkMovieStatus(movieId);
            }
        });
    }

    private void loadTvShowDetail() {
        detailViewModel.getTvShowDetail().observe(this, tvShowEntity -> {
            if (tvShowEntity != null) {
                progressBar.setVisibility(View.GONE);
                container.setVisibility(View.VISIBLE);
                populateTvShow(tvShowEntity);
                getTvShowTrailer();
                checkTvShowStatus(tvShowId);
            }
        });
    }

    private void populateMovie(MovieEntity movie) {
        /* To Movie Database*/
        movieTitle = movie.getMovieTitle();
        movieDate = movie.getMovieDate();
        moviePopularity = movie.getMoviePopularity();
        moviePoster = movie.getMoviePoster();
        movieRating = movie.getMovieRating();

        /* To The Views */
        detailCollapseToolbar.setTitle(movie.getMovieTitle());
        tvTitle.setText(movie.getMovieTitle());
        tvDate.setText(movie.getMovieDate().split("-")[0]);
        tvRating.setText(String.valueOf(movie.getMovieRating() / 2));
        tvLang.setText(movie.getMovieLanguage());
        tvPopularity.setText(movie.getMoviePopularity());
        tvVoted.setText(String.valueOf(movie.getMovieVoted()));
        tvOverview.setText(movie.getMovieDescription());
        getMovieGenre(movie);
        detailRatingBar.setRating((float) (movie.getMovieRating() / 2));
        GlideApp.with(this)
                .load(IMG_BASE_URL + movie.getMoviePoster())
                .apply(new RequestOptions().override(1920, 1080))
                .into(imgPoster);
        GlideApp.with(this)
                .load(IMG_BASE_URL + movie.getMovieBackdrop())
                .apply(new RequestOptions().override(1920, 1080))
                .into(imgBackdrop);
    }

    private void populateTvShow(TvShowEntity tvShow) {
        /* To Tv Show Database*/
        tvShowTitle = tvShow.getTvTitle();
        tvShowDate = tvShow.getTvDate();
        tvShowPopularity = tvShow.getTvPopularity();
        tvShowPoster = tvShow.getTvPoster();
        tvShowRating = tvShow.getTvRating();

        /* To The Views */
        detailCollapseToolbar.setTitle(tvShow.getTvTitle());
        tvTitle.setText(tvShow.getTvTitle());
        tvDate.setText(tvShow.getTvDate().split("-")[0]);
        tvRating.setText(String.valueOf(tvShow.getTvRating() / 2));
        tvLang.setText(tvShow.getTvLanguage());
        tvPopularity.setText(tvShow.getTvPopularity());
        tvVoted.setText(String.valueOf(tvShow.getTvVoted()));
        tvOverview.setText(tvShow.getTvDescription());
        getTvShowGenre(tvShow);
        detailRatingBar.setRating((float) (tvShow.getTvRating() / 2));
        GlideApp.with(this)
                .load(IMG_BASE_URL + tvShow.getTvPoster())
                .apply(new RequestOptions().override(1920, 1080))
                .into(imgPoster);
        GlideApp.with(this)
                .load(IMG_BASE_URL + tvShow.getTvBackdrop())
                .apply(new RequestOptions().override(1920, 1080))
                .into(imgBackdrop);
    }

    /*
     * Get Genres
     * */
    private void getMovieGenre(final MovieEntity movie) {
        if (movie.getGenres() != null) {
            List<String> currentGenres = new ArrayList<>();
            for (GenreEntity genre : movie.getGenres()) {
                currentGenres.add(genre.getGenreName());
            }
            tvGenre.setText(TextUtils.join(", ", currentGenres));
        }
    }

    private void getTvShowGenre(final TvShowEntity tvShow) {
        if (tvShow.getGenres() != null) {
            List<String> currentGenres = new ArrayList<>();
            for (GenreEntity genre : tvShow.getGenres()) {
                currentGenres.add(genre.getGenreName());
            }
            tvGenre.setText(TextUtils.join(", ", currentGenres));
        }
    }

    /*
     * Get Trailers
     * */
    private void getMovieTrailer() {
        EspressoIdlingResource.increment();
        detailViewModel.getMovieTrailer().observe(this, trailerResponse -> {
            EspressoIdlingResource.decrement();
            List<TrailerEntity> currentTrailers = trailerResponse.getTrailers();
            trailerAdapter.setTrailer(currentTrailers);
            trailerAdapter.notifyDataSetChanged();
        });

        setUpRvTrailer();
    }

    private void getTvShowTrailer() {
        detailViewModel.getTvShowTrailer().observe(this, trailerResponse -> {
            List<TrailerEntity> currentTrailers = trailerResponse.getTrailers();
            trailerAdapter.setTrailer(currentTrailers);
            trailerAdapter.notifyDataSetChanged();
        });

        setUpRvTrailer();
    }

    private void setUpRvTrailer() {
        trailerAdapter = new TrailerAdapter(this);
        rvTrailer.setHasFixedSize(true);
        rvTrailer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvTrailer.setAdapter(trailerAdapter);
    }

    private void setUpLoadingBar() {
        Sprite fadingCircle = new FadingCircle();
        progressBar.setIndeterminateDrawable(fadingCircle);
    }

    /*
     * Database (Favorite Movie)
     * */

    private void insertMovieToDatabase() {
        Movie movie = new Movie();
        movie.setMovieId(movieId);
        movie.setMovieTitle(movieTitle);
        movie.setMovieDate(movieDate);
        movie.setMoviePopularity(moviePopularity);
        movie.setMoviePoster(moviePoster);
        movie.setMovieRating(movieRating);

        favoriteViewModel.insertMovie(movie);
    }

    private void deleteMovieFromDatabase() {
        Movie movie = new Movie();
        movie.setMovieId(movieId);

        favoriteViewModel.deleteMovie(movie);
    }

    @SuppressLint("StaticFieldLeak")
    private void checkMovieStatus(final int movieId) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                movies = ContentDatabase.getInstance(getApplicationContext()).contentDao().loadAllMovie(movieId);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (movies.size() > 0) {
                    favoriteButton.setFavorite(true);
                    favoriteButton.setOnFavoriteChangeListener((buttonView, favorite) -> {
                        if (favorite) {
                            insertMovieToDatabase();
                            Snackbar.make(buttonView, getString(R.string.movie_added_info), Snackbar.LENGTH_SHORT).show();
                        } else {
                            deleteMovieFromDatabase();
                            Snackbar.make(buttonView, getString(R.string.movie_removed_info), Snackbar.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    favoriteButton.setOnFavoriteChangeListener((buttonView, favorite) -> {
                        if (favorite) {
                            insertMovieToDatabase();
                            Snackbar.make(buttonView, getString(R.string.movie_added_info), Snackbar.LENGTH_SHORT).show();
                        } else {
                            deleteMovieFromDatabase();
                            Snackbar.make(buttonView, getString(R.string.movie_removed_info), Snackbar.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }.execute();
    }

    private void insertTvToDatabase() {
        TvShow tvShow = new TvShow();
        tvShow.setTvId(tvShowId);
        tvShow.setTvTitle(tvShowTitle);
        tvShow.setTvDate(tvShowDate);
        tvShow.setTvPopularity(tvShowPopularity);
        tvShow.setTvPoster(tvShowPoster);
        tvShow.setTvRating(tvShowRating);

        favoriteViewModel.insertTvShow(tvShow);
    }

    private void deleteTvFromDatabase() {
        TvShow tvShow = new TvShow();
        tvShow.setTvId(tvShowId);

        favoriteViewModel.deleteTvShow(tvShow);
    }

    @SuppressLint("StaticFieldLeak")
    private void checkTvShowStatus(final int tvId) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                tvShows = ContentDatabase.getInstance(getApplicationContext()).contentDao().loadAllTv(tvId);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (tvShows.size() > 0) {
                    favoriteButton.setFavorite(true);
                    favoriteButton.setOnFavoriteChangeListener((buttonView, favorite) -> {
                        if (favorite) {
                            insertTvToDatabase();
                            Snackbar.make(buttonView, getString(R.string.tv_added_info), Snackbar.LENGTH_SHORT).show();
                        } else {
                            deleteTvFromDatabase();
                            Snackbar.make(buttonView, getString(R.string.tv_removed_info), Snackbar.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    favoriteButton.setOnFavoriteChangeListener((buttonView, favorite) -> {
                        if (favorite) {
                            insertTvToDatabase();
                            Snackbar.make(buttonView, getString(R.string.tv_added_info), Snackbar.LENGTH_SHORT).show();
                        } else {
                            deleteTvFromDatabase();
                            Snackbar.make(buttonView, getString(R.string.tv_removed_info), Snackbar.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }.execute();
    }
}
