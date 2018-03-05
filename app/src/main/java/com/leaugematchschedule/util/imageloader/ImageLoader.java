package com.leaugematchschedule.util.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.leaugematchschedule.R;
import com.leaugematchschedule.customview.CircleImageView;

/**
 * Image loader class which will use Glide to load images.
 */
public class ImageLoader {

    private final Context mContext;

    public ImageLoader(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * Loads the image from url into ImageView.
     */
    public void loadImage(final ImageView imageView, final String imageUrl, int placeHolderRes) {
        Glide.with(mContext).asBitmap().load(imageUrl).apply(new RequestOptions().placeholder(placeHolderRes).error(placeHolderRes)).into(imageView);
    }

    /**
     * Loads the image resource into ImageView.
     */
    public void loadImage(final ImageView imageView, final int resource, int placeHolderRes) {
        Glide.with(mContext).asBitmap().load(resource).apply(new RequestOptions().placeholder(placeHolderRes).error(placeHolderRes)).into(imageView);
    }

    /**
     * Loads the image resource into ImageView.
     */
    public void loadImage(final ImageView imageView, final Bitmap bitmap, int placeHolderRes) {
        Glide.with(mContext).asBitmap().load(bitmap).apply(new RequestOptions().placeholder(placeHolderRes).error(placeHolderRes).circleCrop()).into(imageView);
    }

    /**
     * Loads the image resource into ImageView.
     */
    public void loadImageRound(final ImageView imageView, final String path, int placeHolderRes) {
        Glide.with(mContext).asBitmap().load(path).apply(new RequestOptions().placeholder(placeHolderRes).error(placeHolderRes).circleCrop()).into(imageView);
    }

    /**
     * Set rounded image view
     * <p>
     * If want to do something after setting the image so don't use this generalized method. Use it at same place at your own.
     * </p>
     *
     * @param uri       String image Uri
     * @param imageView imageView into set image
     */

    public void getGlideRounded(final String uri, final CircleImageView imageView) {
        Glide.with(mContext).asBitmap().load(uri).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                imageView.setImageBitmap(bitmap);
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                //                mImageView.setImageDrawable(VectorDrawableCompat.create(getResources(), R.drawable.img_placeholder, null));
                super.onLoadFailed(errorDrawable);
                imageView.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.placeholder_banner));
            }

            @Override
            public void onLoadStarted(@Nullable Drawable placeholder) {
                super.onLoadStarted(placeholder);
                imageView.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.placeholder_banner));
            }
        });
    }
}
