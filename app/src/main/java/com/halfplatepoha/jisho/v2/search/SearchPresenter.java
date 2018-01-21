package com.halfplatepoha.jisho.v2.search;

import com.halfplatepoha.jisho.JishoPreference;
import com.halfplatepoha.jisho.apimodel.SearchApi;
import com.halfplatepoha.jisho.apimodel.Word;
import com.halfplatepoha.jisho.base.BasePresenter;
import com.halfplatepoha.jisho.v2.data.IDataProvider;
import com.halfplatepoha.jisho.jdb.Entry;
import com.halfplatepoha.jisho.utils.IConstants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.RealmResults;

/**
 * Created by surjo on 20/12/17.
 */

public class SearchPresenter extends BasePresenter<SearchContract.View> implements SearchContract.Presenter, EntriesAdapterPresenter.Listener{

    private EntriesAdapterContract.Presenter adapterPresenter;

    private int currentOrientation = EntriesAdapterPresenter.TYPE_VERTICAL;

    private IDataProvider dataProvider;

    private List<Word> words;

    @Inject
    public SearchPresenter(SearchContract.View view,
                           IDataProvider dataProvider,
                           EntriesAdapterContract.Presenter adapterPresenter) {
        super(view);
        this.dataProvider = dataProvider;
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

        searchOffline(searchString);
    }

    private void searchOffline(String searchString) {
        RealmResults<Entry> entries = dataProvider.getEntries(searchString);

        view.hideSpinner();

        if(entries != null && !entries.isEmpty()) {
            List<EntryModel> entryModels = new ArrayList<>();

            for(Entry entry : entries) {
                entryModels.add(EntryModel.newInstance(entry));
            }

            adapterPresenter.setResults(entryModels);
        } else {
            view.showZeroOffline();
        }
    }

    @Override
    public void clickOffline(String searchTerm) {
        hideErrorViews();
        searchOffline(searchTerm);
    }

    private void hideErrorViews() {
        view.hideZeroOffline();
    }

    @Override
    public void report(String searchTerm) {
        view.openGmailForError("REPORT: No results for " + searchTerm);
    }

    @Override
    public void switchToOffline() {
        view.showOfflineSwitchConfirmation();
    }

    @Override
    public void onOfflineSwitchConfirm(String searchTerm) {

        JishoPreference.setInPref(IConstants.PREF_OFFLINE_MODE, true);

        hideErrorViews();
        if(searchTerm != null && searchTerm.length() > 0)
            searchOffline(searchTerm);
    }

    private void setOrientation() {
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
    public void onItemClick(String tag) {
        String[] pieces = tag.split(":");
        view.openDetails(pieces[0], pieces[1]);
    }

}
