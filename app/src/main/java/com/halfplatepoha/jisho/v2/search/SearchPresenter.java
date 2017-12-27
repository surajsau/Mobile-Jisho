package com.halfplatepoha.jisho.v2.search;

import android.util.Log;

import com.halfplatepoha.jisho.base.BasePresenter;
import com.halfplatepoha.jisho.jdb.Entry;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by surjo on 20/12/17.
 */

public class SearchPresenter extends BasePresenter<SearchContract.View> implements SearchContract.Presenter, SearchAdapterPresenter.Listener{

    private Realm realm;

    private SearchAdapterContract.Presenter adapterPresenter;

    @Inject
    public SearchPresenter(SearchContract.View view, Realm realm, SearchAdapterContract.Presenter adapterPresenter) {
        super(view);
        this.realm = realm;
        this.adapterPresenter = adapterPresenter;
    }

    @Override
    public void search(String searchString) {
        RealmResults<Entry> entries = realm.where(Entry.class).equalTo("japanese", searchString).findAll();
        if(entries != null) {
            for(Entry entry : entries) {
                Log.e("ENTRY", entry.japanese);
            }
        }
    }

    @Override
    public void onItemClick(String japanese) {

    }

}
