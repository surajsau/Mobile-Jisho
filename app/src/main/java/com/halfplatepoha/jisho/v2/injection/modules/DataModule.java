package com.halfplatepoha.jisho.v2.injection.modules;

import android.app.Application;

import com.halfplatepoha.jisho.Jisho;
import com.halfplatepoha.jisho.JishoMigration;
import com.halfplatepoha.jisho.v2.data.DataProvider;
import com.halfplatepoha.jisho.v2.data.IDataProvider;
import com.halfplatepoha.jisho.v2.injection.AppScope;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;

/**
 * Created by surjo on 20/12/17.
 */

@Module
public abstract class DataModule {

    public static final String APP_REALM_CONFIG = "app_realm_config";
    public static final String JDB_REALM_CONFIG = "jdb_realm_config";

    @Binds
    @AppScope
    abstract IDataProvider dataProvider(DataProvider dataProvider);

    @Named(APP_REALM_CONFIG)
    @Provides
    @AppScope
    static Realm realm(Application application) {
        return Realm.getInstance(((Jisho)application).getJdbConfig());
    }

    @Named(JDB_REALM_CONFIG)
    @Provides
    @AppScope
    static Realm jdbRealm(Application application) {
        return Realm.getInstance(((Jisho)application).getJdbConfig());
    }

}
