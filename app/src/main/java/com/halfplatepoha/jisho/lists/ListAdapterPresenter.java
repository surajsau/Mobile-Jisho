package com.halfplatepoha.jisho.lists;

import com.halfplatepoha.jisho.base.BaseAdapterPresenter;
import com.halfplatepoha.jisho.jdb.JishoList;

import javax.inject.Inject;

import io.realm.RealmResults;

/**
 * Created by surjo on 03/01/18.
 */

public class ListAdapterPresenter extends BaseAdapterPresenter<ListAdapterContract.View> implements ListAdapterContract.Presenter {

    private RealmResults<JishoList> lists;

    private int currentSelectedPosition;

    private Listener listener;

    @Inject
    public ListAdapterPresenter() {
        super();
    }

    @Override
    public void onBind(ListAdapterContract.View viewHolder, int position) {
        viewHolder.setListName(lists.get(position).name);

        if(position == currentSelectedPosition) {
            viewHolder.setSelectedBackground(viewHolder.bgSelected());
        } else {
            viewHolder.removeBackground();
        }
    }

    @Override
    public int getItemCount() {
        return lists != null ? lists.size() : 0;
    }

    @Override
    public void onItemClick(int adapterPosition) {
        currentSelectedPosition = adapterPosition;
        adapterInterface.itemChanged(adapterPosition);

        if(listener != null)
            listener.onListSelected(lists.get(adapterPosition).name);
    }

    @Override
    public void addLists(RealmResults<JishoList> lists) {
        this.lists = lists;
        adapterInterface.itemRangeInserted(0, lists.size());
    }

    @Override
    public void addListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void removeListener() {
        this.listener = null;
    }

    public interface Listener {
        void onListSelected(String listName);
    }
}
