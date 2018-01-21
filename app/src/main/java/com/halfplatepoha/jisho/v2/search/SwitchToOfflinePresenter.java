package com.halfplatepoha.jisho.v2.search;

import com.halfplatepoha.jisho.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by surjo on 20/01/18.
 */

public class SwitchToOfflinePresenter extends BasePresenter<SwitchToOfflineContract.View> implements SwitchToOfflineContract.Presenter {

    @Inject
    public SwitchToOfflinePresenter(SwitchToOfflineContract.View view) {
        super(view);
    }

    @Override
    public void clickConfirm() {
        view.confirm();
    }

    @Override
    public void clickCancel() {
        view.cancel();
    }
}
