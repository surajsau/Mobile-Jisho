package com.halfplatepoha.jisho;

import android.support.v7.app.AppCompatDelegate;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.halfplatepoha.jisho.analytics.Analytics;
import com.halfplatepoha.jisho.v2.injection.DaggerJishoComponent;
import com.halfplatepoha.jisho.v2.injection.JishoComponent;
import com.halfplatepoha.jisho.offline.OfflineDbHelper;
import com.halfplatepoha.jisho.utils.IConstants;
import com.halfplatepoha.jisho.utils.Utils;
import com.thefinestartist.Base;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by surjo on 22/04/17.
 */

public class Jisho extends DaggerApplication {

    public static final int APP_REALM_VERSION = 4;

    @Override
    public void onCreate() {
        super.onCreate();
        initRealm();

        Fabric.with(this, new Crashlytics());
        Fabric.with(this, new Answers());
        Analytics.init(this);
        JishoPreference.init(this, "JishoPref");
        OfflineDbHelper.init(this);
        Base.initialize(this);

        if(!Utils.isFileDowloaded())
            JishoPreference.setInPref(IConstants.PREF_OFFLINE_MODE, false);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        checkAppUpdate();
    }

    private void initRealm() {
        Realm.init(this);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(Jisho.APP_REALM_VERSION)
                .migration(new JishoMigration())
                .build();
        Realm.setDefaultConfiguration(config);
    }

    private void checkAppUpdate() {
        int version = BuildConfig.VERSION_CODE;
        int prevVersion = JishoPreference.getIntFromPref(IConstants.PREF_VERSION_CODE, -1);

        if(version > prevVersion) {
            JishoPreference.setInPref(IConstants.PREF_VERSION_CODE, version);
            JishoPreference.setInPref(IConstants.PREF_SHOW_NEW, false);
        }
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        JishoComponent component = DaggerJishoComponent.builder()
                .application(this)
                .build();

        component.inject(this);

        return component;
    }

}
