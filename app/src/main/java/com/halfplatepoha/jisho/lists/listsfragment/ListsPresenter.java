package com.halfplatepoha.jisho.lists.listsfragment;

import com.halfplatepoha.jisho.base.BasePresenter;
import com.halfplatepoha.jisho.jdb.JishoList;
import com.halfplatepoha.jisho.jdb.Schema;

import java.util.ArrayList;
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

    private int selectedListCount;

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

        if(listMode == MODE_ADD_LIST) {
            view.hideHeader();
        }

        RealmResults<JishoList> results = realm.where(JishoList.class).findAll();

        if(results != null && !results.isEmpty()) {
            view.hideZeroState();
            listAdapterPresenter.addLists(getListObjects(results));
        } else {
            view.showZeroState();
        }
    }

    @Override
    public void onListNameChanged(String finalName, String originalName) {

        view.hideKeyboard();

        JishoList list = realm.where(JishoList.class).equalTo(Schema.JishoList.NAME, originalName).findFirst();

        realm.beginTransaction();

        if(list != null) {
            list.name = finalName;
        } else {
            list = realm.createObject(JishoList.class);
            list.name = finalName;
        }

        realm.insertOrUpdate(list);

        realm.commitTransaction();
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
    public void onListItemLongClick(String listName) {
        if(selectedListCount == 0) {
            view.showEditView();
            listAdapterPresenter.showSelection();
        }

        selectedListCount += 1;
    }

    @Override
    public void onListItemChecked(String name, boolean isSelected) {
        if(isSelected)
            selectedListCount += 1;
        else
            selectedListCount -= 1;

        if(selectedListCount == 0) {
            view.hideEditView();
        } else if(selectedListCount == 1) {
            view.showEditListView();
        } else {
            view.hideEditListView();
        }
    }

    @Override
    public void removeList(String name) {
        realm.beginTransaction();

        realm.where(JishoList.class).equalTo(Schema.JishoList.NAME, name).findAll().deleteAllFromRealm();

        realm.commitTransaction();
    }

    @Override
    public void onNewListName(String name) {
        realm.beginTransaction();

        JishoList newList = realm.createObject(JishoList.class);
        newList.name = name;

        realm.insertOrUpdate(newList);

        realm.commitTransaction();

        listAdapterPresenter.addList(ListObject.fromJishoList(newList));

        onListSelected(name);
    }

    @Override
    public void clickDelete() {
        listAdapterPresenter.deleteSelectedItems();
    }

    @Override
    public void clickEdit() {
        listAdapterPresenter.editListName();
    }

    @Override
    public void addNewList() {
        if(listAdapterPresenter != null) {

            long count = realm.where(JishoList.class).contains(Schema.JishoList.NAME, "New List #").count();

            ListObject listObject = new ListObject();
            listObject.isNameChange = true;
            listObject.name = "New List #" + count;
            listAdapterPresenter.addList(listObject);
        }
    }

    private List<ListObject> getListObjects(RealmResults<JishoList> lists) {

        List<ListObject> listObjects = new ArrayList<>();

        for(JishoList jishoList : lists) {
            listObjects.add(ListObject.fromJishoList(jishoList));
        }

        return listObjects;
    }
}
