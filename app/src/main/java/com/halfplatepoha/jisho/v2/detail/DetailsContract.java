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

        void openKanjiDialog(String kanjiLiteral);

        void openSentenceDetail(Sentence sentence);

    }

    interface Presenter extends IPresenter {

        void clickKanjiPlay();

        void clickExamples();
    }
}