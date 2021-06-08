package com.coder.knight.jetpack.discover.ui.movie;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.RequestOptions;
import com.coder.knight.jetpack.discover.R;
import com.coder.knight.jetpack.discover.data.source.local.entity.MovieEntity;
import com.coder.knight.jetpack.discover.ui.detail.DetailActivity;
import com.coder.knight.jetpack.discover.utils.GlideApp;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private static final String IMG_BASE_URL = "https://image.tmdb.org/t/p/w500";
    private Context context;
    private ArrayList<MovieEntity> movieEntities = new ArrayList<>();

    MovieAdapter(Context context) {
        this.context = context;
    }

    void setMovieList(ArrayList<MovieEntity> movieList) {
        if (movieList == null) return;
        this.movieEntities.clear();
        this.movieEntities.addAll(movieList);
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_item_list_view, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.onBind(movieEntities.get(position));
        holder.itemView.setOnClickListener(view -> {
            Intent sendIntent = new Intent(context, DetailActivity.class);
            sendIntent.putExtra(DetailActivity.EXTRA_MOVIE, movieEntities.get(position).getMovieId());
            context.startActivity(sendIntent);
        });
    }

    @Override
    public int getItemCount() {
        return movieEntities.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        MovieEntity movieEntity;
        private RoundedImageView imgPoster;
        private TextView tvTitle, tvDate, tvRating, tvPopularity;
        private RatingBar rbRating;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.title_text);
            tvDate = itemView.findViewById(R.id.date_text);
            tvRating = itemView.findViewById(R.id.rating_text);
            tvPopularity = itemView.findViewById(R.id.popularity_text);
            rbRating = itemView.findViewById(R.id.item_rating_bar);
            imgPoster = itemView.findViewById(R.id.poster_image);
        }

        void onBind(MovieEntity movie) {
            this.movieEntity = movie;
            tvTitle.setText(movie.getMovieTitle());
            tvDate.setText(movie.getMovieDate().split("-")[0]);
            tvRating.setText(String.valueOf(movie.getMovieRating()));
            tvPopularity.setText(movie.getMoviePopularity());
            rbRating.setRating((float) (movie.getMovieRating() / 2));
            GlideApp.with(context)
                    .load(IMG_BASE_URL + movie.getMoviePoster())
                    .apply(new RequestOptions().override(1920, 1080))
                    .into(imgPoster);
        }
    }
}
