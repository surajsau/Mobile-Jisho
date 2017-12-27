package com.halfplatepoha.jisho.base;

/**
 * Created by surjo on 21/12/17.
 */

public interface IAdapterPresenter<VH extends BaseViewholderView> {
    void onBind(VH viewHolder, int position);

    int getItemCount();

    int getItemViewType();

    void attachAdapterInterface(BaseAdapterPresenter.AdapterInterface adapterInterface);
}
