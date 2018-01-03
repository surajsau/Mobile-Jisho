package com.halfplatepoha.jisho.lists;

import com.halfplatepoha.jisho.base.BasePresenter;
import com.halfplatepoha.jisho.jdb.JishoList;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by surjo on 20/12/17.
 */

public class ListsPresenter extends BasePresenter<ListContract.View> implements ListContract.Presenter,
        NewListDialogPresenter.Listener,
        ListAdapterPresenter.Listener {

    private ListAdapterContract.Presenter listAdapterPresenter;

    private Realm realm;

    private Listener listener;

    @Inject
    public ListsPresenter(ListContract.View view, ListAdapterContract.Presenter listAdapterPresenter) {
        super(view);
        this.listAdapterPresenter = listAdapterPresenter;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        realm = Realm.getDefaultInstance();

        listAdapterPresenter.addListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        RealmResults<JishoList> lists = realm.where(JishoList.class).findAll();

        if(lists != null && !lists.isEmpty()) {
            view.hideZeroState();
            listAdapterPresenter.addLists(lists);
        } else {
            view.showZeroState();
        }
    }

    @Override
    public void listNameAdded(String listName) {
        if(listener != null)
            listener.onListSelected(listName);
    }

    @Override
    public void onListSelected(String listName) {
        if(listener != null)
            listener.onListSelected(listName);
    }

    public interface Listener {
        void onListSelected(String listName);
    }
}
