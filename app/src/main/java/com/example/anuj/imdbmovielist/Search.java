package com.example.anuj.imdbmovielist;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by anuj on 4/24/17.
 */

public class Search implements Serializable {

    private String Title;
    private String Year;

    public String getTitle() { return Title; }
    public String getYear() { return  Year; }
}
