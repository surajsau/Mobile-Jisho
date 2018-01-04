package com.halfplatepoha.jisho.jdb;

/**
 * Created by surjo on 03/01/18.
 */

public interface Schema {

    interface Entry {
        String ENTRY = "Entry";
        String ENTRY_SEQ = "entrySeq";
        String JAPANESE = "japanese";
        String POS = "pos";
        String GLOSSES = "glosses";
        String COMMON = "common";
        String FURIGANA = "furigana";
        String TAGS = "tags";
        String FIELDS = "fields";
        String KANJI_TAGS = "kanjiTags";
        String DIALECTS = "dialects";
        String KANA_TAGS = "kanaTags";
        String NOTE = "note";
    }

    interface JishoList {
        String JISHOLIST = "JishoList";
        String NAME = "name";
        String ENTRIES = "entries";
    }
}
