package com.halfplatepoha.jisho.lists;

import com.halfplatepoha.jisho.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by surjo on 03/01/18.
 */

public class NewListDialogPresenter extends BasePresenter<NewListDialogContract.View> implements NewListDialogContract.Presenter {

    private Listener listener;

    @Inject
    public NewListDialogPresenter(NewListDialogContract.View view) {
        super(view);
    }

    @Override
    public void addListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void clickAddListName(String listName) {
        if(listName != null && listName.length() != 0) {
            if (listener != null)
                listener.listNameAdded(listName);
            view.dismissDialog();
        }
    }

    public interface Listener {
        void listNameAdded(String listName);
    }
}
