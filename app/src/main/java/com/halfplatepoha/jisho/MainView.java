package com.halfplatepoha.jisho;

import com.halfplatepoha.jisho.model.Word;

/**
 * Created by surjo on 21/04/17.
 */

public interface MainView extends BaseView {
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
}
