package com.halfplatepoha.jisho.v2.detail;

import com.halfplatepoha.jisho.base.BaseView;
import com.halfplatepoha.jisho.base.IPresenter;

/**
 * Created by surjo on 28/12/17.
 */

public interface DetailsContract {

    interface View extends BaseView {

        void setJapanese(String japanese);

        void showFurigana();

        void setFurigana(String furigana);

        void openKanjiDialog(String kanjiLiteral);

    }

    interface Presenter extends IPresenter {

        void clickKanjiPlay();

        void clickExamples();
    }
}
