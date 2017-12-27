package com.halfplatepoha.jisho.injection.modules;

import com.halfplatepoha.jisho.JishoMigration;

import dagger.Module;
import dagger.Provides;

/**
 * Created by surjo on 20/12/17.
 */

@Module
public class DataModule {

    public static final String APP_REALM_CONFIG = "app_realm_config";
    public static final String JDB_REALM_CONFIG = "jdb_realm_config";

    @Provides
    JishoMigration provideJishoMigration() {
        return new JishoMigration();
    }

}
