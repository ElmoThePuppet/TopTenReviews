package com.example.princeofnigeria.tptenreviews.net.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieDBModel {
    @SerializedName("results")
    @Expose
    private List<MovieModel> movieModels;

    public List<MovieModel> getMovieModels() {
        return movieModels;
    }

    public void setMovieModels(List<MovieModel> movieModels) {
        this.movieModels = movieModels;
    }
}
