package com.halfplatepoha.jisho.v2;

import android.Manifest;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.halfplatepoha.jisho.BaseActivity;
import com.halfplatepoha.jisho.R;
import com.halfplatepoha.jisho.v2.realm.Counter;
import com.halfplatepoha.jisho.v2.realm.Kanji;

import java.io.File;

import butterknife.OnClick;
import io.realm.Realm;
import io.realm.internal.IOException;
import pub.devrel.easypermissions.EasyPermissions;

public class ExperimentActivity extends BaseActivity {

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_experiment;
    }

    @OnClick(R.id.btnExtract)
    public void extract() {
        if(EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            final Realm realm = Realm.getInstance(ExperimentApp.getInstance().getRelineRealmConfiguration());

            try {
                final File file = new File(Environment.getExternalStorageDirectory().getPath().concat("/sample.realm"));
                if (file.exists()) {
                    //noinspection ResultOfMethodCallIgnored
                    file.delete();
                }

                realm.writeCopyTo(file);
                Toast.makeText(this, "Success export realm file", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                realm.close();
                e.printStackTrace();
            }
        } else {
            EasyPermissions.requestPermissions(this, "Need Storage", 101, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    @OnClick(R.id.btnRun)
    public void run() {
        Realm realm = Realm.getInstance(ExperimentApp.getInstance().getRelineRealmConfiguration());
        SQLiteDatabase db = ExperimentApp.getDBInstance().getReadableDatabase();

        extractCounters(db, realm);
        extractKanji(db, realm);

        realm.close();
    }

    private void extractKanji(SQLiteDatabase db, Realm realm) {
        String query = "SELECT * FROM kanji";

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            do {
                realm.beginTransaction();

                Kanji counter = realm.createObject(Kanji.class, cursor.getString(0));
                counter.setKunyomi(cursor.getString(3));
                counter.setOnyomi(cursor.getString(1));
                counter.setNanori(cursor.getString(5));
                counter.setRadical(cursor.getInt(7));
                counter.setGrade(cursor.getInt(8));
                counter.setLayout(cursor.getInt(11));
                counter.setSkip(cursor.getString(13));

                realm.commitTransaction();

                Log.d("task", cursor.getString(0));
            } while (cursor.moveToNext());
        }
    }

    private void extractCounters(SQLiteDatabase db, Realm realm) {
        String query = "SELECT * FROM counter";

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            do {
                realm.beginTransaction();

                Counter counter = realm.createObject(Counter.class, cursor.getString(0));
                counter.setCount1(cursor.getString(1));
                counter.setCount2(cursor.getString(2));
                counter.setCount3(cursor.getString(3));
                counter.setCount4(cursor.getString(4));
                counter.setCount5(cursor.getString(5));
                counter.setCount6(cursor.getString(6));
                counter.setCount7(cursor.getString(7));
                counter.setCount8(cursor.getString(8));
                counter.setCount9(cursor.getString(9));
                counter.setCount10(cursor.getString(10));

                realm.commitTransaction();

                Log.d("task", cursor.getString(0));
            } while (cursor.moveToNext());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
