package com.drowsyatmidnight.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.drowsyatmidnight.flicks.R;

/**
 * Created by haint on 15/06/2017.
 */

public class HolderItemMovie extends RecyclerView.ViewHolder {
    public ImageView imgMovie;

    public HolderItemMovie(View convertView) {
        super(convertView);
        imgMovie = (ImageView) convertView.findViewById(R.id.imgMovie);
    }
}
