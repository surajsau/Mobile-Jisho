package com.halfplatepoha.jisho.injection.modules;

import android.app.Activity;

import com.halfplatepoha.jisho.base.BaseActivityModule;
import com.halfplatepoha.jisho.injection.ActivityScope;
import com.halfplatepoha.jisho.injection.DialogScope;
import com.halfplatepoha.jisho.injection.modules.views.KanjiDetailModule;
import com.halfplatepoha.jisho.kanjidetail.KanjiDetailFragment;
import com.halfplatepoha.jisho.settings.SettingsActivity;
import com.halfplatepoha.jisho.settings.SettingsContract;
import com.halfplatepoha.jisho.settings.SettingsPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by surjo on 20/12/17.
 */

@Module(includes = BaseActivityModule.class)
public abstract class SettingsModule {

    @Binds
    @ActivityScope
    abstract Activity activity(SettingsActivity activity);

    @Binds
    @ActivityScope
    abstract SettingsContract.View view(SettingsActivity activity);

    @Binds
    @ActivityScope
    abstract SettingsContract.Presenter presenter(SettingsPresenter presenter);

    @ContributesAndroidInjector(modules = {KanjiDetailModule.class})
    @DialogScope
    abstract KanjiDetailFragment kanjiDetailFragment();

}
