package com.halfplatepoha.jisho.kanjidetail;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.github.badoualy.kanjistroke.KanjiStrokeView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.halfplatepoha.jisho.R;
import com.halfplatepoha.jisho.base.BaseDialog;
import com.halfplatepoha.jisho.jdb.Kanji;
import com.halfplatepoha.jisho.utils.IConstants;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;

import butterknife.BindView;
import io.realm.Realm;

/**
 * Created by surjo on 18/12/17.
 */

public class KanjiDetailFragment extends BaseDialog<KanjiDetailContract.Presenter> implements KanjiDetailContract.View {

    private static final String KEY_KANJI = "kanji";

    @BindView(R.id.ivKanji)
    KanjiStrokeView ivKanjiView;

    private String kanji;

    private Realm realm;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_kanji_detail;
    }

    public static KanjiDetailFragment getInstance(String kanji) {
        KanjiDetailFragment fragment = new KanjiDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_KANJI, kanji);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        kanji = getArguments().getString(KEY_KANJI);
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Kanji k = realm.where(Kanji.class).equalTo("literal", kanji).findFirst();

        if(k != null) {
            ivKanjiView.loadPathData(k.strokes);
            ivKanjiView.startDrawAnimation();
        }

//        new GetStrokesTask().execute(kanji);
    }

//    private class GetStrokesTask extends AsyncTask<String, Void, ArrayList<String>> {
//
//        @Override
//        protected ArrayList<String> doInBackground(String... strings) {
//            try {
//                BufferedReader reader = null;
//                InputStreamReader isr = null;
//                InputStream is = null;
//                try {
//                    is = getContext().getAssets().open("kanji.json");
//                    isr = new InputStreamReader(is);
//                    reader = new BufferedReader(isr);
//
//                    Gson gson = new GsonBuilder().create();
//
//
//
//                    try {
//
//                        String jsonStr = null;
//
//                        FileChannel fc = fis.getChannel();
//                        MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
//
//                        jsonStr = Charset.defaultCharset().decode(mbb).toString();
//
//                        Log.e("JSON", jsonStr);
//
//                        JSONArray jsonObject = new JSONArray(jsonStr);
//                        ArrayList<String> list = new ArrayList<>();
//                        for (int i = 0; i < jsonObject.length(); i++) {
//                            list.add(jsonObject.getString(i));
//                        }
//
//                        return list;
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    } finally {
//                        fis.close();
//                    }
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//
//                    is.close();
//                    isr.close();
//                    reader.close();
//
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<String> strings) {
//            super.onPostExecute(strings);
//            ivKanjiView.loadPathData(strings);
//            ivKanjiView.startDrawAnimation();
//        }
//    }
}
