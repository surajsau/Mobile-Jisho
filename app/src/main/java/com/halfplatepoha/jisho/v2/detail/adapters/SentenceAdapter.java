package com.halfplatepoha.jisho.v2.detail.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.halfplatepoha.jisho.R;
import com.halfplatepoha.jisho.base.BaseAdapter;
import com.halfplatepoha.jisho.base.BaseViewholder;
import com.halfplatepoha.jisho.view.CustomTextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by surjo on 28/12/17.
 */

public class SentenceAdapter extends BaseAdapter<SentenceAdapterContract.Presenter, SentenceAdapter.SentenceViewHolder> {

    @Inject
    public SentenceAdapter(SentenceAdapterContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    protected SentenceViewHolder createVH(ViewGroup parent, int viewType) {
        return new SentenceViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sentence, parent, false), presenter);
    }

    public static class SentenceViewHolder extends BaseViewholder<SentenceAdapterContract.Presenter> implements SentenceAdapterContract.View {

        @BindView(R.id.tvOriginalSentence)
        CustomTextView tvOriginalSentence;

        @BindView(R.id.tvEnglish)
        CustomTextView tvEnglish;

        public SentenceViewHolder(View itemView, SentenceAdapterContract.Presenter presenter) {
            super(itemView, presenter);
        }

        @OnClick(R.id.row_sentence)
        public void clickSentence() {
            presenter.onItemClick(tvOriginalSentence.getTag().toString());
        }

        @Override
        public void setEnglish(String english) {
            tvEnglish.setText(english);
        }

        @Override
        public void setOriginal(String sentence) {
            tvOriginalSentence.setText(sentence);
        }

        @Override
        public void setTag(String sentence) {
            tvOriginalSentence.setTag(sentence);
        }
    }
}
