package com.halfplatepoha.jisho.v2.sentences;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.halfplatepoha.jisho.R;
import com.halfplatepoha.jisho.base.BaseActivity;
import com.halfplatepoha.jisho.v2.detail.adapters.SentenceAdapter;
import com.halfplatepoha.jisho.view.CustomTextView;

import javax.inject.Inject;

import butterknife.BindView;

public class SentencesActivity extends BaseActivity<SentencesContract.Presenter> implements SentencesContract.View {

    public static final String KEY_JAPANESE = "key_japanese";
    public static final String KEY_FURIGANA = "key_furigana";

    @BindView(R.id.rlSentences)
    RecyclerView rlSentences;

    @BindView(R.id.tvEntry)
    CustomTextView tvEntry;

    @Inject
    SentenceAdapter sentenceAdapter;

    public static Intent getIntent(Context context, String japenese, String furigana) {
        Intent intent = new Intent(context, SentencesActivity.class);
        intent.putExtra(KEY_FURIGANA, furigana);
        intent.putExtra(KEY_JAPANESE, japenese);
        return intent;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_sentences;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rlSentences.setLayoutManager(new LinearLayoutManager(this));
        rlSentences.setAdapter(sentenceAdapter);
    }

    @Override
    public void openSentenceDetails(String sentence) {
        shortToast(sentence);
    }

    @Override
    public void setEntry(String entry) {
        tvEntry.setText(entry);
    }
}
