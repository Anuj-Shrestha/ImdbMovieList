package com.example.anuj.imdbmovielist;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by anuj on 4/20/17.
 */

public class TmdbResponse {

    @SerializedName("results")
    public  ArrayList<Results> Results;

    public void setResults(ArrayList<Results> Results) {
        this.Results = Results;
    }

    public ArrayList<Results> getResults() {
        return Results;
    }
}
