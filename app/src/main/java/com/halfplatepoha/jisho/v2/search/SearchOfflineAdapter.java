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

public class SearchOfflineAdapter extends BaseAdapter<SearchAdapterContract.Presenter, BaseViewholder<SearchAdapterContract.Presenter>> {

    public SearchOfflineAdapter(SearchAdapterContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    protected BaseViewholder<SearchAdapterContract.Presenter> createVH(ViewGroup parent, int viewType) {
        switch (viewType) {
            case SearchAdapterPresenter.TYPE_HORIZONTAL:
                return new SearchResultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_word_horizontal, parent, false), presenter);

            case SearchAdapterPresenter.TYPE_VERTICAL:
                return new SearchResultVerticalViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_word_vertical, parent, false), presenter);
        }

        return null;

    }

    public static class SearchResultViewHolder extends BaseViewholder<SearchAdapterContract.Presenter> implements SearchAdapterContract.HorizontalView {

        @BindView(R.id.tvJapanese)
        TextView tvJapanese;

        @BindView(R.id.ivCommon)
        ImageView ivCommon;

        @BindView(R.id.tvFurigana)
        TextView tvFurigana;

        @BindView(R.id.tvSense)
        TextView tvSense;

//        @BindView(R.id.tvOtherForms)
//        TextView tvOtherForms;

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
            tvFurigana.setVisibility(View.VISIBLE);
        }

        @Override
        public void setFurigana(String furigana) {
            tvFurigana.setText(furigana);
        }

        @Override
        public void hideFurigana() {
            tvFurigana.setVisibility(View.GONE);
        }

        @Override
        public void showCommon() {
            ivCommon.setVisibility(View.VISIBLE);
        }

        @Override
        public void hideCommon() {
            ivCommon.setVisibility(View.GONE);
        }

        @Override
        public void setSense(String sense) {
            tvSense.setText(sense);
        }
    }

    public static class SearchResultVerticalViewHolder extends BaseViewholder<SearchAdapterContract.Presenter> implements SearchAdapterContract.VerticalView {

        @BindView(R.id.tvJapanese)
        TextView tvJapanese;

        @BindView(R.id.tvSense)
        TextView tvSense;

        @BindView(R.id.ivCommon)
        View ivCommon;

        public SearchResultVerticalViewHolder(View itemView, SearchAdapterContract.Presenter presenter) {
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
        public void showCommon() {
            ivCommon.setVisibility(View.VISIBLE);
        }

        @Override
        public void hideCommon() {
            ivCommon.setVisibility(View.GONE);
        }

        @Override
        public void setSense(String sense) {
            tvSense.setText(sense);
        }

    }

}
