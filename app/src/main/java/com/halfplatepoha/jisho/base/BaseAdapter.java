package com.halfplatepoha.jisho.base;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import javax.inject.Inject;

/**
 * Created by surjo on 21/12/17.
 */

public abstract class BaseAdapter<P extends IAdapterPresenter, VH extends BaseViewholder<P>> extends RecyclerView.Adapter<VH>
        implements BaseAdapterPresenter.AdapterInterface {

    @Inject
    protected P presenter;

    public BaseAdapter(P presenter) {
        this.presenter = presenter;
        this.presenter.attachAdapterInterface(this);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return createVH(parent, viewType);
    }

    @Override
    public int getItemCount() {
        return presenter.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        return presenter.getItemViewType();
    }

    protected abstract VH createVH(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(VH holder, int position) {
        presenter.onBind(holder, position);
    }

    @Override
    public void dataSetChanged() {
        notifyDataSetChanged();
    }

    @Override
    public void itemChanged(int position) {
        notifyItemChanged(position);
    }

    @Override
    public void itemInserted(int position) {
        notifyItemInserted(position);
    }

    @Override
    public void itemMoved(int from, int to) {
        notifyItemMoved(from, to);
    }

    @Override
    public void itemRangeChanged(int start, int count) {
        notifyItemRangeChanged(start, count);
    }

    @Override
    public void itemRangeInserted(int start, int count) {
        notifyItemRangeInserted(start, count);
    }

    @Override
    public void itemRangeRemoved(int start, int count) {
        notifyItemRangeRemoved(start, count);
    }

    @Override
    public void itemRemoved(int position) {
        notifyItemRemoved(position);
    }
}
