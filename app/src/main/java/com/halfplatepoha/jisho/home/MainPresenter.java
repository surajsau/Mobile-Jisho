package com.halfplatepoha.jisho.home;

import com.halfplatepoha.jisho.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by surjo on 20/12/17.
 */

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    @Inject
    public MainPresenter(MainContract.View view) {
        super(view);
    }

}
