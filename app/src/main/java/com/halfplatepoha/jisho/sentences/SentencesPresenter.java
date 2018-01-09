package com.halfplatepoha.jisho.sentences;

import com.halfplatepoha.jisho.base.BasePresenter;
import com.halfplatepoha.jisho.data.IDataProvider;
import com.halfplatepoha.jisho.jdb.Sentence;
import com.halfplatepoha.jisho.v2.detail.adapters.SentenceAdapterContract;
import com.halfplatepoha.jisho.v2.detail.adapters.SentenceAdapterPresenter;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.realm.Realm;

/**
 * Created by surjo on 09/01/18.
 */

public class SentencesPresenter extends BasePresenter<SentencesContract.View> implements SentencesContract.Presenter, SentenceAdapterPresenter.Listener {

    private String japanese;
    private String furigana;

    private List<Sentence> sentences;

    private IDataProvider dataProvider;

    private SentenceAdapterContract.Presenter sentencesAdapterPresenter;

    @Inject
    public SentencesPresenter(SentencesContract.View view,
                              @Named(SentencesActivity.KEY_JAPANESE) String japanese,
                              @Named(SentencesActivity.KEY_FURIGANA) String furigana,
                              IDataProvider dataProvider,
                              SentenceAdapterContract.Presenter sentencesAdapterPresenter) {
        super(view);
        this.furigana = furigana;
        this.japanese = japanese;
        this.dataProvider = dataProvider;
        this.sentencesAdapterPresenter = sentencesAdapterPresenter;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sentencesAdapterPresenter.attachListener(this);

        sentences = dataProvider.getSentences(japanese, furigana);
    }

    @Override
    public void onResume() {
        super.onResume();

        view.setEntry(japanese != null ? japanese : furigana);

        sentencesAdapterPresenter.setSentences(sentences);
    }

    @Override
    public void onStop() {
        if(sentencesAdapterPresenter != null)
            sentencesAdapterPresenter.removeListener();
        super.onStop();
    }

    @Override
    public void onItemClick(Sentence sentence) {
        view.openSentenceDetails(sentence.sentence);
    }
}
