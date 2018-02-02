package com.halfplatepoha.jisho;

import com.halfplatepoha.jisho.jdb.Codepoint;
import com.halfplatepoha.jisho.jdb.DicNumber;
import com.halfplatepoha.jisho.jdb.Entry;
import com.halfplatepoha.jisho.jdb.Gloss;
import com.halfplatepoha.jisho.jdb.JishoList;
import com.halfplatepoha.jisho.jdb.Kanji;
import com.halfplatepoha.jisho.jdb.KanjiElement;
import com.halfplatepoha.jisho.jdb.Meaning;
import com.halfplatepoha.jisho.jdb.QueryCode;
import com.halfplatepoha.jisho.jdb.Radical;
import com.halfplatepoha.jisho.jdb.Reading;
import com.halfplatepoha.jisho.jdb.Sentence;
import com.halfplatepoha.jisho.jdb.Split;

import io.realm.annotations.RealmModule;

/**
 * Created by surjo on 21/01/18.
 */

@RealmModule(classes = {Codepoint.class, DicNumber.class,
        Entry.class, Gloss.class,
        JishoList.class, Kanji.class,
        KanjiElement.class, Meaning.class,
        QueryCode.class, Radical.class,
        Reading.class, Sentence.class, Split.class})
public class JdbRealmModule {
}
