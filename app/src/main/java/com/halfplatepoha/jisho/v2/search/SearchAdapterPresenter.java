package com.halfplatepoha.jisho.v2.search;

import com.halfplatepoha.jisho.base.BaseAdapterPresenter;
import com.halfplatepoha.jisho.jdb.Entry;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.realm.RealmResults;

/**
 * Created by surjo on 21/12/17.
 */

public class SearchAdapterPresenter extends BaseAdapterPresenter<SearchAdapterContract.View> implements SearchAdapterContract.Presenter {

    RealmResults<Entry> entries;

    private Listener listener;

    @Inject
    public SearchAdapterPresenter() {
        super();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void setResults(RealmResults<Entry> entries) {
        this.entries = entries;
        adapterInterface.dataSetChanged();
    }

    @Override
    public void onItemClick(int adapterPosition) {
        if(listener != null)
            listener.onItemClick(entries.get(adapterPosition).japanese);
    }

    @Override
    public void onBind(SearchAdapterContract.View viewHolder, int position) {
        Entry entry = entries.get(position);
        viewHolder.setJapanese(entry.japanese);

        if(entry.furigana != null && entry.furigana.length() != 0) {
            viewHolder.showFurigana();
            viewHolder.setFurigana(entry.furigana);
        } else {
            viewHolder.hideFurigana();
        }

        if(entry.common)
            viewHolder.showCommon();
        else
            viewHolder.hideCommon();
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public interface Listener {
        void onItemClick(String japanese);
    }
}
