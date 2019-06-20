package com.halfplatepoha.jisho.v2;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ExperimentApp extends Application {

    private static RelineDB relineDB;

    private static ExperimentApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Realm.init(this);
    }

    public static ExperimentApp getInstance() {
        return instance;
    }

    public static RelineDB getDBInstance() {
        if(relineDB == null)
            relineDB = new RelineDB(getInstance());
        return relineDB;
    }

    public RealmConfiguration getRelineRealmConfiguration() {
        return new RealmConfiguration.Builder()
                .name("offline.realm")
                .modules(new OfflineModule())
                .migration(new RelineMigrator())
                .schemaVersion(RelineMigrator.VERSION)
                .build();
    }

}
