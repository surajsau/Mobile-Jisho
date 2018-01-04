package com.halfplatepoha.jisho.lists;

import com.halfplatepoha.jisho.base.BaseView;
import com.halfplatepoha.jisho.base.IPresenter;

/**
 * Created by surjo on 20/12/17.
 */

public interface ListContract {

    interface View extends BaseView {

        void hideZeroState();

        void showZeroState();
    }

    interface Presenter extends IPresenter {

        void onNewListName(String name);
    }

    interface Bus {

        void pushListName(ListName ln);

    }

}
