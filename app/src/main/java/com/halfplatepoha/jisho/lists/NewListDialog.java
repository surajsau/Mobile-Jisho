package com.halfplatepoha.jisho.lists;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

import com.halfplatepoha.jisho.R;
import com.halfplatepoha.jisho.base.BaseDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by surjo on 03/01/18.
 */

public class NewListDialog extends BaseDialog<NewListDialogContract.Presenter> implements NewListDialogContract.View {

    @BindView(R.id.etListName)
    EditText etListName;

    @Override
    protected int getLayoutId() {
        return R.layout.dlg_new_list;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.btnAddListName)
    public void clickAddListName() {
        presenter.clickAddListName(etListName.getText().toString());
    }

    @Override
    public void dismissDialog() {
        dismiss();
    }
}
