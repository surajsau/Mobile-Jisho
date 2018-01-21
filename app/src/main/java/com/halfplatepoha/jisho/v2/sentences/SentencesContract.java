package com.halfplatepoha.jisho.v2.sentences;

import com.halfplatepoha.jisho.base.BaseView;
import com.halfplatepoha.jisho.base.IPresenter;

/**
 * Created by surjo on 09/01/18.
 */

public interface SentencesContract {

    interface View extends BaseView {

        void openSentenceDetails(String sentence);

        void setEntry(String entry);
    }

    interface Presenter extends IPresenter {

    }
}
