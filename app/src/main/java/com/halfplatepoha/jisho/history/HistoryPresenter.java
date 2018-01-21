package com.halfplatepoha.jisho.history;

import com.halfplatepoha.jisho.base.BasePresenter;

import javax.inject.Inject;

import io.realm.Realm;

/**
 * Created by surjo on 20/12/17.
 */

public class HistoryPresenter extends BasePresenter<HistoryContract.View> implements HistoryContract.Presenter {

    private Realm realm;

    @Inject
    public HistoryPresenter(HistoryContract.View view) {
        super(view);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        realm = Realm.getDefaultInstance();


    }
}
