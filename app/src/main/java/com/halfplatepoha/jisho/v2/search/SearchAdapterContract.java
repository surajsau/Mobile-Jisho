package com.halfplatepoha.jisho.v2.search;

import com.halfplatepoha.jisho.base.BaseViewholderView;
import com.halfplatepoha.jisho.base.IAdapterPresenter;
import com.halfplatepoha.jisho.jdb.Entry;

import io.realm.RealmResults;

/**
 * Created by surjo on 21/12/17.
 */

public interface SearchAdapterContract {

    interface View extends BaseViewholderView {

        void setJapanese(String japanese);

        void showCommon();

        void hideCommon();

        void setSense(String sense);

    }

    interface VerticalView extends View {

    }

    interface HorizontalView extends View {

        void showFurigana();

        void setFurigana(String furigana);

        void hideFurigana();

    }

    interface Presenter extends IAdapterPresenter<View> {

        void setResults(RealmResults<Entry> entries);

        void onItemClick(int adapterPosition);

        void attachListener(SearchAdapterPresenter.Listener listener);

        void removeListener();

        void setItemViewType(int itemViewType);
    }
}
