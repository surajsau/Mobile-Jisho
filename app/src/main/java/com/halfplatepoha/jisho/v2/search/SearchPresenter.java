package com.halfplatepoha.jisho.v2.search;

import com.halfplatepoha.jisho.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by surjo on 20/12/17.
 */

public class SearchPresenter extends BasePresenter<SearchContract.View> implements SearchContract.Presenter{

    @Inject
    public SearchPresenter(SearchContract.View view) {
        super(view);
    }

}
