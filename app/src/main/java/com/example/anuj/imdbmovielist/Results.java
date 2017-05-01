package com.example.anuj.imdbmovielist;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by anuj on 4/24/17.
 */

public class Results implements Parcelable {

    @SerializedName("titleTextView")
    private String title;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("poster_path")
    private String poster_path;

    @SerializedName("backdrop_path")
    private String backdrop_path;

    @SerializedName("id")
    private String id;

    @SerializedName("overview")
    private String overview;

    @SerializedName("vote_average")
    private String vote_average;

    @SerializedName("name")
    private String name;

    @SerializedName("key")
    private String key;

    protected Results(Parcel in) {
        title = in.readString();
        releaseDate = in.readString();
        poster_path = in.readString();
        backdrop_path = in.readString();
        id = in.readString();
        overview = in.readString();
        vote_average = in.readString();
        name = in.readString();
        key = in.readString();
    }

    public static final Creator<Results> CREATOR = new Creator<Results>() {
        @Override
        public Results createFromParcel(Parcel in) {
            return new Results(in);
        }

        @Override
        public Results[] newArray(int size) {
            return new Results[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return releaseDate;
    }

    public String getPoster() {
        return poster_path;
    }

    public String getBackdrop() {
        return backdrop_path;
    }

    public String getId() {
        return id;
    }

    public String getOverview() {
        return overview;
    }

    public String getVote() {
        return vote_average;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(releaseDate);
        dest.writeString(poster_path);
        dest.writeString(backdrop_path);
        dest.writeString(id);
        dest.writeString(overview);
        dest.writeString(vote_average);
        dest.writeString(name);
        dest.writeString(key);
    }
}
