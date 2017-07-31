package com.halfplatepoha.jisho.viewholders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.TextView;

import com.halfplatepoha.jisho.R;
import com.halfplatepoha.jisho.utils.VerbInflection;

import butterknife.BindView;

/**
 * Created by surjo on 12/05/17.
 */

public class InflectionViewHolder extends BaseViewHolder<VerbInflection> {

    @BindView(R.id.tvPositive)
    TextView tvPositive;

    @BindView(R.id.tvNegative)
    TextView tvNegative;

    @BindView(R.id.tvTense)
    TextView tvTense;

    public InflectionViewHolder(Context context, ViewGroup parent, VerbInflection verbInflection) {
        super(context, parent, verbInflection);
    }

    @Override
    public void bindView(VerbInflection verbInflection) {
        tvNegative.setText(verbInflection.getNegative());
        tvPositive.setText(verbInflection.getAffirmative());
        tvTense.setText(verbInflection.getTense());
    }

    @Override
    public int getLayoutRes() {
        return R.layout.row_inlfection;
    }
}
