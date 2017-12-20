package com.halfplatepoha.jisho.v2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.halfplatepoha.jisho.R;
import com.halfplatepoha.jisho.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by surjo on 19/12/17.
 */

public class DetailsActivity2 extends BaseActivity {

    @BindView(R.id.tvJapanese)
    TextView tvJapanese;

    @BindView(R.id.tvJapaneseReading)
    TextView tvJapaneseReading;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_details_2;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
