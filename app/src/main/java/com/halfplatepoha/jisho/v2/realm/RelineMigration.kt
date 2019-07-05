package com.halfplatepoha.jisho.v2.realm

import io.realm.DynamicRealm
import io.realm.RealmMigration

class RelineMigration : RealmMigration {

    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {

    }

    companion object {
        const val DB_VERSION = 2L
    }

    override fun equals(other: Any?): Boolean {
        return other != null && other is RelineMigration
    }

    override fun hashCode(): Int {
        return 121
    }

}
