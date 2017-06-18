package com.drowsyatmidnight.flicks;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.drowsyatmidnight.model.ItemTrailer;
import com.drowsyatmidnight.model.Trailers;
import com.drowsyatmidnight.service.RetrofitUtil;
import com.drowsyatmidnight.service.TrailerAPI;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Trailer extends YouTubeBaseActivity {

    private YouTubePlayerView youTubePlayerView;
    private List<ItemTrailer> itemTrailers;
    private long id;
    private int posKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);
        id = getIntent().getLongExtra("idMovie", 0);
        addControls();
        addEvents();
    }

    private void addEvents() {
        getAllTrailer();
    }

    private void addControls() {
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.trailerMovie);
    }

    private void getAllTrailer(){
        Retrofit retrofit = RetrofitUtil.create(RetrofitUtil.BASE_URL_TRAILER+String.valueOf(id)+"/");
        TrailerAPI trailerAPI = retrofit.create(TrailerAPI.class);
        trailerAPI.getAllTrailers().enqueue(new Callback<Trailers>() {
            @Override
            public void onResponse(Call<Trailers> call, Response<Trailers> response) {
                itemTrailers = response.body().getItemTrailers();
                if (!itemTrailers.isEmpty()) {
                    Random rand = new Random();
                    posKey = rand.nextInt(itemTrailers.size());
                    playYouTube(itemTrailers.get(posKey).getKey());
                }
                else {
                    Toast.makeText(Trailer.this, "No trailer video available", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Trailers> call, Throwable t) {
                Log.d("FailHTTP", t.toString());
            }
        });
    }

    private void playYouTube(final String keyTrailer){
        youTubePlayerView.initialize(BuildConfig.YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.setFullscreen(true);
                youTubePlayer.loadVideo(keyTrailer);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(Trailer.this, youTubeInitializationResult.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
