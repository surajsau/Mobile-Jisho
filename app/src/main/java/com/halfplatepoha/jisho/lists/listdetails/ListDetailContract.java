package com.halfplatepoha.jisho.lists.listdetails;

import com.halfplatepoha.jisho.base.BaseView;
import com.halfplatepoha.jisho.base.IPresenter;

/**
 * Created by surjo on 05/01/18.
 */

public interface ListDetailContract {

    interface View extends BaseView {

        void setTitle(String name);

        void openDetailScreen(String japanese, String furigana);

    }

    interface Presenter extends IPresenter {

    }

}
