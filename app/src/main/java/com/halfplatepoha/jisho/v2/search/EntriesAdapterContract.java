package com.halfplatepoha.jisho.v2.search;

import com.halfplatepoha.jisho.base.BaseViewholderView;
import com.halfplatepoha.jisho.base.IAdapterPresenter;

import java.util.List;

/**
 * Created by surjo on 21/12/17.
 */

public interface EntriesAdapterContract {

    interface View extends BaseViewholderView {

        void setJapanese(String japanese);

        void showCommon();

        void hideCommon();

        void setSense(String sense);

        void setTag(String tag);

    }

    interface VerticalView extends View {

    }

    interface HorizontalView extends View {

        void showFurigana();

        void setFurigana(String furigana);

        void hideFurigana();

    }

    interface Presenter extends IAdapterPresenter<View> {

        void setResults(List<EntryModel> entries);

        void onItemClick(String tag);

        void attachListener(EntriesAdapterPresenter.Listener listener);

        void removeListener();

        void setItemViewType(int itemViewType);
    }
}
