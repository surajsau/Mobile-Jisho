package com.halfplatepoha.jisho.v2;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class RelineDB extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "Japanese4.db";
    private static final int DATABASE_VERSION = 1;

    public RelineDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

}
