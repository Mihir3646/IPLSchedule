package com.leaugematchschedule;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.leaugematchschedule.customview.CircleImageView;
import com.leaugematchschedule.util.Constants;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Application Class
 * Created by M.T. on 17/1/18.
 */
public class LeagueMatchApp extends Application {
    private static final String TAG = LeagueMatchApp.class.getSimpleName();

    private static LeagueMatchApp sLeagueMatchApp;

    public GoogleSignInClient getGoogleSignInClient() {
        return mGoogleSignInClient;
    }

    private GoogleSignInClient mGoogleSignInClient;

    private static GoogleAnalytics sAnalytics;
    private static Tracker sTracker;

    public static LeagueMatchApp getInstance() {
        return sLeagueMatchApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sLeagueMatchApp = this;

        initGoogleAnalytics();
        initGoogleClient();
    }

    /**
     * Initialize google analytics
     */
    private void initGoogleAnalytics() {
    /* Google analytics initialization */
        sAnalytics = GoogleAnalytics.getInstance(this);
        // Obtain the shared Tracker instance.
        sTracker = getDefaultTracker();
    }

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     *
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
        if (sTracker == null) {
            sTracker = sAnalytics.newTracker(Constants.GOOGLE_ANALYTICS_TRACKING_ID);
        }

        return sTracker;
    }

    private void initGoogleClient() {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    /**
     * Generate reusable observable source object.
     *
     * @return ObservableTransformer
     */
    public <T> ObservableTransformer<T, T> applyObservableAsync() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    /**
     * Loads the image from url into ImageView.
     */
    public void loadImage(final ImageView imageView, final String imageUrl, int placeHolderRes) {
        Glide.with(this).asBitmap().load(imageUrl).apply(new RequestOptions().placeholder(placeHolderRes).error(placeHolderRes)).into(imageView);
    }

    /**
     * Loads the image from url into ImageView.
     */
    public void loadImage(final ImageView imageView, final Uri imageUri, int placeHolderRes) {
        Glide.with(this).asBitmap().load(imageUri).apply(new RequestOptions().placeholder(placeHolderRes).error(placeHolderRes)).into(imageView);
    }

    /**
     * Loads the image resource into ImageView.
     */
    public void loadImage(final ImageView imageView, final int resource, int placeHolderRes) {
        Glide.with(this).asBitmap().load(resource).apply(new RequestOptions().placeholder(placeHolderRes).error(placeHolderRes)).into(imageView);
    }

    /**
     * Loads the image resource into ImageView.
     */
    public void loadImage(final ImageView imageView, final Bitmap bitmap, int placeHolderRes) {
        Glide.with(this).asBitmap().load(bitmap).apply(new RequestOptions().placeholder(placeHolderRes).error(placeHolderRes).circleCrop()).into(imageView);
    }

    /**
     * Loads the image resource into ImageView.
     */
    public void loadImageRound(final ImageView imageView, final String path, int placeHolderRes) {
        Glide.with(this).asBitmap().load(path).apply(new RequestOptions().placeholder(placeHolderRes).error(placeHolderRes).circleCrop()).into(imageView);
    }

    public RequestBuilder<Bitmap> getGlidePlaceholder(int placeholder) {
        return Glide.with(this).asBitmap().apply(new RequestOptions().placeholder(placeholder).error(placeholder));
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
        Glide.with(this).asBitmap().load(uri).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                imageView.setImageBitmap(bitmap);
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                //                mImageView.setImageDrawable(VectorDrawableCompat.create(getResources(), R.drawable.img_placeholder, null));
                super.onLoadFailed(errorDrawable);
                imageView.setImageDrawable(ContextCompat.getDrawable(LeagueMatchApp.this, R.drawable.placeholder_banner));
            }

            @Override
            public void onLoadStarted(@Nullable Drawable placeholder) {
                super.onLoadStarted(placeholder);
                imageView.setImageDrawable(ContextCompat.getDrawable(LeagueMatchApp.this, R.drawable.placeholder_banner));
            }
        });
    }
}