package com.example.princeofnigeria.tptenreviews.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.princeofnigeria.tptenreviews.R;
import com.example.princeofnigeria.tptenreviews.adapter.SeventhArtMovieAdapter;
import com.example.princeofnigeria.tptenreviews.adapter.SeventhArtShowAdapter;
import com.example.princeofnigeria.tptenreviews.net.MovieAPI;
import com.example.princeofnigeria.tptenreviews.net.event.onMovieListEvent;
import com.example.princeofnigeria.tptenreviews.net.event.onShowsListEvent;
import com.example.princeofnigeria.tptenreviews.net.model.MovieModel;
import com.example.princeofnigeria.tptenreviews.net.model.ShowModel;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class DashboardActivity extends AppCompatActivity {
    @BindView(R.id.tabLayout)       TabLayout tabLayout;
    @BindView(R.id.recyclerView)    RecyclerView recyclerView;
    boolean movieTabSelected = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        ButterKnife.bind(this);
        initTab();
    }
    @Override
    protected void onStart(){
        super.onStart();
        EventBus.getDefault().register(this);
        if (movieTabSelected)
            MovieAPI.getInstance().getMoviesList("GET", getApplicationContext());
        else
            MovieAPI.getInstance().getShowsList("GET", getApplicationContext());
//        MovieAPI.getInstance().getSearchMovies("GET","Shawshank", getApplicationContext());
//        MovieAPI.getInstance().getSearchShows("GET","Friends", getApplicationContext());
//        MovieAPI.getInstance().getShowsList("GET", getApplicationContext());
    }
    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void initTab() {
        tabLayout.addTab(tabLayout.newTab().setText("Movies").setTag("Tag 1"));
        tabLayout.addTab(tabLayout.newTab().setText("TV Shows").setTag("Tag tri"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getTag().toString()){
                    case "Tag 1":
                        MovieAPI.getInstance().getMoviesList("GET", getApplicationContext());
                        movieTabSelected=true;
                        break;
                    case "Tag tri":
                        MovieAPI.getInstance().getShowsList("GET", getApplicationContext());
                        movieTabSelected = false;
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @OnClick(R.id.ivSearch)
    public void onSearch(){
        Intent intent = new Intent(DashboardActivity.this, SearchActivity.class);
        intent.putExtra("isMovie", movieTabSelected);
        startActivity(intent);
    }







    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnMoviesListEvent(onMovieListEvent response){
        if (response.getMoviesListResponse() != null){
            LinearLayoutManager llm = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(llm);
            SeventhArtMovieAdapter adapter = new SeventhArtMovieAdapter(response.getMoviesListResponse(), new SeventhArtMovieAdapter.onMovieClickListener() {
                @Override
                public void OnItemClickListener(MovieModel model) {
                    Intent intent= new Intent(DashboardActivity.this, DetailsActivity.class);
                    intent.putExtra("title", model.getTitle());
                    intent.putExtra("poster", model.getPosterPath());
                    intent.putExtra("overview", model.getOverview());
                    startActivity(intent);
                }
            }, this);
            recyclerView.setAdapter(adapter);
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnShowsListEvent(onShowsListEvent response){
        if (response.getShowsListResponse() != null){
            LinearLayoutManager llm = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(llm);
            SeventhArtShowAdapter adapter = new SeventhArtShowAdapter(response.getShowsListResponse(), this, new SeventhArtShowAdapter.onShowClickListener() {
                @Override
                public void OnItemClickListener(ShowModel model) {
                    Intent intent= new Intent(DashboardActivity.this, DetailsActivity.class);
                    intent.putExtra("title", model.getName());
                    intent.putExtra("poster", model.getPosterPath());
                    intent.putExtra("overview", model.getOverview());
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(adapter);
        }
    }
}
