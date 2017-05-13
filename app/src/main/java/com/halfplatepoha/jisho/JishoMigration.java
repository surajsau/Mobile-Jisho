package com.halfplatepoha.jisho;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

/**
 * Created by surjo on 13/05/17.
 */

public class JishoMigration implements RealmMigration {

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();

        if(oldVersion == 0) {
            RealmObjectSchema sense = schema.get("FavSense");
            if(sense.hasField("verbType"))
                sense.removeField("verbType");
            oldVersion++;
        }
    }
}
