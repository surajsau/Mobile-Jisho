package com.halfplatepoha.jisho.lists.listsfragment;

import com.halfplatepoha.jisho.base.BasePresenter;
import com.halfplatepoha.jisho.jdb.JishoList;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by surjo on 20/12/17.
 */

public class ListsPresenter extends BasePresenter<ListContract.View> implements ListContract.Presenter,
        ListAdapterPresenter.Listener {

    public static final int MODE_ADD_LIST = 1;
    public static final int MODE_OPEN_LIST = 2;

    private ListAdapterContract.Presenter listAdapterPresenter;

    private ListContract.Bus eventBus;

    private Realm realm;

    private int listMode;

    @Inject
    public ListsPresenter(ListContract.View view,
                          ListContract.Bus eventBus,
                          ListAdapterContract.Presenter listAdapterPresenter,
                          @Named(ListsFragment.KEY_LIST_MODE) int listMode) {
        super(view);
        this.eventBus = eventBus;
        this.listAdapterPresenter = listAdapterPresenter;
        this.listMode = listMode;
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

        RealmResults<JishoList> results = realm.where(JishoList.class).findAll();

        if(results != null && !results.isEmpty()) {
            view.hideZeroState();
            List<JishoList> lists = realm.copyFromRealm(results);
            listAdapterPresenter.addLists(lists);
        } else {
            view.showZeroState();
        }
    }

    @Override
    public void onListSelected(String listName) {
        switch (listMode) {
            case MODE_ADD_LIST:
                ListName ln = new ListName();
                ln.name = listName;

                eventBus.pushListName(ln);
                break;

            case MODE_OPEN_LIST:
                view.openListDetailsScreen(listName);
                break;
        }
    }

    @Override
    public void onNewListName(String name) {
        realm.beginTransaction();

        JishoList newList = realm.createObject(JishoList.class);
        newList.name = name;

        realm.insertOrUpdate(newList);

        realm.commitTransaction();

        listAdapterPresenter.addList(newList);

        onListSelected(name);
    }
}
