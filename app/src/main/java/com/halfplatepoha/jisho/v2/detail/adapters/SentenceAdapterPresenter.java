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

    private Listener listener;

    @Override
    public void onBind(SentenceAdapterContract.View viewHolder, int position) {
        viewHolder.setOriginal(sentences.get(position).sentence);
        viewHolder.setEnglish(sentences.get(position).english);
    }

    @Override
    public void attachListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void removeListener() {
        this.listener = null;
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

    @Override
    public void onItemClick(int adapterPosition) {
        if(listener != null)
            listener.onItemClick(sentences.get(adapterPosition));
    }

    public interface Listener {
        void onItemClick(Sentence sentence);
    }
}
