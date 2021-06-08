package com.coder.knight.jetpack.discover.data.source.remote.response;

import com.coder.knight.jetpack.discover.data.source.local.entity.TrailerEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailerResponse {
    @SerializedName("results")
    @Expose
    private List<TrailerEntity> trailers;

    public TrailerResponse(List<TrailerEntity> trailers) {
        this.trailers = trailers;
    }

    public List<TrailerEntity> getTrailers() {
        return trailers;
    }
}
