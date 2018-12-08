package com.example.princeofnigeria.tptenreviews.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
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

public class SearchActivity extends AppCompatActivity {
    @BindView(R.id.etSearch)        EditText editText;
    @BindView(R.id.recyclerView)    RecyclerView recyclerView;

    boolean isMovie = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        isMovie = getIntent().getBooleanExtra("isMovie", true);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isMovie)
                    MovieAPI.getInstance().getSearchMovies("GET", editText.getText().toString(), getApplicationContext());
                else
                    MovieAPI.getInstance().getSearchShows("GET", editText.getText().toString(), getApplicationContext());
            }
        });
    }
    @Override
    protected void onStart(){
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    @OnClick (R.id.arrow)
    public void onBackClick(){
        super.onBackPressed();
    }






    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnMoviesListEvent(onMovieListEvent response){
        if (response.getMoviesListResponse() != null){
            LinearLayoutManager llm = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(llm);
            SeventhArtMovieAdapter adapter = new SeventhArtMovieAdapter(response.getMoviesListResponse(), new SeventhArtMovieAdapter.onMovieClickListener() {
                @Override
                public void OnItemClickListener(MovieModel model) {
                    Intent intent= new Intent(SearchActivity.this, DetailsActivity.class);
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
                    Intent intent= new Intent(SearchActivity.this, DetailsActivity.class);
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
