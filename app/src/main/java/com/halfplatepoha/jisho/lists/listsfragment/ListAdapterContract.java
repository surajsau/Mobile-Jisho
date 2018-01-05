package com.halfplatepoha.jisho.lists.listsfragment;

import com.halfplatepoha.jisho.base.BaseViewholderView;
import com.halfplatepoha.jisho.base.IAdapterPresenter;
import com.halfplatepoha.jisho.jdb.JishoList;

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

    }

    interface Presenter extends IAdapterPresenter<View> {

        void onItemClick(int adapterPosition);

        void addLists(List<JishoList> lists);

        void addListener(ListAdapterPresenter.Listener listener);

        void removeListener();

        void addList(JishoList name);

    }
}
