package com.halfplatepoha.jisho.v2.search;

import com.halfplatepoha.jisho.R;
import com.halfplatepoha.jisho.base.BaseDialog;

import butterknife.OnClick;

/**
 * Created by surjo on 20/01/18.
 */

public class SwitchToOfflineDialog extends BaseDialog<SwitchToOfflineContract.Presenter> implements SwitchToOfflineContract.View {

    public static final String TAG = SwitchToOfflineDialog.class.getSimpleName();

    private Listener listener;

    @Override
    protected int getLayoutId() {
        return R.layout.dlg_switch_to_offline;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @OnClick(R.id.btnConfirm)
    public void clickConfirm() {
        presenter.clickConfirm();
    }

    @Override
    public void confirm() {
        if(listener != null)
            listener.onConfirm();
        dismiss();
    }

    @Override
    public void onStop() {
        listener = null;
        super.onStop();
    }

    @Override
    public void cancel() {
        dismiss();
    }

    public interface Listener {
        void onConfirm();
    }
}
