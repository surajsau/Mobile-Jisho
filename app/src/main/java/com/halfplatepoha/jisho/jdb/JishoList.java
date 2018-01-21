package com.halfplatepoha.jisho.jdb;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by surjo on 03/01/18.
 */

public class JishoList extends RealmObject {

    @Required
    @PrimaryKey
    public String name;

    public RealmList<Entry> entries;
}
