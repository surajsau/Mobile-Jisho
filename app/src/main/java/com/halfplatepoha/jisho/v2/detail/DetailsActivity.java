package com.halfplatepoha.jisho.v2.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.halfplatepoha.jisho.R;
import com.halfplatepoha.jisho.base.BaseActivity;
import com.halfplatepoha.jisho.jdb.Sentence;
import com.halfplatepoha.jisho.kanjidetail.KanjiDetailFragment;
import com.halfplatepoha.jisho.v2.detail.adapters.KanjiAdapter;
import com.halfplatepoha.jisho.v2.detail.adapters.SentenceAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by surjo on 19/12/17.
 */

public class DetailsActivity extends BaseActivity<DetailsContract.Presenter> implements DetailsContract.View {

    public static final String KEY_JAPANESE = "key_japanese";

    private static final String KANJI_DETAIL_TAG = "kanji_detail";

    @BindView(R.id.tvJapanese)
    TextView tvJapanese;

    @BindView(R.id.tvJapaneseReading)
    TextView tvJapaneseReading;

    @BindView(R.id.tvMeaning)
    TextView tvMeaning;

    @BindView(R.id.rlKanji)
    RecyclerView rlKanji;

    @BindView(R.id.rlSentences)
    RecyclerView rlSentences;

    @BindView(R.id.tvExamplesCount)
    TextView tvExamplesCount;

    @BindView(R.id.tvPos)
    TextView tvPos;

    @Inject
    KanjiAdapter kanjiAdapter;

    @Inject
    SentenceAdapter sentenceAdapter;

    public static Intent getLaunchIntent(Context context, String japanese) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(KEY_JAPANESE, japanese);
        return intent;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_details_2;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rlKanji.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rlKanji.setAdapter(kanjiAdapter);

        rlSentences.setLayoutManager(new LinearLayoutManager(this));
        rlSentences.setAdapter(sentenceAdapter);
    }

    @Override
    public void setJapanese(String japanese) {
        tvJapanese.setText(japanese);
    }

    @Override
    public void showFurigana() {
        tvJapaneseReading.setVisibility(View.VISIBLE);
    }

    @Override
    public void setFurigana(String furigana) {
        tvJapaneseReading.setText(furigana);
    }

    @Override
    public void openKanjiDialog(String kanjiLiteral) {
        KanjiDetailFragment kanjiDetailFragment = KanjiDetailFragment.getInstance(kanjiLiteral);
        kanjiDetailFragment.show(supportFragmentManager, KANJI_DETAIL_TAG);
    }

    @Override
    public void openSentenceDetail(Sentence sentence) {

    }

    @OnClick(R.id.btnKanjiPlay)
    public void clickKanjiPlay() {
        presenter.clickKanjiPlay();
    }

}