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

        void openGmailForError(String title);

        void hideZeroOffline();

        void showZeroOffline();

        void showOfflineSwitchConfirmation();

    }

    interface Presenter extends IPresenter {

        void searchOnEditorAction(String searchString);

        void clickOffline(String searchTerm);

        void report(String searchTerm);

        void switchToOffline();

        void onOfflineSwitchConfirm(String searchTerm);

        void searchError();

        void searchOnTextChange(String s, int before, int count);
    }
}
