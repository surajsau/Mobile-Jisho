package com.halfplatepoha.jisho.injection.modules;

import com.halfplatepoha.jisho.JDBMigration;
import com.halfplatepoha.jisho.JishoMigration;
import com.halfplatepoha.jisho.injection.*;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by surjo on 20/12/17.
 */

@Module
public class DataModule {

    public static final String APP_REALM_CONFIG = "app_realm_config";
    public static final String JDB_REALM_CONFIG = "jdb_realm_config";

    private static final String JDB_REALM_FILE = "jdb.realm";

    private static final int JDB_VERSION = 1;
    private static final int APP_REALM_VERSION = 1;

    @Provides
    JdbModule jdbModule() {
        return new JdbModule();
    }

    @Provides
    JDBMigration provideJdbMigration() {
        return new JDBMigration();
    }

    @Provides
    JishoMigration provideJishoMigration() {
        return new JishoMigration();
    }

    @Named(JDB_REALM_CONFIG)
    @Provides
    RealmConfiguration jdbRealmConfig(JDBMigration jdbMigration, JdbModule jdbModule) {
        return new RealmConfiguration.Builder()
                .schemaVersion(JDB_VERSION)
                .modules(jdbModule)
                .name(JDB_REALM_FILE)
                .migration(jdbMigration)
                .build();
    }

    @Named(APP_REALM_CONFIG)
    @Provides
    RealmConfiguration appRealmConfiguration(JishoMigration jishoMigration) {
        return new RealmConfiguration.Builder()
                .schemaVersion(APP_REALM_VERSION)
                .migration(jishoMigration)
                .build();
    }

}
