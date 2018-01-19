package com.halfplatepoha.jisho.kanji;

import android.view.ViewGroup;

import com.halfplatepoha.jisho.base.BaseView;
import com.halfplatepoha.jisho.base.IPresenter;

import java.util.List;

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

        void showKunyomi();

        void showOnyomi();

        void setKoreanReading(String reading);

        void setKunReading(String kunyomi);

        void buildNode(KanjiNode node, ViewGroup parent, int childPosition, int totalChildrenOfParent);

        ViewGroup getComponentsRoot();

        void setKanji(String literal);

        void setOnReading(String onyomi);
    }

    interface Presenter extends IPresenter {

        void clickKanjiPlay();

        void buildFurther(KanjiNode node, ViewGroup kanjiNodeView);

    }
}
