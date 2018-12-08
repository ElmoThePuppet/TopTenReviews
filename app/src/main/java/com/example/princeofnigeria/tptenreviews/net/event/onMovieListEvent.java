package com.example.princeofnigeria.tptenreviews.net.event;

import com.example.princeofnigeria.tptenreviews.net.model.MovieDBModel;
import com.example.princeofnigeria.tptenreviews.net.model.MovieModel;

import java.util.List;

public class onMovieListEvent {
    private MovieDBModel movieDBModel;

    public onMovieListEvent() {
    }

    public onMovieListEvent(MovieDBModel movieListResponse) {
        this.movieDBModel = movieListResponse;
    }


    public MovieDBModel getMoviesListResponse() {
        return movieDBModel;
    }
}
