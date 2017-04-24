package com.halfplatepoha.jisho.analytics;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.halfplatepoha.jisho.Jisho;

/**
 * Created by surjo on 22/04/17.
 */

public class Analytics {

    private static FirebaseAnalytics analytics;
    private static Analytics mInstance;

    public static void init(Context context) {
        analytics = FirebaseAnalytics.getInstance(context);
    }

    public static Analytics getInstance() {
        if(mInstance == null)
            mInstance = new Analytics();
        return mInstance;
    }

    public void recordClick(String view, String param) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, view);
        bundle.putString(FirebaseAnalytics.Param.CONTENT, param);
        analytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    public void recordSearch(String text) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT, text);
        analytics.logEvent(FirebaseAnalytics.Event.SEARCH, bundle);
    }
}
