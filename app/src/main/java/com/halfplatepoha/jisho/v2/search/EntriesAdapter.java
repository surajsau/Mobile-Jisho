package com.halfplatepoha.jisho.v2.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.halfplatepoha.jisho.R;
import com.halfplatepoha.jisho.analytics.Analytics;
import com.halfplatepoha.jisho.base.BaseAdapter;
import com.halfplatepoha.jisho.base.BaseViewholder;
import com.halfplatepoha.jisho.view.CustomTextView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by surjo on 21/12/17.
 */

public class EntriesAdapter extends BaseAdapter<EntriesAdapterContract.Presenter, BaseViewholder<EntriesAdapterContract.Presenter>> {

    public EntriesAdapter(EntriesAdapterContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    protected BaseViewholder<EntriesAdapterContract.Presenter> createVH(ViewGroup parent, int viewType) {
        switch (viewType) {
            case EntriesAdapterPresenter.TYPE_HORIZONTAL:
                return new SearchResultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_word_horizontal, parent, false), presenter);

            case EntriesAdapterPresenter.TYPE_VERTICAL:
                return new SearchResultVerticalViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_word_vertical, parent, false), presenter);
        }

        return null;

    }

    public static class SearchResultViewHolder extends BaseViewholder<EntriesAdapterContract.Presenter> implements EntriesAdapterContract.HorizontalView {

        @BindView(R.id.tvJapanese)
        CustomTextView tvJapanese;

        @BindView(R.id.ivCommon)
        ImageView ivCommon;

        @BindView(R.id.tvFurigana)
        CustomTextView tvFurigana;

        @BindView(R.id.tvSense)
        CustomTextView tvSense;

        @BindView(R.id.rowCard)
        View rowCard;

//        @BindView(R.id.tvOtherForms)
//        CustomTextView tvOtherForms;

        public SearchResultViewHolder(View itemView, EntriesAdapterContract.Presenter presenter) {
            super(itemView, presenter);
        }

        @OnClick(R.id.rowCard)
        void seeMore() {
            Analytics.getInstance().recordClick("Search_Details", "Fav_Detail");
            presenter.onItemClick(rowCard.getTag().toString());
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

        @Override
        public void setTag(String tag) {
            rowCard.setTag(tag);
        }
    }

    public static class SearchResultVerticalViewHolder extends BaseViewholder<EntriesAdapterContract.Presenter> implements EntriesAdapterContract.VerticalView {

        @BindView(R.id.tvJapanese)
        CustomTextView tvJapanese;

        @BindView(R.id.tvSense)
        CustomTextView tvSense;

        @BindView(R.id.rowCard)
        View rowCard;

        @BindView(R.id.ivCommon)
        View ivCommon;

        public SearchResultVerticalViewHolder(View itemView, EntriesAdapterContract.Presenter presenter) {
            super(itemView, presenter);
        }

        @OnClick(R.id.rowCard)
        void seeMore() {
            Analytics.getInstance().recordClick("Search_Details", "Fav_Detail");
            presenter.onItemClick(rowCard.getTag().toString());
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

        @Override
        public void setTag(String tag) {
            rowCard.setTag(tag);
        }

    }

}
