package com.halfplatepoha.jisho.v2.search;

import com.halfplatepoha.jisho.base.BaseView;
import com.halfplatepoha.jisho.base.IPresenter;

/**
 * Created by surjo on 20/12/17.
 */

public interface SearchContract {

    interface View extends BaseView {

        void openDetails(String japanese, String furigana);

        void showSpinner();

        void hideSpinner();

        void changeSearchListOrientation(int currentOrientation);

        void showVerticalSpace();

        void hideVerticalSpace();

    }

    interface Presenter extends IPresenter {

        void search(String searchString);

        void clickOrientation();

    }
}
