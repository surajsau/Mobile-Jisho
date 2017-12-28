package com.halfplatepoha.jisho.v2.detail.adapters;

import com.halfplatepoha.jisho.base.BaseViewholderView;
import com.halfplatepoha.jisho.base.IAdapterPresenter;
import com.halfplatepoha.jisho.jdb.Sentence;

import io.realm.RealmResults;

/**
 * Created by surjo on 28/12/17.
 */

public interface SentenceAdapterContract {

    interface View extends BaseViewholderView {

    }

    interface Presenter extends IAdapterPresenter<View> {

        void setSentences(RealmResults<Sentence> sentences);

        void setKeyword(String keyword);
    }

}
