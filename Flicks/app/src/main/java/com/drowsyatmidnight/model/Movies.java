package com.drowsyatmidnight.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by haint on 15/06/2017.
 */

public class Movies {
    @SerializedName("results")
    private List<ItemMovie> itemMovies;

    public List<ItemMovie> getItemMovies() {
        return itemMovies;
    }

    public void setItemMovies(List<ItemMovie> itemMovies) {
        this.itemMovies = itemMovies;
    }
}
