package com.halfplatepoha.jisho;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.halfplatepoha.jisho.analytics.Analytics;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;

/**
 * Created by surjo on 22/04/17.
 */

public class Jisho extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        Fabric.with(this, new Crashlytics());
        Analytics.init(this);
    }
}
