package com.halfplatepoha.jisho.lists.listsfragment;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.airbnb.lottie.LottieAnimationView;
import com.halfplatepoha.jisho.R;
import com.halfplatepoha.jisho.base.BaseAdapter;
import com.halfplatepoha.jisho.base.BaseViewholder;
import com.halfplatepoha.jisho.view.CustomEditText;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnLongClick;

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

        @BindView(R.id.etListName)
        CustomEditText etListName;

        @BindView(R.id.row_list)
        View row;

        @BindView(R.id.chkList)
        AppCompatCheckBox chkList;

        @BindView(R.id.btnDone)
        Button btnDone;

        public ListViewHolder(View itemView, ListAdapterContract.Presenter presenter) {
            super(itemView, presenter);
        }

        @Override
        public void setListName(String name) {
            etListName.setText(name);
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
            row.setBackgroundColor(bg);
        }

        @Override
        public void hideCheckListView() {
            chkList.setVisibility(View.GONE);
        }

        @Override
        public void showChecklistView() {
            chkList.setVisibility(View.VISIBLE);
        }

        @Override
        public void unselectList() {
            chkList.setChecked(false);
        }

        @Override
        public void selectList() {
            chkList.setChecked(true);
        }

        @Override
        public void setTextEditable() {
            etListName.setClickable(true);
            etListName.setFocusable(true);
        }

        @Override
        public void setTextUnEditable() {
            etListName.setClickable(false);
            etListName.setFocusable(false);
        }

        @Override
        public void showDoneButton() {
            btnDone.setVisibility(View.VISIBLE);
        }

        @Override
        public void hideDoneButton() {
            btnDone.setVisibility(View.GONE);
        }

        @OnClick(R.id.row_list)
        public void clickList() {
            presenter.onItemClick(getAdapterPosition());
        }

        @OnLongClick(R.id.row_list)
        public boolean longClickList() {
            presenter.onItemLongClick(getAdapterPosition());
            return true;
        }

        @OnClick(R.id.chkList)
        public void clickCheckList() {
            presenter.onListChecked(getAdapterPosition(), chkList.isChecked());
        }

        @OnClick(R.id.btnDone)
        public void clickDone() {
            presenter.onListNameChanged(etListName.text(), getAdapterPosition());
        }

        @OnFocusChange(R.id.etListName)
        public void onEtNameFocus(boolean focused) {
            if(focused) {
                etListName.setSelection(etListName.getText() != null ? etListName.getText().length() : 0);
            }
        }
    }
}
