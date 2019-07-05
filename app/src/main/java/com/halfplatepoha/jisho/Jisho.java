package com.halfplatepoha.jisho;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.halfplatepoha.jisho.analytics.Analytics;
import com.halfplatepoha.jisho.offline.OfflineDbHelper;
import com.halfplatepoha.jisho.utils.IConstants;
import com.halfplatepoha.jisho.utils.Utils;
import com.halfplatepoha.jisho.v2.JishoPreference;
import com.halfplatepoha.jisho.v2.realm.JishoMigration;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by surjo on 22/04/17.
 */

public class Jisho extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        Realm.setDefaultConfiguration(getConfig());
        Fabric.with(this, new Crashlytics());
        Fabric.with(this, new Answers());
        Analytics.init(this);
//        JishoPreference.Companion.init(this, "JishoPref");
        OfflineDbHelper.init(this);

//        if(!Utils.isFileDowloaded())
//            JishoPreference.Companion.setInPref(IConstants.PREF_OFFLINE_MODE, false);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        checkAppUpdate();
    }

    private void checkAppUpdate() {
        int version = BuildConfig.VERSION_CODE;
//        int prevVersion = JishoPreference.Companion.getIntFromPref(IConstants.PREF_VERSION_CODE, -1);
//
//        if(version > prevVersion) {
//            JishoPreference.Companion.setInPref(IConstants.PREF_VERSION_CODE, version);
//            JishoPreference.Companion.setInPref(IConstants.PREF_SHOW_NEW, false);
//        }
    }

    private RealmConfiguration getConfig() {
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .schemaVersion(1)
                .migration(new JishoMigration())
                .build();
        return configuration;
    }
}
