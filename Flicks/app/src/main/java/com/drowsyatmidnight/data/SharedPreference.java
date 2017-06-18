package com.drowsyatmidnight.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.drowsyatmidnight.model.ItemMovie;
import com.drowsyatmidnight.model.ItemTrailer;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by haint on 17/06/2017.
 */

public class SharedPreference {
    public static final String PREFS_NAME = "PRODUCT_APP";
    public static final String PREFS_NAME_TRAILER = "KEY_TRAILER";
    public static final String FAVORITES = "Now_Playing";
    public static final String TRAILER = "KEY";

    public static void saveFavorites(Context context, List<ItemMovie> favorites) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);

        editor.commit();
    }

    public static void saveKeys(Context context, List<ItemTrailer> trailers) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME_TRAILER,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(trailers);

        editor.putString(TRAILER, jsonFavorites);

        editor.commit();
    }

    public static ArrayList<ItemMovie> getFavorites(Context context) {
        SharedPreferences settings;
        List<ItemMovie> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            ItemMovie[] favoriteItems = gson.fromJson(jsonFavorites,
                    ItemMovie[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<ItemMovie>(favorites);
        } else
            return null;

        return (ArrayList<ItemMovie>) favorites;
    }

    public static ArrayList<ItemTrailer> getKeys(Context context) {
        SharedPreferences settings;
        List<ItemTrailer> trailers;

        settings = context.getSharedPreferences(PREFS_NAME_TRAILER,
                Context.MODE_PRIVATE);

        if (settings.contains(TRAILER)) {
            String jsonFavorites = settings.getString(TRAILER, null);
            Gson gson = new Gson();
            ItemTrailer[] trailersList = gson.fromJson(jsonFavorites,
                    ItemTrailer[].class);

            trailers = Arrays.asList(trailersList);
            trailers = new ArrayList<ItemTrailer>(trailers);
        } else
            return null;

        return (ArrayList<ItemTrailer>) trailers;
    }
}
