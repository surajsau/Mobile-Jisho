package com.halfplatepoha.jisho;

import com.halfplatepoha.jisho.jdb.Entry;
import com.halfplatepoha.jisho.jdb.Schema;

import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
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

        if(oldVersion <= 0) {
            RealmObjectSchema sense = schema.get("FavSense");
            if(sense.hasField("verbType"))
                sense.removeField("verbType");
            oldVersion++;
        }

        if(oldVersion < Jisho.APP_REALM_VERSION) {
            
            schema.get(Schema.Entry.ENTRY)
                    .addField(Schema.Entry.NOTE, String.class);

            schema.create(Schema.JishoList.JISHOLIST)
                    .addField(Schema.JishoList.NAME, String.class, FieldAttribute.REQUIRED)
                    .addRealmListField(Schema.JishoList.ENTRIES, schema.get(Schema.Entry.ENTRY));

        }
    }
}
