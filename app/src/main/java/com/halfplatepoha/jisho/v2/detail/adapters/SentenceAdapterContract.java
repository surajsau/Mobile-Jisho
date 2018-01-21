package com.halfplatepoha.jisho.v2.detail.adapters;

import com.halfplatepoha.jisho.base.BaseViewholderView;
import com.halfplatepoha.jisho.base.IAdapterPresenter;
import com.halfplatepoha.jisho.jdb.Sentence;

import java.util.List;

/**
 * Created by surjo on 28/12/17.
 */

public interface SentenceAdapterContract {

    interface View extends BaseViewholderView {

        void setEnglish(String english);

        void setOriginal(String sentence);

        void setTag(String sentence);

    }

    interface Presenter extends IAdapterPresenter<View> {

        void attachListener(SentenceAdapterPresenter.Listener listener);

        void removeListener();

        void setSentences(List<Sentence> sentences);

        void setKeyword(String keyword);

        void onItemClick(String tag);
    }

}
