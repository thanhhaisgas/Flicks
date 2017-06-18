package com.drowsyatmidnight.service;

import com.drowsyatmidnight.model.Trailers;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by haint on 18/06/2017.
 */

public interface TrailerAPI {
    @GET("videos")
    Call<Trailers> getAllTrailers();
}
