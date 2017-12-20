package com.halfplatepoha.jisho.base;

import javax.inject.Inject;

/**
 * Created by surjo on 21/04/17.
 */

public abstract class BasePresenter<V extends BaseView> implements IPresenter {

    protected V view;

    public BasePresenter(V view) {
        this.view = view;
    }

    @Override
    public void onCreate() {}

    @Override
    public void onResume() {}

    @Override
    public void onStop() {}

    @Override
    public void onPause() {}

    @Override
    public void onDestroy() {
        view = null;
    }

}
