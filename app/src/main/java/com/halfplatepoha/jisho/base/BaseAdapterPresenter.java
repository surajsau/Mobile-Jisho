package com.halfplatepoha.jisho.base;

/**
 * Created by surjo on 21/12/17.
 */

public abstract class BaseAdapterPresenter<VH extends BaseViewholderView> implements IAdapterPresenter<VH> {

    protected AdapterInterface adapterInterface;

    private static final int DEFAULT = 0;

    @Override
    public void attachAdapterInterface(AdapterInterface adapterInterface) {
        this.adapterInterface = adapterInterface;
    }

    @Override
    public int getItemViewType() {
        return DEFAULT;
    }
    
    public interface AdapterInterface {
        void dataSetChanged();
        void itemChanged(int position);
        void itemInserted(int position);
        void itemMoved(int from, int to);
        void itemRangeChanged(int start, int count);
        void itemRangeInserted(int start, int count);
        void itemRangeRemoved(int start, int count);
        void itemRemoved(int position);
    }
}
