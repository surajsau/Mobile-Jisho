package com.halfplatepoha.jisho.db;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by surjo on 22/04/17.
 */

public class History extends RealmObject {

    private RealmList<String> history;

    public RealmList<String> getHistory() {
        return history;
    }

    public void setHistory(RealmList<String> history) {
        this.history = history;
    }

}
