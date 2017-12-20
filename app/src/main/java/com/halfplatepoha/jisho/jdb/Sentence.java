package com.halfplatepoha.jisho.jdb;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by surjo on 20/12/17.
 */

public class Sentence extends RealmObject {

    public RealmList<Split> splits;
    public String sentence;
    public String english;

}
