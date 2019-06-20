package com.halfplatepoha.jisho.v2;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;

public class RelineMigrator implements RealmMigration {

    public static final long VERSION = 1L;

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

    }

}
