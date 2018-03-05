package com.leaugematchschedule.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;

/**
 * Manages shortcut menu options on long press of app icon in Mobile device.
 * <p>
 * This feature is available from Android 7.1 and above.
 * <p>
 * Due to unavailability of Android 7.1 device the below methods are commented because this function is tested into Android Emulators only.
 * We can deploy this feature by uncommenting below methods & after proper testing in real Android devices.
 */
public class AppShortcutManager {
    private final static String INTENT_KEY_APP_SHORTCUT_MENU = "INTENT_KEY_APP_SHORTCUT_MENU";

    /**
     * Sets the app shortcut.
     */
    public static void setShortcutMenu(final Context mContext, final Class<? extends Activity> mActivityToOpen) {
        if (mContext == null || mActivityToOpen == null) {
            throw new IllegalArgumentException("null argument is not allowed.");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            final ShortcutManager shortcutManager = mContext.getSystemService(ShortcutManager.class);

            if (shortcutManager != null && shortcutManager.getDynamicShortcuts().size() == 0) {
                final ArrayList<ShortcutInfo> list = new ArrayList<>();

                //                final ShortcutInfo shortcutTopUp = createShortcut(mContext, 1, mContext.getString(R.string.menu_do_top_up), R.drawable.ic_mobile_topup, mActivityToOpen);
                //                final ShortcutInfo shortcutInternationalTopUp = createShortcut(mContext, 2, mContext.getString(R.string.menu_international_top_up), R.drawable.ic_itn_mobile_topup, mActivityToOpen);
                //                final ShortcutInfo shortcutRedeemVoucher = createShortcut(mContext, 3, mContext.getString(R.string.menu_redeem_voucher), R.drawable.ic_redeem, mActivityToOpen);

                //                list.add(shortcutTopUp);
                //                list.add(shortcutRedeemVoucher);
                //                list.add(shortcutInternationalTopUp);

                shortcutManager.setDynamicShortcuts(list);
            }
        }
    }

    private static void initShortCut(Context mContext, Class<? extends Activity> mActivityToOpen, ShortcutManager mShortcutManager) {
    }

    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    @NonNull
    private static ShortcutInfo createShortcut(final Context context, int i, final String label, final int resourceIcon, final Class<? extends Activity> activityToOpen) {
        final Intent intent = new Intent(context, activityToOpen);
        intent.setAction(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(INTENT_KEY_APP_SHORTCUT_MENU, label);

        final ShortcutInfo.Builder builder = new ShortcutInfo.Builder(context, label);
        builder.setShortLabel(label);
        builder.setLongLabel(label);
        builder.setIcon(Icon.createWithResource(context, resourceIcon));
        builder.setRank(i);
        builder.setIntent(intent);

        return builder.build();
    }

    /**
     * Removes all the app shortcut.
     */
    public static void removeAllShortcutMenu(final Context mContext) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                final ShortcutManager shortcutManager = mContext.getSystemService(ShortcutManager.class);

                ArrayList<String> mList = new ArrayList<>();
                //                mList.add(mContext.getString(R.string.menu_do_top_up));
                //                mList.add(mContext.getString(R.string.menu_redeem_voucher));
                //                mList.add(mContext.getString(R.string.menu_international_top_up));

                if (shortcutManager != null) {
                    shortcutManager.removeAllDynamicShortcuts();
                    shortcutManager.disableShortcuts(mList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
