package com.eddiemartnez.app.mygallery.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.eddiemartnez.app.mygallery.R;

import java.io.File;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by Brian on 3/11/18.
 */

public class Functions {
    private static RequestOptions options = new RequestOptions()
            //.fitCenter()
            .placeholder(R.mipmap.ic_launcher_round)
            .error(R.mipmap.ic_launcher)
            //.fitCenter()
            .override(400,300)
            .diskCacheStrategy(DiskCacheStrategy.ALL);

    public static void loadImage(String image, ImageView imagePrev, Context context) {
        Glide.with(context)
                .load(image)
                .apply(options)
                .transition(withCrossFade())
                .into(imagePrev);
    }

    public static void loadImage(File image, ImageView imagePrev, Context context) {
        Glide.with(context)
                .load(image)
                .apply(options)
                .transition(withCrossFade())
                .into(imagePrev);
    }

    public static void loadImage( ImageView imagePrev, Context context) {
        Glide.with(context)
                .load(R.mipmap.ic_launcher_round)
                .apply(options)
                .transition(withCrossFade())
                .into(imagePrev);
    }


}