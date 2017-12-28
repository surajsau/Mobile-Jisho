package com.halfplatepoha.jisho.v2.detail.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.halfplatepoha.jisho.R;
import com.halfplatepoha.jisho.base.BaseAdapter;
import com.halfplatepoha.jisho.base.BaseViewholder;

import javax.inject.Inject;

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
        return new SentenceViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sense, parent, false), presenter);
    }

    public static class SentenceViewHolder extends BaseViewholder<SentenceAdapterContract.Presenter> {

        public SentenceViewHolder(View itemView, SentenceAdapterContract.Presenter presenter) {
            super(itemView, presenter);
        }

    }
}
