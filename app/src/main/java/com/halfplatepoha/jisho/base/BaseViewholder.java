package com.halfplatepoha.jisho.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by surjo on 21/12/17.
 */

public abstract class BaseViewholder<P extends IAdapterPresenter> extends RecyclerView.ViewHolder implements BaseViewholderView {

    protected P presenter;

    public BaseViewholder(View itemView, P presenter) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.presenter = presenter;
    }
}
