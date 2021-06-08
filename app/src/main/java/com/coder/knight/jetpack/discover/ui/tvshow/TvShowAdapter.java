package com.coder.knight.jetpack.discover.ui.tvshow;

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
import com.coder.knight.jetpack.discover.data.source.local.entity.TvShowEntity;
import com.coder.knight.jetpack.discover.ui.detail.DetailActivity;
import com.coder.knight.jetpack.discover.utils.GlideApp;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvViewHolder> {
    private static final String IMG_BASE_URL = "https://image.tmdb.org/t/p/w500";
    private Context context;
    private ArrayList<TvShowEntity> tvShowEntities = new ArrayList<>();

    TvShowAdapter(Context context) {
        this.context = context;
    }

    void setTvShowList(ArrayList<TvShowEntity> tvShowList) {
        if (tvShowList == null) return;
        this.tvShowEntities.clear();
        this.tvShowEntities.addAll(tvShowList);
    }

    @NonNull
    @Override
    public TvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_item_list_view, parent, false);
        return new TvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvViewHolder holder, int position) {
        holder.onBind(tvShowEntities.get(position));
        holder.itemView.setOnClickListener(view -> {
            Intent sendIntent = new Intent(context, DetailActivity.class);
            sendIntent.putExtra(DetailActivity.EXTRA_TV, tvShowEntities.get(position).getTvId());
            context.startActivity(sendIntent);
        });
    }

    @Override
    public int getItemCount() {
        return tvShowEntities.size();
    }

    class TvViewHolder extends RecyclerView.ViewHolder {
        TvShowEntity tvShowEntity;
        private RoundedImageView imgPoster;
        private TextView tvTitle, tvDate, tvRating, tvPopularity;
        private RatingBar rbRating;

        TvViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.title_text);
            tvDate = itemView.findViewById(R.id.date_text);
            tvRating = itemView.findViewById(R.id.rating_text);
            tvPopularity = itemView.findViewById(R.id.popularity_text);
            rbRating = itemView.findViewById(R.id.item_rating_bar);
            imgPoster = itemView.findViewById(R.id.poster_image);
        }

        void onBind(TvShowEntity tvShow) {
            this.tvShowEntity = tvShow;
            tvTitle.setText(tvShow.getTvTitle());
            tvDate.setText(tvShow.getTvDate().split("-")[0]);
            tvRating.setText(String.valueOf(tvShow.getTvRating() / 2));
            tvPopularity.setText(tvShow.getTvPopularity());
            rbRating.setRating((float) (tvShow.getTvRating() / 2));
            GlideApp.with(context)
                    .load(IMG_BASE_URL + tvShow.getTvPoster())
                    .apply(new RequestOptions().override(1920, 1080))
                    .into(imgPoster);
        }
    }
}
