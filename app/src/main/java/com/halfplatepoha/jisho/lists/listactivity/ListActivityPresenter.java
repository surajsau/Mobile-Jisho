package com.halfplatepoha.jisho.lists.listactivity;

import com.halfplatepoha.jisho.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by surjo on 03/01/18.
 */

public class ListActivityPresenter extends BasePresenter<ListActivityContract.View> implements ListActivityContract.Presenter {

    @Inject
    public ListActivityPresenter(ListActivityContract.View view) {
        super(view);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        view.openListFragment();
    }

    @Override
    public void onListNameReceived(String listName) {
        view.setResult(listName);
        view.finishScreen();
    }
}
