package com.halfplatepoha.jisho.offline.utils;

import android.os.AsyncTask;

import com.halfplatepoha.jisho.offline.OfflineDbHelper;
import com.halfplatepoha.jisho.offline.model.Entry;

/**
 * Created by surjo on 18/07/17.
 */

public class GetEntryDetailsTask extends AsyncTask<Void, Void, Entry> {

    private EntryDetailsTaskListener listener;
    private OfflineDbHelper db;
    private int entryId;

    public GetEntryDetailsTask(EntryDetailsTaskListener listener, OfflineDbHelper db, int entryId) {
        this.listener = listener;
        this.db = db;
        this.entryId = entryId;
    }

    @Override
    protected Entry doInBackground(Void... params) {
        return db.getEntry(entryId);
    }

    @Override
    protected void onPostExecute(Entry entry) {
        listener.onResult(entry);
    }

    public interface EntryDetailsTaskListener {
        void onResult(Entry entry);
    }
}
