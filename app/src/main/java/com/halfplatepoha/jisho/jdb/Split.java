package com.halfplatepoha.jisho.jdb;

import io.realm.RealmObject;

/**
 * Created by surjo on 20/12/17.
 */

public class Split extends RealmObject {

    public String keyword;
    public String sense;
    public String sentenceForm;
    public boolean isGood;
    public String reading;

}
