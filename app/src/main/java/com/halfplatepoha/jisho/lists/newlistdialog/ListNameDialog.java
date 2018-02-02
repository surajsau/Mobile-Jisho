package com.halfplatepoha.jisho.lists.newlistdialog;

import android.os.Bundle;

import com.halfplatepoha.jisho.R;
import com.halfplatepoha.jisho.base.BaseDialog;
import com.halfplatepoha.jisho.view.CustomEditText;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by surjo on 03/01/18.
 */

public class ListNameDialog extends BaseDialog<NewListDialogContract.Presenter> implements NewListDialogContract.View, NewListDialogContract.Bus {

    public static final String TAG = ListNameDialog.class.getSimpleName();

    private static final String KEY_LIST_NAME = "list_name";

    @BindView(R.id.tvListName)
    CustomEditText etListName;

    public static ListNameDialog getInstance(String listName) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_LIST_NAME, listName);
        ListNameDialog dlg = new ListNameDialog();
        dlg.setArguments(bundle);

        return dlg;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dlg_new_list;
    }

    @OnClick(R.id.btnAddListName)
    public void clickAddListName() {
        presenter.clickAddListName(etListName.getText().toString());
    }

    @Override
    public void dismissDialog() {
        dismiss();
    }

    @Override
    public void showDuplicateListError() {
        etListName.setError("List already exists");
    }

    @Override
    public void pushNewListName(NewListName nlm) {
        EventBus.getDefault().post(nlm);
    }

}
