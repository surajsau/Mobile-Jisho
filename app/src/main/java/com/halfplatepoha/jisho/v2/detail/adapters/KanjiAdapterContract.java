package com.halfplatepoha.jisho.v2.detail.adapters;

import com.halfplatepoha.jisho.base.BaseViewholderView;
import com.halfplatepoha.jisho.base.IAdapterPresenter;

import java.util.List;

/**
 * Created by surjo on 28/12/17.
 */

public interface KanjiAdapterContract {

    interface View extends BaseViewholderView {

        void setKanji(String kanji);

        void setMeaning(String meaning);
    }

    interface Presenter extends IAdapterPresenter<View> {

        void attachListener(KanjiAdapterPresenter.Listener listener);

        void removeListener();

        void onItemClick(int position);

        void setKanjis(List<KanjiModel> kanjis);

    }

}
