package com.halfplatepoha.jisho.viewholders;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.halfplatepoha.jisho.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by surjo on 21/04/17.
 */

public class KanjiViewHolder extends BaseViewHolder<String> {

    @BindView(R.id.tvKanji)
    TextView tvKanji;

    private OnKanjiClickedListener onKanjiClickedListener;

    public KanjiViewHolder(Context context, ViewGroup parent, String kanji, OnKanjiClickedListener onKanjiClickedListener) {
        super(context, parent, kanji);
        this.onKanjiClickedListener = onKanjiClickedListener;
    }

    @Override
    public void bindView(String s) {
        tvKanji.setText(s);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.row_kanji;
    }

    @OnClick(R.id.tvKanji)
    public void openKanjiDetails() {
        if(onKanjiClickedListener != null)
            onKanjiClickedListener.onKanjiClicked(tvKanji.getText().toString());
    }

    public interface OnKanjiClickedListener {
        void onKanjiClicked(String kanji);
    }

}
