package com.halfplatepoha.jisho.viewholders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.halfplatepoha.jisho.R;
import com.halfplatepoha.jisho.model.Sense;
import com.halfplatepoha.jisho.utils.Utils;

import butterknife.BindView;

/**
 * Created by surjo on 21/04/17.
 */

public class SearchResultSenseViewHolder extends BaseViewHolder<Sense> {

    @BindView(R.id.tvSensePoS)
    TextView tvSensePoS;

    @BindView(R.id.tvSense)
    TextView tvSense;

    public SearchResultSenseViewHolder(Context context, ViewGroup parent, Sense sense) {
        super(context, parent, sense);
    }

    @Override
    public void bindView(Sense sense) {
        if(sense.getParts_of_speech() != null && !sense.getParts_of_speech().isEmpty()) {
            tvSensePoS.setVisibility(View.VISIBLE);
            tvSensePoS.setText(Utils.getCommaSeparatedString(sense.getParts_of_speech()));
        }

        tvSense.setText(Utils.getCommaSeparatedString(sense.getEnglish_definitions()));
    }

    @NonNull
    @Override
    public int getLayoutRes() {
        return R.layout.row_sense;
    }
}
