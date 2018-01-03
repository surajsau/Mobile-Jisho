package com.halfplatepoha.jisho.v2.search;

import com.halfplatepoha.jisho.base.BaseAdapterPresenter;
import com.halfplatepoha.jisho.jdb.Entry;
import com.halfplatepoha.jisho.jdb.Gloss;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.realm.RealmResults;

/**
 * Created by surjo on 21/12/17.
 */

public class SearchAdapterPresenter extends BaseAdapterPresenter<SearchAdapterContract.View> implements SearchAdapterContract.Presenter {

    public static final int TYPE_VERTICAL = 1;
    public static final int TYPE_HORIZONTAL = 2;

    RealmResults<Entry> entries;

    private Listener listener;

    private int itemViewType = TYPE_HORIZONTAL;

    @Inject
    public SearchAdapterPresenter() {
        super();
    }

    @Override
    public void setResults(RealmResults<Entry> entries) {
        this.entries = entries;
        adapterInterface.dataSetChanged();
    }

    @Override
    public void onItemClick(int adapterPosition) {
        if(listener != null)
            listener.onItemClick(entries.get(adapterPosition).japanese, entries.get(adapterPosition).furigana);
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
    public void onBind(SearchAdapterContract.View viewHolder, int position) {

        Entry entry = entries.get(position);
        viewHolder.setJapanese(entry.japanese);

        if(entry.glosses != null && !entry.glosses.isEmpty()) {

            StringBuilder senseBuilder = new StringBuilder(entry.glosses.get(0).english);

            for (int i=1; i<entry.glosses.size(); i++) {
                senseBuilder.append(", ");
                senseBuilder.append(entry.glosses.get(i).english);
            }

            viewHolder.setSense(senseBuilder.toString());
        }

        if (entry.common)
            viewHolder.showCommon();
        else
            viewHolder.hideCommon();

        if(viewHolder instanceof SearchAdapterContract.HorizontalView) {
            if (entry.furigana != null && entry.furigana.length() != 0) {
                ((SearchAdapterContract.HorizontalView)viewHolder).showFurigana();
                ((SearchAdapterContract.HorizontalView)viewHolder).setFurigana(entry.furigana);
            } else {
                ((SearchAdapterContract.HorizontalView)viewHolder).hideFurigana();
            }
        }
    }

    @Override
    public int getItemCount() {
        return entries != null ?  entries.size() : 0;
    }

    @Override
    public int getItemViewType() {
        return itemViewType;
    }

    @Override
    public void setItemViewType(int itemViewType) {
        this.itemViewType = itemViewType;
        adapterInterface.itemRangeRemoved(0, entries.size());
        adapterInterface.itemRangeInserted(0, entries.size());
    }

    public interface Listener {
        void onItemClick(String japanese, String furigana);
    }
}
