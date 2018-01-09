package com.halfplatepoha.jisho.jdb;

/**
 * Created by surjo on 03/01/18.
 */

public interface Schema {

    interface Entry {
        String TABLE_NAME = "Entry";
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
        String TABLE_NAME = "JishoList";
        String NAME = "name";
        String ENTRIES = "entries";
    }

    interface Sentence {
        String TABLE_NAME = "Sentence";
        String SENTENCE = "sentence";
        String SPLITS = "splits";
        String ENGLISH = "english";
    }

    interface Split {
        String TABLE_NAME = "Split";
        String KEYWORD = "keyword";
        String SENSE = "sense";
        String SENTENCE_FORM = "sentenceForm";
        String IS_GOOD = "isGood";
        String READING = "reading";
    }

    interface Kanji {
        String TABLE_NAME = "Kanji";
        String CODEPOINTS = "codepoints";
        String DICNUMBERS = "dicNumbers";
        String FREQ = "freq";
        String GRADE = "grade";
        String LITERAL = "literal";
        String MEANINGS = "meanings";
        String QUERY_CODES = "queryCodes";
        String READINGS = "readings";
        String STROKE_COUNT = "strokeCount";
        String STROKES = "strokes";
        String ELEMENTS = "elements";
    }
}
