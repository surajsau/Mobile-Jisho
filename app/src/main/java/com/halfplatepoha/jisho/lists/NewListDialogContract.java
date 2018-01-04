package com.halfplatepoha.jisho.lists;

import com.halfplatepoha.jisho.base.BaseView;
import com.halfplatepoha.jisho.base.IPresenter;

/**
 * Created by surjo on 03/01/18.
 */

public interface NewListDialogContract {

    interface View extends BaseView {

        void dismissDialog();

        void showDuplicateListError();

    }

    interface Presenter extends IPresenter {

        void clickAddListName(String listName);

    }

    interface Bus {

        void pushNewListName(NewListName nlm);

    }
}
