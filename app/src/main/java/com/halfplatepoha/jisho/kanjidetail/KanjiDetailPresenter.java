package com.halfplatepoha.jisho.kanjidetail;

import com.halfplatepoha.jisho.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by surjo on 21/12/17.
 */

public class KanjiDetailPresenter extends BasePresenter<KanjiDetailContract.View> implements KanjiDetailContract.Presenter {

    @Inject
    public KanjiDetailPresenter(KanjiDetailContract.View view) {
        super(view);
    }
}
