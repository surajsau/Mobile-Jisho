package com.halfplatepoha.jisho.lists;

import com.halfplatepoha.jisho.base.BasePresenter;
import com.halfplatepoha.jisho.jdb.JishoList;
import com.halfplatepoha.jisho.jdb.Schema;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by surjo on 03/01/18.
 */

public class NewListDialogPresenter extends BasePresenter<NewListDialogContract.View> implements NewListDialogContract.Presenter {

    private Realm realm;

    private NewListDialogContract.Bus eventBus;

    @Inject
    public NewListDialogPresenter(NewListDialogContract.View view, NewListDialogContract.Bus eventBus) {
        super(view);
        this.eventBus = eventBus;
    }

    @Override
    public void clickAddListName(String listName) {
        if(listName != null && listName.length() != 0) {
            RealmResults<JishoList> results = realm.where(JishoList.class).equalTo(Schema.JishoList.NAME, listName).findAll();

            if(results != null && results.size() != 0) {
                view.showDuplicateListError();
            } else {
                NewListName nlm = new NewListName();
                nlm.name = listName;
                eventBus.pushNewListName(nlm);
                view.dismissDialog();
            }
        }
    }
}
