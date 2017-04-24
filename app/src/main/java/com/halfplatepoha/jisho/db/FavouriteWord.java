package com.halfplatepoha.jisho.db;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by surjo on 22/04/17.
 */

public class FavouriteWord extends RealmObject {

    private boolean is_common;
    private String primary;
    private RealmList<FavJapanese> japanese;
    private RealmList<FavSense> senses;

    public String getPrimary() {
        return primary;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }

    public boolean is_common() {
        return is_common;
    }

    public void setIs_common(boolean is_common) {
        this.is_common = is_common;
    }

    public RealmList<FavJapanese> getJapanese() {
        return japanese;
    }

    public void setJapanese(RealmList<FavJapanese> japanese) {
        this.japanese = japanese;
    }

    public RealmList<FavSense> getSenses() {
        return senses;
    }

    public void setSenses(RealmList<FavSense> senses) {
        this.senses = senses;
    }
}
