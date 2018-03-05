package com.leaugematchschedule.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.leaugematchschedule.R;

/**
 * Created by M.T on 30/1/18.
 * Purpose of this Class is to display different dialog in application for example : Progress Dialog, Information Dialog
 */
public class DisplayDialog {
    private static DisplayDialog ourInstance = new DisplayDialog();
    /**
     * A Progress Dialog object
     */
    public Dialog progressDialog;

    /**
     * A custom dialog object for No permission
     */
    private Dialog noPermissionDialog;

    /**
     * A custom dialog object for call or send text
     */
    private Dialog fullImagesDialog;

    /**
     * A custom dialog object for Common
     */
    private Dialog commonDialog;

    /**
     * A custom dialog object for Guest sign in
     */
    private Dialog guestSignInDialog;

    private DisplayDialog() {
    }

    public static DisplayDialog getInstance() {
        return ourInstance;
    }
    //    private static ProgressDialog mDialog;

    /**
     * Displays the progress dialog on activity.
     * This method will generate progress dialog and displays it on screen if its not currently showing,
     * If the progressbar dialog already been showing than it will not generate new dialog and return old generated dialog.
     *
     * @param mContext requires to create ProgressDialog in Application
     * @return Returns the object of Progress dialog that currently generated or previously generated.
     */
    public void showProgressDialog(final Context mContext) {
        if (mContext != null) {
            if (progressDialog == null) {
                progressDialog = new Dialog(mContext);
                progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            }

            if (progressDialog.getWindow() != null) {
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }

            progressDialog.setContentView(R.layout.layout_progress);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            final ImageView iv = progressDialog.findViewById(R.id.ivProgress);
            Glide.with(mContext).asGif().load(R.drawable.placeholder_banner1).into(iv);

            if (((Activity) mContext).isFinishing() && progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }
        }
    }

    /**
     * Dismiss Progress dialog if it is showing
     */
    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    //    /**
    //     * Displays the dialog for no permission.
    //     *
    //     * @param mContext mContext
    //     */
    //    public void showNoPermissionDialog(final Context mContext) {
    //        if (mContext != null) {
    //            noPermissionDialog = new Dialog(mContext);
    //            noPermissionDialog.setContentView(R.layout.dialog_no_permission);
    //            noPermissionDialog.setCancelable(true);
    //            noPermissionDialog.setCanceledOnTouchOutside(false);
    //
    //            if (noPermissionDialog.getWindow() != null) {
    //                noPermissionDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    //                noPermissionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    //            }
    //
    //            final ImageView ivClose = noPermissionDialog.findViewById(R.id.dialog_no_permission_ivClose);
    //
    //            ivClose.setOnClickListener(new OnClickListenerWrapper(new View.OnClickListener() {
    //                @Override
    //                public void onClick(View mView) {
    //                    dismissNoPermissionDialog();
    //                }
    //            }));
    //
    //            if (noPermissionDialog != null && !noPermissionDialog.isShowing()) {
    //                noPermissionDialog.show();
    //            }
    //        }
    //    }
    //
    //    /**
    //     * Dismiss no permission dialog if it is showing
    //     */
    //    private void dismissNoPermissionDialog() {
    //        if (noPermissionDialog != null && noPermissionDialog.isShowing()) {
    //            noPermissionDialog.dismiss();
    //            noPermissionDialog = null;
    //        }
    //    }

    public interface DialogButtonListener {
        void onPositiveButtonClicked(final String message);

        void onNegativeButtonClicked();
    }
}
