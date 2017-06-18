package com.drowsyatmidnight.flicks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.drowsyatmidnight.data.SharedPreference;
import com.drowsyatmidnight.model.ItemMovie;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.drowsyatmidnight.adapter.MovieAdapter.URLimg;

public class DetailsMovie extends AppCompatActivity {

    private long id;
    private List<ItemMovie> itemMovies;
    private ItemMovie itemMovie;

    @BindView(R.id.header_cover_image)
    ImageView headerCoverImage;
    @BindView(R.id.btnPlay)
    ImageButton btnPlay;
    @BindView(R.id.txtMovieName)
    TextView txtMovieName;
    @BindView(R.id.txtReleaseDay)
    TextView txtReleaseDay;
    @BindView(R.id.txtContent)
    TextView txtContent;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_movie);
        ButterKnife.bind(this);
        id = getIntent().getLongExtra("idMovie", 0);
        itemMovies = SharedPreference.getFavorites(this);
        try {
            addControls();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        addEvents();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
    }

    private void addEvents() {
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playTrailer = new Intent(DetailsMovie.this, Trailer.class);
                playTrailer.putExtra("idMovie", itemMovie.getId());
                startActivity(playTrailer);
            }
        });
    }

    private void addControls() throws ParseException {
        itemMovie = new ItemMovie();
        for(ItemMovie m : itemMovies){
            if(m.getId() == id){
                itemMovie = m;
            } else {
                Log.d("ID", String.valueOf(id));
            }
        }
        Picasso.with(this)
                .load(URLimg + itemMovie.getBackdropPath())
                .error(R.drawable.noimagefound)
                .placeholder(R.drawable.progress_animation)
                .into(headerCoverImage);
        txtMovieName.setText(itemMovie.getTitle());

        txtReleaseDay.setText("Release day: "+ dateFormat());
        float rating = itemMovie.getVoteAverage()*5/10;
        ratingBar.setRating(rating);
        ratingBar.setIsIndicator(true);
        txtContent.setText(itemMovie.getOverview());
    }

    private String dateFormat() {
        String releaseDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {

            Date date = sdf.parse(itemMovie.getReleaseDate());
            releaseDate = new SimpleDateFormat("dd/MM/yyyy").format(date);

        } catch (ParseException e) {
            Log.d("DateFormatFail", e.toString());
        }
        return releaseDate;
    }
}
