package com.halfplatepoha.jisho.offline;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.halfplatepoha.jisho.offline.model.ListEntry;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.List;

/**
 * Created by surjo on 23/05/17.
 */

public class OfflineDbHelper extends SQLiteAssetHelper {

    private static final String DB_NAME = "dictionary.db";
    private static final int DB_VERSION = 1;

    private static OfflineDbHelper mInstance;

    public static void init(Context context) {
        if(mInstance == null)
            mInstance = new OfflineDbHelper(context);
    }

    public static OfflineDbHelper getInstance() {
        return mInstance;
    }

    private OfflineDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public List<ListEntry> searchDictionary (String searchTerm, @DbSchema.SearchType int searchType) {
        SQLiteDatabase db = getReadableDatabase();
        String query;

        switch (searchType) {
            case DbSchema.TYPE_KANA:
                break;
            case DbSchema.TYPE_ENGLISH:
                break;
            case DbSchema.TYPE_KANJI:
                break;
            case DbSchema.TYPE_ROMAJI:
                break;
        }
        return null;
    }
}
