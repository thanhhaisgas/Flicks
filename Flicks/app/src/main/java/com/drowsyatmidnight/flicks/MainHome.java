package com.drowsyatmidnight.flicks;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.drowsyatmidnight.adapter.MovieAdapter;
import com.drowsyatmidnight.data.SharedPreference;
import com.drowsyatmidnight.model.ItemMovie;
import com.drowsyatmidnight.model.Movies;
import com.drowsyatmidnight.service.MovieAPI;
import com.drowsyatmidnight.service.RetrofitUtil;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;
import com.yarolegovich.discretescrollview.Orientation;
import com.yarolegovich.discretescrollview.transform.Pivot;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainHome extends AppCompatActivity implements DiscreteScrollView.OnItemChangedListener {

    private DiscreteScrollView lvMovie;
    private InfiniteScrollAdapter infiniteScrollAdapter;
    private MovieAdapter movieAdapter;
    private List<ItemMovie> itemMovies;
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.txtOverview)
    TextView txtOverview;
    @BindView(R.id.txtNowPlaying)
    TextView txtNowPlaying;
    @BindView(R.id.swipeToRefresh)
    SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        ButterKnife.bind(this);
        addControls();
        addEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
    }

    private void addEvents() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void refresh() {
        infiniteScrollAdapter = InfiniteScrollAdapter.wrap(new MovieAdapter(MainHome.this, MainHome.this, itemMovies));
        lvMovie.setAdapter(infiniteScrollAdapter);
    }


    private void addControls() {
        getAllMovies();
        Typeface capture = Typeface.createFromAsset(getAssets(),"capture.ttf");
        txtNowPlaying.setTypeface(capture);
        lvMovie = (DiscreteScrollView) findViewById(R.id.lvMovie);
        itemMovies = SharedPreference.getFavorites(MainHome.this);
        lvMovie.setOrientation(Orientation.HORIZONTAL);
        lvMovie.addOnItemChangedListener(this);
        movieAdapter = new MovieAdapter(MainHome.this, MainHome.this, itemMovies);
        infiniteScrollAdapter = InfiniteScrollAdapter.wrap(movieAdapter);
        lvMovie.setAdapter(infiniteScrollAdapter);
        lvMovie.setItemTransitionTimeMillis(300);
        lvMovie.setItemTransformer(new ScaleTransformer.Builder()
        .setMinScale(0.8f)
        .setPivotX(Pivot.X.CENTER)
        .build());
    }

    private void getAllMovies(){
        Retrofit retrofit = RetrofitUtil.create(RetrofitUtil.BASE_URL);
        MovieAPI movieAPI = retrofit.create(MovieAPI.class);
        movieAPI.getAllMovies().enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                itemMovies = response.body().getItemMovies();
                movieAdapter = new MovieAdapter(MainHome.this, MainHome.this, itemMovies);
                infiniteScrollAdapter = InfiniteScrollAdapter.wrap(movieAdapter);
                lvMovie.setAdapter(infiniteScrollAdapter);
                SharedPreference.saveFavorites(MainHome.this, itemMovies);
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
                Log.d("FailHTTP", t.toString());
            }
        });
    }



    @Override
    public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {
        int pos = infiniteScrollAdapter.getRealCurrentPosition();
        onItemChanged(itemMovies.get(pos));
    }

    private void onItemChanged(ItemMovie itemMovie) {
        txtTitle.setText(itemMovie.getTitle());
        txtOverview.setText(itemMovie.getOverview());
    }
}
