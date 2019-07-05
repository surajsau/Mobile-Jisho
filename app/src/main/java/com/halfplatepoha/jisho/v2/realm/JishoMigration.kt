package com.halfplatepoha.jisho.v2.realm

import io.realm.DynamicRealm
import io.realm.RealmMigration

/**
 * Created by surjo on 13/05/17.
 */

class JishoMigration : RealmMigration {

    companion object {
        const val DB_VERSION = 3L
    }

    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        val schema = realm.schema

        if (oldVersion == 0L) {
            val sense = schema.get("FavSense")
            if (sense!!.hasField("verbType"))
                sense.removeField("verbType")
        }

        if(oldVersion < 3L) {
            schema.create("HistoryV2")
                    .addField("id", String::class.java)
                    .addField("timestamp", Long::class.java).setNullable("timestamp", true)
                    .addField("word", String::class.java)
            schema.create("FavoriteV2")
                    .addField("id", String::class.java)
                    .addField("word", String::class.java)
                    .addField("furigana", String::class.java)
        }
    }

    override fun equals(other: Any?): Boolean {
        return other != null && other is JishoMigration
    }

    override fun hashCode(): Int {
        return 122
    }
}
