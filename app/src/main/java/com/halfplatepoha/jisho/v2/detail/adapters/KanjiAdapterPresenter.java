package com.halfplatepoha.jisho.v2.detail.adapters;

import com.halfplatepoha.jisho.base.BaseAdapterPresenter;
import com.halfplatepoha.jisho.jdb.Kanji;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by surjo on 28/12/17.
 */

public class KanjiAdapterPresenter extends BaseAdapterPresenter<KanjiAdapterContract.View> implements KanjiAdapterContract.Presenter {

    private List<KanjiModel> kanjis;

    private Listener listener;

    @Inject
    public KanjiAdapterPresenter() {
        super();
    }

    @Override
    public void onBind(KanjiAdapterContract.View viewHolder, int position) {
        KanjiModel kanji = kanjis.get(position);
        viewHolder.setKanji(kanji.literal);

        if(kanji.meanings != null && !kanji.meanings.isEmpty()) {
            StringBuilder meanings = new StringBuilder(kanji.meanings.get(0).meaning);
            for(int i=1; i<kanji.meanings.size(); i++) {
                if(kanji.meanings.get(i).lang == null)
                    meanings.append(", ").append(kanji.meanings.get(i).meaning);
            }

            viewHolder.setMeaning(meanings.toString());
        }
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
            listener.onItemClick(kanjis.get(position).literal);
    }

    @Override
    public void setKanjis(List<KanjiModel> kanjis) {
        this.kanjis = kanjis;
        adapterInterface.itemRangeInserted(0, this.kanjis.size());
    }

    public interface Listener {

        void onItemClick(String kanjiLiteral);
    }
}
