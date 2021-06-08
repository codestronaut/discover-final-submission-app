package com.coder.knight.jetpack.discover.data.source.local.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenreEntity implements Parcelable {
    @SerializedName("id")
    @Expose
    private int genreId;

    @SerializedName("name")
    @Expose
    private String genreName;

    public GenreEntity(int genreId, String genreName) {
        this.genreId = genreId;
        this.genreName = genreName;
    }

    public String getGenreName() {
        return genreName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.genreId);
        dest.writeString(this.genreName);
    }

    private GenreEntity(Parcel in) {
        this.genreId = in.readInt();
        this.genreName = in.readString();
    }

    public static final Parcelable.Creator<GenreEntity> CREATOR = new Parcelable.Creator<GenreEntity>() {
        @Override
        public GenreEntity createFromParcel(Parcel source) {
            return new GenreEntity(source);
        }

        @Override
        public GenreEntity[] newArray(int size) {
            return new GenreEntity[size];
        }
    };
}
