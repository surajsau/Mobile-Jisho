package com.halfplatepoha.jisho.db;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by surjo on 22/04/17.
 */

public class History extends RealmObject {

    private RealmList<RealmString> history;

    public RealmList<RealmString> getHistory() {
        return history;
    }

    public void setHistory(RealmList<RealmString> history) {
        this.history = history;
    }
}
