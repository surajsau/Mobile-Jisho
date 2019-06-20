package com.halfplatepoha.jisho.v2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.halfplatepoha.jisho.BaseActivity;
import com.halfplatepoha.jisho.R;

import butterknife.OnClick;

public class ExperimentActivity extends BaseActivity {

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_experiment;
    }

    @OnClick(R.id.btnRun)
    public void click() {
        String where = "SELECT * FROM counter WHERE ROWID == 19969";
        SQLiteDatabase db = ExperimentApp.getDBInstance().getReadableDatabase();
        Cursor cursor = db.rawQuery(where, null);

        int position = 0;

        while(cursor.moveToNext()) {
            Log.d("query", cursor.getString(position));
            position++;
        }
    }
}
