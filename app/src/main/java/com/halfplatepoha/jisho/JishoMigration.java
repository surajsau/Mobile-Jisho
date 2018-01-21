package com.halfplatepoha.jisho;

import com.halfplatepoha.jisho.jdb.Schema;

import javax.inject.Inject;

import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

/**
 * Created by surjo on 13/05/17.
 */

public class JishoMigration implements RealmMigration {

    @Inject
    public JishoMigration() {}

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
            
            schema.get(Schema.Entry.TABLE_NAME)
                    .addField(Schema.Entry.NOTE, String.class);

            schema.create(Schema.JishoList.TABLE_NAME)
                    .addField(Schema.JishoList.NAME, String.class, FieldAttribute.REQUIRED)
                    .addRealmListField(Schema.JishoList.ENTRIES, schema.get(Schema.Entry.TABLE_NAME));

        }
    }
}
