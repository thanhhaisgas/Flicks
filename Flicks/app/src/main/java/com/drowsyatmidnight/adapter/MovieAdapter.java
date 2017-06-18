package com.drowsyatmidnight.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drowsyatmidnight.flicks.DetailsMovie;
import com.drowsyatmidnight.flicks.R;
import com.drowsyatmidnight.holder.HolderItemMovie;
import com.drowsyatmidnight.model.ItemMovie;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by haint on 15/06/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<HolderItemMovie> {

    private List<ItemMovie> itemMovies;
    private View view;
    private Context context;
    private LayoutInflater layoutInflater;
    public static final String URLimg = "https://image.tmdb.org/t/p/w342";
    private Activity activity;

    public MovieAdapter(Activity activity, Context context, List<ItemMovie> itemMovies) {
        this.itemMovies = itemMovies;
        this.context = context;
        this.activity = activity;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public long getItemId(int position) {
        if (itemMovies == null){
            return super.getItemId(position);
        }else {
            return itemMovies.get(position).getId();
        }
    }

    @Override
    public HolderItemMovie onCreateViewHolder(ViewGroup parent, int viewType) {
        view = layoutInflater.inflate(R.layout.item_movie, parent, false);
        return new HolderItemMovie(view);
    }

    @Override
    public void onBindViewHolder(final HolderItemMovie holder, final int position) {
        final ItemMovie itemMovie = itemMovies.get(position);
        Configuration configuration = context.getResources().getConfiguration();
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            Picasso.with(context)
                    .load(URLimg + itemMovie.getBackdropPath())
                    .error(R.drawable.noimagefound)
                    .placeholder(R.drawable.progress_animation)
                    .into(holder.imgMovie, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            holder.imgMovie.setImageResource(R.drawable.noimagefound);
                        }
                    });
        }else {
            Picasso.with(context)
                    .load(URLimg + itemMovie.getPosterPath())
                    .error(R.drawable.noimagefound)
                    .placeholder(R.drawable.progress_animation)
                    .into(holder.imgMovie, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            holder.imgMovie.setImageResource(R.drawable.noimagefound);
                        }
                    });
        }
        holder.imgMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goDetail = new Intent(context.getApplicationContext(), DetailsMovie.class);
                goDetail.putExtra("idMovie", itemMovie.getId());
                context.startActivity(goDetail);
                activity.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (itemMovies == null){
            return 0;
        }else {
            return itemMovies.size();
        }
    }
}
