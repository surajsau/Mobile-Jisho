package com.halfplatepoha.jisho.v2.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.halfplatepoha.jisho.R;
import com.halfplatepoha.jisho.analytics.Analytics;
import com.halfplatepoha.jisho.base.BaseAdapter;
import com.halfplatepoha.jisho.base.BaseViewholder;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by surjo on 21/12/17.
 */

public class SearchOfflineAdapter extends BaseAdapter<SearchAdapterContract.Presenter, SearchOfflineAdapter.SearchResultViewHolder> {

    public SearchOfflineAdapter(SearchAdapterContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    protected SearchResultViewHolder createVH(ViewGroup parent, int viewType) {
        return new SearchResultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_word, parent, false), presenter);
    }

    public static class SearchResultViewHolder extends BaseViewholder<SearchAdapterContract.Presenter> implements SearchAdapterContract.View {

        @BindView(R.id.tvJapanese)
        TextView tvJapanese;

        @BindView(R.id.ivCommon)
        ImageView ivCommon;

        @BindView(R.id.tvHiragana)
        TextView tvHiragana;

        @BindView(R.id.sensesContainer)
        LinearLayout sensesContainer;

        @BindView(R.id.tvOtherForms)
        TextView tvOtherForms;

        public SearchResultViewHolder(View itemView, SearchAdapterContract.Presenter presenter) {
            super(itemView, presenter);
        }

        @OnClick(R.id.rowCard)
        void seeMore() {
            Analytics.getInstance().recordClick("Search_Details", "Fav_Detail");
            presenter.onItemClick(getAdapterPosition());
        }

        @Override
        public void setJapanese(String japanese) {
            tvJapanese.setText(japanese);
        }

        @Override
        public void showFurigana() {
            tvHiragana.setVisibility(View.VISIBLE);
        }

        @Override
        public void setFurigana(String furigana) {
            tvHiragana.setText(furigana);
        }

        @Override
        public void hideFurigana() {
            tvHiragana.setVisibility(View.GONE);
        }

        @Override
        public void showCommon() {
            ivCommon.setVisibility(View.VISIBLE);
        }

        @Override
        public void hideCommon() {
            ivCommon.setVisibility(View.GONE);
        }
    }

}
