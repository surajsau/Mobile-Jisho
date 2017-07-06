package com.halfplatepoha.jisho.offline;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.halfplatepoha.jisho.offline.model.ListEntry;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
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
        String query = null;

        switch (searchType) {
            case DbSchema.TYPE_KANA:
                query = DbQueryUtil.getKanaSearchQuery(true);
                break;
            case DbSchema.TYPE_ENGLISH:
                query = DbQueryUtil.getEnglishSearchQuery(true);
                break;
            case DbSchema.TYPE_KANJI:
                query = DbQueryUtil.getKanjiSearchQuery(true);
                break;
            case DbSchema.TYPE_ROMAJI:
                WanaKanaJava wanaKanaJava = new WanaKanaJava(false);
                searchTerm = wanaKanaJava.toKana(searchTerm);
                query = DbQueryUtil.getKanaSearchQuery(true);
                break;
        }

        String[] argument = new String[]{searchTerm};

        List<ListEntry> results = new ArrayList<>();

        Cursor c = null;

        try {
            c = db.rawQuery(query, argument);
            while (c.moveToNext()) {
                ListEntry result = new ListEntry();
                result.setEntryId(c.getInt(c.getColumnIndexOrThrow(DbSchema.ReadingTable.Cols.ENTRY_ID)));

                String kanji = c.getString(c.getColumnIndexOrThrow("kanji_value"));
                List<String> kanjis = DbQueryUtil.formatString(kanji);

                if(kanjis != null && !kanjis.isEmpty()) {
                    result.setKanji(kanjis.get(0));
                } else {
                    result.setKanji("");
                }

                String reading = c.getString(c.getColumnIndexOrThrow("read_value"));
                List<String> readings = DbQueryUtil.formatString(reading);

                if(readings != null && !readings.isEmpty()) {
                    result.setReading(readings.get(0));
                } else {
                    result.setReading("");
                }

                String gloss = c.getString(c.getColumnIndexOrThrow("gloss_value"));
                result.setGloss(DbQueryUtil.formatString(gloss));

                results.add(result);
            }
            return results;
        } finally {
            if(c != null)
                c.close();
        }
    }
}
