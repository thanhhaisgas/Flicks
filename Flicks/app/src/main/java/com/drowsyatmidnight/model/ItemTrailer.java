package com.drowsyatmidnight.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by haint on 18/06/2017.
 */

public class ItemTrailer {
    @SerializedName("key")
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ItemTrailer() {
    }

    public ItemTrailer(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "ItemTrailer{" +
                "key='" + key + '\'' +
                '}';
    }
}
