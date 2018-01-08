package com.halfplatepoha.jisho.v2.search;

import com.halfplatepoha.jisho.base.BasePresenter;
import com.halfplatepoha.jisho.jdb.Entry;
import com.halfplatepoha.jisho.jdb.Schema;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by surjo on 20/12/17.
 */

public class SearchPresenter extends BasePresenter<SearchContract.View> implements SearchContract.Presenter, EntriesAdapterPresenter.Listener{

    private Realm realm;

    private EntriesAdapterContract.Presenter adapterPresenter;

    private int currentOrientation = EntriesAdapterPresenter.TYPE_HORIZONTAL;

    @Inject
    public SearchPresenter(SearchContract.View view, Realm realm, EntriesAdapterContract.Presenter adapterPresenter) {
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
                .contains(Schema.Entry.JAPANESE, searchString)
                .or()
                .contains(Schema.Entry.FURIGANA, searchString)
                .or()
                .equalTo(Schema.Entry.FURIGANA, searchString)
                .findAll();

        view.hideSpinner();

        if(entries != null) {
            adapterPresenter.setResults(entries);
        }
    }

    @Override
    public void clickOrientation() {
        if(currentOrientation == EntriesAdapterPresenter.TYPE_HORIZONTAL)
            currentOrientation = EntriesAdapterPresenter.TYPE_VERTICAL;
        else
            currentOrientation = EntriesAdapterPresenter.TYPE_HORIZONTAL;

        if(currentOrientation == EntriesAdapterPresenter.TYPE_HORIZONTAL) {
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
