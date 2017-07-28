package com.halfplatepoha.jisho.offline;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.halfplatepoha.jisho.offline.model.Entry;
import com.halfplatepoha.jisho.offline.model.KanjiElement;
import com.halfplatepoha.jisho.offline.model.ListEntry;
import com.halfplatepoha.jisho.offline.model.ReadingElement;
import com.halfplatepoha.jisho.offline.model.SenseElement;
import com.halfplatepoha.jisho.offline.utils.DbQueryUtil;
import com.halfplatepoha.jisho.utils.IConstants;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by surjo on 23/05/17.
 */

public class OfflineDbHelper extends SQLiteAssetHelper {

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
        super(context, IConstants.DICTIONARY_FILE_NAME, IConstants.STORAGE_DIRECTORY, null, DB_VERSION);
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

    public Entry getEntry(int id) {
        Entry entry = new Entry();

        try {
            entry.setEntryId(id);
            entry.setKanjiElements(getKanjiElements(id));
            entry.setReadingElements(getReadingElements(id));
            entry.setSenseElementElements(getSenseElements(id));

            return entry;
        } catch (SQLException e) {
            return new Entry();
        }
    }

    private List<SenseElement> getSenseElements(int id) {
        SQLiteDatabase db = getReadableDatabase();
        List<SenseElement> senseElements = new ArrayList<>();

        String[] args = new String[]{Integer.toString(id)};

        Cursor c = null;

        try {
            c = db.rawQuery(DbQueryUtil.getSenseElementsQuery(), args);

            while (c.moveToNext()) {
                SenseElement senseElement = new SenseElement();
                int elementId = c.getInt(c.getColumnIndexOrThrow(DbSchema.SenseTable.Cols._ID));
                String posValue = c.getString(c.getColumnIndexOrThrow("pos_value"));
                String foaValue = c.getString(c.getColumnIndexOrThrow("foa_value"));
                String dialValue = c.getString(c.getColumnIndexOrThrow("dial_value"));
                String glossValue = c.getString(c.getColumnIndexOrThrow("gloss_value"));

                senseElement.setSenseId(elementId);
                senseElement.setPartsOfSpeech(DbQueryUtil.formatString(posValue));
                senseElement.setFieldOfApplication(DbQueryUtil.formatString(foaValue));
                senseElement.setDialect(DbQueryUtil.formatString(dialValue));
                senseElement.setGlosses(DbQueryUtil.formatString(glossValue));

                senseElements.add(senseElement);
            }

            return senseElements;
        } finally {
            if(c != null)
                c.close();
        }
    }

    private List<ReadingElement> getReadingElements(int id) {
        SQLiteDatabase db = getReadableDatabase();
        List<ReadingElement> readingElements = new ArrayList<>();

        String[] args = new String[]{Integer.toString(id)};

        Cursor c = null;

        try{
            c = db.rawQuery(DbQueryUtil.getReadingElementsQuery(), args);

            while(c.moveToNext()) {
                ReadingElement readingElement = new ReadingElement();
                int elementId = c.getInt(c.getColumnIndexOrThrow(DbSchema.ReadingTable.Cols._ID));
                String value = c.getString(c.getColumnIndexOrThrow("read_value"));
                int isTrueReading = c.getInt(c.getColumnIndexOrThrow(DbSchema.ReadingTable.Cols.IS_TRUE_READING));
                String relationValue = c.getString(c.getColumnIndexOrThrow("rel_value"));

                readingElement.setReadingId(elementId);
                readingElement.setValue(value);
                readingElement.setTrueReading(!(isTrueReading == 1)); // Inverse the result
                readingElement.setReadingRelation(DbQueryUtil.formatString(relationValue));

                readingElements.add(readingElement);
            }

            return readingElements;
        } finally {
            if(c != null)
                c.close();
        }
    }

    private List<KanjiElement> getKanjiElements(int id) {
        SQLiteDatabase db = getReadableDatabase();
        List<KanjiElement> kanjiElements = new ArrayList<>();

        String[] projection = {
                DbSchema.KanjiTable.Cols._ID,
                DbSchema.KanjiTable.Cols.VALUE
        };

        String selection = DbSchema.KanjiTable.Cols.ENTRY_ID + " = ?";
        String[] args = new String[]{Integer.toString(id)};

        Cursor c = null;

        try {
            c = db.query(DbSchema.KanjiTable.NAME,
                    projection,
                    selection,
                    args,
                    null,
                    null,
                    null);

            while(c.moveToNext()) {
                KanjiElement kanjiElement = new KanjiElement();
                int elementId = c.getInt(c.getColumnIndexOrThrow(DbSchema.KanjiTable.Cols._ID));
                String value = c.getString(c.getColumnIndexOrThrow(DbSchema.KanjiTable.Cols.VALUE));

                kanjiElement.setKanjiId(elementId);
                kanjiElement.setValue(value);

                kanjiElements.add(kanjiElement);
            }
            return kanjiElements;
        } finally {
            if(c != null)
                c.close();
        }
    }
}
