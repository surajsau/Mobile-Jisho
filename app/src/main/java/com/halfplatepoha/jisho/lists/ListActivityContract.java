package com.halfplatepoha.jisho.lists;

import com.halfplatepoha.jisho.base.BaseView;
import com.halfplatepoha.jisho.base.IPresenter;

/**
 * Created by surjo on 03/01/18.
 */

public interface ListActivityContract {

    interface View extends BaseView {

        void openListFragment();

        void setResult(String listName);

    }

    interface Presenter extends IPresenter {

        void onListNameReceived(String listName);

    }
}
