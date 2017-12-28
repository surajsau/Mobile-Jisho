package com.halfplatepoha.jisho.v2.detail.adapters;

import com.halfplatepoha.jisho.base.BaseAdapterPresenter;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by surjo on 28/12/17.
 */

public class KanjiAdapterPresenter extends BaseAdapterPresenter<KanjiAdapterContract.View> implements KanjiAdapterContract.Presenter {

    private List<String> kanjis;

    private Listener listener;

    @Inject
    public KanjiAdapterPresenter() {
        super();
    }

    @Override
    public void onBind(KanjiAdapterContract.View viewHolder, int position) {
        viewHolder.setKanji(kanjis.get(position));
    }

    @Override
    public int getItemCount() {
        return kanjis != null ? kanjis.size() : 0;
    }

    @Override
    public void attachListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void removeListener() {
        this.listener = null;
    }

    @Override
    public void onItemClick(int position) {
        if(listener != null)
            listener.onItemClick(kanjis.get(position));
    }

    @Override
    public void setKanjis(List<String> kanjis) {
        this.kanjis = kanjis;
        adapterInterface.itemRangeInserted(0, this.kanjis.size());
    }

    public interface Listener {

        void onItemClick(String kanjiLiteral);
    }
}
