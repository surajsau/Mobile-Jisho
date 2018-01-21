package com.halfplatepoha.jisho.v2.search;

import com.halfplatepoha.jisho.base.BaseAdapterPresenter;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by surjo on 21/12/17.
 */

public class EntriesAdapterPresenter extends BaseAdapterPresenter<EntriesAdapterContract.View> implements EntriesAdapterContract.Presenter {

    public static final int TYPE_VERTICAL = 1;
    public static final int TYPE_HORIZONTAL = 2;

    List<EntryModel> entries;

    private Listener listener;

    private int itemViewType = TYPE_VERTICAL;

    @Inject
    public EntriesAdapterPresenter() {
        super();
    }

    @Override
    public void setResults(List<EntryModel> entries) {
        this.entries = entries;
        adapterInterface.dataSetChanged();
    }

    @Override
    public void onItemClick(String tag) {
        if(listener != null)
            listener.onItemClick(tag);
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
    public void onBind(EntriesAdapterContract.View viewHolder, int position) {

        EntryModel entry = entries.get(position);
        viewHolder.setJapanese(entry.japanese);
        viewHolder.setTag(entry.japanese + ":" + entry.furigana);

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

        if(viewHolder instanceof EntriesAdapterContract.HorizontalView) {
            if (entry.furigana != null && entry.furigana.length() != 0) {
                ((EntriesAdapterContract.HorizontalView)viewHolder).showFurigana();
                ((EntriesAdapterContract.HorizontalView)viewHolder).setFurigana(entry.furigana);
            } else {
                ((EntriesAdapterContract.HorizontalView)viewHolder).hideFurigana();
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
        if(entries != null) {
            this.itemViewType = itemViewType;
            adapterInterface.itemRangeRemoved(0, entries.size());
            adapterInterface.itemRangeInserted(0, entries.size());
        }
    }

    public interface Listener {
        void onItemClick(String tag);
    }
}
