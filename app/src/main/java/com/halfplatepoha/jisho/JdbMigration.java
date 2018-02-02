package com.halfplatepoha.jisho;

import com.halfplatepoha.jisho.jdb.Entry;
import com.halfplatepoha.jisho.jdb.Schema;

import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Created by surjo on 21/01/18.
 */

public class JdbMigration implements RealmMigration {

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

        RealmSchema schema = realm.getSchema();

        if(oldVersion < Jisho.JDB_REALM_VERSION) {
            schema.create(Schema.JishoList.TABLE_NAME)
                    .addField(Schema.JishoList.NAME, String.class, FieldAttribute.REQUIRED)
                    .addRealmListField(Schema.JishoList.ENTRIES, schema.get(Schema.Entry.TABLE_NAME));

            schema.get(Schema.Entry.TABLE_NAME)
                    .addField(Schema.Entry.NOTE, String.class);
        }
    }
}
