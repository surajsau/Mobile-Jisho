package com.halfplatepoha.jisho.favorite;

import com.halfplatepoha.jisho.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by surjo on 20/12/17.
 */

public class FavoritePresenter extends BasePresenter<FavoriteContract.View> implements FavoriteContract.Presenter {

    @Inject
    public FavoritePresenter(FavoriteContract.View view) {
        super(view);
    }
}
