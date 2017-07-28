package com.halfplatepoha.jisho.utils;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.util.TypedValue;

import com.afollestad.materialdialogs.MaterialDialog;
import com.halfplatepoha.jisho.R;

/**
 * Created by surjo on 13/05/17.
 */

public class UIUtils {

    public static final int TOOLBAR_HEIGHT_DP = 50;
    public static final int HEADER_HEIGHT_DP = 150;
    public static final int TOOLBAR_THRESHOLD_DP = HEADER_HEIGHT_DP - TOOLBAR_HEIGHT_DP;

    public static int convertPxToDp(Context context, int px) {
        int dp = (int)(px / context.getResources().getDisplayMetrics().density);
        return dp;
    }

    public static void showNewItemsDialog(Context context, String title, @LayoutRes int layoutRes, String buttonString, MaterialDialog.SingleButtonCallback buttonAction) {
        new MaterialDialog.Builder(context)
                .title(title)
                .customView(layoutRes, true)
                .positiveText(R.string.ok)
                .positiveText(buttonString)
                .onPositive(buttonAction)
                .build()
                .show();
    }
}
