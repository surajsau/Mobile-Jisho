package com.halfplatepoha.jisho.v2.experiment

import android.content.Context
import android.util.Log

import androidx.room.Room
import com.halfplatepoha.jisho.v2.IJishoPreference
import com.halfplatepoha.jisho.v2.KEY_IS_DB_LOADED

import com.halfplatepoha.jisho.v2.room.OfflineDatabase

import java.io.FileOutputStream
import java.io.IOException

class DatabaseProvider(private val appContext: Context,
                       private val jishoPreference: IJishoPreference) {

    //call method that check if database not exists and copy prepopulated file from assets
    val roomDatabase: OfflineDatabase
        get() {
            if(!jishoPreference.getBooleanFromPref(KEY_IS_DB_LOADED))
                copyAttachedDatabase(appContext, DATABASE_NAME)

            return Room.databaseBuilder(appContext,
                    OfflineDatabase::class.java, DATABASE_NAME)
                    .addMigrations(OfflineDatabase.migration_1548319745_to_1548319746)
                    .build()
        }

    companion object {
        private val TAG = DatabaseProvider::class.java.simpleName
        private val DATABASE_NAME = "Japanese4.db"
    }


    private fun copyAttachedDatabase(context: Context, databaseName: String) {
        val dbPath = context.getDatabasePath(databaseName)

        // If the database already exists, return
        if (dbPath.exists()) {
            return
        }

        // Make sure we have a path to the file
        dbPath.parentFile.mkdirs()

        // Try to copy database file
        try {
            val inputStream = context.assets.open("databases/$databaseName")
            val output = FileOutputStream(dbPath)

            val buffer = ByteArray(8192)
            var length: Int = inputStream.read(buffer, 0, 8192)

            while (length > 0) {
                length = inputStream.read(buffer, 0, 8192)
                output.write(buffer, 0, length)
            }

            output.flush()
            output.close()
            inputStream.close()

            jishoPreference.setInPref(KEY_IS_DB_LOADED, true)
            Log.d(TAG, "DONE!!!")
        } catch (e: IOException) {
            jishoPreference.setInPref(KEY_IS_DB_LOADED, false)
            Log.d(TAG, "Failed to open file", e)
            e.printStackTrace()
        } catch (e: Exception) {
            jishoPreference.setInPref(KEY_IS_DB_LOADED, false)
            e.printStackTrace()
        }

    }

}
