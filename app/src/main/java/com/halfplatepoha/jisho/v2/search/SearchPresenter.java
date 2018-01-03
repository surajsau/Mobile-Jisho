package com.halfplatepoha.jisho.v2.search;

import android.util.Log;

import com.halfplatepoha.jisho.base.BasePresenter;
import com.halfplatepoha.jisho.jdb.Entry;
import com.halfplatepoha.jisho.jdb.Schema;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by surjo on 20/12/17.
 */

public class SearchPresenter extends BasePresenter<SearchContract.View> implements SearchContract.Presenter, SearchAdapterPresenter.Listener{

    private Realm realm;

    private SearchAdapterContract.Presenter adapterPresenter;

    private int currentOrientation = SearchAdapterPresenter.TYPE_HORIZONTAL;

    @Inject
    public SearchPresenter(SearchContract.View view, Realm realm, SearchAdapterContract.Presenter adapterPresenter) {
        super(view);
        this.realm = realm;
        this.adapterPresenter = adapterPresenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapterPresenter.attachListener(this);
    }

    @Override
    public void search(String searchString) {
        view.showSpinner();

        RealmResults<Entry> entries = realm.where(Entry.class)
                .equalTo(Schema.Entry.JAPANESE, searchString)
                .or()
                .contains(Schema.Entry.FURIGANA, searchString)
                .sort(Schema.Entry.COMMON, Sort.ASCENDING)
                .findAll();

        view.hideSpinner();

        if(entries != null) {
            adapterPresenter.setResults(entries);
        }
    }

    @Override
    public void clickOrientation() {
        if(currentOrientation == SearchAdapterPresenter.TYPE_HORIZONTAL)
            currentOrientation = SearchAdapterPresenter.TYPE_VERTICAL;
        else
            currentOrientation = SearchAdapterPresenter.TYPE_HORIZONTAL;

        if(currentOrientation == SearchAdapterPresenter.TYPE_HORIZONTAL) {
            view.showVerticalSpace();
        } else {
            view.hideVerticalSpace();
        }

        view.changeSearchListOrientation(currentOrientation);

        adapterPresenter.setItemViewType(currentOrientation);
    }

    @Override
    public void onStop() {
        super.onStop();
        adapterPresenter.removeListener();
    }

    @Override
    public void onItemClick(String japanese, String furigana) {
        view.openDetails(japanese, furigana);
    }

}
