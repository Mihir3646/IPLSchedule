package com.leaugematchschedule.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.leaugematchschedule.R;
import com.leaugematchschedule.util.Utils;

import pub.devrel.easypermissions.EasyPermissions;


/**
 * Base Activity class performing common functions and providing common features to the child activities.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Gets the fragment manager object of activity required for fragment transaction
     * <p>This method can be customised on the need of application,in which it returns {@link android.support.v4.app.FragmentManager} or {@link android.support.v4.app.FragmentManager}</p>
     *
     * @return object of {@link android.support.v4.app.FragmentManager} or {@link android.support.v4.app.FragmentManager}
     */
    public android.support.v4.app.FragmentManager getLocalFragmentManager() {
        return this.getSupportFragmentManager();
    }

    public android.support.v4.app.Fragment getCurrentFragmentFromStack() {
        return getLocalFragmentManager().findFragmentById(R.id.content_main);
        //        return fragment;
    }

    /***
     * Add new fragment in given container.
     * <p>
     * This method will add new fragment in container and hide the current fragment.
     * And also will add current fragment in backstack.
     * </p>
     *
     * @param newFragment  This parameter will take new fragment name which need to be add.
     * @param hideFragment This parameter will take fragmnet name which you want to hide.
     */
    public void addFragment(final Fragment newFragment, final Fragment hideFragment) {
        Utils.hideSoftKeyBoard(this);
        getLocalFragmentManager()
                .beginTransaction()
                .add(R.id.content_main, newFragment, newFragment.getClass().getSimpleName())
                .hide(hideFragment)
                .addToBackStack(hideFragment.getClass().getSimpleName())
                .commit();
    }

    /**
     * removes current fragment from container and replace with the new Fragment recieves in parameter
     *
     * @param newFragment a fragment object that replaces current fragment
     */
    public void replaceFragment(final Fragment newFragment) {
        Utils.hideSoftKeyBoard(this);
        getLocalFragmentManager()
                .beginTransaction()
                .replace(R.id.content_main, newFragment, newFragment.getClass().getSimpleName())
                .commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}