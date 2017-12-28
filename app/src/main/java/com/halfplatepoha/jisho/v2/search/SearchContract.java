package com.halfplatepoha.jisho.v2.search;

import com.halfplatepoha.jisho.base.BaseView;
import com.halfplatepoha.jisho.base.IPresenter;

/**
 * Created by surjo on 20/12/17.
 */

public interface SearchContract {

    interface View extends BaseView {

        void openDetails(String japanese);

        void showSpinner();

        void hideSpinner();
    }

    interface Presenter extends IPresenter {

        void search(String searchString);
    }
}
