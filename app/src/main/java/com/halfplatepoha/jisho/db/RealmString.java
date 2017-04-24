package com.halfplatepoha.jisho.db;

import io.realm.RealmObject;

/**
 * Created by surjo on 22/04/17.
 */

public class RealmString extends RealmObject {
    private String value;

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
