package com.example.princeofnigeria.tptenreviews.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.princeofnigeria.tptenreviews.R;
import com.example.princeofnigeria.tptenreviews.Util.ImageUtil;

public class DetailsActivity extends AppCompatActivity {
    @BindView(R.id.tvTitle)     TextView title;
    @BindView(R.id.ivPoster)    ImageView poster;
    @BindView(R.id.tvOverview)  TextView overview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        title.setText(getIntent().getStringExtra("title"));
        ImageUtil.setImageFromURL(this, poster, "http://image.tmdb.org/t/p/original" + getIntent().getStringExtra("poster"));
        overview.setText(getIntent().getStringExtra("overview"));
    }
    @OnClick(R.id.arrow)
    public void onBackClick(){
        super.onBackPressed();
    }
}
