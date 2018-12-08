package com.example.princeofnigeria.tptenreviews.net;

import android.content.Context;
import android.util.Log;
import com.example.princeofnigeria.tptenreviews.Util.AppUtil;
import com.example.princeofnigeria.tptenreviews.net.event.onMovieListEvent;
import com.example.princeofnigeria.tptenreviews.net.event.onShowsListEvent;
import com.example.princeofnigeria.tptenreviews.net.model.MovieDBModel;
import com.example.princeofnigeria.tptenreviews.net.model.MovieModel;
import com.example.princeofnigeria.tptenreviews.net.model.ShowModel;
import com.example.princeofnigeria.tptenreviews.net.model.ShowsDBModel;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
import java.net.ConnectException;

import com.google.gson.reflect.TypeToken;
import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.util.List;

public class MovieAPI {

    //protected static final String url = "https://api.themoviedb.org/3/movie/550?api_key=3ded124fa41e8effb412d4f93b1b0814";
    protected static final String GET_TOP_MOVIES="https://api.themoviedb.org/3/movie/top_rated?api_key=3ded124fa41e8effb412d4f93b1b0814";

    protected static final String GET_TOP_SHOWS="https://api.themoviedb.org/3/tv/top_rated?api_key=3ded124fa41e8effb412d4f93b1b0814";

    protected static final String GET_MOVIES_BY_STRING="https://api.themoviedb.org/3/search/movie?api_key=3ded124fa41e8effb412d4f93b1b0814&query=%s";

    protected static final String GET_SHOWS_BY_STRING="https://api.themoviedb.org/3/search/tv?api_key=3ded124fa41e8effb412d4f93b1b0814&query=%s";

    protected static final Gson gson = new Gson();

    private static MovieAPI instance = null;

    private static OkHttpClient getClient() {
        return client;
    }

    private static OkHttpClient client = new OkHttpClient();

    public static MovieAPI getInstance() {
        if (instance == null) instance = new MovieAPI();
        return instance;
    }
    private void sendRequest(String method, String url, Context context, Callback callback) {
       Request request = new Request.Builder()
                .url(url)
                .method(method, null)
                .build();


        Call call = getClient().newCall(request);
        if (AppUtil.isNetworkAvailable(context))
            call.enqueue(callback);
        else {
            callback.onFailure(call, new ConnectException()); // this way we don't wait for timeout which is at this moment substantial - 20s
        }
    }
    public void getMoviesList(String method, Context context){
        sendRequest(method, GET_TOP_MOVIES, context, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new onMovieListEvent());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = response.body().string();
                response.close();
                if (response.isSuccessful()) {
                    MovieDBModel movieDBModel = gson.fromJson(body, MovieDBModel.class);
                    EventBus.getDefault().post(new onMovieListEvent(movieDBModel));
                } else {
                    // fail
                    EventBus.getDefault().post(new onMovieListEvent());
                }
            }
        });
    }
    public void getShowsList(String method, Context context){
        sendRequest(method, GET_TOP_SHOWS, context, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new onShowsListEvent());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = response.body().string();
                response.close();
                if (response.isSuccessful()) {
                    ShowsDBModel showModels = gson.fromJson(body, ShowsDBModel.class);
                    EventBus.getDefault().post(new onShowsListEvent(showModels));
                } else {
                    // fail
                    EventBus.getDefault().post(new onShowsListEvent());
                }
            }
        });
    }
    public void getSearchMovies(String method, String suffix, Context context){
        sendRequest(method, String.format(GET_MOVIES_BY_STRING, suffix), context, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new onMovieListEvent());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = response.body().string();
                response.close();
                if (response.isSuccessful()) {

                    MovieDBModel movieModels = gson.fromJson(body, MovieDBModel.class);
                    EventBus.getDefault().post(new onMovieListEvent(movieModels));
                } else {
                    // fail
                    EventBus.getDefault().post(new onMovieListEvent());
                }
            }
        });
    }
    public void getSearchShows (String method, String suffix, Context context){
        sendRequest(method, String.format(GET_SHOWS_BY_STRING, suffix), context, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new onShowsListEvent());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = response.body().string();
                response.close();
                if (response.isSuccessful()) {
                    ShowsDBModel showModels = gson.fromJson(body, ShowsDBModel.class);
                    EventBus.getDefault().post(new onShowsListEvent(showModels));
                } else {
                    // fail
                    EventBus.getDefault().post(new onShowsListEvent());
                }
            }
        });
    }
}
