package com.halfplatepoha.jisho.lists.listsfragment;

import com.halfplatepoha.jisho.base.BaseAdapterPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by surjo on 03/01/18.
 */

public class ListAdapterPresenter extends BaseAdapterPresenter<ListAdapterContract.View> implements ListAdapterContract.Presenter {

    private static final int MODE_NORMAL = 1;
    private static final int MODE_SELECTION = 2;
    private static final int MODE_EDITABLE = 3;

    private List<ListObject> lists;

    private Listener listener;

    private int currentMode = MODE_NORMAL;

    @Inject
    public ListAdapterPresenter() {
        super();
    }

    @Override
    public void onBind(ListAdapterContract.View viewHolder, int position) {
        ListObject listObject = lists.get(position);
        if(listObject.count != 0)
            viewHolder.setListNameCount(listObject.name, listObject.count);
        else
            viewHolder.setListName(listObject.name);

        if(listObject.isSelected) {
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

        if(!lists.get(adapterPosition).isNameChange) {
            lists.get(adapterPosition).isSelected = true;

            adapterInterface.itemChanged(adapterPosition);

            if (listener != null)
                listener.onListSelected(lists.get(adapterPosition).name);
        }
    }

    @Override
    public void addLists(List<ListObject> lists) {
        this.lists = lists;
        adapterInterface.itemRangeChanged(0, lists.size());
    }

    @Override
    public void addListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void removeListener() {
        this.listener = null;
    }

    @Override
    public void addList(ListObject newList) {
        if(lists == null)
            lists = new ArrayList<>();

        lists.add(0, newList);

        adapterInterface.itemInserted(0);
    }

    @Override
    public void onItemLongClick(int adapterPosition) {
        lists.get(adapterPosition).isSelected = true;

        if(listener != null)
            listener.onListItemLongClick(lists.get(adapterPosition).name);
    }

    @Override
    public void showSelection() {
        currentMode = MODE_SELECTION;
        adapterInterface.itemRangeChanged(0, lists.size());
    }

    @Override
    public void deleteSelectedItems() {
        for(int i =0; i<lists.size(); i++) {

            if(lists.get(i).isSelected) {
                if (listener != null)
                    listener.removeList(lists.get(i).name);

                lists.remove(i);
                adapterInterface.itemRemoved(i);
            }
        }
    }

    @Override
    public void onListChecked(int adapterPosition, boolean isSelected) {
        lists.get(adapterPosition).isSelected = isSelected;

        if(listener != null)
            listener.onListItemChecked(lists.get(adapterPosition).name, isSelected);
    }

    @Override
    public void editListName() {
        for(int i=0; i<lists.size(); i++) {
            if(lists.get(i).isSelected) {
                lists.get(i).isNameChange = true;
                adapterInterface.itemChanged(i);
                break;
            }
        }
    }

    @Override
    public void onListNameChanged(String finalName, int adapterPosition) {
        if(listener != null) {
            listener.onListNameChanged(finalName, lists.get(adapterPosition).name);
        }

        lists.get(adapterPosition).isNameChange = false;
        lists.get(adapterPosition).name = finalName;

        adapterInterface.itemChanged(adapterPosition);
    }

    public interface Listener {
        void onListNameChanged(String finalName, String originalName);
        void onListSelected(String listName);
        void onListItemLongClick(String listName);
        void onListItemChecked(String name, boolean isSelected);
        void removeList(String name);
    }
}
