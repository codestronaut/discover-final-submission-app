package com.coder.knight.jetpack.discover.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.RequestOptions;
import com.coder.knight.jetpack.discover.R;
import com.coder.knight.jetpack.discover.data.source.local.entity.TrailerEntity;
import com.coder.knight.jetpack.discover.utils.GlideApp;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {
    private static String YOUTUBE_VIDEO_URL = "http://www.youtube.com/watch?v=%s";
    private List<TrailerEntity> trailerEntities = new ArrayList<>();
    private Context context;

    TrailerAdapter(Context context) {
        this.context = context;
    }

    void setTrailer(List<TrailerEntity> trailer) {
        if (trailer == null) return;
        this.trailerEntities.clear();
        this.trailerEntities.addAll(trailer);
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_thumbnail_item, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        holder.onBind(trailerEntities.get(position));
        holder.itemView.setOnClickListener(view -> {
            Intent linkIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(String.format(
                            YOUTUBE_VIDEO_URL,
                            trailerEntities.get(position).getKey())));
            linkIntent.putExtra("force_fullscreen", true);
            context.startActivity(linkIntent);
        });
    }

    @Override
    public int getItemCount() {
        return trailerEntities.size();
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder {
        TrailerEntity trailerEntity;
        private RoundedImageView imgThumbnail;

        TrailerViewHolder(@NonNull View itemView) {
            super(itemView);
            imgThumbnail = itemView.findViewById(R.id.thumbnail_image);
        }

        void onBind(TrailerEntity trailer) {
            this.trailerEntity = trailer;
            String YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi/%s/0.jpg";
            GlideApp.with(context)
                    .load(String.format(YOUTUBE_THUMBNAIL_URL, trailer.getKey()))
                    .apply(new RequestOptions().placeholder(R.color.colorPrimary).centerCrop())
                    .into(imgThumbnail);
        }
    }
}
