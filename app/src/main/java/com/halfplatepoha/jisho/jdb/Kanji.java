package com.halfplatepoha.jisho.jdb;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Kanji extends RealmObject {
    
    public RealmList<Codepoint> codepoints;
    public RealmList<DicNumber> dicNumbers;
    public int freq;
    public int grade;
    public String literal;
    public RealmList<Meaning> meanings;
    public RealmList<QueryCode> queryCodes;
    public RealmList<Reading> readings;
    public int strokeCount;

}