package com.halfplatepoha.jisho.v2.detail.adapters;

import com.halfplatepoha.jisho.jdb.Kanji;
import com.halfplatepoha.jisho.jdb.Meaning;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by surjo on 20/01/18.
 */

public class KanjiModel {
    public String literal;
    public List<Meaning> meanings;

    public static class Meaning {

        public String lang;
        public String meaning;
    }

    public static KanjiModel newInstance(Kanji kanji) {
        KanjiModel kanjiModel = new KanjiModel();
        kanjiModel.literal = kanji.literal;

        if(kanji.meanings != null) {
            List<Meaning> meanings = new ArrayList<>();

            for(com.halfplatepoha.jisho.jdb.Meaning meaning : kanji.meanings) {
                Meaning mng = new Meaning();
                mng.lang = meaning.lang;
                mng.meaning = meaning.meaning;

                meanings.add(mng);
            }

            kanjiModel.meanings = meanings;
        }
        return null;
    }
}
