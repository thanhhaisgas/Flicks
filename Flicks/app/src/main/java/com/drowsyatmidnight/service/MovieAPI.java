package com.drowsyatmidnight.service;

import com.drowsyatmidnight.model.Movies;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by haint on 15/06/2017.
 */

public interface  MovieAPI{
        @GET("now_playing")
        Call<Movies> getAllMovies();
}
