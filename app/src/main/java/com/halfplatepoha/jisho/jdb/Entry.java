package com.halfplatepoha.jisho.jdb;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by surjo on 19/12/17.
 */

public class Entry extends RealmObject {

    public long entrySeq;

    public String japanese;

    public RealmList<String> pos;

    public RealmList<Gloss> glosses;

    public boolean common;

    public String furigana;

    public RealmList<String> tags;

    public RealmList<String> fields;

    public RealmList<String> kanjiTags;

    public RealmList<String> dialects;

    public RealmList<String> kanaTags;

    public RealmList<Entry> related;

    public String note;

}
