package com.example.anuj.imdbmovielist;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.net.URI;

/**
 * Created by anuj on 4/24/17.
 */

public class Search implements Serializable {

    private String Title;
    private String Year;
    private String Poster;
    private String imdbID;
    private String Type;

    public String getTitle() { return Title; }
    public String getYear() { return  Year; }

    public String getUri() { return Poster; }

    public String getType() {
        return Type;
    }
    public String getImdbID() { return imdbID; }
}
