package com.halfplatepoha.jisho.lists;

import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.halfplatepoha.jisho.R;
import com.halfplatepoha.jisho.base.BaseAdapter;
import com.halfplatepoha.jisho.base.BaseViewholder;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by surjo on 03/01/18.
 */

public class ListsAdapter extends BaseAdapter<ListAdapterContract.Presenter,ListsAdapter.ListViewHolder> {

    public ListsAdapter(ListAdapterContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    protected ListViewHolder createVH(ViewGroup parent, int viewType) {
        return new ListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list, parent, false), presenter);
    }

    public static class ListViewHolder extends BaseViewholder<ListAdapterContract.Presenter> implements ListAdapterContract.View {

        @BindView(R.id.tvListName)
        TextView tvListName;

        @BindView(R.id.row_list)
        View row;

        public ListViewHolder(View itemView, ListAdapterContract.Presenter presenter) {
            super(itemView, presenter);
        }

        @Override
        public void setListName(String name) {
            tvListName.setText(name);
        }

        @Override
        public void removeBackground() {
            row.setBackgroundResource(0);
        }

        @Override
        public int bgSelected() {
            return ContextCompat.getColor(row.getContext(), R.color.colorPrimaryLight);
        }

        @Override
        public void setSelectedBackground(int bg) {
            row.setBackgroundResource(bg);
        }

        @OnClick(R.id.row_list)
        public void clickList() {
            presenter.onItemClick(getAdapterPosition());
        }
    }
}
