package com.halfplatepoha.jisho.history;

import com.halfplatepoha.jisho.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by surjo on 20/12/17.
 */

public class HistoryPresenter extends BasePresenter<HistoryContract.View> implements HistoryContract.Presenter {

    @Inject
    public HistoryPresenter(HistoryContract.View view) {
        super(view);
    }

}
