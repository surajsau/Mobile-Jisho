package com.halfplatepoha.jisho;

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

        if(oldVersion < 2) {
//            schema.create("Codepoint")
//                    .addField("cpType", String.class, FieldAttribute.REQUIRED)
//                    .addField("cpValue", String.class, FieldAttribute.REQUIRED)
//                    .setNullable("cpType", true)
//                    .setNullable("cpValue", true);
//
//            schema.create("DicNumber")
//                    .addField("dicRef", String.class, FieldAttribute.REQUIRED)
//                    .setNullable("dicRef", true)
//                    .addField("drType", String.class, FieldAttribute.REQUIRED)
//                    .setNullable("drType", true);
//
//            schema.create("Entry")
//                    .addField("entrySeq", Long.class, FieldAttribute.REQUIRED)
//                    .addField("japanese", String.class, FieldAttribute.REQUIRED)
//                    .addRealmListField("pos", String.class)
//                    .addField("common", Boolean.class, FieldAttribute.REQUIRED)
//                    .addField("furigana", String.class, FieldAttribute.REQUIRED)
//                    .addRealmListField("tags", String.class)
//                    .addRealmListField("fields", String.class)
//                    .addRealmListField("kanjiTags", String.class)
//                    .addRealmListField("dialects", String.class)
//                    .addRealmListField("kanaTags", String.class);
//
//            schema.create("Gloss");
//            schema.create("Kanji");
//            schema.create("Meaning");
//            schema.create("QueryCode");
//            schema.create("Radical");
//            schema.create("Reading");
//            schema.create("Sentence");
//            schema.create("Split");
        }
    }
}
