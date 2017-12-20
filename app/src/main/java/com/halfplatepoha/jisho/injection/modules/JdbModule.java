package com.halfplatepoha.jisho.injection.modules;

import com.halfplatepoha.jisho.jdb.Codepoint;
import com.halfplatepoha.jisho.jdb.DicNumber;
import com.halfplatepoha.jisho.jdb.Entry;
import com.halfplatepoha.jisho.jdb.Gloss;
import com.halfplatepoha.jisho.jdb.Kanji;
import com.halfplatepoha.jisho.jdb.Meaning;
import com.halfplatepoha.jisho.jdb.QueryCode;
import com.halfplatepoha.jisho.jdb.Radical;
import com.halfplatepoha.jisho.jdb.Reading;
import com.halfplatepoha.jisho.jdb.Sentence;
import com.halfplatepoha.jisho.jdb.Split;

import io.realm.annotations.RealmModule;

/**
 * Created by surjo on 20/12/17.
 */

@RealmModule(classes = {Codepoint.class, DicNumber.class, Entry.class,
        Gloss.class, Kanji.class, Meaning.class, QueryCode.class,
        Radical.class, Reading.class, Sentence.class, Split.class})
public class JdbModule {}
