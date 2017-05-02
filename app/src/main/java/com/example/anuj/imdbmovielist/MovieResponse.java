package com.example.anuj.imdbmovielist;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anuj on 4/20/17.
 */

public class MovieResponse {

    @SerializedName("Search")
    public  ArrayList<Search> Search;

    public void setSearch (ArrayList<Search> Search) {
        this.Search = Search;
    }

    public ArrayList<Search> getSearch () {
        return Search;
    }
}
