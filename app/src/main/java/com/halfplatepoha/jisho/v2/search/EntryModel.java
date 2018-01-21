package com.halfplatepoha.jisho.v2.search;

import com.halfplatepoha.jisho.apimodel.Sense;
import com.halfplatepoha.jisho.apimodel.Word;
import com.halfplatepoha.jisho.jdb.Entry;
import com.halfplatepoha.jisho.jdb.Gloss;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by surjo on 20/01/18.
 */

public class EntryModel {
    public String japanese;
    public String furigana;
    public List<Gloss> glosses;
    public boolean common;

    public static class Gloss {

        public String english;
    }

    public static EntryModel newInstance(Entry entry) {
        EntryModel entryModel = new EntryModel();
        entryModel.japanese = entry.japanese;
        entryModel.furigana = entry.furigana;
        entryModel.common = entry.common;

        if(entry.glosses != null) {
            List<Gloss> glosses = new ArrayList<>();

            for(com.halfplatepoha.jisho.jdb.Gloss entryGloss : entry.glosses) {
                Gloss gloss = new Gloss();
                gloss.english = entryGloss.english;
                glosses.add(gloss);
            }

            entryModel.glosses = glosses;
        }

        return entryModel;
    }

    public static EntryModel newInstance(Word word) {
        EntryModel entryModel = new EntryModel();
        entryModel.japanese = word.getJapanese().get(0).getWord();
        entryModel.furigana = word.getJapanese().get(0).getReading();
        entryModel.common = word.is_common();

        if(word.getSenses() != null) {
            List<Gloss> glosses = new ArrayList<>();
            for(Sense sense : word.getSenses()) {
                Gloss gloss = new Gloss();
                gloss.english = sense.getEnglish_definitions().get(0);
                glosses.add(gloss);
            }

            entryModel.glosses = glosses;
        }

        return entryModel;
    }
}
