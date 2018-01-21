package com.halfplatepoha.jisho.v2.data;

import com.halfplatepoha.jisho.jdb.Entry;
import com.halfplatepoha.jisho.jdb.JishoList;
import com.halfplatepoha.jisho.jdb.Kanji;
import com.halfplatepoha.jisho.jdb.Sentence;

import java.io.File;
import java.util.List;

import io.realm.RealmResults;

/**
 * Created by surjo on 09/01/18.
 */

public interface IDataProvider {

    Entry getEntry(String japanese, String furigana);

    long sentencesCount(String japanese, String furigana);

    List<Sentence> getSentences(String japanese, String furigana);

    void addEntryToList(Entry entry, String listName);

    List<Kanji> getKanjis(String japanese);

    void writeCopyTo(File realmFile);

    RealmResults<Entry> getEntries(String searchString);

    void changeListName(String originalName, String finalName);

    void deleteList(String name);

    void createNewList(String name);

    long getNewJishoListEntryCount();

    RealmResults<JishoList> getAllJishoLists();

    JishoList getJishoList(String listName);

}
