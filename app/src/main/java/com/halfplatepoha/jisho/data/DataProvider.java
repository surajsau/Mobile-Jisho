package com.halfplatepoha.jisho.data;

import com.halfplatepoha.jisho.jdb.Entry;
import com.halfplatepoha.jisho.jdb.JishoList;
import com.halfplatepoha.jisho.jdb.Schema;
import com.halfplatepoha.jisho.jdb.Sentence;

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
    public DataProvider() {
        realm = Realm.getDefaultInstance();
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
