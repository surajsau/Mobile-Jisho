package com.halfplatepoha.jisho.viewholders;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by surjo on 21/04/17.
 */

public abstract class BaseViewHolder<Model> {

    protected Context mContext;

    private View vhView;

    public BaseViewHolder(Context context, ViewGroup parent, Model model) {
        mContext = context;
        vhView = LayoutInflater.from(mContext).inflate(getLayoutRes(), parent, false);
        ButterKnife.bind(this, vhView);

        bind(model);
    }

    public abstract void bind(Model model);

    public abstract @NonNull @LayoutRes int getLayoutRes();

    public View getView() {
        return vhView;
    }
}
