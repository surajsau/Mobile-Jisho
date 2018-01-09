package com.halfplatepoha.jisho.kanji;

import com.halfplatepoha.jisho.base.BaseView;
import com.halfplatepoha.jisho.base.IPresenter;

import java.util.List;

import io.realm.RealmList;

/**
 * Created by surjo on 09/01/18.
 */

public interface KanjiDetailContract {

    interface View extends BaseView {

        void setKanjiStrokes(List<String> strokes);

        void animateStroke();

        void setKanjiElements(String kanjiElements);

        void setPinyinReading(String reading);

        void showPinyin();

        void showKorean();

        void showJapanese();

        void setKoreanReading(String reading);

        void setJapaneseReading(String reading);
    }

    interface Presenter extends IPresenter {

        void clickKanjiPlay();

    }
}
