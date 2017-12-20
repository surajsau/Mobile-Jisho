package com.halfplatepoha.jisho.jdb;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by surjo on 19/12/17.
 */

public class Gloss extends RealmObject {

    public String field;

    public RealmList<String> tags;

    public boolean common;

    public String english;

    public RealmList<String> related;

}
