package com.halfplatepoha.jisho.v2;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;

public class RelineMigrator implements RealmMigration {

    public static final long VERSION = 2L;

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        if(oldVersion == 1L) {
            realm.getSchema().get("EntryReadings").removePrimaryKey();
        }
    }

}
