package com.halfplatepoha.jisho.v2.detail.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.halfplatepoha.jisho.R;
import com.halfplatepoha.jisho.base.BaseAdapter;
import com.halfplatepoha.jisho.base.BaseViewholder;
import com.halfplatepoha.jisho.view.CustomTextView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by surjo on 28/12/17.
 */

public class KanjiAdapter extends BaseAdapter<KanjiAdapterContract.Presenter, KanjiAdapter.KanjiViewHolder> {

    public KanjiAdapter(KanjiAdapterContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    protected KanjiAdapter.KanjiViewHolder createVH(ViewGroup parent, int viewType) {
        return new KanjiViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_kanji, parent, false), presenter);
    }

    public static class KanjiViewHolder extends BaseViewholder<KanjiAdapterContract.Presenter> implements KanjiAdapterContract.View {

        @BindView(R.id.tvKanji)
        CustomTextView tvKanji;

        @BindView(R.id.tvKanjiMeaning)
        CustomTextView tvKanjiMeaning;

        public KanjiViewHolder(View itemView, KanjiAdapterContract.Presenter presenter) {
            super(itemView, presenter);
        }

        @OnClick(R.id.row_kanji)
        public void clickKanji() {
            presenter.onItemClick(getAdapterPosition());
        }

        @Override
        public void setKanji(String kanji) {
            tvKanji.setText(kanji);
        }

        @Override
        public void setMeaning(String meaning) {
            tvKanjiMeaning.setText(meaning);
        }

    }
}
