package com.halfplatepoha.jisho.settings;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.halfplatepoha.jisho.Download;
import com.halfplatepoha.jisho.DownloadService;
import com.halfplatepoha.jisho.JishoPreference;
import com.halfplatepoha.jisho.jdb.KanjiElement;
import com.halfplatepoha.jisho.jdb.transfermodel.GElement;
import com.halfplatepoha.jisho.jdb.transfermodel.GStrokes;
import com.halfplatepoha.jisho.kanjidetail.KanjiDetailFragment;
import com.halfplatepoha.jisho.KanjiStrokesService;
import com.halfplatepoha.jisho.R;
import com.halfplatepoha.jisho.SingleFragmentActivity;
import com.halfplatepoha.jisho.SwitchButton;
import com.halfplatepoha.jisho.analytics.Analytics;
import com.halfplatepoha.jisho.base.BaseFragmentActivity;
import com.halfplatepoha.jisho.jdb.Codepoint;
import com.halfplatepoha.jisho.jdb.DicNumber;
import com.halfplatepoha.jisho.jdb.Entry;
import com.halfplatepoha.jisho.jdb.Gloss;
import com.halfplatepoha.jisho.jdb.Kanji;
import com.halfplatepoha.jisho.jdb.Meaning;
import com.halfplatepoha.jisho.jdb.QueryCode;
import com.halfplatepoha.jisho.jdb.Reading;
import com.halfplatepoha.jisho.jdb.Sentence;
import com.halfplatepoha.jisho.jdb.Split;
import com.halfplatepoha.jisho.jdb.transfermodel.GCodepoint;
import com.halfplatepoha.jisho.jdb.transfermodel.GDicNumber;
import com.halfplatepoha.jisho.jdb.transfermodel.GEntry;
import com.halfplatepoha.jisho.jdb.transfermodel.GGloss;
import com.halfplatepoha.jisho.jdb.transfermodel.GKanji;
import com.halfplatepoha.jisho.jdb.transfermodel.GMeaning;
import com.halfplatepoha.jisho.jdb.transfermodel.GQueryCode;
import com.halfplatepoha.jisho.jdb.transfermodel.GReading;
import com.halfplatepoha.jisho.jdb.transfermodel.GSentence;
import com.halfplatepoha.jisho.jdb.transfermodel.GSplit;
import com.halfplatepoha.jisho.utils.IConstants;
import com.halfplatepoha.jisho.utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmList;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class SettingsActivity extends BaseFragmentActivity<SettingsContract.Presenter> implements SettingsContract.View {

    private static final int REQ_PERM_STORAGE = 101;
    private static final String[] STORAGE_PERMS = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                                            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private static final String KANJIDIC = "kanjidic-%d.json";
    private static final String EDICT = "edict-%d.json";
    private static final String TANAKA = "tanaka-%d.json";

    private static final int DOWNLOAD_TYPE_JISHO = 102;
    private static final int DOWNLOAD_TYPE_STROKES = 103;

    private static final int KANJIDIC_LIMIT = 13;
    private static final int TANAKA_LIMIT = 14;
    private static final int EDICT_LIMIT = 27;

    private int downloadType;

    @BindView(R.id.swtchOffline)
    SwitchButton swtchOffline;

    @BindView(R.id.offlineView)
    View offlineView;

    @BindView(R.id.downloadView)
    View downloadView;

    @BindView(R.id.offlineWarning)
    View offlineWarning;

    @BindView(R.id.tvDownloading)
    View tvDownloading;

    @BindView(R.id.btnStartDownload)
    View downloadButton;

    private Snackbar downloadSnackbar;

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        realm = Realm.getDefaultInstance();

        boolean isOffline = JishoPreference.getBooleanFromPref(IConstants.PREF_OFFLINE_MODE, false);

        swtchOffline.setChecked(isOffline);
        swtchOffline.setBackColorRes(isOffline ? R.color.colorOn : R.color.colorOff);

        if(Utils.isFileDowloaded()) {
            downloadView.setVisibility(View.GONE);
            offlineView.setVisibility(View.VISIBLE);
            offlineWarning.setVisibility(View.VISIBLE);
        } else {
            downloadView.setVisibility(View.VISIBLE);
            offlineView.setVisibility(View.GONE);
            offlineWarning.setVisibility(View.GONE);
        }

        downloadSnackbar = Snackbar.make(background, "Beginning download...", Snackbar.LENGTH_INDEFINITE);

    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(downloadBroadcastReceiver, new IntentFilter(IConstants.DOWNLOAD_BROADCAST_FILTER));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(downloadBroadcastReceiver);
    }

    @OnCheckedChanged({R.id.swtchOffline})
    public void offlineCheckedChange(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.swtchOffline:
                Analytics.getInstance().recordOfflineSwitch(isChecked);

                JishoPreference.setInPref(IConstants.PREF_OFFLINE_MODE, isChecked);

                swtchOffline.setBackColorRes(isChecked ? R.color.colorOn : R.color.colorOff);
                break;
        }
    }

    @AfterPermissionGranted(REQ_PERM_STORAGE)
    private void checkStoragePersmissionAndStartDownload() {
        if(EasyPermissions.hasPermissions(this, STORAGE_PERMS)) {
            switch (downloadType) {
                case DOWNLOAD_TYPE_JISHO:
                    Intent downloadIntent = new Intent(getApplicationContext(), DownloadService.class);
                    startService(downloadIntent);
                    break;

                case DOWNLOAD_TYPE_STROKES:
                    Intent strokesDownloadIntent = new Intent(getApplicationContext(), KanjiStrokesService.class);
                    startService(strokesDownloadIntent);
                    break;
            }
        } else {
            EasyPermissions.requestPermissions(this,
                    "Storage permission required to download Jisho for offline mode",
                    REQ_PERM_STORAGE,
                    STORAGE_PERMS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void showDownloadFileDialog(String title, String message, final int downloadType) {

        this.downloadType = downloadType;

        new MaterialDialog.Builder(this)
                .title(title)
                .content(message)
                .positiveText("Download")
                .negativeText("Cancel")
                .canceledOnTouchOutside(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Analytics.getInstance().recordDownload();

                        if(!Utils.checkInternetConnection(dialog.getContext()))
                            showNoInternetDialog();
                        else
                            checkStoragePersmissionAndStartDownload();

                        dialog.dismiss();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void showNoInternetDialog() {
        new MaterialDialog.Builder(this)
                .title("Check internet connection")
                .content("There seems to be some issue with the internet connection. Can you please check it once?")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .positiveText("OK")
                .build()
                .show();
    }

    @OnClick(R.id.back)
    public void back() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(IConstants.EXTRA_OFFLINE_STATUS, swtchOffline.isChecked());
        setResult(RESULT_OK, resultIntent);

        finish();
    }

    @OnClick(R.id.tvAbout)
    public void openAbout() {
        startActivity(SingleFragmentActivity.getLaunchIntent(this, SingleFragmentActivity.FRAG_ABOUT, "About"));
    }

    @OnClick(R.id.tvLicense)
    public void openLicense() {
        startActivity(SingleFragmentActivity.getLaunchIntent(this, SingleFragmentActivity.FRAG_LICENSE, "License"));
    }

    @OnClick(R.id.btnStartDownload)
    public void startDownload() {
        showDownloadFileDialog("Download Offline Jisho",
                "To enable offline mode of Jisho, we\'ll have to download the offline dictionary. It\'ll be best if you\'ve good internet connection for uninterrupted download.",
                DOWNLOAD_TYPE_JISHO);
    }

    @OnClick(R.id.btnDownloadStrokes)
    public void downloadStrokes() {

        showDownloadFileDialog("Download Kanji Strokes",
                "To see kanji strokes, we\'ll have to download the strokes files. It\'ll be best if you\'ve good internet connection for uninterrupted download.",
                DOWNLOAD_TYPE_STROKES);
    }

    @OnClick(R.id.btnConsumeDB)
    public void consumeDB() {
        consumeEdict(0);
        consumeTanaka(0);
        consumeKanjiDic(0);
    }

    private void consumeKanjiStroke() {
        new ReadFileTask<>(this, GStrokes[].class, new ReadFileTask.Listener<GStrokes>() {
            @Override
            public void onResult(GStrokes[] result) {
                Log.e("CONSUME", "KanjiStrokes");
                realm.beginTransaction();

                for(GStrokes gStroke : result) {
                    Kanji kanji = realm.where(Kanji.class).equalTo("literal", gStroke.name).findFirst();

                    if(kanji != null) {
                        for(GElement gElement : gStroke.elements) {
                            KanjiElement element = realm.createObject(KanjiElement.class);
                            element.depth = gElement.depth;
                            element.element = gElement.element;

                            kanji.elements.add(element);
                        }

                        for(String gPaths : gStroke.paths) {
                            kanji.strokes.add(gPaths);
                        }

                        realm.insertOrUpdate(kanji);
                    }
                }

                realm.commitTransaction();
            }
        }).execute("kanji.json");
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_settings;
    }

    private BroadcastReceiver downloadBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent != null){

                Download download = intent.getParcelableExtra(DownloadService.EXTRA_DOWNLOAD);

                if(download.getProgress() == 100){
                    downloadSnackbar.dismiss();
                    downloadView.setVisibility(View.GONE);
                    offlineView.setVisibility(View.VISIBLE);

                    Snackbar.make(background, getString(R.string.download_completed), Snackbar.LENGTH_SHORT);
                } else {
                    downloadSnackbar.setText(String.format(getString(R.string.download_progress), download.getProgress()));
                }

                if(!downloadSnackbar.isShown()) {
                    downloadButton.setVisibility(View.GONE);
                    tvDownloading.setVisibility(View.VISIBLE);
                    downloadSnackbar.show();
                }
            }
        }
    };

    private void consumeKanjiDic(final int count) {
        new ReadFileTask<>(getApplicationContext(), GKanji[].class, new ReadFileTask.Listener<GKanji>() {
            @Override
            public void onResult(GKanji[] kanjis) {
                Log.e("CONSUME", String.format(KANJIDIC, count));
                realm.beginTransaction();

                for (GKanji gKanji : kanjis) {
                    Kanji kanji = realm.createObject(Kanji.class);

                    kanji.freq = gKanji.freq;
                    kanji.grade = gKanji.grade;
                    kanji.strokeCount = gKanji.strokeCount;
                    kanji.literal = gKanji.literal;

                    if(gKanji.dicNumbers != null) {
                        for (GDicNumber gDicNumber : gKanji.dicNumbers) {
                            DicNumber dicNumber = realm.createObject(DicNumber.class);
                            dicNumber.dicRef = gDicNumber.dicRef;
                            dicNumber.drType = gDicNumber.drType;

                            kanji.dicNumbers.add(dicNumber);
                        }
                    }

                    if(gKanji.codepoints != null) {
                        for (GCodepoint gCodepoint : gKanji.codepoints) {
                            Codepoint codepoint = realm.createObject(Codepoint.class);
                            codepoint.cpType = gCodepoint.cpType;
                            codepoint.cpValue = gCodepoint.cpValue;

                            kanji.codepoints.add(codepoint);
                        }
                    }

                    if(gKanji.meanings != null) {
                        for (GMeaning gMeaning : gKanji.meanings) {
                            Meaning meaning = realm.createObject(Meaning.class);
                            meaning.meaning = gMeaning.meaning;
                            meaning.lang = gMeaning.lang;

                            kanji.meanings.add(meaning);
                        }
                    }

                    if(gKanji.readings != null) {
                        for (GReading gReading : gKanji.readings) {
                            Reading reading = realm.createObject(Reading.class);
                            reading.reading = gReading.reading;
                            reading.type = gReading.type;

                            kanji.readings.add(reading);
                        }
                    }

                    if(gKanji.queryCodes != null) {
                        for (GQueryCode gQueryCode : gKanji.queryCodes) {
                            QueryCode queryCode = realm.createObject(QueryCode.class);
                            queryCode.qCode = gQueryCode.qCode;
                            queryCode.qcType = gQueryCode.qcType;

                            kanji.queryCodes.add(queryCode);
                        }
                    }

                    realm.insertOrUpdate(kanji);
                }

                realm.commitTransaction();

                int nextCount = count + 1;

                if(nextCount <= KANJIDIC_LIMIT) {
                    consumeKanjiDic(nextCount);
                } else {
                    consumeKanjiStroke();
                }
            }
        }).execute(String.format(KANJIDIC, count));
    }

    private void consumeEdict(final int count) {
        new ReadFileTask<>(getApplicationContext(), GEntry[].class, new ReadFileTask.Listener<GEntry>() {
            @Override
            public void onResult(GEntry[] gEntries) {
                Log.e("CONSUME", String.format(EDICT, count));
                realm.beginTransaction();

                for(GEntry gEntry : gEntries) {
                    Entry entry = realm.createObject(Entry.class);

                    entry.japanese = gEntry.japanese;
                    entry.furigana = gEntry.furigana;
                    entry.entrySeq = gEntry.entrySeq;
                    entry.common = gEntry.common;

                    if(gEntry.glosses != null) {
                        for(GGloss gGloss : gEntry.glosses) {
                            Gloss gloss = realm.createObject(Gloss.class);
                            gloss.english = gGloss.english;
                            gloss.field = gGloss.field;
                            gloss.common = gGloss.common;

                            if(gGloss.related != null) {
                                for(String glossRelated : gGloss.related) {
                                    gloss.related.add(glossRelated);
                                }
                            }

                            if(gGloss.tags != null) {
                                for(String glossTag : gGloss.tags) {
                                    gloss.tags.add(glossTag);
                                }
                            }

                            entry.glosses.add(gloss);
                        }
                    }

                    if(gEntry.tags != null) {
                        for (String tag : gEntry.tags) {
                            entry.tags.add(tag);
                        }
                    }

                    if(gEntry.dialects != null) {
                        for (String dialect : gEntry.dialects) {
                            entry.dialects.add(dialect);
                        }
                    }

                    if(gEntry.fields != null) {
                        for (String field : gEntry.fields) {
                            entry.fields.add(field);
                        }
                    }

                    if(gEntry.kanaTags != null) {
                        for (String kanatag : gEntry.kanaTags) {
                            entry.kanaTags.add(kanatag);
                        }
                    }

                    if(gEntry.kanjiTags != null) {
                        for (String kanjitag : gEntry.kanjiTags) {
                            entry.kanjiTags.add(kanjitag);
                        }
                    }

                    if(gEntry.pos != null) {
                        for (String pos : gEntry.pos) {
                            entry.pos.add(pos);
                        }
                    }

                    realm.insertOrUpdate(entry);
                }

                realm.commitTransaction();

                int nextCount = count + 1;

                if(nextCount <= EDICT_LIMIT) {
                    consumeEdict(nextCount);
                }
            }
        }).execute(String.format(EDICT, count));
    }

    private void consumeTanaka(final int count) {
        new ReadFileTask<>(getApplicationContext(), GSentence[].class, new ReadFileTask.Listener<GSentence>() {
            @Override
            public void onResult(GSentence[] gSentences) {
                Log.e("CONSUME", String.format(TANAKA, count));
                realm.beginTransaction();

                for(GSentence gSentence : gSentences) {
                    Sentence sentence = realm.createObject(Sentence.class);

                    sentence.sentence = gSentence.sentence;
                    sentence.english = gSentence.english;

                    if(gSentence.splits != null) {
                        for(GSplit gSplit : gSentence.splits) {
                            Split split = realm.createObject(Split.class);
                            split.isGood = gSplit.isGood;
                            split.keyword = gSplit.keyword;
                            split.reading = gSplit.reading;
                            split.sentenceForm = gSplit.sentenceForm;
                            split.sense = gSplit.sense;

                            sentence.splits.add(split);
                        }
                    }

                    realm.insertOrUpdate(sentence);
                }

                realm.commitTransaction();

                int nextCount = count + 1;

                if(nextCount <= TANAKA_LIMIT) {
                    consumeTanaka(nextCount);
                }
            }
        }).execute(String.format(TANAKA, count));
    }

    private static class ReadFileTask<A> extends AsyncTask<String, Void, A[]> {

        private Class<A[]> clazz;
        private Context context;
        private Listener<A> listener;

        public ReadFileTask(Context context, Class<A[]> clazz, Listener<A> listener) {
            this.clazz = clazz;
            this.listener = listener;
            this.context = context;
        }

        @Override
        protected A[] doInBackground(String... strings) {
            BufferedReader reader = null;
            try {
                InputStream is = context.getAssets().open(strings[0]);
                InputStreamReader isr = new InputStreamReader(is);
                reader = new BufferedReader(isr);
                Gson gson = new GsonBuilder().create();
                A[] results = gson.fromJson(reader, clazz);
                return results;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(A[] as) {
            if(listener != null)
                listener.onResult(as);
        }

        interface Listener<A> {
            void onResult(A[] result);
        }
    }

//    private void populateEdict() {
//        Log.e("FILE", EDICT);
//        JsonReader reader = null;
//        try {
//            InputStream is = getApplicationContext().getAssets().open(EDICT);
//            InputStreamReader isr = new InputStreamReader(is);
//            reader = new JsonReader(isr);
//            Gson gson = new GsonBuilder().create();
//
//            GEntry[] gEntries = gson.fromJson(reader, GEntry[].class);
//
//            realm.beginTransaction();
//
//            for(GEntry gEntry : gEntries) {
//                Entry entry = realm.createObject(Entry.class);
//
//                entry.japanese = gEntry.japanese;
//                entry.furigana = gEntry.furigana;
//                entry.entrySeq = gEntry.entrySeq;
//                entry.common = gEntry.common;
//
//                if(gEntry.glosses != null) {
//                    for(GGloss gGloss : gEntry.glosses) {
//                        Gloss gloss = realm.createObject(Gloss.class);
//                        gloss.english = gGloss.english;
//                        gloss.field = gGloss.field;
//                        gloss.common = gGloss.common;
//
//                        RealmList<String> glossRelateds = new RealmList<>();
//                        RealmList<String> glossTags = new RealmList<>();
//
//                        if(gGloss.related != null) {
//                            for(String glossRelated : gGloss.related) {
//                                glossRelateds.add(glossRelated);
//                            }
//                        }
//
//                        if(gGloss.tags != null) {
//                            for(String glossTag : gGloss.tags) {
//                                glossTags.add(glossTag);
//                            }
//                        }
//
//                        gloss.related = glossRelateds;
//                        gloss.tags = glossTags;
//
//                        entry.glosses.add(gloss);
//                    }
//                }
//
//                if(gEntry.tags != null) {
//                    for (String tag : gEntry.tags) {
//                        entry.tags.add(tag);
//                    }
//                }
//
//                if(gEntry.dialects != null) {
//                    for (String dialect : gEntry.dialects) {
//                        entry.dialects.add(dialect);
//                    }
//                }
//
//                if(gEntry.fields != null) {
//                    for (String field : gEntry.fields) {
//                        entry.fields.add(field);
//                    }
//                }
//
//                if(gEntry.kanaTags != null) {
//                    for (String kanatag : gEntry.kanaTags) {
//                        entry.kanaTags.add(kanatag);
//                    }
//                }
//
//                if(gEntry.kanjiTags != null) {
//                    for (String kanjitag : gEntry.kanjiTags) {
//                        entry.kanjiTags.add(kanjitag);
//                    }
//                }
//
//                if(gEntry.pos != null) {
//                    for (String pos : gEntry.pos) {
//                        entry.pos.add(pos);
//                    }
//                }
//
//                realm.insertOrUpdate(entry);
//            }
//
//            realm.commitTransaction();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void populateKanjiDic() {
//        Log.e("FILE", KANJIDIC);
//        BufferedReader reader = null;
//        try {
//            InputStream is = getApplicationContext().getAssets().open(KANJIDIC);
//            InputStreamReader isr = new InputStreamReader(is);
//            reader = new BufferedReader(isr);
//            Gson gson = new GsonBuilder().create();
//
//            GKanji[] kanjis = gson.fromJson(reader, GKanji[].class);
//
//            realm.beginTransaction();
//
//            for (GKanji gKanji : kanjis) {
//                Kanji kanji = realm.createObject(Kanji.class);
//
//                kanji.freq = gKanji.freq;
//                kanji.grade = gKanji.grade;
//                kanji.strokeCount = gKanji.strokeCount;
//                kanji.literal = gKanji.literal;
//
//                if(gKanji.dicNumbers != null) {
//                    for (GDicNumber gDicNumber : gKanji.dicNumbers) {
//                        DicNumber dicNumber = realm.createObject(DicNumber.class);
//                        dicNumber.dicRef = gDicNumber.dicRef;
//                        dicNumber.drType = gDicNumber.drType;
//
//                        kanji.dicNumbers.add(dicNumber);
//                    }
//                }
//
//                if(gKanji.codepoints != null) {
//                    for (GCodepoint gCodepoint : gKanji.codepoints) {
//                        Codepoint codepoint = realm.createObject(Codepoint.class);
//                        codepoint.cpType = gCodepoint.cpType;
//                        codepoint.cpValue = gCodepoint.cpValue;
//
//                        kanji.codepoints.add(codepoint);
//                    }
//                }
//
//                if(gKanji.meanings != null) {
//                    for (GMeaning gMeaning : gKanji.meanings) {
//                        Meaning meaning = realm.createObject(Meaning.class);
//                        meaning.meaning = gMeaning.meaning;
//                        meaning.lang = gMeaning.lang;
//
//                        kanji.meanings.add(meaning);
//                    }
//                }
//
//                if(gKanji.readings != null) {
//                    for (GReading gReading : gKanji.readings) {
//                        Reading reading = realm.createObject(Reading.class);
//                        reading.reading = gReading.reading;
//                        reading.type = gReading.type;
//
//                        kanji.readings.add(reading);
//                    }
//                }
//
//                if(gKanji.queryCodes != null) {
//                    for (GQueryCode gQueryCode : gKanji.queryCodes) {
//                        QueryCode queryCode = realm.createObject(QueryCode.class);
//                        queryCode.qCode = gQueryCode.qCode;
//                        queryCode.qcType = gQueryCode.qcType;
//
//                        kanji.queryCodes.add(queryCode);
//                    }
//                }
//
//                realm.insertOrUpdate(kanji);
//            }
//
//            realm.commitTransaction();
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void populateTanaka() {
//        Log.e("FILE", TANAKA);
//        BufferedReader reader = null;
//        try {
//            InputStream is = getApplicationContext().getAssets().open(TANAKA);
//            InputStreamReader isr = new InputStreamReader(is);
//            reader = new BufferedReader(isr);
//            Gson gson = new GsonBuilder().create();
//
//            GSentence[] gSentences = gson.fromJson(reader, GSentence[].class);
//
//            realm.beginTransaction();
//
//            for(GSentence gSentence : gSentences) {
//                Sentence sentence = realm.createObject(Sentence.class);
//
//                sentence.sentence = gSentence.sentence;
//                sentence.english = gSentence.english;
//
//                if(gSentence.splits != null) {
//                    for(GSplit gSplit : gSentence.splits) {
//                        Split split = realm.createObject(Split.class);
//                        split.isGood = gSplit.isGood;
//                        split.keyword = gSplit.keyword;
//                        split.reading = gSplit.reading;
//                        split.sentenceForm = gSplit.sentenceForm;
//                        split.sense = gSplit.sense;
//
//                        sentence.splits.add(split);
//                    }
//                }
//
//                realm.insertOrUpdate(sentence);
//            }
//
//            realm.commitTransaction();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    }

}
