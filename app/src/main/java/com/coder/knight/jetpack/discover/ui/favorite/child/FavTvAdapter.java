package com.coder.knight.jetpack.discover.ui.favorite.child;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.RequestOptions;
import com.coder.knight.jetpack.discover.R;
import com.coder.knight.jetpack.discover.data.source.local.room.TvShow;
import com.coder.knight.jetpack.discover.ui.detail.DetailActivity;
import com.coder.knight.jetpack.discover.utils.GlideApp;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class FavTvAdapter extends PagedListAdapter<TvShow, FavTvAdapter.FavoriteViewHolder> {
    private static final String IMG_BASE_URL = "https://image.tmdb.org/t/p/w500";
    private Context context;
    private List<TvShow> tvShows = new ArrayList<>();

    FavTvAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    void setTvShow(List<TvShow> tvShow) {
        this.tvShows = tvShow;
        notifyDataSetChanged();
    }

    private static DiffUtil.ItemCallback<TvShow> DIFF_CALLBACK = new DiffUtil.ItemCallback<TvShow>() {
        @Override
        public boolean areItemsTheSame(@NonNull TvShow oldItem, @NonNull TvShow newItem) {
            return oldItem.getTvTitle().equals(newItem.getTvTitle());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull TvShow oldItem, @NonNull TvShow newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_item_list_view, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        holder.onBind(tvShows.get(position));
        holder.itemView.setOnClickListener(view -> {
            Intent moveIntent = new Intent(context, DetailActivity.class);
            moveIntent.putExtra(DetailActivity.EXTRA_TV, tvShows.get(position).tvId);
            context.startActivity(moveIntent);
        });
    }

    @Override
    public int getItemCount() {
        return tvShows.size();
    }

    class FavoriteViewHolder extends RecyclerView.ViewHolder {
        TvShow tvShow;
        private RoundedImageView imgPoster;
        private TextView tvTitle, tvDate, tvRating, tvPopularity;
        private RatingBar rbRating;

        FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.title_text);
            tvDate = itemView.findViewById(R.id.date_text);
            tvRating = itemView.findViewById(R.id.rating_text);
            tvPopularity = itemView.findViewById(R.id.popularity_text);
            rbRating = itemView.findViewById(R.id.item_rating_bar);
            imgPoster = itemView.findViewById(R.id.poster_image);
        }

        void onBind(TvShow tvShow) {
            this.tvShow = tvShow;
            tvTitle.setText(tvShow.getTvTitle());
            tvDate.setText(tvShow.getTvDate().split("-")[0]);
            tvPopularity.setText(tvShow.getTvPopularity());
            tvRating.setText(String.valueOf(tvShow.getTvRating() / 2));
            rbRating.setRating((float) (tvShow.getTvRating() / 2));
            GlideApp.with(context)
                    .load(IMG_BASE_URL + tvShow.getTvPoster())
                    .apply(new RequestOptions().override(1920, 1080))
                    .into(imgPoster);
        }
    }
}
