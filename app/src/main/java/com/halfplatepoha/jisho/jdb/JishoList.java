package com.halfplatepoha.jisho.jdb;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by surjo on 03/01/18.
 */

public class JishoList extends RealmObject {

    public String name;
    public RealmList<Entry> entries;
}
