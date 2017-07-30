package com.halfplatepoha.jisho.analytics;

import android.content.Context;
import android.os.Bundle;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.crashlytics.android.answers.CustomEvent;
import com.crashlytics.android.answers.SearchEvent;
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
        bundle.putString(FirebaseAnalytics.Param.VALUE, param);
        analytics.logEvent(view, bundle);

        CustomEvent event = new CustomEvent(view);
        event.putCustomAttribute(FirebaseAnalytics.Param.VALUE, param);
        Answers.getInstance().logCustom(event);
    }

    public void recordDownload() {
        Bundle bundle = new Bundle();
        analytics.logEvent("track_download", bundle);

        Answers.getInstance().logCustom(new CustomEvent("track_download"));
    }

    public void viewDetails(String value) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.VALUE, value);
        analytics.logEvent("details", bundle);

        Answers.getInstance().logContentView(new ContentViewEvent()
                .putContentName(value));
    }

    public void recordOfflineSwitch(boolean isOffline) {
        Bundle bundle = new Bundle();
        bundle.putInt(FirebaseAnalytics.Param.VALUE, isOffline ? 1 : -1);
        analytics.logEvent("track_offline", bundle);

        CustomEvent event = new CustomEvent("track_offline");
        event.putCustomAttribute(FirebaseAnalytics.Param.VALUE, isOffline ? "on" : "off");
        Answers.getInstance().logCustom(event);
    }

    public void recordSearch(String text) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.VALUE, text);
        bundle.putString(FirebaseAnalytics.Param.SEARCH_TERM, text);
        analytics.logEvent(FirebaseAnalytics.Event.SEARCH, bundle);

        Answers.getInstance().logSearch(new SearchEvent().putQuery(text));
    }

    public void recordDownloadFailure() {
        Bundle bundle = new Bundle();
        analytics.logEvent("track_download", bundle);

        Answers.getInstance().logCustom(new CustomEvent("track_download_failed"));
    }
}
