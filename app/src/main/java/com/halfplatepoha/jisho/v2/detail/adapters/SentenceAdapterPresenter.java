package com.halfplatepoha.jisho.v2.detail.adapters;

import com.halfplatepoha.jisho.base.BaseAdapterPresenter;
import com.halfplatepoha.jisho.jdb.Sentence;

import io.realm.RealmResults;

/**
 * Created by surjo on 28/12/17.
 */

public class SentenceAdapterPresenter extends BaseAdapterPresenter<SentenceAdapterContract.View> implements SentenceAdapterContract.Presenter {

    private RealmResults<Sentence> sentences;

    private String keyword;

    @Override
    public void onBind(SentenceAdapterContract.View viewHolder, int position) {

    }

    @Override
    public int getItemCount() {
        return sentences != null ? sentences.size() : 0;
    }

    @Override
    public void setSentences(RealmResults<Sentence> sentences) {
        this.sentences = sentences;
        adapterInterface.itemRangeInserted(0, this.sentences.size());
    }

    @Override
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
