package com.drowsyatmidnight.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by haint on 18/06/2017.
 */

public class Trailers {
    @SerializedName("results")
    private List<ItemTrailer> itemTrailers;

    public List<ItemTrailer> getItemTrailers() {
        return itemTrailers;
    }

    public void setItemTrailers(List<ItemTrailer> itemTrailers) {
        this.itemTrailers = itemTrailers;
    }
}
