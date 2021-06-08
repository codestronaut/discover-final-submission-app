package com.coder.knight.jetpack.discover.data.source.remote.response;

import com.coder.knight.jetpack.discover.data.source.local.entity.TvShowEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TvShowResponse {

    @SerializedName("results")
    @Expose
    private ArrayList<TvShowEntity> tvShowList;

    public TvShowResponse(ArrayList<TvShowEntity> tvShowList) {
        this.tvShowList = tvShowList;
    }
    
    public ArrayList<TvShowEntity> getTvShowList() {
        return tvShowList;
    }
}
