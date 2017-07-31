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

    private Model mModel;

    private boolean isBound;

    public BaseViewHolder(Context context, ViewGroup parent, Model model) {
        mContext = context;
        mModel = model;
        vhView = LayoutInflater.from(mContext).inflate(getLayoutRes(), parent, false);
        ButterKnife.bind(this, vhView);
    }

    public BaseViewHolder<Model> bind() {
        isBound = true;
        bindView(mModel);
        return this;
    }

    protected abstract void bindView(Model model);

    public abstract @LayoutRes int getLayoutRes();

    public View getView() {
        if(!isBound)
            throw new Error("first bind() to getView(). You cannot call vh.getView() directly without calling vh.bind() first");
        return vhView;
    }

    public void notifyDataSetChange(Model model) {
        mModel = model;
        bind();
    }
}
