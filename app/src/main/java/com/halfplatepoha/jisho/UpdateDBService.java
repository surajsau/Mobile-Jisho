package com.halfplatepoha.jisho;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.halfplatepoha.jisho.jdb.Codepoint;
import com.halfplatepoha.jisho.jdb.DicNumber;
import com.halfplatepoha.jisho.jdb.Entry;
import com.halfplatepoha.jisho.jdb.Gloss;
import com.halfplatepoha.jisho.jdb.Kanji;
import com.halfplatepoha.jisho.jdb.KanjiElement;
import com.halfplatepoha.jisho.jdb.Meaning;
import com.halfplatepoha.jisho.jdb.QueryCode;
import com.halfplatepoha.jisho.jdb.Reading;
import com.halfplatepoha.jisho.jdb.Sentence;
import com.halfplatepoha.jisho.jdb.Split;
import com.halfplatepoha.jisho.jdb.transfermodel.GCodepoint;
import com.halfplatepoha.jisho.jdb.transfermodel.GDicNumber;
import com.halfplatepoha.jisho.jdb.transfermodel.GElement;
import com.halfplatepoha.jisho.jdb.transfermodel.GEntry;
import com.halfplatepoha.jisho.jdb.transfermodel.GGloss;
import com.halfplatepoha.jisho.jdb.transfermodel.GKanji;
import com.halfplatepoha.jisho.jdb.transfermodel.GMeaning;
import com.halfplatepoha.jisho.jdb.transfermodel.GQueryCode;
import com.halfplatepoha.jisho.jdb.transfermodel.GReading;
import com.halfplatepoha.jisho.jdb.transfermodel.GSentence;
import com.halfplatepoha.jisho.jdb.transfermodel.GSplit;
import com.halfplatepoha.jisho.jdb.transfermodel.GStrokes;
import com.halfplatepoha.jisho.settings.SettingsActivity;
import com.halfplatepoha.jisho.utils.IConstants;

import io.realm.Realm;

/**
 * Created by surjo on 03/01/18.
 */

public class UpdateDBService extends Service {

    public static final String KEY_PROGRESS = "key_progress";

    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;


    private static final String KANJIDIC = "kanjidic-%d.json";
    private static final String EDICT = "edict-%d.json";
    private static final String TANAKA = "tanaka-%d.json";

    private static final int KANJIDIC_LIMIT = 13;
    private static final int TANAKA_LIMIT = 14;
    private static final int EDICT_LIMIT = 27;

    private Realm realm;

    private int conversionProgress;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        realm = Realm.getDefaultInstance();

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Jisho offline dictionary")
                .setContentText("Downloading File")
                .setAutoCancel(true);
        notificationManager.notify(0, notificationBuilder.build());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initExtract();
        return START_STICKY;
    }

    private void initExtract() {
        consumeEdict(0);
        consumeTanaka(0);
        consumeKanjiDic(0);
    }

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

                conversionProgress++;

                updateProgress();

                if(nextCount <= KANJIDIC_LIMIT) {
                    consumeKanjiDic(nextCount);
                } else {
                    consumeKanjiStroke();
                }
            }
        }).execute(String.format(KANJIDIC, count));
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

                updateProgress();

                realm.commitTransaction();
            }
        }).execute("kanji.json");
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

                updateProgress();

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

                updateProgress();

                if(nextCount <= TANAKA_LIMIT) {
                    consumeTanaka(nextCount);
                }
            }
        }).execute(String.format(TANAKA, count));
    }

    private void updateProgress() {
        conversionProgress++;

        int percentage = (conversionProgress % (KANJIDIC_LIMIT + TANAKA_LIMIT + EDICT_LIMIT + 1));

        sendIntent(percentage);
        notificationBuilder.setProgress((KANJIDIC_LIMIT + TANAKA_LIMIT + EDICT_LIMIT + 1), conversionProgress,false);
        notificationBuilder.setContentText("Updating offline "+ percentage +" %");
        notificationManager.notify(0, notificationBuilder.build());
    }

    private void sendIntent(int percentage){
        Intent intent = new Intent(IConstants.UPDATE_BROADCAST_FILTER);
        intent.putExtra(KEY_PROGRESS, percentage);
        sendBroadcast(intent);
    }
}
