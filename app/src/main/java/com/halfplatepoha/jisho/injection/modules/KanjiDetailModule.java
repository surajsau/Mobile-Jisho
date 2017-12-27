package com.halfplatepoha.jisho.injection.modules;

import com.halfplatepoha.jisho.injection.DialogScope;
import com.halfplatepoha.jisho.kanjidetail.KanjiDetailContract;
import com.halfplatepoha.jisho.kanjidetail.KanjiDetailFragment;
import com.halfplatepoha.jisho.kanjidetail.KanjiDetailPresenter;

import dagger.Binds;
import dagger.Module;

/**
 * Created by surjo on 21/12/17.
 */

@Module
public abstract class KanjiDetailModule {

    @Binds
    @DialogScope
    abstract KanjiDetailContract.View view(KanjiDetailFragment fragment);

    @Binds
    @DialogScope
    abstract KanjiDetailContract.Presenter presenter(KanjiDetailPresenter presenter);

}
