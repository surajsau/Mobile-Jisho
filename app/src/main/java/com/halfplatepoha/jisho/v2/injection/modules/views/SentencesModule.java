package com.halfplatepoha.jisho.v2.injection.modules.views;

import android.app.Activity;

import com.halfplatepoha.jisho.base.BaseActivityModule;
import com.halfplatepoha.jisho.v2.injection.ActivityScope;
import com.halfplatepoha.jisho.v2.sentences.SentencesActivity;
import com.halfplatepoha.jisho.v2.sentences.SentencesContract;
import com.halfplatepoha.jisho.v2.sentences.SentencesPresenter;
import com.halfplatepoha.jisho.v2.detail.adapters.SentenceAdapter;
import com.halfplatepoha.jisho.v2.detail.adapters.SentenceAdapterContract;
import com.halfplatepoha.jisho.v2.detail.adapters.SentenceAdapterPresenter;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by surjo on 09/01/18.
 */

@Module(includes = BaseActivityModule.class)
public abstract class SentencesModule {

    @Binds
    @ActivityScope
    abstract Activity activity(SentencesActivity activity);

    @Binds
    @ActivityScope
    abstract SentencesContract.View view(SentencesActivity activity);

    @Binds
    @ActivityScope
    abstract SentencesContract.Presenter presenter(SentencesPresenter presenter);

    @Named(SentencesActivity.KEY_JAPANESE)
    @Provides
    @ActivityScope
    static String japanese(SentencesActivity activity) {
        return activity.getIntent().getStringExtra(SentencesActivity.KEY_JAPANESE);
    }

    @Named(SentencesActivity.KEY_FURIGANA)
    @Provides
    @ActivityScope
    static String furigana(SentencesActivity activity) {
        return activity.getIntent().getStringExtra(SentencesActivity.KEY_FURIGANA);
    }

    @Provides
    @ActivityScope
    static SentenceAdapter sentenceAdapter(SentenceAdapterContract.Presenter presenter) {
        return new SentenceAdapter(presenter);
    }

    @Binds
    @ActivityScope
    abstract SentenceAdapterContract.Presenter sentenceAdapterContract(SentenceAdapterPresenter sentenceAdapterPresenter);

}
