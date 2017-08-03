package com.halfplatepoha.jisho;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.clevertap.android.sdk.ActivityLifecycleCallback;
import com.clevertap.android.sdk.CleverTapAPI;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.halfplatepoha.jisho.analytics.Analytics;
import com.halfplatepoha.jisho.offline.OfflineDbHelper;
import com.halfplatepoha.jisho.utils.IConstants;
import com.halfplatepoha.jisho.utils.Utils;
import com.thefinestartist.Base;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by surjo on 22/04/17.
 */

public class Jisho extends Application {

    @Override
    public void onCreate() {
        ActivityLifecycleCallback.register(this);
        super.onCreate();

        Realm.init(this);
        Realm.setDefaultConfiguration(getConfig());
        Fabric.with(this, new Crashlytics());
        Fabric.with(this, new Answers());
        Analytics.init(this);
        JishoPreference.init(this, "JishoPref");
        OfflineDbHelper.init(this);
        Base.initialize(this);

        CleverTapAPI.setDebugLevel(1);

        if(!Utils.isFileDowloaded())
            JishoPreference.setInPref(IConstants.PREF_OFFLINE_MODE, false);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        checkAppUpdate();
    }

    private void checkAppUpdate() {
        int version = BuildConfig.VERSION_CODE;
        int prevVersion = JishoPreference.getIntFromPref(IConstants.PREF_VERSION_CODE, -1);

        if(version > prevVersion) {
            JishoPreference.setInPref(IConstants.PREF_VERSION_CODE, version);
            JishoPreference.setInPref(IConstants.PREF_SHOW_NEW, false);
        }
    }

    private RealmConfiguration getConfig() {
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .schemaVersion(1)
                .migration(new JishoMigration())
                .build();
        return configuration;
    }
}
