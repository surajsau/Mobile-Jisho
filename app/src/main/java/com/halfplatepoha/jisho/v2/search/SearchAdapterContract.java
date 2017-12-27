package com.halfplatepoha.jisho.v2.search;

import com.halfplatepoha.jisho.base.BaseViewholderView;
import com.halfplatepoha.jisho.base.IAdapterPresenter;

/**
 * Created by surjo on 21/12/17.
 */

public interface SearchAdapterContract {

    interface View extends BaseViewholderView {

        void setJapanese(String japanese);

        void showFurigana();

        void setFurigana(String furigana);

        void hideFurigana();

        void showCommon();

        void hideCommon();
    }

    interface Presenter extends IAdapterPresenter<View> {

        void onItemClick(int adapterPosition);

    }
}
