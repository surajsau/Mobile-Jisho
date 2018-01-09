package com.halfplatepoha.jisho.kanji;

import com.halfplatepoha.jisho.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by surjo on 09/01/18.
 */

public class KanjiDetailPresenter extends BasePresenter<KanjiDetailContract.View> implements KanjiDetailContract.Presenter {

    @Inject
    public KanjiDetailPresenter(KanjiDetailContract.View view) {
        super(view);
    }
}
