package com.halfplatepoha.jisho.v2.detail;

import com.halfplatepoha.jisho.base.BaseView;
import com.halfplatepoha.jisho.base.IPresenter;
import com.halfplatepoha.jisho.jdb.Sentence;

/**
 * Created by surjo on 28/12/17.
 */

public interface DetailsContract {

    interface View extends BaseView {

        void setJapanese(String japanese);

        void showFurigana();

        void setFurigana(String furigana);

        void openKanjiDetails(String kanjiLiteral);

        void openListsScreenForResults();

        void setPos(String pos);

        void setGloss(String gloss);

        void showKanjiContainer();

        void openSentencesScreen(String japanese, String furigana);

        void setExamplesCount(long count);

        void showExamplesContainer();

    }

    interface Presenter extends IPresenter {

        void clickAddNote();

        void onListNameResultReceived(String listName);

        void clickAddToList();

        void clickExamplesContainer();

    }
}
