package com.halfplatepoha.jisho.kanji;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.halfplatepoha.jisho.R;
import com.halfplatepoha.jisho.base.BaseActivity;

public class KanjiDetailActivity extends BaseActivity<KanjiDetailContract.Presenter> implements KanjiDetailContract.View {

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_kanji_detail;
    }


}
