package com.halfplatepoha.jisho.v2.detail;

import android.support.annotation.Nullable;

import com.halfplatepoha.jisho.base.BasePresenter;
import com.halfplatepoha.jisho.jdb.Entry;
import com.halfplatepoha.jisho.jdb.JishoList;
import com.halfplatepoha.jisho.jdb.Schema;
import com.halfplatepoha.jisho.jdb.Sentence;
import com.halfplatepoha.jisho.utils.Utils;
import com.halfplatepoha.jisho.v2.detail.adapters.KanjiAdapterContract;
import com.halfplatepoha.jisho.v2.detail.adapters.KanjiAdapterPresenter;
import com.halfplatepoha.jisho.v2.detail.adapters.SentenceAdapterContract;
import com.halfplatepoha.jisho.v2.detail.adapters.SentenceAdapterPresenter;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by surjo on 28/12/17.
 */

public class DetailsPresenter extends BasePresenter<DetailsContract.View> implements DetailsContract.Presenter,
        KanjiAdapterPresenter.Listener,
        SentenceAdapterPresenter.Listener {

    private Realm realm;

    private String japanese;

    @Nullable
    private String furigana;

    private Entry entry;

    private KanjiAdapterContract.Presenter kanjiAdapterPresenter;

    private SentenceAdapterContract.Presenter sentenceAdapterPresenter;

    private RealmResults<Sentence> sentences;

    private StringBuilder pos;

    private StringBuilder glosses;

    @Inject
    public DetailsPresenter(DetailsContract.View view,
                            Realm realm,
                            KanjiAdapterContract.Presenter kanjiAdapterPresenter,
                            SentenceAdapterContract.Presenter sentenceAdapterPresenter,
                            @Named(DetailsActivity.KEY_JAPANESE) String japanese,
                            @Nullable @Named(DetailsActivity.KEY_FURIGANA) String furigana) {
        super(view);
        this.realm = realm;
        this.japanese = japanese;
        this.furigana = furigana;
        this.kanjiAdapterPresenter = kanjiAdapterPresenter;
        this.sentenceAdapterPresenter = sentenceAdapterPresenter;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        kanjiAdapterPresenter.attachListener(this);
        sentenceAdapterPresenter.attachListener(this);

        RealmQuery<Entry> detailQuery = realm.where(Entry.class)
                .equalTo(Schema.Entry.JAPANESE, japanese);

        if(furigana != null) {
            detailQuery = detailQuery.and()
                    .equalTo(Schema.Entry.FURIGANA, furigana);
        }

        entry = detailQuery.findFirst();

        if(entry != null) {
            sentences = realm.where(Sentence.class)
                    .contains(Schema.Sentence.SENTENCE, entry.japanese)
                    .or()
                    .contains(Schema.Sentence.SENTENCE, entry.furigana)
                    .and()
                    .equalTo(Schema.Sentence.SPLITS + "." + Schema.Split.KEYWORD, entry.japanese)
                    .or()
                    .equalTo(Schema.Sentence.SPLITS + "." + Schema.Split.KEYWORD, entry.furigana)
                    .findAll();

            if(entry.pos != null) {
                pos = new StringBuilder("");
                pos.append(entry.pos.get(0));

                for (int i = 1; i < entry.pos.size(); i++) {
                    pos.append(", ").append(entry.pos.get(i));
                }
            }

            if(entry.glosses != null) {
                glosses = new StringBuilder("");
                glosses.append(entry.glosses.get(0).english);

                for(int i=0; i<entry.glosses.size(); i++) {
                    glosses.append(", ").append(entry.glosses.get(i).english);
                }
            }

        }

    }

    @Override
    public void onResume() {
        populate();
    }

    private void populate() {
        view.setJapanese(japanese);

        if(furigana != null && furigana.length() > 0) {
            view.showFurigana();
            view.setFurigana(furigana);
        }

        if(sentences != null) {
            sentenceAdapterPresenter.setSentences(realm.copyFromRealm(sentences));
        }

        view.setPos(pos.toString());
        view.setGloss(glosses.toString());
    }

    @Override
    public void clickKanjiPlay() {
        List<String> kanjis = Utils.kanjiList(entry.japanese);
        kanjiAdapterPresenter.setKanjis(kanjis);
    }

    @Override
    public void clickExamples() {
        RealmResults<Sentence> sentences = realm.where(Sentence.class).equalTo("splits.keyword", entry.japanese).findAll();

        if(sentences != null && !sentences.isEmpty()) {
            sentenceAdapterPresenter.setKeyword(entry.japanese);
            sentenceAdapterPresenter.setSentences(sentences);
        } else {
            //TODO: hide sentence recycler
        }
    }

    @Override
    public void clickAddNote() {
        //TODO: implemenet adding note
    }

    @Override
    public void clickAddToList() {
        view.openListsScreenForResults();
    }

    @Override
    public void onListNameResultReceived(String listName) {

        JishoList list = realm.where(JishoList.class).equalTo(Schema.JishoList.NAME, listName).findFirst();

        realm.beginTransaction();

        if(list == null) {
            list = realm.createObject(JishoList.class);
            list.name = listName;
        }

        list.entries.add(entry);

        realm.commitTransaction();

    }

    @Override
    public void onStop() {
        super.onStop();
        kanjiAdapterPresenter.removeListener();
        sentenceAdapterPresenter.removeListener();
    }

    @Override
    public void onItemClick(String kanjiLiteral) {
        view.openKanjiDetails(kanjiLiteral);
    }

    @Override
    public void onItemClick(Sentence sentence) {
        view.openSentenceDetail(sentence);
    }
}
