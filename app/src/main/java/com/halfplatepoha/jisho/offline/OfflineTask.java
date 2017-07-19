package com.halfplatepoha.jisho.offline;

import com.halfplatepoha.jisho.offline.utils.GetEntryDetailsTask;
import com.halfplatepoha.jisho.offline.utils.SearchTask;

/**
 * Created by surjo on 19/07/17.
 */

public class OfflineTask {

    private static OfflineTask mInstance;
    private OfflineDbHelper db;

    private OfflineTask(OfflineDbHelper db) {
        this.db = db;
    }

    public static OfflineTask getInstance(OfflineDbHelper db) {
        if(mInstance == null)
            mInstance = new OfflineTask(db);
        return mInstance;
    }

    public void search(String searchString, SearchTask.SearchResultListener listener) {
        new SearchTask(listener, db, searchString).execute();
    }

    public void getDetails(int id, GetEntryDetailsTask.EntryDetailsTaskListener listener) {
        new GetEntryDetailsTask(listener, db, id).execute();
    }
}
