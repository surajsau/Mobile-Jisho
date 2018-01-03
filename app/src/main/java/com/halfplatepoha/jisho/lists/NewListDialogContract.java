package com.halfplatepoha.jisho.lists;

import com.halfplatepoha.jisho.base.BaseView;
import com.halfplatepoha.jisho.base.IPresenter;

/**
 * Created by surjo on 03/01/18.
 */

public interface NewListDialogContract {

    interface View extends BaseView {

        void dismissDialog();

    }

    interface Presenter extends IPresenter {

        void addListener(NewListDialogPresenter.Listener listener);

        void clickAddListName(String listName);

    }
}
