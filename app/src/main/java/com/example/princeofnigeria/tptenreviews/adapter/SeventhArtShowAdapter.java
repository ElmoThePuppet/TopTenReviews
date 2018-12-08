package com.example.princeofnigeria.tptenreviews.adapter;

import android.content.Context;
import android.net.sip.SipSession;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.princeofnigeria.tptenreviews.R;
import com.example.princeofnigeria.tptenreviews.Util.ImageUtil;
import com.example.princeofnigeria.tptenreviews.net.model.ShowModel;
import com.example.princeofnigeria.tptenreviews.net.model.ShowsDBModel;

import java.util.List;

public class SeventhArtShowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private SeventhArtShowAdapter.onShowClickListener listener;
    private List<ShowModel> showList;
    private Context context;
    public SeventhArtShowAdapter(ShowsDBModel showsDBModel, Context context, SeventhArtShowAdapter.onShowClickListener listener){
        this.listener=listener;
        this.context=context;
        if(showsDBModel.getShowModels().size() > 10)
            showList = showsDBModel.getShowModels().subList(0,10);
        else
            showList = showsDBModel.getShowModels();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seventhart, parent, false);
        return new SeventhArtShowAdapter.ViewHolderItem(view);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position){
        final ShowModel showModel = showList.get(position);
        ViewHolderItem viewHolderItem = (ViewHolderItem)  viewHolder;
        viewHolderItem.tvName.setText(showModel.getName());
        ImageUtil.setRoundImageFromURL(context, viewHolderItem.ivThumb, "http://image.tmdb.org/t/p/w45"+showModel.getPosterPath());
        viewHolderItem.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null)
                    listener.OnItemClickListener(showModel);
            }
        });

    }
    @Override
    public int getItemCount(){
        return showList.size();
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
    public interface onShowClickListener {
        void OnItemClickListener(ShowModel model);
    }
}
