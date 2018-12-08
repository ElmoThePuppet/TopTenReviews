package com.example.princeofnigeria.tptenreviews.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

public class ImageUtil {
    public static void setRoundImageFromURL(final Context context, final ImageView imageView, String url) {
        RequestOptions req = new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.RESOURCE);

        Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(req)
                .into(new BitmapImageViewTarget(imageView){
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }
    public static void setImageFromURL(final Context context, final ImageView imageView, String url) {
        RequestOptions req = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE);

        Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(req)
                .into(imageView);
    }

}
