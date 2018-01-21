package com.halfplatepoha.jisho.v2.injection.modules.views;

import android.app.Activity;
import android.support.annotation.Nullable;

import com.halfplatepoha.jisho.apimodel.Word;
import com.halfplatepoha.jisho.base.BaseActivityModule;
import com.halfplatepoha.jisho.v2.injection.ActivityScope;
import com.halfplatepoha.jisho.v2.detail.DetailsActivity;
import com.halfplatepoha.jisho.v2.detail.DetailsContract;
import com.halfplatepoha.jisho.v2.detail.DetailsPresenter;
import com.halfplatepoha.jisho.v2.detail.adapters.KanjiAdapter;
import com.halfplatepoha.jisho.v2.detail.adapters.KanjiAdapterContract;
import com.halfplatepoha.jisho.v2.detail.adapters.KanjiAdapterPresenter;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by surjo on 28/12/17.
 */

@Module(includes = BaseActivityModule.class)
public abstract class DetailsModule {

    @Binds
    @ActivityScope
    abstract Activity activity(DetailsActivity activity);

    @Binds
    @ActivityScope
    abstract DetailsContract.View view(DetailsActivity activity);

    @Binds
    @ActivityScope
    abstract DetailsContract.Presenter presenter(DetailsPresenter presenter);

    @Binds
    @ActivityScope
    abstract KanjiAdapterContract.Presenter kanjiAdapterContract(KanjiAdapterPresenter kanjiAdapterPresenter);

    @Provides
    @ActivityScope
    static KanjiAdapter kanjiAdapter(KanjiAdapterContract.Presenter presenter) {
        return new KanjiAdapter(presenter);
    }

    @Named(DetailsActivity.KEY_JAPANESE)
    @Provides
    @ActivityScope
    static String japanese(DetailsActivity activity) {
        return activity.getIntent().getStringExtra(DetailsActivity.KEY_JAPANESE);
    }

    @Nullable
    @Named(DetailsActivity.KEY_FURIGANA)
    @Provides
    @ActivityScope
    static String furigana(DetailsActivity activity) {
        return activity.getIntent().getStringExtra(DetailsActivity.KEY_FURIGANA);
    }

}
