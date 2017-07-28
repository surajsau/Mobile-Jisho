package com.halfplatepoha.jisho;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.crashlytics.android.Crashlytics;
import com.halfplatepoha.jisho.analytics.Analytics;
import com.halfplatepoha.jisho.offline.OfflineDbHelper;
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
        super.onCreate();

        Realm.init(this);
        Realm.setDefaultConfiguration(getConfig());
        Fabric.with(this, new Crashlytics());
        Analytics.init(this);
        JishoPreference.init(this, "JishoPref");
        OfflineDbHelper.init(this);
        Base.initialize(this);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private RealmConfiguration getConfig() {
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .schemaVersion(1)
                .migration(new JishoMigration())
                .build();
        return configuration;
    }
}
