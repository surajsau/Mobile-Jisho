package com.halfplatepoha.jisho.lists.listsfragment;

import com.halfplatepoha.jisho.base.BasePresenter;
import com.halfplatepoha.jisho.v2.data.IDataProvider;
import com.halfplatepoha.jisho.jdb.JishoList;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

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

    private int listMode;

    private IDataProvider dataProvider;

    @Inject
    public ListsPresenter(ListContract.View view,
                          ListContract.Bus eventBus,
                          ListAdapterContract.Presenter listAdapterPresenter,
                          IDataProvider dataProvider,
                          @Named(ListsFragment.KEY_LIST_MODE) int listMode) {
        super(view);
        this.eventBus = eventBus;
        this.listAdapterPresenter = listAdapterPresenter;
        this.listMode = listMode;
        this.dataProvider = dataProvider;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        listAdapterPresenter.addListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        if(listMode == MODE_ADD_LIST) {
            view.hideHeader();
        }

        RealmResults<JishoList> results = dataProvider.getAllJishoLists();

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

        dataProvider.changeListName(originalName, finalName);
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
            view.hideTitle();
            view.hideAddList();
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
            view.showTitle();
            view.showAddList();
        } else if(selectedListCount == 1) {
            view.showEditListView();
        } else {
            view.hideEditListView();
        }
    }

    @Override
    public void removeList(String name) {

        dataProvider.deleteList(name);

    }

    @Override
    public void onNewListName(String name) {

        dataProvider.createNewList(name);

        listAdapterPresenter.addList(ListObject.fromJishoListName(name, 0));

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

            long count = dataProvider.getNewJishoListEntryCount();

            ListObject listObject = new ListObject();
            listObject.isNameChange = true;
            listObject.name = "New List #" + count;
            listAdapterPresenter.addList(listObject);
        }
    }

    private List<ListObject> getListObjects(RealmResults<JishoList> lists) {

        List<ListObject> listObjects = new ArrayList<>();

        for(JishoList jishoList : lists) {
            listObjects.add(ListObject.fromJishoListName(jishoList.name, jishoList.entries.size()));
        }

        return listObjects;
    }
}
