package com.halfplatepoha.jisho.offline.utils;

import android.os.AsyncTask;

import com.halfplatepoha.jisho.offline.OfflineDbHelper;
import com.halfplatepoha.jisho.offline.model.ListEntry;

import java.util.List;

/**
 * Created by surjo on 18/07/17.
 */

public class SearchTask extends AsyncTask<Void, Void, List<ListEntry>> {

    private SearchResultListener listener;
    private OfflineDbHelper db;
    private String query;

    public SearchTask(SearchResultListener listener, OfflineDbHelper db, String query) {
        this.listener = listener;
        this.db = db;
        this.query = query;
    }

    @Override
    protected List<ListEntry> doInBackground(Void... params) {
        int searchType = SearchUtil.getSearchType(query);
        return db.searchDictionary(query, searchType);
    }

    @Override
    protected void onPostExecute(List<ListEntry> entries) {
        listener.onResult(entries);
    }

    public interface SearchResultListener {
        void onResult(List<ListEntry> entries);
    }
}
