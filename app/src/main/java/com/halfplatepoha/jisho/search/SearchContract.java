package com.halfplatepoha.jisho.search;

import com.halfplatepoha.jisho.base.BaseView;
import com.halfplatepoha.jisho.base.IPresenter;
import com.halfplatepoha.jisho.apimodel.Word;

/**
 * Created by surjo on 20/12/17.
 */

public interface SearchContract {

    interface View extends BaseView {
        void addWordToAdapter(Word word);

        void clearData();

        void showLoader();

        void hideLoader();

        void showClearButton();

        void hideClearButton();

        void saveInHistory(String searchString);

        void showInternetError();

        void hideError();

        void showEmptyResultError();

        void showResults();

        void setSearchString(String searchString);
    }

    interface Presenter extends IPresenter {

        void search(String searchString);

    }
}
