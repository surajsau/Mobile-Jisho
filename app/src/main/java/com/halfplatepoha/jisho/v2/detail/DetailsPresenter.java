package com.halfplatepoha.jisho.v2.detail;

import com.halfplatepoha.jisho.base.BasePresenter;
import com.halfplatepoha.jisho.jdb.Entry;
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
import io.realm.RealmResults;

/**
 * Created by surjo on 28/12/17.
 */

public class DetailsPresenter extends BasePresenter<DetailsContract.View> implements DetailsContract.Presenter,
        KanjiAdapterPresenter.Listener,
        SentenceAdapterPresenter.Listener {

    private Realm realm;

    private String japanese;

    private Entry entry;

    private KanjiAdapterContract.Presenter kanjiAdapterPresenter;

    private SentenceAdapterContract.Presenter sentenceAdapterPresenter;

    @Inject
    public DetailsPresenter(DetailsContract.View view,
                            Realm realm,
                            KanjiAdapterContract.Presenter kanjiAdapterPresenter,
                            SentenceAdapterContract.Presenter sentenceAdapterPresenter,
                            @Named(DetailsActivity.KEY_JAPANESE) String japanese) {
        super(view);
        this.realm = realm;
        this.japanese = japanese;
        this.kanjiAdapterPresenter = kanjiAdapterPresenter;
        this.sentenceAdapterPresenter = sentenceAdapterPresenter;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        kanjiAdapterPresenter.attachListener(this);
        sentenceAdapterPresenter.attachListener(this);

        entry = realm.where(Entry.class).equalTo("japanese", japanese).findFirst();

        if(entry != null) {
            view.setJapanese(entry.japanese);

            if(entry.furigana != null && entry.furigana.length() > 0) {
                view.showFurigana();
                view.setFurigana(entry.furigana);
            }
        }
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
    public void onStop() {
        super.onStop();
        kanjiAdapterPresenter.removeListener();
        sentenceAdapterPresenter.removeListener();
    }

    @Override
    public void onItemClick(String kanjiLiteral) {
        view.openKanjiDialog(kanjiLiteral);
    }

    @Override
    public void onItemClick(Sentence sentence) {
        view.openSentenceDetail(sentence);
    }
}
