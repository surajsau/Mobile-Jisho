package com.halfplatepoha.jisho.lists.listsfragment;

import com.halfplatepoha.jisho.base.BaseViewholderView;
import com.halfplatepoha.jisho.base.IAdapterPresenter;

import java.util.List;

/**
 * Created by surjo on 03/01/18.
 */

public interface ListAdapterContract {

    interface View extends BaseViewholderView {

        void setListName(String name);

        void removeBackground();

        int bgSelected();

        void setSelectedBackground(int bg);

        void hideCheckListView();

        void showChecklistView();

        void unselectList();

        void selectList();

        void setTextEditable();

        void setTextUnEditable();

        void showDoneButton();

        void hideDoneButton();

    }

    interface Presenter extends IAdapterPresenter<View> {

        void onItemClick(int adapterPosition);

        void addLists(List<ListObject> lists);

        void addListener(ListAdapterPresenter.Listener listener);

        void removeListener();

        void addList(ListObject name);

        void onItemLongClick(int adapterPosition);

        void showSelection();

        void deleteSelectedItems();

        void onListChecked(int adapterPosition, boolean isSelected);

        void editListName();

        void onListNameChanged(String finalName, int adapterPosition);
    }
}
