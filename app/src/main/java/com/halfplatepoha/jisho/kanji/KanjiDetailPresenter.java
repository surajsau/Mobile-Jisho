package com.halfplatepoha.jisho.kanji;

import com.halfplatepoha.jisho.base.BasePresenter;
import com.halfplatepoha.jisho.jdb.Kanji;
import com.halfplatepoha.jisho.jdb.Reading;
import com.halfplatepoha.jisho.jdb.Schema;

import javax.inject.Inject;
import javax.inject.Named;

import io.realm.Realm;

/**
 * Created by surjo on 09/01/18.
 */

public class KanjiDetailPresenter extends BasePresenter<KanjiDetailContract.View> implements KanjiDetailContract.Presenter {

    private Realm realm;

    private String kanji;

    private StringBuilder kanjiElements;

    @Inject
    public KanjiDetailPresenter(KanjiDetailContract.View view, @Named(KanjiDetailActivity.KEY_KANJI) String kanji) {
        super(view);
        this.kanji = kanji;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        realm = Realm.getDefaultInstance();

        Kanji kanjiResult = realm.where(Kanji.class).equalTo(Schema.Kanji.LITERAL, kanji).findFirst();

        if(kanjiResult != null) {
            view.setKanjiStrokes(kanjiResult.strokes);

            if(kanjiResult.readings != null) {

                StringBuilder pinyin = new StringBuilder("");
                StringBuilder korean = new StringBuilder("");
                StringBuilder japanese = new StringBuilder("");

                for(Reading reading : kanjiResult.readings) {
                    if(Reading.PINYIN.equals(reading.type)) {
                        view.showPinyin();
                    } else if(Reading.HANGUL.equals(reading.type) || Reading.KOREAN_ROMAJI.equals(reading.type)) {
                        view.showKorean();
                    } else if(Reading.KUNYOMI.equals(reading.type) || Reading.ONYOMI.equals(reading.type)) {
                        view.showJapanese();
                    }
                }

                view.setJapaneseReading(japanese.toString());
                view.setKoreanReading(korean.toString());
                view.setPinyinReading(pinyin.toString());

            }

            if(kanjiResult.elements != null) {
                kanjiElements = new StringBuilder(kanjiResult.elements.get(0).element);

                for(int i=1; i<kanjiResult.elements.size(); i++)
                    kanjiElements.append(", ").append(kanjiResult.elements.get(i).element + ";" + kanjiResult.elements.get(i).depth);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        view.animateStroke();
        view.setKanjiElements(kanjiElements.toString());

    }

    @Override
    public void clickKanjiPlay() {
        view.animateStroke();
    }
}
