package com.example.princeofnigeria.tptenreviews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.princeofnigeria.tptenreviews.R;
import com.example.princeofnigeria.tptenreviews.Util.ImageUtil;
import com.example.princeofnigeria.tptenreviews.net.model.MovieDBModel;
import com.example.princeofnigeria.tptenreviews.net.model.MovieModel;

import java.util.List;

public class SeventhArtMovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MovieModel> movieList;
    private Context context;
    SeventhArtMovieAdapter.onMovieClickListener listener;
    public SeventhArtMovieAdapter(MovieDBModel movieDBModel, SeventhArtMovieAdapter.onMovieClickListener listener, Context context){
        this.context=context;
        this.listener=listener;
        if(movieDBModel.getMovieModels().size() > 10)
            movieList = movieDBModel.getMovieModels().subList(0,10);
        else
            movieList = movieDBModel.getMovieModels();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seventhart, parent, false);
        return new SeventhArtMovieAdapter.ViewHolderItem(view);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position){
        final MovieModel movieModel = movieList.get(position);
        ViewHolderItem viewHolderItem = (ViewHolderItem)  viewHolder;
        viewHolderItem.tvName.setText(movieModel.getTitle());
        ImageUtil.setRoundImageFromURL(context, viewHolderItem.ivThumb, "http://image.tmdb.org/t/p/w45"+movieModel.getPosterPath());
        viewHolderItem.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null)
                    listener.OnItemClickListener(movieModel);
            }
        });
    }
    @Override
    public int getItemCount(){
        return movieList.size();
    }
    public class ViewHolderItem extends RecyclerView.ViewHolder {

        TextView tvName;
        ImageView ivThumb;
        ViewHolderItem(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            ivThumb = itemView.findViewById(R.id.ivThumb);
        }
    }
    public interface onMovieClickListener {
        void OnItemClickListener(MovieModel model);
    }
}
