package com.halfplatepoha.jisho.v2.data;

import com.halfplatepoha.jisho.jdb.Entry;
import com.halfplatepoha.jisho.jdb.JishoList;
import com.halfplatepoha.jisho.jdb.Kanji;
import com.halfplatepoha.jisho.jdb.Schema;
import com.halfplatepoha.jisho.jdb.Sentence;
import com.halfplatepoha.jisho.utils.Utils;
import com.halfplatepoha.jisho.v2.injection.modules.DataModule;

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by surjo on 09/01/18.
 */

public class DataProvider implements IDataProvider {

    private Realm realm;
    private Realm jdbRealm;

    @Inject
    public DataProvider(@Named(DataModule.APP_REALM_CONFIG) Realm realm,
                        @Named(DataModule.JDB_REALM_CONFIG) Realm jdbRealm) {
        this.realm = realm;
        this.jdbRealm = jdbRealm;
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
    public RealmResults<Entry> getEnglishSearchEntries(String searchString) {
        return jdbRealm.where(Entry.class)
                .equalTo(Schema.Entry.GLOSSES + "." + Schema.Gloss.ENGLISH, searchString)
                .findAll();
    }

    @Override
    public RealmResults<Entry> getEntries(String searchString, boolean common) {

        RealmQuery<Entry> stringMatchQuery = jdbRealm.where(Entry.class)
                .beginGroup()
                .equalTo(Schema.Entry.JAPANESE, searchString)
                .or()
                .contains(Schema.Entry.JAPANESE, searchString)
                .or()
                .contains(Schema.Entry.FURIGANA, searchString)
                .or()
                .equalTo(Schema.Entry.FURIGANA, searchString)
                .endGroup()
                .and()
                .equalTo(Schema.Entry.COMMON, common);

        RealmResults<Entry> relatedEntries = stringMatchQuery
                    .and()
                    .isNotEmpty(Schema.Entry.RELATED)
                    .findAll();

        if(relatedEntries.isEmpty()) {
            relatedEntries = stringMatchQuery
                    .and()
                    .isEmpty(Schema.Entry.RELATED)
                    .findAll();
        }

        return relatedEntries;
    }

    @Override
    public void changeListName(String originalName, String finalName) {
        JishoList list = jdbRealm.where(JishoList.class).equalTo(Schema.JishoList.NAME, originalName).findFirst();

        jdbRealm.beginTransaction();

        if(list != null) {
            list.name = finalName;
        } else {
            list = jdbRealm.createObject(JishoList.class);
            list.name = finalName;
        }

        jdbRealm.insertOrUpdate(list);

        jdbRealm.commitTransaction();
    }

    @Override
    public void deleteList(String name) {
        jdbRealm.beginTransaction();

        jdbRealm.where(JishoList.class).equalTo(Schema.JishoList.NAME, name).findAll().deleteAllFromRealm();

        jdbRealm.commitTransaction();
    }

    @Override
    public void createNewList(String name) {
        jdbRealm.beginTransaction();

        JishoList newList = new JishoList();
        newList.name = name;

        jdbRealm.copyToRealmOrUpdate(newList);

        jdbRealm.commitTransaction();
    }

    @Override
    public long getNewJishoListEntryCount() {
        return jdbRealm.where(JishoList.class).contains(Schema.JishoList.NAME, "New List #").count();
    }

    @Override
    public RealmResults<JishoList> getAllJishoLists() {
        return jdbRealm.where(JishoList.class).findAll();
    }

    @Override
    public JishoList getJishoList(String listName) {
        return jdbRealm.where(JishoList.class).equalTo(Schema.JishoList.NAME, listName).findFirst();
    }

    private RealmQuery<JishoList> getJishoListQuery(String listName) {
        return jdbRealm.where(JishoList.class).equalTo(Schema.JishoList.NAME, listName);
    }

    private RealmQuery<Entry> getEntryQuery(String japanese, String furigana) {
        RealmQuery<Entry> detailQuery = jdbRealm.where(Entry.class)
                .equalTo(Schema.Entry.JAPANESE, japanese);

        if(furigana != null) {
            detailQuery = detailQuery.and()
                    .equalTo(Schema.Entry.FURIGANA, furigana);
        }

        return detailQuery;
    }

    private RealmQuery<Sentence> getEntrySentencesQuery(String japanese, String furigana) {
        return jdbRealm.where(Sentence.class)
                .contains(Schema.Sentence.SENTENCE, japanese)
                .or()
                .contains(Schema.Sentence.SENTENCE, furigana)
                .and()
                .equalTo(Schema.Sentence.SPLITS + "." + Schema.Split.KEYWORD, japanese)
                .or()
                .equalTo(Schema.Sentence.SPLITS + "." + Schema.Split.KEYWORD, furigana);
    }
}
