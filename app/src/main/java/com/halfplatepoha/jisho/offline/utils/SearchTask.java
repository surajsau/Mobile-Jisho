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
    private int searchType;

    public SearchTask(SearchResultListener listener, OfflineDbHelper db, String query, int searchType) {
        this.listener = listener;
        this.db = db;
        this.query = query;
        this.searchType = searchType;
    }

    @Override
    protected void onPreExecute() {
        listener.toggleProgressBar(true);
    }

    @Override
    protected List<ListEntry> doInBackground(Void... params) {
        return db.searchDictionary(query, searchType);
    }

    @Override
    protected void onPostExecute(List<ListEntry> entries) {
        listener.onResult(entries);
        listener.toggleProgressBar(false);
    }

    public interface SearchResultListener {
        void onResult(List<ListEntry> entries);
        void toggleProgressBar(boolean show);
    }
}
