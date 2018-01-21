package com.halfplatepoha.jisho.viewholders;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.halfplatepoha.jisho.R;
import com.halfplatepoha.jisho.apimodel.Sense;
import com.halfplatepoha.jisho.utils.Utils;
import com.halfplatepoha.jisho.view.CustomTextView;

import butterknife.BindView;

/**
 * Created by surjo on 21/04/17.
 */

public class SearchResultSenseViewHolder extends BaseViewHolder<Sense> {

    @BindView(R.id.tvSensePoS)
    CustomTextView tvSensePoS;

    @BindView(R.id.tvSense)
    CustomTextView tvSense;

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

    @Override
    public int getLayoutRes() {
        return R.layout.row_sense;
    }
}
