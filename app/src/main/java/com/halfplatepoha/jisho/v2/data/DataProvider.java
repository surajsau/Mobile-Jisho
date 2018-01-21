package com.halfplatepoha.jisho.v2.data;

import com.halfplatepoha.jisho.jdb.Entry;
import com.halfplatepoha.jisho.jdb.JishoList;
import com.halfplatepoha.jisho.jdb.Kanji;
import com.halfplatepoha.jisho.jdb.Schema;
import com.halfplatepoha.jisho.jdb.Sentence;
import com.halfplatepoha.jisho.utils.Utils;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by surjo on 09/01/18.
 */

public class DataProvider implements IDataProvider {

    private Realm realm;

    @Inject
    public DataProvider(Realm realm) {
        this.realm = realm;
    }


    @Override
    public Entry getEntry(String japanese, String furigana) {
        return getEntryQuery(japanese, furigana).findFirst();
    }

    @Override
    public long sentencesCount(String japanese, String furigana) {
        return getEntrySentencesQuery(japanese, furigana).count();
    }

    @Override
    public List<Sentence> getSentences(String japanese, String furigana) {
        RealmResults<Sentence> sentences = getEntrySentencesQuery(japanese, furigana).findAll();
        return realm.copyFromRealm(sentences);
    }

    @Override
    public void addEntryToList(Entry entry, String listName) {
        JishoList list = getJishoListQuery(listName).findFirst();

        realm.beginTransaction();

        list.entries.add(entry);

        realm.commitTransaction();
    }

    @Override
    public List<Kanji> getKanjis(String japanese) {
        return realm.copyFromRealm(realm.where(Kanji.class).in(Schema.Kanji.LITERAL, Utils.kanjiList(japanese)).findAll());
    }

    @Override
    public void writeCopyTo(File realmFile) {
        realm.writeCopyTo(realmFile);
    }

    @Override
    public RealmResults<Entry> getEntries(String searchString) {
        return realm.where(Entry.class)
                .equalTo(Schema.Entry.JAPANESE, searchString)
                .or()
                .contains(Schema.Entry.JAPANESE, searchString)
                .or()
                .contains(Schema.Entry.FURIGANA, searchString)
                .or()
                .equalTo(Schema.Entry.FURIGANA, searchString)
                .findAll();
    }

    @Override
    public void changeListName(String originalName, String finalName) {
        JishoList list = realm.where(JishoList.class).equalTo(Schema.JishoList.NAME, originalName).findFirst();

        realm.beginTransaction();

        if(list != null) {
            list.name = finalName;
        } else {
            list = realm.createObject(JishoList.class);
            list.name = finalName;
        }

        realm.insertOrUpdate(list);

        realm.commitTransaction();
    }

    @Override
    public void deleteList(String name) {
        realm.beginTransaction();

        realm.where(JishoList.class).equalTo(Schema.JishoList.NAME, name).findAll().deleteAllFromRealm();

        realm.commitTransaction();
    }

    @Override
    public void createNewList(String name) {
        realm.beginTransaction();

        JishoList newList = new JishoList();
        newList.name = name;

        realm.copyToRealmOrUpdate(newList);

        realm.commitTransaction();
    }

    @Override
    public long getNewJishoListEntryCount() {
        return realm.where(JishoList.class).contains(Schema.JishoList.NAME, "New List #").count();
    }

    @Override
    public RealmResults<JishoList> getAllJishoLists() {
        return realm.where(JishoList.class).findAll();
    }

    @Override
    public JishoList getJishoList(String listName) {
        return realm.where(JishoList.class).equalTo(Schema.JishoList.NAME, listName).findFirst();
    }

    private RealmQuery<JishoList> getJishoListQuery(String listName) {
        return realm.where(JishoList.class).equalTo(Schema.JishoList.NAME, listName);
    }

    private RealmQuery<Entry> getEntryQuery(String japanese, String furigana) {
        RealmQuery<Entry> detailQuery = realm.where(Entry.class)
                .equalTo(Schema.Entry.JAPANESE, japanese);

        if(furigana != null) {
            detailQuery = detailQuery.and()
                    .equalTo(Schema.Entry.FURIGANA, furigana);
        }

        return detailQuery;
    }

    private RealmQuery<Sentence> getEntrySentencesQuery(String japanese, String furigana) {
        return realm.where(Sentence.class)
                .contains(Schema.Sentence.SENTENCE, japanese)
                .or()
                .contains(Schema.Sentence.SENTENCE, furigana)
                .and()
                .equalTo(Schema.Sentence.SPLITS + "." + Schema.Split.KEYWORD, japanese)
                .or()
                .equalTo(Schema.Sentence.SPLITS + "." + Schema.Split.KEYWORD, furigana);
    }
}
