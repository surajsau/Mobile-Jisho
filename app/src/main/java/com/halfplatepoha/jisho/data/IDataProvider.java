package com.halfplatepoha.jisho.data;

import com.halfplatepoha.jisho.jdb.Entry;
import com.halfplatepoha.jisho.jdb.Sentence;

import java.util.List;

/**
 * Created by surjo on 09/01/18.
 */

public interface IDataProvider {

    Entry getEntry(String japanese, String furigana);

    long sentencesCount(String japanese, String furigana);

    List<Sentence> getSentences(String japanese, String furigana);

    void addEntryToList(Entry entry, String listName);

}
