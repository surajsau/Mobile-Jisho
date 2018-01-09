package com.halfplatepoha.jisho.kanji;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.badoualy.kanjistroke.KanjiStrokeView;
import com.halfplatepoha.jisho.R;
import com.halfplatepoha.jisho.base.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class KanjiDetailActivity extends BaseActivity<KanjiDetailContract.Presenter> implements KanjiDetailContract.View {

    public static final String KEY_KANJI = "kanji";

    @BindView(R.id.tvComponents)
    TextView tvComponents;

    @BindView(R.id.ivKanjiPlay)
    KanjiStrokeView ivKanjiPlay;

    @BindView(R.id.japanese)
    View japanese;

    @BindView(R.id.korean)
    View korean;

    @BindView(R.id.chinese)
    View chinese;

    @BindView(R.id.tvJapaneseReading)
    TextView tvJapaneseReading;

    @BindView(R.id.tvKoreanReading)
    TextView tvKoreanReading;

    @BindView(R.id.tvChineseReading)
    TextView tvChineseReading;

    public static Intent getIntent(Context context, String kanji) {
        Intent intent = new Intent(context, KanjiDetailActivity.class);
        intent.putExtra(KEY_KANJI, kanji);
        return intent;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_kanji_detail;
    }


    @Override
    public void setKanjiStrokes(List<String> strokes) {
        ivKanjiPlay.loadPathData(strokes);
    }

    @Override
    public void animateStroke() {
        ivKanjiPlay.startDrawAnimation(500);
    }

    @Override
    public void setKanjiElements(String kanjiElements) {
        tvComponents.setText(kanjiElements);
    }

    @Override
    public void setPinyinReading(String reading) {
        tvChineseReading.setText(reading);
    }

    @Override
    public void showPinyin() {
        chinese.setVisibility(View.VISIBLE);
    }

    @Override
    public void showKorean() {
        korean.setVisibility(View.VISIBLE);
    }

    @Override
    public void showJapanese() {
        japanese.setVisibility(View.VISIBLE);
    }

    @Override
    public void setKoreanReading(String reading) {
        tvKoreanReading.setText(reading);
    }

    @Override
    public void setJapaneseReading(String reading) {
        tvChineseReading.setText(reading);
    }

    @OnClick(R.id.ivKanjiPlay)
    public void clickKanjiPlay() {
        presenter.clickKanjiPlay();
    }
}
