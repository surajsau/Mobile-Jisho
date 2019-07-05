package com.halfplatepoha.jisho.v2.room

import android.content.Context
import android.database.Cursor
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteQuery
import com.halfplatepoha.jisho.v2.room.dao.EntryDao
import com.halfplatepoha.jisho.v2.room.entity.*

@Database(entities = [Counter::class, Entry::class, Example::class, Kana::class, Kanji::class, Radicals::class],
        version = OfflineDatabase.VERSION)
abstract class OfflineDatabase : RoomDatabase() {

    abstract fun entryDao(): EntryDao

    companion object {

        const val PREVIOUS_VERSION = 1548319745
        const val VERSION = 1548319746

        @JvmField
        val migration_1548319745_to_1548319746: Migration = object : Migration(1548319745, 1548319746) {
            override fun migrate(database: SupportSQLiteDatabase) {}
        }

    }

    override fun query(query: String?, args: Array<out Any>?): Cursor {
        Log.e("OfflineDB", query)
        return super.query(query, args)
    }

    override fun query(query: SupportSQLiteQuery?): Cursor {
        Log.e("OfflineDB", query?.sql?:"".plus(" ").plus(query?.argCount))
        return super.query(query)
    }

}