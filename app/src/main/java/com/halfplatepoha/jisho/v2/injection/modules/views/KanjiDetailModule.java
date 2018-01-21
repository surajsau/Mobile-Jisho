package com.halfplatepoha.jisho.v2.injection.modules.views;

import android.app.Activity;

import com.halfplatepoha.jisho.base.BaseActivityModule;
import com.halfplatepoha.jisho.v2.injection.ActivityScope;
import com.halfplatepoha.jisho.v2.kanji.KanjiDetailActivity;
import com.halfplatepoha.jisho.v2.kanji.KanjiDetailContract;
import com.halfplatepoha.jisho.v2.kanji.KanjiDetailPresenter;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by surjo on 21/12/17.
 */

@Module(includes = BaseActivityModule.class)
public abstract class KanjiDetailModule {

    @Binds
    @ActivityScope
    abstract Activity activity(KanjiDetailActivity activity);

    @Binds
    @ActivityScope
    abstract KanjiDetailContract.View view(KanjiDetailActivity fragment);

    @Binds
    @ActivityScope
    abstract KanjiDetailContract.Presenter presenter(KanjiDetailPresenter presenter);

    @Named(KanjiDetailActivity.KEY_KANJI)
    @Provides
    @ActivityScope
    static String kanji(KanjiDetailActivity activity) {
        return activity.getIntent().getStringExtra(KanjiDetailActivity.KEY_KANJI);
    }

}
