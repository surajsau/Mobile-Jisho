package com.halfplatepoha.jisho.injection.modules;

import com.halfplatepoha.jisho.JishoMigration;
import com.halfplatepoha.jisho.data.DataProvider;
import com.halfplatepoha.jisho.data.IDataProvider;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by surjo on 20/12/17.
 */

@Module
public abstract class DataModule {

    public static final String APP_REALM_CONFIG = "app_realm_config";
    public static final String JDB_REALM_CONFIG = "jdb_realm_config";

    @Provides
    static JishoMigration provideJishoMigration() {
        return new JishoMigration();
    }

    @Binds
    abstract IDataProvider dataProvider(DataProvider dataProvider);

}
