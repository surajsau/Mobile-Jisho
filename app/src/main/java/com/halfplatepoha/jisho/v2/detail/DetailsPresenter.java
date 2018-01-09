package com.halfplatepoha.jisho.v2.detail;

import android.support.annotation.Nullable;

import com.halfplatepoha.jisho.base.BasePresenter;
import com.halfplatepoha.jisho.data.DataProvider;
import com.halfplatepoha.jisho.data.IDataProvider;
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
        KanjiAdapterPresenter.Listener {

    private String japanese;

    @Nullable
    private String furigana;

    private Entry entry;

    private KanjiAdapterContract.Presenter kanjiAdapterPresenter;

    private List<String> kanjis;

    private StringBuilder pos;

    private StringBuilder glosses;

    long sentenceCount;

    private IDataProvider dataProvider;

    @Inject
    public DetailsPresenter(DetailsContract.View view,
                            KanjiAdapterContract.Presenter kanjiAdapterPresenter,
                            @Named(DetailsActivity.KEY_JAPANESE) String japanese,
                            @Nullable @Named(DetailsActivity.KEY_FURIGANA) String furigana,
                            IDataProvider dataProvider) {
        super(view);
        this.japanese = japanese;
        this.furigana = furigana;
        this.kanjiAdapterPresenter = kanjiAdapterPresenter;
        this.dataProvider = dataProvider;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        kanjiAdapterPresenter.attachListener(this);

        entry = dataProvider.getEntry(japanese, furigana);

        if(entry != null) {

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

            kanjis = Utils.kanjiList(entry.japanese);

            sentenceCount = dataProvider.sentencesCount(entry.japanese, entry.furigana);

        }

        populate();

    }

    private void populate() {
        view.setJapanese(japanese);

        if(furigana != null && furigana.length() > 0) {
            view.showFurigana();
            view.setFurigana(furigana);
        }

        view.setPos(pos.toString());
        view.setGloss(glosses.toString());

        if(kanjis != null) {
            view.showKanjiContainer();
            kanjiAdapterPresenter.setKanjis(kanjis);
        }

        if(sentenceCount > 0) {
            view.showExamplesContainer();
            view.setExamplesCount(sentenceCount);
        }
    }

    @Override
    public void clickKanjiPlay() {
        List<String> kanjis = Utils.kanjiList(entry.japanese);
        kanjiAdapterPresenter.setKanjis(kanjis);
    }

    @Override
    public void clickExamplesContainer() {
        view.openSentencesScreen(entry.japanese, entry.furigana);
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
        dataProvider.addEntryToList(entry, listName);
    }

    @Override
    public void onStop() {
        super.onStop();
        kanjiAdapterPresenter.removeListener();
    }

    @Override
    public void onItemClick(String kanjiLiteral) {
        view.openKanjiDetails(kanjiLiteral);
    }

}
