package com.halfplatepoha.jisho.v2;

import android.app.Application;

public class ExperimentApp extends Application {

    private static RelineDB relineDB;

    private static ExperimentApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static ExperimentApp getInstance() {
        return instance;
    }

    public static RelineDB getDBInstance() {
        if(relineDB == null)
            relineDB = new RelineDB(getInstance());
        return relineDB;
    }

}
