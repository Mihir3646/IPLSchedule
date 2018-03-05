package com.leaugematchschedule.fragment;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leaugematchschedule.R;
import com.leaugematchschedule.activity.BaseActivity;
import com.leaugematchschedule.util.Constants;
import com.leaugematchschedule.util.Utils;

import io.reactivex.disposables.CompositeDisposable;


/**
 * Base Fragment
 * Created by M.T. on 30/11/17.
 */
public abstract class BaseFragment extends Fragment
        implements View.OnClickListener {
    //        , GPSTracker.LocationChangeListener {

    //    public GPSTracker mGPSTracker;

    public CompositeDisposable mCompositeDisposable;

    private long mLastClickTime = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //        mGPSTracker = new GPSTracker(getActivity(), this);
        mCompositeDisposable = new CompositeDisposable();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public abstract void initView(View view);

    public abstract void trackScreen();

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        trackScreen();
    }

    /**
     * Gets the fragment manager object of activity required for fragment transaction
     * <p>This method can be customised on the need of application,in which it returns {@link FragmentManager} or {@link android.support.v4.app.FragmentManager}</p>
     *
     * @return object of {@link FragmentManager} or {@link android.support.v4.app.FragmentManager}
     */
    public FragmentManager getLocalFragmentManager() {
        return ((BaseActivity) getActivity()).getLocalFragmentManager();
    }

    /**
     * Gets the child fragment manager object of fragment required for fragment transaction
     * <p>This method can be customised on the need of application,in which it returns {@link FragmentManager} or {@link android.support.v4.app.FragmentManager}</p>
     *
     * @return object of {@link FragmentManager} or {@link android.support.v4.app.FragmentManager}
     */
    public FragmentManager getLocalChildFragmentManager() {
        return this.getChildFragmentManager();
    }

    @Override
    public void onClick(View v) {
        Utils.hideSoftKeyBoard(getActivity());
        /*
         * Logic to Prevent the Launch of the Fragment Twice if User makes
         * the Tap(Click) very Fast.
         */
        if (SystemClock.elapsedRealtime() - mLastClickTime < Constants.MAX_CLICK_INTERVAL) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
    }

    /**
     * removes current fragment from container and replace with the new Fragment recieves in parameter
     *
     * @param newFragment  a fragment object that replaces current fragment
     * @param container_id id of container in which you want to replace fragment
     */
    public void replaceChildFragment(final Fragment newFragment, final int container_id) {
        getLocalChildFragmentManager().beginTransaction().replace(container_id, newFragment, newFragment.getClass().getSimpleName()).commit();
    }

    /**
     * show snackBar
     *
     * @param view         view for SnackBar
     * @param msg          message to show in SnackBar
     * @param bottomMargin bottom margin if required
     */
    public void showSnackBar(final View view, final String msg, final int bottomMargin) {
        //        if (msg.equalsIgnoreCase("401")) {
        //            displayLogOutFromOtherDevicesDialog();
        //        } else {
        final Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT);
        final View group = snackbar.getView();
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) group.getLayoutParams();
        params.setMargins(0, 0, 0, bottomMargin);
        group.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorBlackTransDark));
        group.setLayoutParams(params);
        snackbar.show();
        //        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }

    //    @Override
    //    public void onLocationChange(Location mLocation) {
    //    }
}
