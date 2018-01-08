package com.halfplatepoha.jisho.lists.listsfragment;

import com.halfplatepoha.jisho.base.BaseView;
import com.halfplatepoha.jisho.base.IPresenter;

/**
 * Created by surjo on 20/12/17.
 */

public interface ListContract {

    interface View extends BaseView {

        void hideZeroState();

        void showZeroState();

        void openListDetailsScreen(String listName);

        void showEditView();

        void hideEditView();

        void showEditListView();

        void hideEditListView();

        void hideHeader();

    }

    interface Presenter extends IPresenter {

        void onNewListName(String name);

        void clickDelete();

        void clickEdit();

        void addNewList();

    }

    interface Bus {

        void pushListName(ListName ln);

    }

}
